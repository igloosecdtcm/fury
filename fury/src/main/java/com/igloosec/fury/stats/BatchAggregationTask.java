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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.igloosec.fury.stats.vo.BatchJobinfo;
import com.igloosec.fury.stats.vo.FuryConfig;

/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: job의 주기에 따라 일괄 파싱, 집계, 저장하는 메소드
* 설       명: db에서 job을 불러, yaml 파일로 job을 등록, r 스크립트가 실행되면서 job 정보를 파싱, 집계, 저장한다.
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 2.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
public class BatchAggregationTask extends TimerTask{
	private final Logger logger =  LoggerFactory.getLogger(BatchAggregationTask.class);

	/** job 정보 */
	private List<BatchJobinfo> jobs = new ArrayList<>();
	
	private StatsService statsService;
	private FuryConfig furyConfig;
	
	private int jobMinutes = 0;

	public BatchAggregationTask(StatsService statsService, FuryConfig furyConfig) {
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
	 * db에서 job 정보를 가져와 jobs에 추가한다.
	 * 지금은 db연동이 안되있음.
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void getJobs(){
		//db에서 job 정보 가져온다.
		/*if(jobs.isEmpty()) {
			List<Map<String, Object>> jobs = statsService.getDBHandler().getNColumnList("logger", 
					"select "
							+ "a.id as id, a.title as title, a.schedule as schedule, a.match as match, a.function as function, "
							+ "a.groupBy, a.having, a.limit, a.type "
					+ "from "
							+ "STATS_JOB_CONF a, STATS_BATCH_JOB_CONF b "
					+ "where "
							+ "a.id = b.job_id "
					+ "and "
							+ "b.BATCH_COUNT > 0"
			);
			logger.info("job size - ",jobs);
			for (Map<String, Object> job :jobs){
				int id = Integer.parseInt(job.get("id").toString());
				String title = job.get("title").toString();
				int schedule = Integer.parseInt(job.get("schedule").toString());
				String match = job.get("match").toString();
				String function = job.get("function").toString();
				String groupBy = job.get("groupBy").toString();
				String having = job.get("having").toString();
				int limit = Integer.parseInt(job.get("limit").toString());
				char type = (char) job.get("type");
				int startDate = Integer.parseInt(job.get("startDate").toString());
				int endDate = Integer.parseInt(job.get("endDate").toString());
				int batchId = Integer.parseInt(job.get("batchId").toString());
				int jobId = Integer.parseInt(job.get("jobId").toString());
				int startTime = Integer.parseInt(job.get("startTime").toString());
				int batchCount = Integer.parseInt(job.get("batchCount").toString());
				
				this.jobs.add(new BatchJobinfo(id, title, schedule, match, function, groupBy, having, limit, 
						type, startDate, endDate, batchId, jobId, startTime, batchCount));
			}
		}*/
		
		/* db 연동 전, 로컬 테스트 */
		List<BatchJobinfo> batchJobs = new ArrayList<>();
		
		try {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, BatchJobinfo.class);
			batchJobs = mapper.readValue(new File("./R/batch_test_jobs.yaml"), collectionType);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	    
		for (BatchJobinfo job :batchJobs){
			if (job.getType() == 'P' && jobMinutes >= job.getSchedule() && jobMinutes%job.getSchedule() == 0){
			//if (job.getBatchCount() > 0 && jobMinutes >= job.getSchedule() && jobMinutes%job.getSchedule() == 0){
				if (job.getSchedule() < 10){
					for (int i = 1; i <= 60; i+=job.getSchedule()){
						setJobs(job, i);
					}
				} else {
					for (int i = 1; i <= 1440; i+=job.getSchedule()){
						setJobs(job, i);
					}
				}
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
	 * job정보와 스케쥴정보로 batch 작업할 job 리스트를 만든다.
	 * @param job : 잡 정보, idx : 루프 인덱스
	 * @return void
	 * @exception    
	******************************************************/ 
	private void setJobs(BatchJobinfo job, int idx){
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.SECOND, -((60*job.getSchedule()) + (60*(idx+job.getSchedule())-60)));
		job.setStartDate(date.getTimeInMillis() / 1000);
		date.set(Calendar.SECOND, (60*job.getSchedule()));
		job.setEndDate(date.getTimeInMillis() / 1000);

		this.jobs.add(new BatchJobinfo(job.getId(), job.getTitle(), job.getSchedule(), job.getMatch(), job.getFunction(), 
				job.getGroupBy(), job.getHaving(), job.getLimit(), job.getType(), job.getStartDate(), job.getEndDate(), 0, 0, 0, 0));
	}
	/***************************************************** 
	 * jobs에 있는 job 정보를 jobs.yaml 파일 생성
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void printJobs(){
		if (!this.jobs.isEmpty() && this.jobs.size() > 0){
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			try {
				logger.info("realtime job path : " + furyConfig.getBatchJobPath());
				mapper.writeValue(new File(furyConfig.getBatchJobPath()), this.jobs);
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
	 * jobs 리스트 초기화
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void resetJobs(){
		this.jobs = new ArrayList<>();
	}
	/***************************************************** 
	 * printJobs 메소드에서 만들어진 jobs.yaml 파일을 읽고, batch 스크립트 파일 실행한다.
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void runAggregation() {
		Process proc = null;
		ProcessBuilder pb = null;
		
		try {
			
			long startTime = new Date().getTime();
			
			String rscript = furyConfig.getBatchScriptPath();
			File rscriptFile = new File(rscript);
			if (rscriptFile.exists()) {
				Date date = new Date();
				logger.info("PID=RTS Status=Start Result=  Time=" + date + " Cmd=" +rscript);
				
				pb = new ProcessBuilder("/usr/bin/Rscript", rscript);
				File logFile = new File(furyConfig.getBatchLogPath());
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
	/***************************************************** 
	 * 인덱싱을 완료한 job정보 파일 삭제
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void removeJobs() {
		if (furyConfig.getBatchJobPath()!= null) {
			File fileName = null;
			fileName = new File(furyConfig.getBatchJobPath());
			fileName.delete();
		}
	}
}
