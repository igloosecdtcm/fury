/**
 * 
 */
package com.igloosec.fury.stats.vo;

/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: 
* 설       명: FuryConfig 정보
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 2.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
public class FuryConfig {
	/** valkyrie info */
	private String valkyrieIp;
	private int valkyriePort;
	private String valkyrieIndex;
	private String valkyrieIndexType;
	
	/** aggregation info */
	private String aggregationIp;
	private int aggregationPort;
	private String aggregationIndex;
	private String aggregationIndexType;

	/** realtime state info */
	private String realtimeScriptPath;
	private String realtimeJobPath;
	private String realtimeLogPath;
	private int realtimeIndexingSleep;
	
	/** batch state info */
	private String batchScriptPath;
	private String batchJobPath;
	private String batchLogPath;
	private int batchIndexingSleep;
	
	/** kafka info */
	private String kafkaIp;
	private String kafkaPort;
	private String topic;
	
	public String getValkyrieIp() {
		return valkyrieIp;
	}

	public void setValkyrieIp(String valkyrieIp) {
		this.valkyrieIp = valkyrieIp;
	}

	public int getValkyriePort() {
		return valkyriePort;
	}

	public void setValkyriePort(int valkyriePort) {
		this.valkyriePort = valkyriePort;
	}

	public String getValkyrieIndex() {
		return valkyrieIndex;
	}

	public void setValkyrieIndex(String valkyrieIndex) {
		this.valkyrieIndex = valkyrieIndex;
	}

	public String getValkyrieIndexType() {
		return valkyrieIndexType;
	}

	public void setValkyrieIndexType(String valkyrieIndexType) {
		this.valkyrieIndexType = valkyrieIndexType;
	}

	public String getAggregationIp() {
		return aggregationIp;
	}

	public void setAggregationIp(String aggregationIp) {
		this.aggregationIp = aggregationIp;
	}

	public int getAggregationPort() {
		return aggregationPort;
	}

	public void setAggregationPort(int aggregationPort) {
		this.aggregationPort = aggregationPort;
	}

	public String getAggregationIndex() {
		return aggregationIndex;
	}

	public void setAggregationIndex(String aggregationIndex) {
		this.aggregationIndex = aggregationIndex;
	}

	public String getAggregationIndexType() {
		return aggregationIndexType;
	}

	public void setAggregationIndexType(String aggregationIndexType) {
		this.aggregationIndexType = aggregationIndexType;
	}

	public String getRealtimeScriptPath() {
		return realtimeScriptPath;
	}

	public void setRealtimeScriptPath(String realtimeScriptPath) {
		this.realtimeScriptPath = realtimeScriptPath;
	}

	public String getRealtimeJobPath() {
		return realtimeJobPath;
	}

	public void setRealtimeJobPath(String realtimeJobPath) {
		this.realtimeJobPath = realtimeJobPath;
	}

	public String getRealtimeLogPath() {
		return realtimeLogPath;
	}

	public void setRealtimeLogPath(String realtimeLogPath) {
		this.realtimeLogPath = realtimeLogPath;
	}

	public int getRealtimeIndexingSleep() {
		return realtimeIndexingSleep;
	}

	public void setRealtimeIndexingSleep(int realtimeIndexingSleep) {
		this.realtimeIndexingSleep = realtimeIndexingSleep;
	}

	public String getBatchScriptPath() {
		return batchScriptPath;
	}

	public void setBatchScriptPath(String batchScriptPath) {
		this.batchScriptPath = batchScriptPath;
	}

	public String getBatchJobPath() {
		return batchJobPath;
	}

	public void setBatchJobPath(String batchJobPath) {
		this.batchJobPath = batchJobPath;
	}

	public String getBatchLogPath() {
		return batchLogPath;
	}

	public void setBatchLogPath(String batchLogPath) {
		this.batchLogPath = batchLogPath;
	}

	public int getBatchIndexingSleep() {
		return batchIndexingSleep;
	}

	public void setBatchIndexingSleep(int batchIndexingSleep) {
		this.batchIndexingSleep = batchIndexingSleep;
	}

	public String getKafkaIp() {
		return kafkaIp;
	}

	public void setKafkaIp(String kafkaIp) {
		this.kafkaIp = kafkaIp;
	}

	public String getKafkaPort() {
		return kafkaPort;
	}

	public void setKafkaPort(String kafkaPort) {
		this.kafkaPort = kafkaPort;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
