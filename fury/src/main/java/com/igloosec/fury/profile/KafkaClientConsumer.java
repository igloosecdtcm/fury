/**
 * 
 */
package com.igloosec.fury.profile;

import java.util.Arrays;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

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

	private FuryConfig furyConfig;

	public KafkaClientConsumer(FuryConfig furyConfig) {
		this.furyConfig = furyConfig;
	}
	
	/***************************************************** 
	 * 메소드 설명
	 * @see java.util.TimerTask#run()
	******************************************************/
	@Override
	public void run() {
		Properties props = new Properties();
		props.put("bootstrap.servers", furyConfig.getKafkaIp() + ":" + furyConfig.getKafkaPort());
		props.put("group.id", "my-topic");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
     	ConsumerRecords<String, String> records = consumer.poll(1000); // 대기 시간!!
     	for (ConsumerRecord<String, String> record : records)
 			System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
     	consumer.close();
	}
}
