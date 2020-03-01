package com.apms.sample;

import java.sql.*;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;

public class SqlServerConn {

	private Connection conn = null;
	private Statement stmt = null;
	private static String sqlJdbc = "";
	private static String sqlUser = "sa";
	private static String sqlPwd = "";
	private static Class driverClass = null;
	private static ObjectPool connectionPool = null;

	public SqlServerConn() {
		if ("".equals(sqlJdbc)) {
			// jdbc:microsoft:sqlserver://192.168.1.117;DatabaseName=lordeDAS

			sqlJdbc = "jdbc:microsoft:sqlserver://192.168.249.128:1433;DatabaseName=MCCAMSDB";
		}
		if ("".equals(sqlUser)) {
			sqlUser = "sa";
		}
		if ("".equals(sqlPwd)) {
			sqlPwd = "";
		}
		initDataSource();
	}

	public synchronized static void initDataSource() {
		if (driverClass == null) {
			try {
				driverClass = Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		if (connectionPool == null) {
			try {
				setupDriver(sqlJdbc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Display some pool statistics
		try {
			printDriverStats();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取一个连接
	private Connection getDbPollConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:auxdbpoll");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 获得记录集
	public ResultSet executeQuery(String SQL) {
		try {
			stmt = getDbPollConnection().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 执行SQL
	public boolean execute(String SQL) {
		try {
			stmt = getDbPollConnection().createStatement();
			stmt.execute(SQL);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			close();
		}
	}

	// 执行SQL
	public boolean executeUpdate(String SQL) {
		try {
			stmt = getDbPollConnection().createStatement();
			stmt.executeUpdate(SQL);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			close();
		}
	}

	public void close() {
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static void printDriverStats() throws Exception {
		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
		ObjectPool connectionPool = driver.getConnectionPool("auxdbpoll");
		System.out.println("活动的连接: " + connectionPool.getNumActive());
		System.out.println("空闲的连接: " + connectionPool.getNumIdle());
	}

	public static void shutdownDriver() throws Exception {
		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
		driver.closePool("auxdbpoll");
	}

	public static void setupDriver(String connectURI) throws Exception {
		System.out.println(connectURI);
		connectionPool = new GenericObjectPool(null);
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, sqlUser, sqlPwd);
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,
				connectionPool, null, null, false, true);
		Class.forName("org.apache.commons.dbcp.PoolingDriver");
		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
		driver.registerPool("auxdbpoll", connectionPool);
	}

	// 测试
	public void testQuery() {
		String sql = "select * from logintable";
		try {
			ResultSet rs = executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("user_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public static void main(String[] args) {
		SqlServerConn dbConn = new SqlServerConn();
		dbConn.testQuery();
	}
}
