/**
 * 
 */
package com.igloosec.fury.profile.test.vo;

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
public class TestData {
	private ResponseHeader responseHeader;
	private Response response;
	private Error error;
	public TestData() {
	}
	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
}
