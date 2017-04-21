package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class UserDao {
	public static void connect(){
		 ComboPooledDataSource dataSource = new ComboPooledDataSource("sso");
//		 dataSource.get
		 try {
			Connection con = dataSource.getConnection();
			PreparedStatement s = con.prepareStatement("select * from t_user");
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(2));
			}
			System.out.println(con.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public static void main(String[] args) {
		connect();
	}
}
