package com.freegians.timeline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static PasswordEncoder encoder;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/")
                    .and()
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/logout").permitAll()
                    .antMatchers("/api/timeline").permitAll()
                    .antMatchers("/api/timeline/**").fullyAuthenticated()
                    .antMatchers("/api/user/**").permitAll()
                    .antMatchers("/api/follower/**").permitAll()
                    .antMatchers("/api/**").fullyAuthenticated()
                    .antMatchers("/**").permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
        // TODO 암호화 해야함
//                .passwordEncoder(new BCryptPasswordEncoder());
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        if(encoder == null) {
//            encoder = new BCryptPasswordEncoder();
//        }
//
//        return encoder;
//    }
}