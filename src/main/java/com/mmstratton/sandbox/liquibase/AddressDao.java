package com.mmstratton.sandbox.liquibase;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AddressDao {
	static Logger log = Logger.getLogger(AddressDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * Create a new address entity in the database
	 * 
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return id of new address entity
	 * @throws DataAccessException
	 */
	public int createAddress(String street, String city, String state, String zip) throws DataAccessException{
		log.info(String.format("\nCreating address: \n\t%s\n\t%s, %s %s", street, city, state, zip));
		
		jdbcTemplate.execute(String.format("insert into address(id, street, city, state, zip) values(nextval('address_id_seq'), '%s', '%s', '%s', '%s')", street, city, state, zip));
		
		return jdbcTemplate.queryForObject("select currval('address_id_seq')", Integer.class);
	}
}
