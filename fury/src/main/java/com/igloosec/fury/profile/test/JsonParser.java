/**
 * 
 */
package com.igloosec.fury.profile.test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igloosec.fury.profile.test.vo.TestData;

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
public class JsonParser {
	/*public static void main(String[] args) {
		//TestData td = new JsonParser().getTestData();
		//System.out.println(td.getResponse().getDocs().size());
		
		Random random = new Random();    
		int[] random_num = new int[3];
		for (int i = 0; i < random_num.length; i++) {
			random_num[i] = random.nextInt(100 + 1) + 1;
		}
		for (int num :random_num)
			System.out.println(num);
	}*/
	
	public TestData getTestData(){
		TestData td = null;
		ObjectMapper om = new ObjectMapper(new JsonFactory());
		try {
			td = om.readValue(new File("/home/k/git/fury/fury/test/test_data.json"), TestData.class);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return td;
	}
}
