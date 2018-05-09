/**
 * 
 */
package com.igloosec.fury.db.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*************************************************** 
 * <pre> 
* 업무 그룹명: Fury
* 서브 업무명: 
* 설       명: 
* 작   성  자: 이선구 [devleesk@igloosec.com]
* 작   성  일: 2018. 5. 10.
* Copyright ⓒIGLOO SEC. All Right Reserved
 * </pre> 
 ***************************************************/
public class JdbcConnect {

	private Connection con;
	private Statement stat;
	private ResultSet rs;
	
	private String driverName = "oracle.jdbc.driver";
	private String url = "jdbc:oracle:thin:@192.168.150.81:1521:orcl";
	private String user = "tm_admin";
	private String password = "tm_admin";
	
	public JdbcConnect() {
		try {
			Class.forName(driverName);
		} 
		catch (ClassNotFoundException e){
			System.out.println("ClassNotFoundException - " + e.getMessage());
		}
	}
	public void closeDb() {
		try {
			if (con != null) {
				con.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (rs != null) {
				rs.close();
			}
		} 
		catch (SQLException e) {
			System.out.println("SQLException - " + e.getMessage());
		}
	}
	public List<Map<String, Object>> select(String sql){
		List<Map<String, Object>> map_data = new LinkedList<Map<String, Object>>();
		Map<String, Object> row = null;
		int colCnt = 0;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			stat = con.createStatement();
			this.rs = stat.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			colCnt = rsmd.getColumnCount();

			while (rs.next()) {
				row = null;
				row = new HashMap<String, Object>();

				for (int i = 1; i <= colCnt; i++) {
					row.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(rsmd.getColumnName(i)));
				}
				map_data.add(row);
			}
		} 
		catch (SQLException e) {
			System.out.println("SQLException - " + e.getMessage());
		} 
		finally {
			closeDb();
		}
		return map_data;
	}
}
