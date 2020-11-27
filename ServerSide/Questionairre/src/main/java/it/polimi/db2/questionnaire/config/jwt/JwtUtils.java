package it.polimi.db2.questionnaire.config.jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import it.polimi.db2.questionnaire.exceptions.InvalidHeaderException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtUtils {

	private final SecretKey secretKey;
	
	private final JwtConfig jwtConfig;
	
	public String buildToken(String subject, Collection<? extends GrantedAuthority> authorities) {
		return Jwts.builder()
        .setSubject(subject)
        .claim("authorities", authorities)
        .signWith(secretKey)
        .compact();
	}
	
	public String buildHeaderBody(String subject, Collection<? extends GrantedAuthority> authorities) {
		return jwtConfig.getTokenPrefix()+buildToken(subject, authorities);
	}

	public String getHeader() {
		return jwtConfig.getHeader();
	}
	
	public String getToken(HttpServletRequest request) throws InvalidHeaderException{
		 String authorizationHeader = request.getHeader(jwtConfig.getHeader());
		 if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
			 throw new InvalidHeaderException("Invalid header"+jwtConfig.getHeader());
	        }
		 return authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
	}
	
	public Claims getBody(String token) throws JwtException{
		 
		Jws<Claims> jws= Jwts.parserBuilder()
				 	.setSigningKey(secretKey)
				 	.build()
				 	.parseClaimsJws(token);
		
		return jws.getBody();
	};
	
	public String getUsername(Claims body) throws JwtException{
		return body.getSubject();
	}
	
	@SuppressWarnings("unchecked")
	public Set<SimpleGrantedAuthority> getAuthorities(Claims body) throws JwtException{
		return ((List<Map<String, String>>) body.get("authorities")).stream()
        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
        .collect(Collectors.toSet());
	};
	
	
	
	
}
