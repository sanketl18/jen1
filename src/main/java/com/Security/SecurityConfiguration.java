package com.Security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // it tells spring that it is security class
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	// Authentication
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*
		 * auth.inMemoryAuthentication() .withUser("san") .password("sanl")
		 * .roles("user") .and() .withUser("Foo") .password("Foo") .roles("Admin");
		 */
		auth.jdbcAuthentication().
		dataSource(dataSource).
		usersByUsernameQuery("select username, password, enabled"

				+ "from users"

				+ "where username=?")

				.authoritiesByUsernameQuery("select username, authority"

						+ "from authorities"

						+ "where username = ?");
		/*
		 * .withDefaultSchema() .withUser( User.withUsername("user") .password("pass")
		 * .roles("USER") ) .withUser( User.withUsername("admin") .password("pass")
		 * .roles("ADMIN") );
		 */

	}

	@Bean
	public PasswordEncoder getpasEncoder() {
		return NoOpPasswordEncoder.getInstance();

	}

	// Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/Admin").hasRole("ADMIN").antMatchers("/user").hasAnyRole("ADMIN", "USER")
				.antMatchers("/").permitAll().and().formLogin();
	}

}