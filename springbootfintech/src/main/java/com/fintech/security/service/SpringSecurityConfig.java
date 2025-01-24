package com.fintech.security.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Created 24/01/2025 - 13:02 PM
 * @project SpringHttpBasicSecure
 * @Author Sharon
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
//                .and()
//                .withUser("hamdamboy").password(passwordEncoder().encode("dan123")).roles("USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//    }

    @Bean
    PasswordEncoder passwordEncoder(){
    	//Use BCrypt for password hashing
        return new BCryptPasswordEncoder();
    }
    
	@Bean
	public SecurityFilterChain FilterChain (HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeHttpRequests(auth -> auth
		    //Allow all POST requests to /api/user/userRegistration without authentication.
            .requestMatchers(HttpMethod.POST, "/api/user/userRegistration").permitAll()  
            //Require authentication for any other requests
            .anyRequest().authenticated())        
		//Enables form-based login
		.formLogin().and()                         
        //Enables HTTP Basic authentication
		.httpBasic();                          
	    return http.build();
	}
    
	/**
	 * load user-specific data
	 * allow for custom logic in user authentication
	 * @return InMemoryUserDetailsManager
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("a12345@")
				.password(passwordEncoder().encode("a12345@"))
				.build();

		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
		return inMemoryUserDetailsManager;
	}
}
