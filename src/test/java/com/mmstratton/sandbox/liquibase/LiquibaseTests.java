package com.mmstratton.sandbox.liquibase;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-context.xml")
public class LiquibaseTests {
	static Logger log = Logger.getLogger(LiquibaseTests.class);
	
	@Test
	public void hello(){
		log.info("Hello World");
	}
}
