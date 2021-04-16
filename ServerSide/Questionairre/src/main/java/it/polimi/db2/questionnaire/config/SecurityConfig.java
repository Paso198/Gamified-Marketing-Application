package it.polimi.db2.questionnaire.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import it.polimi.db2.questionnaire.config.jwt.JwtUtils;
import it.polimi.db2.questionnaire.enumerations.Role;
import it.polimi.db2.questionnaire.filters.JwtAuthenticationFilter;
import it.polimi.db2.questionnaire.filters.JwtVerifier;
import it.polimi.db2.questionnaire.repositories.UserRepository;
import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	private final UserDetailsService userDetailsService;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf().disable()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)		//jwt is stateless
        .and()
        .addFilter(authFilter())
        .addFilterAfter(new JwtVerifier(jwtUtils, userRepository), JwtAuthenticationFilter.class)
        .authorizeRequests()
		 .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
		 .antMatchers("/public/**").permitAll()
         .antMatchers("/user/*").hasRole(Role.USER.name())
         .antMatchers("/admin/*").hasRole(Role.ADMIN.name())
         .anyRequest()
         .authenticated();
 
	}
	

	
	private JwtAuthenticationFilter authFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtils, authenticationManager());
		filter.setAuthenticationFailureHandler(failureHandler());
		return filter;
	}
	
	private AuthenticationFailureHandler failureHandler() {
		return new CustomAuthenticationFailureHandler();
		
	}
	

}
