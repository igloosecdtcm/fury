/**
 * 
 */
package com.igloosec.fury.profile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.igloosec.fury.stats.vo.FuryConfig;

/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: 
* 설       명: profile 스케쥴러 서비스
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 9.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
@Component(name="profile-service")
@Provides
public class ProfileService {
	private final Logger logger = LoggerFactory.getLogger(ProfileService.class);

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
		new ProfileService().start();
	}

	/***************************************************** 
	 * osgi에서 @Component 어노테이션을 읽어 실행하는 메소드
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	@Validate
	private void start() {
		logger.info("ProfileService start");
		
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
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		// test
		timer = new Timer("KafkaClientProducer");
		timer.scheduleAtFixedRate(new KafkaClientProducer(), date.getTime(), 1000 * 3);
		
		timer = new Timer("KafkaClientConsumer");
		timer.scheduleAtFixedRate(new KafkaClientConsumer(furyConfig), date.getTime(), 1000 * 10);
	}
	/***************************************************** 
	 * osgi에서 stop 명령에 실행되는 메소드
	 * @param 
	 * @return void
	 * @exception    
	******************************************************/ 
	@Invalidate
	private void stop() {
		logger.info("ProfileService stop");
		timer.cancel();
		timer = null;
		
	}
}
