package com.javaweb.config;

import com.javaweb.security.CustomSuccessHandler;
import com.javaweb.service.impl.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable()
                .authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/api/user").hasRole("MANAGER")
                        .antMatchers(HttpMethod.DELETE, "/api/user").hasRole("MANAGER")
                        .antMatchers("/api/buildings/{id}/staffs").hasRole("MANAGER")
                        .antMatchers("/api/customers/{id}/staffs").hasRole("MANAGER")
                        .antMatchers("/api/assign/*").hasRole("MANAGER")
                        .antMatchers("/admin/user**").hasRole("MANAGER")
                        .antMatchers("/admin/profile**").hasRole("MANAGER")
                        .antMatchers("/api/transactions/{id}").hasRole("MANAGER")
                        .antMatchers("/api/transactions").hasAnyRole("MANAGER", "STAFF")
                        .antMatchers("/api/buildings").hasAnyRole("MANAGER", "STAFF")
                        .antMatchers("/api/buildings/{ids}").hasAnyRole("MANAGER", "STAFF")
                        .antMatchers(HttpMethod.PUT,"/api/customers").hasAnyRole("MANAGER", "STAFF")
                        .antMatchers("/api/customers/{ids}").hasAnyRole("MANAGER", "STAFF")
                        .antMatchers("/admin/**").hasAnyRole("MANAGER","STAFF")
                        .antMatchers("/login", "/register","/resource/**", "/trang-chu", "/api/**").permitAll()
                .and()
                .formLogin().loginPage("/login").usernameParameter("j_username").passwordParameter("j_password").permitAll()
                .loginProcessingUrl("/j_spring_security_check")
                .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login?incorrectAccount").and()
                .logout().logoutUrl("/logout").deleteCookies("JSESSIONID")
                .and().exceptionHandling().accessDeniedPage("/access-denied").and()
                .sessionManagement().maximumSessions(3).expiredUrl("/login?sessionTimeout");
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new CustomSuccessHandler();
    }
}
