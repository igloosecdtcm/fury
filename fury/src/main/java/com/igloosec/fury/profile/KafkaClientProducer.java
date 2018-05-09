/**
 * 
 */
package com.igloosec.fury.profile;

import java.util.Calendar;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

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

	/***************************************************** 
	 * 메소드 설명
	 * @see java.util.TimerTask#run()
	******************************************************/ 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND, 0);
		String time = Long.toString(date.getTimeInMillis() / 1000);
		String tmpJsonData = 
				"{" + 
						"\"create_date\" : " + time + "," +
						"\"s_info\" : \"192.168.90.99\"," +
						"\"s_port\" : 5678," +
						"\"d_info\" : \"192.168.0.1\"," +
						"\"d_port\" : 1234," +
						"\"protocol\" : \"UDP\"," +
						"\"tos_byte\" : " + 10 + "," +
						"\"method\" : \"GET\"" +
				"}";

		ProducerRecord<String, String> pr = new ProducerRecord<String, String>("my-topic", time, tmpJsonData);
		Properties props = new Properties();
		
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384); 
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		Producer<String, String> producer = new KafkaProducer<>(props);

		producer.send(pr);
		producer.close();
	}
	public static void main(String[] args) {
		new KafkaClientProducer().run();
	}
}
