package com.example.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.example.server.config.jwt.JwtAuthenticationFilter;
import com.example.server.config.jwt.JwtAuthorizationFilter;
import com.example.server.config.jwt.JwtProperties;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // SpringSecurityFilter가 SpringFilterChain 에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured annotation 활성화, preAuthorize,
																			// postAuthorize annotion 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	private final CorsConfig corsConfig;

	private final UserRepository userRepository;

	private final JwtProperties jwtProperties;
		
	private final AuthenticationFailureHandler customFailureHandler;
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		return new JwtAuthenticationFilter(authenticationManager(), jwtProperties);
	}

	
	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		return new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtProperties);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.csrf().disable(); 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
				.and().addFilter(corsConfig.corsFilter()).formLogin().disable().httpBasic().disable()
				.addFilter(jwtAuthenticationFilter())
				.addFilter(jwtAuthorizationFilter()) 
				.authorizeRequests()
				.anyRequest().permitAll();

	}


}
