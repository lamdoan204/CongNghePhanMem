package com.Project.CongNghePhanMem.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	public AuthenticationSuccessHandler customsuccessHandler;
	
    @Bean
    public UserDetailsService userDetailsServices() {
        return new UserDetailsServiceImpl(); 
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServices());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager(); 
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/manager/**").hasRole("ADMIN") 
                .requestMatchers("/user/**").hasRole("USER")  
                .requestMatchers("/employee/**").hasRole("EMPLOYEE")  
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                .requestMatchers("/**").permitAll()           
            )
            .formLogin(form -> form
                .loginPage("/login")                     
                .loginProcessingUrl("/signin")
                .successHandler(customsuccessHandler)        
                .permitAll()                              
            )
           
            
            .csrf(csrf -> csrf.disable());                
        return http.build();
    }

}