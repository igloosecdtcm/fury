/**
 * 
 */
package com.igloosec.fury.profile;

import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.TimerTask;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igloosec.fury.profile.test.JsonParser;
import com.igloosec.fury.profile.test.vo.Doc;
import com.igloosec.fury.profile.test.vo.TestData;

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
public class KafkaClientProducer extends TimerTask{
	private final Logger logger = LoggerFactory.getLogger(KafkaClientProducer.class);

	/***************************************************** 
	 * 메소드 설명
	 * @see java.util.TimerTask#run()
	******************************************************/ 
	@Override
	public void run() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND, 0);
		String time = Long.toString(date.getTimeInMillis() / 1000);
		TestData testData = new JsonParser().getTestData();
		Properties props = new Properties();
		ObjectMapper mapper = new ObjectMapper();
		Random random = new Random();    
		int[] random_num = new int[3];
		for (int i = 0; i < random_num.length; i++) {
			random_num[i] = random.nextInt(100 + 1) + 1;
		}
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384); 
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		Producer<String, String> producer = new KafkaProducer<>(props);

		for (int num : random_num) {
			ProducerRecord<String, String> pr = null;
			try {
				pr = new ProducerRecord<String, String>("my-topic", time, mapper.writeValueAsString(testData.getResponse().getDocs().get(num)));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (pr != null)
				producer.send(pr);
		}

		producer.close();
	}
	public static void main(String[] args) {
		new KafkaClientProducer().run();
	}
}
