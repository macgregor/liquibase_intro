package com.mmstratton.sandbox.liquibase;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

//tells spring to create a bean for this object that we can autowire into other classes
@Service
public class PersonDao{
	
	static Logger log = Logger.getLogger(PersonDao.class);
	
	//inject JDBC template so we can work with the database
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AddressDao addressDao;
	
	/**
	 * Create a new person entity in the database
	 * 
	 * @param name
	 * @param age
	 * @return id of created person entity
	 * @throws DataAccessException
	 */
	public int createPerson(String name, int age) throws DataAccessException{
		log.info(String.format("\nCreating person: \n\t%s, %d", name, age));
		
		jdbcTemplate.execute(String.format("insert into person(id, name, age) values(nextval('person_id_seq'), '%s', %d)", name, age));
		
		return jdbcTemplate.queryForObject("select currval('person_id_seq')", Integer.class);
	}
	
	/**
	 * Create person and address entities in the database
	 * 
	 * @param name
	 * @param age
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return id of the created person entity
	 * @throws DataAccessException
	 */
	public int createPersonWithAddress(String name, int age, String street, String city, String state, String zip) throws DataAccessException{
		log.info(String.format("\nCreating person: \n\t%s, %d", name, age));
		
		int addressId = addressDao.createAddress(street, city, state, zip);
		jdbcTemplate.execute(String.format("insert into person(id, name, age, address_fk) values(nextval('person_id_seq'), '%s', %d, %d)", name, age, addressId));
		
		return jdbcTemplate.queryForObject("select currval('person_id_seq')", Integer.class);
	}
	
	/**
	 * Use a person id to get a information, including address, about them from the database
	 * 
	 * @param personId
	 * @return map of person/address data
	 * @throws DataAccessException
	 */
	public Map<String, Object> getPersonWithAddress(int personId) throws DataAccessException{
		return jdbcTemplate.queryForMap("select id, name, age, street, city, state, zip from person_address_rv where id = " + personId);
	}
}
