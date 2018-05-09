/**
 * 
 */
package com.igloosec.fury.stats;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.igloosec.fury.stats.vo.FuryConfig;
import com.igloosec.jdbc.service.DBHandler;

/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: 
* 설       명: stats 스케쥴러 서비스
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 2.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
@Component(name="stats-service")
@Provides
public class StatsService{
	private final Logger logger = LoggerFactory.getLogger(StatsService.class);

	/** 스케쥴러에서 사용할 디비핸틀 */
	@Requires
	DBHandler dbHandler;
	
	/** 스케쥴 */
	private Timer timer;
	
	private FuryConfig furyConfig;
	
	/***************************************************** 
	 * 로컬 테스트를 하기 위한 메소드
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	public static void main(String[] args) {
		new StatsService().start();
	}
	
	/***************************************************** 
	 * osgi에서 @Component 어노테이션을 읽어 실행하는 메소드
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	@Validate
	private void start() {
		logger.info("StatsService start");

		Calendar date = Calendar.getInstance();
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		try {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			furyConfig = mapper.readValue(new File("./config/config.yaml"), FuryConfig.class);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		// 1분단위로 실시간 집계
		timer = new Timer("RealtimeAggregationTask");
		timer.scheduleAtFixedRate(new RealtimeAggregationTask(this, furyConfig), date.getTime(), 1000 * 60);
		// Batch 집계 - 매 시간 정각
		timer = new Timer("BatchAggregationTask");
		timer.scheduleAtFixedRate(new BatchAggregationTask(this, furyConfig), date.getTime(), 1000 * 60);
	}
	/***************************************************** 
	 * osgi에서 stop 명령에 실행되는 메소드
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	@Invalidate
	private void stop() {
		logger.info("StatsService stop");
		timer.cancel();
		timer = null;
	}
	/***************************************************** 
	 * 메소드 설명
	 * @see com.igloosec.realtime.RealtimeStatsService#getDBHandler()
	******************************************************/ 
	public DBHandler getDBHandler() {
		return this.dbHandler;
	}
	
}
