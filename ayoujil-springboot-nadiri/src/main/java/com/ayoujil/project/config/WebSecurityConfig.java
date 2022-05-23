package com.ayoujil.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import com.ayoujil.project.service.UserServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserServiceImpl();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()
		.exceptionHandling()
		.and()
		.formLogin()
		.defaultSuccessUrl("/home", true)
		.loginPage("/login")
		.and()
		.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/register").permitAll()
		.antMatchers("/logout").permitAll()
		.antMatchers("/users/**", "/tickets/**", "/logiciels/**").hasAnyAuthority("ADMIN")
		.antMatchers("/client/profile/**").hasAnyAuthority("CLIENT")
		.antMatchers("/developper/profile/**").hasAnyAuthority("DEVELOPPER", "ADMIN")
		.antMatchers("/css/**", "/fonts/**", "/images/**", "/icon/**", "/js/**", "/vendors/**")
		.permitAll()
		.antMatchers("/**").authenticated()
		.anyRequest().permitAll();
	}
}