/**
 * 
 */
package com.igloosec.fury.profile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igloosec.fury.stats.vo.FuryConfig;



/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: 
* 설       명: 
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 9.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
public class KafkaClientConsumer extends TimerTask{
	private final Logger logger = LoggerFactory.getLogger(KafkaClientConsumer.class);
	
	private FuryConfig furyConfig;
	private Properties props;
	private KafkaConsumer<String, String> consumer;
	private ConsumerRecords<String, String> records;
	
	
	public KafkaClientConsumer(FuryConfig furyConfig) {
		this.furyConfig = furyConfig;
		setProperties();
	}
	public void setProperties(){
		this.props = new Properties();
		this.props.put("bootstrap.servers", furyConfig.getKafkaIp() + ":" + furyConfig.getKafkaPort());
		this.props.put("group.id", furyConfig.getTopic());
		this.props.put("enable.auto.commit", "true");
		this.props.put("auto.commit.interval.ms", "1000");
		this.props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		this.props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	}

	/***************************************************** 
	 * 메소드 설명
	 * @see java.util.TimerTask#run()
	******************************************************/
	@Override
	public void run() {
		getRecords();
	}
	public void getRecords(){
		try {
			this.consumer = new KafkaConsumer<>(this.props);
			this.consumer.subscribe(Arrays.asList(this.furyConfig.getTopic()));
			this.records = this.consumer.poll(1000); // 대기 시간!!
		} 
		finally {
			this.consumer.close();
		}	
		recordParser();
	}
	public void recordParser(){
		StringBuffer sb = new StringBuffer();
		List<String> tsvStr = new ArrayList<>();
		for (ConsumerRecord<String, String> record : this.records) {
				ObjectMapper mapper = new ObjectMapper(new JsonFactory());
				try {
					Map<String, Object> doc = mapper.readValue(record.value(), Map.class);
					tsvStr.add(makeTsvString(record.key(), doc).toString());
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Map<String, Object> m = mapper.reader()
		}
		
		makeTsv(tsvStr);
	}
	/***************************************************** 
	 * 메소드 설명
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	private void makeTsv(List<String> tsvStr) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND, 0);
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(furyConfig.getProfileTsvDirPath() + "/" + Long.toString(date.getTimeInMillis() / 1000) + ".tsv", true));
			
			for (String str : tsvStr) {
				fw.write(str);
				fw.newLine();
			}
			
			fw.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		runScript();
	}
	public StringBuffer makeTsvString(String fileName, Map<String, Object> doc){
		StringBuffer resultStr = new StringBuffer();
		int idx = 0;
		for (String key : doc.keySet()) {
			if (idx == 0){
				resultStr.append(key).append("=").append(doc.get(key));
			} 
			else {
				resultStr.append("\t").append(key).append("=").append(doc.get(key));
			}
			idx++;
		}
		return resultStr;
	}
	
	public void runScript(){
		Process proc = null;
		ProcessBuilder pb = null;
		
		try {
			
			long startTime = new Date().getTime();
			
			String rscript = furyConfig.getProfileScriptPath();
			File rscriptFile = new File(rscript);
			if (rscriptFile.exists()) {
				Date date = new Date();
				logger.info("PID=RTS Status=Start Result=  Time=" + date + " Cmd=" +rscript);
				
				pb = new ProcessBuilder("/usr/bin/Rscript", rscript);
				File logFile = new File(furyConfig.getProfileLogPath());
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
	private void removeJobs() {
		if (furyConfig.getProfileTsvDirPath() != null) {
			File[] fileNameList = new File(furyConfig.getProfileTsvDirPath()).listFiles();
			for(File fileName : fileNameList){
				fileName.delete();
			}
		}
	}
}
