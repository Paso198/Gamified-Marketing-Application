package it.polimi.db2.questionnaire.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "application.jwt")		//read in properties file
public class JwtConfig {
	private String secretKey;
    private String tokenPrefix;
    
    public String getHeader() {
    	return HttpHeaders.AUTHORIZATION;
    };
}	

