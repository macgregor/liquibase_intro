package com.mmstratton.sandbox.liquibase;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//boilerplate for spring unit tests
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-context.xml")
public class LiquibaseTests {
	static Logger log = Logger.getLogger(LiquibaseTests.class);
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	//inject datasource and initialize JDBC template so we can work with the database
	@Autowired
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}
	
	@Test
	public void testTableExists(){
		//exception will be thrown if there is a problem
		jdbcTemplate.execute("insert into person(id, name) values(0, 'Matt')");		
	}
}
