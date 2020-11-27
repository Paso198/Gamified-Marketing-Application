package it.polimi.db2.questionnaire.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import it.polimi.db2.questionnaire.config.jwt.JwtUtils;
import it.polimi.db2.questionnaire.exceptions.InvalidHeaderException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtVerifier extends OncePerRequestFilter {

	JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = jwtUtils.getToken(request);
			Claims jwtBody = jwtUtils.getBody(token);
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken( // restore
																											// session
					jwtUtils.getUsername(jwtBody), null, jwtUtils.getAuthorities(jwtBody)));

		} catch (InvalidHeaderException e) {
			filterChain.doFilter(request, response);
			return;
			

		} catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		filterChain.doFilter(request, response);

	}

}
