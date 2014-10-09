package com.mmstratton.sandbox.liquibase;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//boilerplate for spring unit tests
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-context.xml")
public class LiquibaseTests {
	static Logger log = Logger.getLogger(LiquibaseTests.class);
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private PersonDao personDao;
	
	private static final String NAME = "Kilgore Trout";
	private static final int AGE = 50;
	private static final String STREET = "42 Northshire Ln";
	private static final String CITY = "Ilium";
	private static final String STATE = "NY";
	private static final String ZIP = "12179";	
	
	@BeforeClass
	public static void setSystemProps() {
		//set property file to use for test to postgres.properties if it is not already set
		System.setProperty("property.file", System.getProperty("property.file", "h2.properties"));
		System.setProperty("spring.profiles.active", System.getProperty("spring.profiles.active", "spring"));
		
	    //System.setProperty("property.file", "postgres.properties");
	    //System.setProperty("spring.profiles.active", "none");
	}
	
	@Test
	public void testDataModel(){
		
		int personId = personDao.createPersonWithAddress(NAME, AGE, STREET, CITY, STATE, ZIP);
		
		Map<String, Object> personAddress = personDao.getPersonWithAddress(personId);
		
		Assert.assertNotNull(personAddress);
		Assert.assertEquals(7, personAddress.size());
		Assert.assertEquals(personId, personAddress.get("id"));
		Assert.assertEquals(NAME, personAddress.get("name"));
		Assert.assertEquals(AGE, personAddress.get("age"));
		Assert.assertEquals(STREET, personAddress.get("street"));
		Assert.assertEquals(CITY, personAddress.get("city"));
		Assert.assertEquals(STATE, personAddress.get("state"));
		Assert.assertEquals(ZIP, personAddress.get("zip"));
	}
}
