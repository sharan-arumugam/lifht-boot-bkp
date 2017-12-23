package com.lti.lifht.config;

import static com.lti.lifht.constant.PathConstant.LOGIN;
import static com.lti.lifht.constant.PathConstant.LOGOUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService employeeDetailsService;

    @Autowired
    SuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().permitAll().successHandler(successHandler)
                .and()
                .logout().logoutUrl(LOGOUT).logoutSuccessUrl(LOGIN);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder(4));
    }
}
