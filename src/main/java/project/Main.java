package project;

import org.apache.log4j.*;

import services.ClientDBManager;
import services.ClientMotorcycleDBManager;
import services.MotoDBManager;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws PriceException {

		PropertyConfigurator.configure("src/resources/java/Log4J.properties");

		Client cl = new Client("Jan", "Kowalski");
		Client cl2 = new Client("Bartosz", "Posiakow");
		try {
			cl.addMotorcycle(Brand.Honda, "CBR 600RR", 59000, 2003);
			cl.addMotorcycle(Brand.Suzuki, "GSXR 750", 64000, 2002);
			cl.addMotorcycle(Brand.Yamaha, "R1", 56900, 2001);
			cl.addMotorcycle(Brand.Aprillia, "Factory", 73000, 2003);
		} catch (PriceException exception) {
			logger.error(exception);
		}

		cl.printMotoCatalog();

//		cl.deleteMotorcycle(cl.findAllMotorcycleByModel("CBR 600RR"));
		
		System.out.println("\n_______________________");

		ClientDBManager dbClient = new ClientDBManager();

		dbClient.addClient(cl);
		dbClient.addClient(cl2);
		for (Client client : dbClient.getAllClient())
		{
			System.out.println(client.getSurname());
		}


		System.out.println("\n_______________________");

		MotoDBManager dbMoto = new MotoDBManager();

		dbMoto.addMotorcycle(new Motorcycle(Brand.BMW, "1000RR", 80000, 2011));
		dbMoto.addMotorcycle(new Motorcycle(Brand.Yamaha, "R1", 56900, 2001));
		dbMoto.addMotorcycle(new Motorcycle(Brand.Aprillia, "Factory", 73000, 2003));
		for (Motorcycle motorcycle : dbMoto.getAllMotorcycles())
		{
			System.out.println("Brand: " + motorcycle.getBrand() + "\tModel: " + motorcycle.getModel() + "\tPrice: " + motorcycle.getPrice() + "\tYear of manufacture: " + motorcycle.getYearOfManufacture());
		}

		ClientMotorcycleDBManager dbClientMotorcycle = new ClientMotorcycleDBManager();

		dbClientMotorcycle.addMotorcycleToClient(dbClient.findClientBySurname("Kowalski"), dbMoto.findMotorcycleByModel("R1"));
		dbClientMotorcycle.addMotorcycleToClient(dbClient.findClientBySurname("Posiakow"), dbMoto.findMotorcycleByBrand(Brand.Aprillia));

		System.out.println("_______________________");
		System.out.println("Lista motocykli Posiakow");
		System.out.println("_______________________");

		for (Motorcycle motorcycle : dbClientMotorcycle.getClientMotorcycle(dbClient.findClientBySurname("Posiakow")))
		{
			System.out.println("Brand: " + motorcycle.getBrand() + "\tModel: " + motorcycle.getModel() + "\tPrice: " + motorcycle.getPrice() + "\tYear of manufacture: " + motorcycle.getYearOfManufacture());
		}

		System.out.println("_______________________");
		System.out.println("Lista motocykli Kowalski");
		System.out.println("_______________________");

		for (Motorcycle motorcycle : dbClientMotorcycle.getClientMotorcycle(dbClient.findClientBySurname("Kowalski")))
		{
			System.out.println("Brand: " + motorcycle.getBrand() + "\tModel: " + motorcycle.getModel() + "\tPrice: " + motorcycle.getPrice() + "\tYear of manufacture: " + motorcycle.getYearOfManufacture());
		}

	
		//-----
		dbClientMotorcycle.deleteAllClientMotorcycle();
		dbMoto.deleteAllMotorcycle();
		dbClient.deleteAllClient();
	}
}
