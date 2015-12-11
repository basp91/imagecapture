package com.doralba.utils;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DAO {
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private static String DRIVER;
	private static String HOST;
	private static String PORT;
	private static String USERNAME;
	private static String PASSWORD;


	public DAO() throws IOException {
		java.io.InputStream inputstream = this.getClass().getResourceAsStream("/db.properties");
		java.util.Properties properties = new Properties();
		properties.load(inputstream);

		DRIVER = properties.getProperty("db.driver");
		HOST = properties.getProperty("db.host");
		PORT = properties.getProperty("db.port");
		USERNAME = properties.getProperty("db.username");
		PASSWORD = properties.getProperty("db.password");
		System.out.println(DRIVER+HOST+PORT+USERNAME+PASSWORD);

	}

	public void read() throws Exception {
		try {
			//Class.forName(DRIVER);
			//connection = DriverManager.getConnection(URL+"?user="+USERNAME+"&password="+PASSWORD);
			//statement = connection.createStatement();
			System.out.println("Conexión exitosa");
		} catch(Exception e) {
			throw e;
		} finally{
			close();
		}
	}

	public void write(ResultSet result) throws SQLException {
		while (result.next()){

		}
	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {

		}
	}
}
