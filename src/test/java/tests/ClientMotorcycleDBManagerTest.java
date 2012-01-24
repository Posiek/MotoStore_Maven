package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.*;
import project.*;

public class ClientMotorcycleDBManagerTest {
	
	ClientDBManager dbClient = new ClientDBManager();
	MotoDBManager dbMoto = new MotoDBManager();
	ClientMotorcycleDBManager dbClientMotorcycle = new ClientMotorcycleDBManager();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dbClient.addClient(new Client("Bartosz", "Posiakow"));
		dbClient.addClient(new Client("Jan", "Kowalski"));
		dbMoto.addMotorcycle(new Motorcycle(Brand.Honda, "600F", 7000, 1996));
		dbMoto.addMotorcycle(new Motorcycle(Brand.Yamaha, "FZY", 4000, 1997));
		dbClientMotorcycle.addMotorcycleToClient(dbClient.findClientBySurname("Posiakow"), dbMoto.findMotorcycleByModel("600F"));
	}

	@After
	public void tearDown() throws Exception {
		dbClient.deleteAllClient();
		dbMoto.deleteAllMotorcycle();
		dbClientMotorcycle.deleteAllClientMotorcycle();
	}

	@Test
	public void testAddMotorcycleToClient() {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Ducati, "Multistrada", 50000, 2011));
		dbClientMotorcycle.addMotorcycleToClient(dbClient.findClientBySurname("Posiakow"), dbMoto.findMotorcycleByBrand(Brand.Ducati));
		assertEquals(2, dbClientMotorcycle.getClientMotorcycle(dbClient.findClientBySurname("Posiakow")).size());
	}
/*
	@Test
	public void testDeleteAllMotorcycleFromClient() {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Ducati, "Multistrada", 50000, 2011));
		dbClientMotorcycle.addMotorcycleToClient(dbClient.findClientBySurname("Kowalski"), dbMoto.findMotorcycleByModel("Multistrada"));
		dbClientMotorcycle.deleteAllMotorcycleFromClient(dbClient.findClientBySurname("Kowalski"));
		assertTrue(dbClientMotorcycle.getClientMotorcycle(dbClient.findClientBySurname("Kowalski")).size() == 0);
	}
	

	@Test
	public void testDeleteAllClientMotorcycle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClientMotorcycle() {
		assertEquals(1, dbClientMotorcycle.getClientMotorcycle(dbClient.findClientBySurname("Posiakow")).size());
	}
*/	

}
