package it.polimi.db2.questionnaire.mappers.unit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import it.polimi.db2.questionnaire.mappers.ProductMapperTest;

@Configuration
@ComponentScan(basePackageClasses = ProductMapperTest.class)
public class SpringTestConfig {
}