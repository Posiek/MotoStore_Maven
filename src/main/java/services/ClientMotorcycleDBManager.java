package services;


import java.io.IOException;
import java.sql.*;
import java.util.*;

import project.Brand;
import project.Motorcycle;

import project.*;

public class ClientMotorcycleDBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement addMotorcycleToClientStmt;
	private PreparedStatement deleteAllClientMotorcycleStmt;
	private PreparedStatement deleteAllMotorcycleFromClientStmt;
	private PreparedStatement getMotorcycleClientStmt;

	public ClientMotorcycleDBManager() 
	{
		try 
		{
			/*
			Properties props = new Properties();
			
			try {
				props.load(ClassLoader.getSystemResourceAsStream("jdbs.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			
			conn = DriverManager
					.getConnection("jdbc:hsqldb:hsql://localhost/workdb");

			stmt = conn.createStatement();
			boolean clientTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while (rs.next()) 
			{
				if ("ClientMotorcycle".equalsIgnoreCase(rs.getString("TABLE_NAME"))) 
				{
					clientTableExists = true;
					break;
				}
			}

			if (!clientTableExists) 
			{
				stmt.executeUpdate("CREATE TABLE clientMotorcycle(client_id int, motorcycle_id int, CONSTRAINT client_id_fk FOREIGN KEY (client_id) REFERENCES client (id), CONSTRAINT motorcycle_id_fk FOREIGN KEY (motorcycle_id) REFERENCES motorcycle (id))");
			}
			
			addMotorcycleToClientStmt = conn.prepareStatement("INSERT INTO clientMotorcycle (client_id, motorcycle_id) VALUES (?, ?)");
			
			deleteAllMotorcycleFromClientStmt = conn.prepareStatement("DELETE FROM clientMotorcycle WHERE client_id = ?");
			
			deleteAllClientMotorcycleStmt = conn.prepareStatement("DELETE FROM clientMotorcycle");
			
			getMotorcycleClientStmt = conn.prepareStatement("SELECT Motorcycle.brand, Motorcycle.model, Motorcycle.price, Motorcycle.yearOfManufacture FROM Motorcycle, ClientMotorcycle WHERE client_id = ? and motorcycle_id = Motorcycle.id");

		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}
	}
	
	
	public void addMotorcycleToClient(List<Integer> listClientId, List<Integer> listMotorcycleId) 
	{
		try 
		{
			for (Integer clientID : listClientId)
			{
				for (Integer motorcycleID : listMotorcycleId)
				{
					addMotorcycleToClientStmt.setInt(1, clientID);
					addMotorcycleToClientStmt.setInt(2, motorcycleID);
					addMotorcycleToClientStmt.executeUpdate();
				}
			}
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}

	}
	
	public void deleteAllMotorcycleFromClient (List<Integer> listClientId) 
	{
		try 
		{
			for (Integer clientID : listClientId)
			{
					deleteAllMotorcycleFromClientStmt.setInt(1, clientID);
					deleteAllMotorcycleFromClientStmt.executeUpdate();
			}
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}

	}
	
	public void deleteAllClientMotorcycle () 
	{
		try 
		{
				deleteAllClientMotorcycleStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}

	}
	
	public List<Motorcycle> getClientMotorcycle (List<Integer> listClientId)
	{
		List<Motorcycle> Motorcycles = new ArrayList<Motorcycle>();
		try 
		{
			for (Integer clientID : listClientId)
			{
				getMotorcycleClientStmt.setInt(1, clientID);
				ResultSet rs = getMotorcycleClientStmt.executeQuery();
				while (rs.next()) 
				{
					Brand brand = null;
					if (rs.getString("brand").equals("Honda"))
						brand = Brand.Honda;
					if (rs.getString("brand").equals("Suzuki"))
						brand = Brand.Suzuki;
					if (rs.getString("brand").equals("Aprillia"))
						brand = Brand.Aprillia;
					if (rs.getString("brand").equals("Ducati"))
						brand = Brand.Ducati;
					if (rs.getString("brand").equals("Yamaha"))
						brand = Brand.Yamaha;
					if (rs.getString("brand").equals("Kawasaki"))
						brand = Brand.Kawasaki;
					if (rs.getString("brand").equals("BMW"))
						brand = Brand.BMW;
						
					Motorcycles.add(new Motorcycle(brand,rs.getString("model"),rs.getInt("price"), rs.getInt("yearOfManufacture")));
				}
			}
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}
		return Motorcycles;
	}

	

}
