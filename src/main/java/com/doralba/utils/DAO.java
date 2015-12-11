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

	public DAO() {
		
		java.io.InputStream inputstream = this.getClass().getResourceAsStream("/db.properties");
		java.util.Properties properties = new Properties();
		try {
			properties.load(inputstream);
		} catch (IOException e) {
			System.out.println("No se pudieron cargar las propiedades desde el archivo");
			e.printStackTrace();
		}
		
		String driver = properties.getProperty("db.driver");
		String host = properties.getProperty("db.host");
		String port = properties.getProperty("db.port");
		String user = properties.getProperty("db.username");
		String password = properties.getProperty("db.password");
		String db = properties.getProperty("db.name");
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL driver not found");
			e.printStackTrace();
		}
		try {
			String connection_address = "jdbc:mysql://"+host+":"+port+"/"+db;
			connection = DriverManager.getConnection(connection_address, user, password);
		} catch (SQLException e) {
			System.out.println("No se pudo establecer la conexión con la base de datos");
			e.printStackTrace();
		}
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
