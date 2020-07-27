package com.log_centter.demo.security;

import com.log_centter.demo.security.authentication.jwt.FilterAuthEntryPoint;
import com.log_centter.demo.security.authentication.jwt.FilterRequestAuth;
import com.log_centter.demo.security.authentication.user_details.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityRestWeb extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  FilterAuthEntryPoint authEntryPoint;

  @Bean
  public FilterRequestAuth newfilterAuthEntryPoint() {
    return new FilterRequestAuth();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .antMatchers(HttpMethod.GET,"/api/auth/users/**").hasAnyAuthority("ADMIN","USER")
        .antMatchers(HttpMethod.PUT,"/api/auth/users/**").hasAuthority("USER")
        .antMatchers(HttpMethod.DELETE,"/api/auth/users/**").hasAuthority("ADMIN")
        .antMatchers("/api/auth/**").permitAll().and().authorizeRequests().antMatchers("/api/logs/**").permitAll()
        .anyRequest().authenticated();

    httpSecurity.addFilterBefore(newfilterAuthEntryPoint(), UsernamePasswordAuthenticationFilter.class);
  }
}