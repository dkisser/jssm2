package org.lf.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	public static Connection getConnection() {
		Connection cnn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			cnn = DriverManager.getConnection("jdbc:oracle:thin:@125.220.70.84:1521:prod", "xqw", "xqw");
			cnn.setAutoCommit(false); // 关闭自动提交sql语句到数据库
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cnn;
	}
}
