/**
 * 
 */
package com.igloosec.fury.profile.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: 
* 설       명: 
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 11.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
public class Doc {
	private String risk;
	@JsonProperty("d_ip") private String dIp;
	private int hourcount;
	@JsonProperty("s_port") private int sPort;
	@JsonProperty("event_time") private String eventTime;
	private String attack;
	private String mprotocol;
	@JsonProperty("d_port") private int dPort;
	private String note;
	@JsonProperty("s_ip") private String sIp;
	@JsonProperty("origin_id") private String originId;
	@JsonProperty("mgr_time") private String mgrTime;
	@JsonProperty("origin_name") private String originName;
	@JsonProperty("origin") private String origin;
	@JsonProperty("mgr_ip") private String mgrIp;
	
	@JsonProperty("inst_id") private String instId;
	private String category;
	@JsonProperty("s_info") private String sInfo;
	@JsonProperty("d_info") private String dInfo;
	private String log;
	private String sublog;
	
	private int status;
	private int protocol;
	@JsonProperty("s_country") private String sCountry;
	@JsonProperty("d_country") private String dCountry;
	private String method;
	private String product;
	private String action;
	private String logtype;
	@JsonProperty("RAW") private String raw;
	private String id;
	
	public Doc() {
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getdIp() {
		return dIp;
	}

	public void setdIp(String dIp) {
		this.dIp = dIp;
	}

	public int getHourcount() {
		return hourcount;
	}

	public void setHourcount(int hourcount) {
		this.hourcount = hourcount;
	}

	public int getsPort() {
		return sPort;
	}

	public void setsPort(int sPort) {
		this.sPort = sPort;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getAttack() {
		return attack;
	}

	public void setAttack(String attack) {
		this.attack = attack;
	}

	public String getMprotocol() {
		return mprotocol;
	}

	public void setMprotocol(String mprotocol) {
		this.mprotocol = mprotocol;
	}

	public int getdPort() {
		return dPort;
	}

	public void setdPort(int dPort) {
		this.dPort = dPort;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getsIp() {
		return sIp;
	}

	public void setsIp(String sIp) {
		this.sIp = sIp;
	}

	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public String getMgrTime() {
		return mgrTime;
	}

	public void setMgrTime(String mgrTime) {
		this.mgrTime = mgrTime;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getMgrIp() {
		return mgrIp;
	}

	public void setMgrIp(String mgrIp) {
		this.mgrIp = mgrIp;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getsInfo() {
		return sInfo;
	}

	public void setsInfo(String sInfo) {
		this.sInfo = sInfo;
	}

	public String getdInfo() {
		return dInfo;
	}

	public void setdInfo(String dInfo) {
		this.dInfo = dInfo;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getSublog() {
		return sublog;
	}

	public void setSublog(String sublog) {
		this.sublog = sublog;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	public String getsCountry() {
		return sCountry;
	}

	public void setsCountry(String sCountry) {
		this.sCountry = sCountry;
	}

	public String getdCountry() {
		return dCountry;
	}

	public void setdCountry(String dCountry) {
		this.dCountry = dCountry;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
