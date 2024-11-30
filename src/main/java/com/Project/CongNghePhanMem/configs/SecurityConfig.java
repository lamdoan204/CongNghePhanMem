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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	public AuthenticationSuccessHandler customsuccessHandler;

	@Bean
	public JwtFilter jwtFilter() {
		return new JwtFilter();
	}

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
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Các URL public không cần authentication
                .requestMatchers(
                    "/css/**", "/js/**", "/images/**", "/fonts/**",
                    "/", "/login", "/register", "/notifyVerify",
                    "/forgotPassword", "/verifyOTP", "/resetPassword",
                    "/verify", "/createUser", "/it_shop_detail", "/search"
                ).permitAll()
                // Các URL cần role cụ thể
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .requestMatchers("/user/**").hasRole("USER") 
                .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Các request khác cần authentication
                .anyRequest().authenticated()
            )
            // Cấu hình form login
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/signin")
                .failureUrl("/login?error=true")
                .successHandler(customsuccessHandler)
                .permitAll()
            )
            // Cấu hình logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            )
            // Thêm JWT filter
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
