/**
 * 
 */
package com.igloosec.fury.stats.vo;

/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: 
* 설       명: 
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 8.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
public class BatchJobinfo extends JobInfo{
	/** batch Job ID : pk */
	private int batchId;
	/** job id : stats_job_conf.fk */
	private int jobId;
	/** 시작 시간 : stats job start time */
	private long startTime;
	/** 
		batch job 실행 횟수 : 최초: 1m = 60 / 2m = 30
		Batch 1회 실행 후: n-1 연산
		0 = Batch Job End 
	*/
	private int batchCount;
	
	/***************************************************** 
	 *
	******************************************************/
	public BatchJobinfo() {
		// TODO Auto-generated constructor stub
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}

	public BatchJobinfo(int id, String title, int schedule, String match, String function, String groupBy,
			String having, int limit, char type, long startDate, long endDate, int batchId, int jobId, long startTime,
			int batchCount) {
		super(id, title, schedule, match, function, groupBy, having, limit, type, startDate, endDate);
		this.batchId = batchId;
		this.jobId = jobId;
		this.startTime = startTime;
		this.batchCount = batchCount;
	}

}
