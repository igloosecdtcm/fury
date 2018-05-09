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
public class ResponseHeader {

	private int status;
	@JsonProperty("QTime") private int qTime;
	private int minDate;
	private int maxDate;
	private Params params;
	
	public ResponseHeader() {
		// TODO Auto-generated constructor stub
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getqTime() {
		return qTime;
	}

	public void setqTime(int qTime) {
		this.qTime = qTime;
	}

	public int getMinDate() {
		return minDate;
	}

	public void setMinDate(int minDate) {
		this.minDate = minDate;
	}

	public int getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(int maxDate) {
		this.maxDate = maxDate;
	}

	public Params getParams() {
		return params;
	}

	public void setParams(Params params) {
		this.params = params;
	}
	
}
