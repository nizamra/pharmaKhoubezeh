package com.axsos.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;

	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            authorizeRequests()
            	.antMatchers("/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index","/pharmacyproducts/**").permitAll()
                .antMatchers("/static/**", "/registration","/bylocal/**").permitAll()
                .antMatchers("/pharmacyproducts/**", "/pharmacyproducts/3","/pharmacyproducts/{id}").permitAll()
                .antMatchers("/css/**", "/imgs/**","/fonts/**","/js/**","/scss/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/css/").permitAll()
                .antMatchers("/img/").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/*.js","/*.css").permitAll()
                .antMatchers("/tokenpost").permitAll()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/pharma/products").access("hasRole('PHARMACY')")
                .antMatchers("/pharma/create").access("hasRole('PHARMACY')")
                .antMatchers("/pharma/edit").access("hasRole('PHARMACY')")
                .antMatchers("/pharma/update").access("hasRole('PHARMACY')")
                .antMatchers("/pharma/delete").access("hasRole('PHARMACY')")
                .antMatchers("/pharma/log").access("hasRole('PHARMACY')")
                .antMatchers("/pharmacyproducts/{id}").access("hasRole('PHARMACY')")
                .antMatchers("/pharmacyproducts/3").access("hasRole('PHARMACY')")
                .antMatchers("/pharmacyproducts/**").access("hasRole('PHARMACY')")
                
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .successForwardUrl("/home")
                .permitAll()
                .and()
            .logout()
                .permitAll()
             .and()
             	.cors().and().csrf().disable();
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
	  }
	
}
