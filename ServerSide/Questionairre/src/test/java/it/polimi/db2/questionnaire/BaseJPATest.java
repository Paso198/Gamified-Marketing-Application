package it.polimi.db2.questionnaire;

import org.testcontainers.containers.MySQLContainer;
//Base class for JPA Test using TestContainer with Production DB
public class BaseJPATest {
	@SuppressWarnings("rawtypes")
	static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("questionnaire-test-db")
            .withUsername("testuser")
            .withPassword("pass")
            .withReuse(true);
 
    static {
        mySQLContainer.start();
    }
}
