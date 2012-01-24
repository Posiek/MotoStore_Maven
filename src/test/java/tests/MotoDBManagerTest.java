package tests;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.*;
import project.*;

public class MotoDBManagerTest {
	
	MotoDBManager dbMoto = new MotoDBManager();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Ducati, "Multistrada 1200S", 52000, 2011));
	}

	@After
	public void tearDown() throws Exception {
		dbMoto.deleteAllMotorcycle();
	}

	@Test
	public void testAddMotorcycle() {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Honda, "1000RR", 55000, 2009));
		dbMoto.addMotorcycle(new Motorcycle(Brand.BMW, "1000RR", 75000, 2010));
		assertEquals(3, dbMoto.getAllMotorcycles().size());
	}

	@Test
	public void testGetAllMotorcycles() {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Yamaha, "R1", 41999, 2006));
		assertEquals(2, dbMoto.getAllMotorcycles().size());
	}

	@Test
	public void testDeleteAllMotorcycle() {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Suzuki, "Gladius", 66666, 2010));
		dbMoto.deleteAllMotorcycle();
		assertEquals(0, dbMoto.getAllMotorcycles().size());
		assertTrue(dbMoto.getAllMotorcycles().size() == 0);
		}

	@Test
	public void testFindMotorcycleByModel() {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Ducati, "Multistrada 1200S2", 32000, 2002));
		dbMoto.addMotorcycle(new Motorcycle(Brand.Ducati, "Multistrada 1200S3", 32000, 2002));
		assertTrue(dbMoto.findMotorcycleByModel("Multistrada 1200S").size() == 1);
		
	}

	@Test
	public void testFindMotorcycleByBrand() {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Suzuki, "Gladius", 66666, 2010));
		dbMoto.addMotorcycle(new Motorcycle(Brand.Ducati, "Diavel", 53000, 2008));
		assertEquals(2, dbMoto.findMotorcycleByBrand(Brand.Ducati).size());
	}

	@Test
	public void testDeleteMotorcycle() {
		dbMoto.addMotorcycle(new Motorcycle(Brand.Ducati, "Diavel", 53000, 2008));
		dbMoto.deleteMotorcycle(dbMoto.findMotorcycleByModel("Diavel"));
		assertEquals(1, dbMoto.findMotorcycleByBrand(Brand.Ducati).size());
	}
	

}
