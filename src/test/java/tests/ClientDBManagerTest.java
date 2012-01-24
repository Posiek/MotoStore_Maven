package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.*;
import project.*;

public class ClientDBManagerTest {

	ClientDBManager dbClient = new ClientDBManager();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dbClient.addClient(new Client("Bartosz", "Posiakow"));
	}

	@After
	public void tearDown() throws Exception {
		dbClient.deleteAllClient();
	}

	@Test
	public void testAddClient() {
		dbClient.addClient(new Client("Marcin", "Posiakow"));
		assertEquals(2, dbClient.getAllClient().size());
	}

	@Test
	public void testGetAllClient() {
		dbClient.addClient(new Client("Jan", "Kowalski"));
		dbClient.addClient(new Client("Piotr", "Nowak"));
		dbClient.addClient(new Client("Marcin", "Posiakow"));
		dbClient.addClient(new Client("Slawomir", "Gombka"));
		assertEquals(5, dbClient.getAllClient().size());
	}

	@Test
	public void testDeleteAllClient() {
		dbClient.addClient(new Client("Marcin", "Posiakow"));
		dbClient.addClient(new Client("Jan", "Kowalski"));
		dbClient.deleteAllClient();
		assertEquals(0, dbClient.getAllClient().size());
	}

	@Test
	public void testFindClientBySurname() {
		dbClient.addClient(new Client("Jan", "Kowalski"));
		dbClient.addClient(new Client("Adam", "Pichocki"));
		dbClient.addClient(new Client("Marcin", "Posiakow"));
		assertEquals(2, dbClient.findClientBySurname("Posiakow").size());
		assertTrue(dbClient.findClientBySurname("Posiakow").size() == 2);
	}

	@Test
	public void testDeleteClient() {
		dbClient.addClient(new Client("Jan", "Kowalski"));
		dbClient.addClient(new Client("Adam", "Pichocki"));
		dbClient.addClient(new Client("Marcin", "Posiakow"));
		dbClient.deleteClient(dbClient.findClientBySurname("Kowalski"));
		assertTrue(dbClient.findClientBySurname("Kowalski").size() == 0);
	}
	

}
