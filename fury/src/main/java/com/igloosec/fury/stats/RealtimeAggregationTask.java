/**
 * 
 */
package com.igloosec.fury.stats;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.igloosec.fury.db.manager.JdbcConnect;
import com.igloosec.fury.stats.vo.FuryConfig;
import com.igloosec.fury.stats.vo.JobInfo;

/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: 1분단위로 파싱, 집계, 저장하는 메소드
* 설       명: db에서 job을 불러, yaml 파일로 job을 등록, r 스크립트가 실행되면서 job 정보를 파싱, 집계, 저장한다.
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 2.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
public class RealtimeAggregationTask extends TimerTask {
	private final Logger logger =  LoggerFactory.getLogger(RealtimeAggregationTask.class);

	/** job 정보 */
	private List<JobInfo> jobs = new ArrayList<>();
	private StatsService statsService;
	private FuryConfig furyConfig;
	private int jobMinutes = 0;
	
	public RealtimeAggregationTask(StatsService statsService, FuryConfig furyConfig) {
		this.jobMinutes = Calendar.getInstance().getTime().getMinutes();
		this.statsService = statsService;
		this.furyConfig = furyConfig;	
	}
	
	/***************************************************** 
	 * TimerTask 스케쥴러가 실행되면, run 메소드를 실행
	 * @see java.util.TimerTask#run()
	******************************************************/ 
	@Override
	public void run() { 
		getJobs();
	}
	/***************************************************** 
	 * jobs 리스트 초기화
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void resetJobs(){
		this.jobs = new ArrayList<>();
	}

	/***************************************************** 
	 * 인덱싱을 완료한 job정보 파일 삭제
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void removeJobs() {
		if (furyConfig.getRealtimeJobPath() != null) {
			File fileName = null;
			fileName = new File(furyConfig.getRealtimeJobPath());
			fileName.delete();
		}
	}
	
	/***************************************************** 
	 * jobs에 있는 job 정보를 jobs.yaml 파일 생성
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void printJobs(){
		if (!this.jobs.isEmpty()){
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			try {
				logger.info("realtime job path : " + furyConfig.getRealtimeJobPath());
				mapper.writeValue(new File(furyConfig.getRealtimeJobPath()), this.jobs);
			} catch (JsonGenerationException e1) {
				logger.error(e1.getMessage(), e1);
			} catch (JsonMappingException e1) {
				logger.error(e1.getMessage(), e1);
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
			} finally {
				if (this.jobs != null) {
					resetJobs();
					runAggregation();
				}
			}
		}
	}
	/***************************************************** 
	 * db에서 job 정보를 가져와 jobs에 추가한다.
	 * 지금은 db연동이 안되있음.
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void getJobs(){
		JdbcConnect jc = new JdbcConnect();
		List<JobInfo> realtimeJobs = new ArrayList<>();
		
		//select 1 as "id", '커스텀 job1' as "title", 1 as "schedule", 'category == '||chr(39)||'E007'||chr(39)' & !method %in% c(\"null\",\"-\")' as "filter", 'count' as "function", 's_info.keyword' as "groupBy", '?' sd "having", 1 as "limit", 'asc' as "sort", 's_info.keyword' as "filedset", 'S' as "type", 'aggs' as "index", 'my_topic' as "topic" from dual;
		String sql = "select 1 as \"id\", '커스텀 job1' as \"title\", 1 as \"schedule\", "
				+ "'category == '||chr(39)||'E007'||chr(39)||' & !method %in% c(\"null\",\"-\")' as \"filter\", 'count' as "
				+ "\"function\", 's_info.keyword' as \"groupBy\", '?' as \"having\", 1 as \"limit\", 'asc' as \"sort\", "
				+ "'s_info.keyword' as \"filedset\", 'S' as \"type\", 'aggs' as \"index\", 'my_topic' as \"topic\" from dual";
		List<Map<String, Object>> jobs = jc.select(sql);

		if(jobs.isEmpty()) {
			for (Map<String, Object> job :jobs){
				Calendar endDate = Calendar.getInstance();
				endDate.set(Calendar.MILLISECOND, 0);
				endDate.set(Calendar.SECOND, 0);
				int id = Integer.parseInt(job.get("id").toString());
				String title = job.get("title").toString();
				int schedule = Integer.parseInt(job.get("schedule").toString());
				String match = job.get("match").toString();
				String function = job.get("function").toString();
				String groupBy = job.get("groupBy").toString();
				String having = job.get("having").toString();
				int limit = Integer.parseInt(job.get("limit").toString());
				char type = (char) job.get("type");
				realtimeJobs.add(new JobInfo(id, title, schedule, match, function, groupBy, having, limit, type, endDate.getTimeInMillis() / 1000, endDate.getTimeInMillis() / 1000));
			}
		}
		
		for (JobInfo job :realtimeJobs){
			if(jobMinutes >= job.getSchedule() && jobMinutes%job.getSchedule() == 0) {
				Calendar startDate = Calendar.getInstance();
				startDate.set(Calendar.MILLISECOND, 0);
				startDate.set(Calendar.SECOND, 0);
				startDate.set(Calendar.SECOND, (-60*job.getSchedule()));
				job.setStartDate(startDate.getTimeInMillis() / 1000);
				this.jobs.add(job);
			}
		}
		
		if (jobMinutes < 60) {
			jobMinutes++;
		} else {
			jobMinutes = 0;
		}
		
		logger.info("job size : " + this.jobs.size());
		printJobs();
	}
	
	/***************************************************** 
	 * printJobs 메소드에서 만들어진 jobs.yaml 파일을 읽고, realtime 스크립트 실행한다.
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void runAggregation() {
		Process proc = null;
		ProcessBuilder pb = null;
		
		try {
			
			long startTime = new Date().getTime();
			
			String rscript = furyConfig.getRealtimeScriptPath();
			File rscriptFile = new File(rscript);
			if (rscriptFile.exists()) {
				Date date = new Date();
				logger.info("PID=RTS Status=Start Result=  Time=" + date + " Cmd=" +rscript);
				
				pb = new ProcessBuilder("/usr/bin/Rscript", rscript);
				File logFile = new File(furyConfig.getRealtimeLogPath());
				pb.redirectErrorStream(true);
				pb.redirectOutput(Redirect.appendTo(logFile));
				proc = pb.start();
				
				int exitValue = proc.waitFor();
				if (exitValue == 0) {
					logger.debug("PID=RTS Status=End Result=Success ExecuteTime=" + 
							(new Date().getTime()-startTime)/1000.0f + " ExitValue="+exitValue);
				}
				else {
					logger.debug("PID=RTS Status=End Result=Fail ExecuteTime=" + 
							(new Date().getTime()-startTime)/1000.0f + " ExitValue="+exitValue);
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (proc != null) {
				proc.destroy();	
			}
			if (pb != null) {
				pb = null;
			}
			removeJobs();
		}
	}
}
