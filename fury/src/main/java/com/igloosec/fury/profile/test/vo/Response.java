/**
 * 
 */
package com.igloosec.fury.profile.test.vo;

import java.util.List;

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
public class Response {
	private String numFound;
	private String start;
	private String maxScore;
	private List<Doc> docs;
	public Response() {
	}
	public String getNumFound() {
		return numFound;
	}
	public void setNumFound(String numFound) {
		this.numFound = numFound;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
	public List<Doc> getDocs() {
		return docs;
	}
	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}
}
