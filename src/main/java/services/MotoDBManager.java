package services;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import project.*;

public class MotoDBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement addMotorcycleStmt;
	private PreparedStatement getMotorcycleStmt;
	private PreparedStatement deleteAllMotorcycleStmt;
	private PreparedStatement findMotorcycleByModelStmt;
	private PreparedStatement findMotorcycleByBrandStmt;
	private PreparedStatement deleteMotorcycleStmt;
	
	List<Integer> listID = new ArrayList<Integer>();
	
	public MotoDBManager() 
	{
		try 
		{
			Properties props = new Properties();
			
			try {
				props.load(ClassLoader.getSystemResourceAsStream("jdbs.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			conn = DriverManager
					.getConnection(props.getProperty("url"));

			stmt = conn.createStatement();
			boolean MotorcycleTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while (rs.next()) 
			{
				if ("Motorcycle".equalsIgnoreCase(rs.getString("TABLE_NAME"))) 
				{
					MotorcycleTableExists = true;
					break;
				}
			}

			if (!MotorcycleTableExists) 
			{
				stmt.executeUpdate("CREATE TABLE Motorcycle(id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,brand varchar(40), model varchar(40), price integer, yearOfManufacture integer)");
			}


			addMotorcycleStmt = conn.prepareStatement("INSERT INTO Motorcycle (brand, model, price, yearOfManufacture) VALUES (?, ?, ?, ?)");

			getMotorcycleStmt = conn.prepareStatement("SELECT * FROM Motorcycle");
			
			deleteAllMotorcycleStmt = conn.prepareStatement("DELETE FROM Motorcycle");
			
			findMotorcycleByModelStmt = conn.prepareStatement("SELECT id FROM Motorcycle WHERE name = ?");
			
			findMotorcycleByBrandStmt = conn.prepareStatement("SELECT id FROM Motorcycle WHERE brand = ?");
			
			deleteMotorcycleStmt = conn.prepareStatement("DELETE FROM Motorcycle WHERE id = ?");
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}
	}

	public void addMotorcycle(Motorcycle MotoCatalog) 
	{
		try 
		{
			addMotorcycleStmt.setString(1, MotoCatalog.getBrand().toString());
			addMotorcycleStmt.setString(2, MotoCatalog.getModel().toString());
			addMotorcycleStmt.setInt(3, MotoCatalog.getPrice());
			addMotorcycleStmt.setInt(4, MotoCatalog.getYearOfManufacture());
			addMotorcycleStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}

	}

	public List<Motorcycle> getAllMotorcycles() 
	{
		List<Motorcycle> Motorcycles = new ArrayList<Motorcycle>();
		try 
		{
			ResultSet rs = getMotorcycleStmt.executeQuery();
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
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return Motorcycles;
	}

	public void deleteAllMotorcycle() 
	{
		try 
		{
			deleteAllMotorcycleStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public List<Integer> findMotorcycleByModel(String model)
	{
		try 
		{
			List<Integer> result = new ArrayList<Integer>();
			findMotorcycleByModelStmt.setString(1, model);
			ResultSet rs = findMotorcycleByModelStmt.executeQuery();
			while (rs.next())
				result.add(rs.getInt("ID"));	
			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Integer> findMotorcycleByBrand(Brand brand)
	{
		try 
		{
			List<Integer> result = new ArrayList<Integer>();
			findMotorcycleByBrandStmt.setString(1, brand.toString());
			ResultSet rs = findMotorcycleByBrandStmt.executeQuery();
			while (rs.next())
				result.add(rs.getInt("ID"));
			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteMotorcycle(List<Integer> list)
	{
		try 
		{
			for (Integer id : list)
			{
				deleteMotorcycleStmt.setInt(1, id);
				deleteMotorcycleStmt.executeUpdate();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void printGameWithCondition(List<Motorcycle> motoCatalog,Condition condition)
	{
		for (Motorcycle motorcycle : motoCatalog)
		{
			if (condition.getCondition(motorcycle))
			{
				System.out.println("Model: " + motorcycle.getModel() + "\tBrand: " + motorcycle.getBrand() + "\tReleasedYear: " + motorcycle.getYearOfManufacture() + "\tPrice: " + motorcycle.getPrice());
			}
		}
	}
	


}
