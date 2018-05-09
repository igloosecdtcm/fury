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
public class Params {
	
	@JsonProperty("output.separate") private String output_separate;
	private boolean indent;
	private String q;
	@JsonProperty("client.id") private String client_id;
	private String ranges;
	private String collection;
	private boolean output;
	@JsonProperty("output.type") private String output_type;
	private String wt;
	private String rows;
	
	public Params() {
		// TODO Auto-generated constructor stub
	}

	public String getOutput_separate() {
		return output_separate;
	}

	public void setOutput_separate(String output_separate) {
		this.output_separate = output_separate;
	}

	public boolean isIndent() {
		return indent;
	}

	public void setIndent(boolean indent) {
		this.indent = indent;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getRanges() {
		return ranges;
	}

	public void setRanges(String ranges) {
		this.ranges = ranges;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public boolean isOutput() {
		return output;
	}

	public void setOutput(boolean output) {
		this.output = output;
	}

	public String getOutput_type() {
		return output_type;
	}

	public void setOutput_type(String output_type) {
		this.output_type = output_type;
	}

	public String getWt() {
		return wt;
	}

	public void setWt(String wt) {
		this.wt = wt;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
	
}
