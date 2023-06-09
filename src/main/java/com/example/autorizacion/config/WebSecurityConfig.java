package com.example.autorizacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

//	 @Override
//	    protected void configure(HttpSecurity http) throws Exception {
//	        http.
//	            authorizeRequests()
//	                .antMatchers("/css/**", "/js/**", "/registration").permitAll()
//	                .anyRequest().authenticated()
//	                .and()
//	            .formLogin()
//	                .loginPage("/login")
//	                .permitAll()
//	                .and()
//	            .logout()
//	                .permitAll();
//	    }

	private UserDetailsService userDetailsService;

	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.
		authorizeHttpRequests()
		.requestMatchers("/admin/**").hasRole("ADMIN")
		.requestMatchers("/", "/home").authenticated()
		.anyRequest().permitAll().and()
				.formLogin()
				.permitAll()
				.loginPage("/login")
				.defaultSuccessUrl("/home")
				.and()
				.logout()
				.permitAll();

		return http.build();
	}
	
	// 1 OJO
   
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
