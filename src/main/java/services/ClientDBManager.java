package services;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import com.pl.projectfiles.*;

public class ClientDBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement addPersonStmt;
	private PreparedStatement getPersonsStmt;
	private PreparedStatement deletePersonStmt;

	
	public ClientDBManager() {
		
		
		Properties props = new Properties();
		
		try {		
			props.load(ClassLoader.getSystemResourceAsStream("com/pl/resources/jdbc.properties"));
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		
		
		try {
			
			conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb");
			stmt = conn.createStatement();
			boolean customerTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while(rs.next()) {
				if("Customer".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					customerTableExists = true;
					break;
				}
			}


			if(!customerTableExists) {
				stmt.executeUpdate("" +
						"CREATE TABLE customer(" +
						"id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
						"name varchar(20)," +
						"surname varchar(20)," +
						")");
			}

			addPersonStmt = conn.prepareStatement("" +
					"INSERT INTO customer (name, surname) VALUES (?, ?)" +
					"");

			getPersonsStmt = conn.prepareStatement("" +
					"SELECT * FROM customer" +
					"");



		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void addCustomer(Customer customer) {
		try {
			addPersonStmt.setString(1, customer.getName());
			addPersonStmt.setString(2, customer.getSurname());
			addPersonStmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();

		try {
			ResultSet rs = getPersonsStmt.executeQuery();

			while(rs.next()) {
				customers.add(new Customer(rs.getString("name"), rs.getString("surname")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customers;
	}
	
	public void deleteAllCustomers() {
		try {
			deletePersonStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}




}