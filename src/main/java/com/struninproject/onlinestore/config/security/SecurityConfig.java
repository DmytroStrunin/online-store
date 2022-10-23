package com.struninproject.onlinestore.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * The {@code SecurityConfig} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(DataSource dataSource, CustomUserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new SCryptPasswordEncoder();
//        return new BCryptPasswordEncoder(8); // FIXME: 03.10.2022
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() throws SQLException {
        final DatabaseMetaData dbm = dataSource.getConnection().getMetaData();
        final boolean ifExistTable = dbm.getTables(null, null, "persistent_logins", null).next();
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        if (!ifExistTable) {
            jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        }
        return jdbcTokenRepositoryImpl;
    }

    @Bean
    public AuthenticationSuccessHandler getAuthSuccessHandler(){
        return new CustomAuthSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler getLogoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// FIXME: 15.10.2022

                .authorizeRequests()

                .antMatchers("/anonymous*").anonymous()

                .antMatchers(
                        "/login*",
                        "/registration",
                        "/",
                        "/css/**",
                        "/js/**",
                        "/user/registration",
                        "/product/products"
                ).permitAll()

                .anyRequest().authenticated()

                .and()
                .formLogin()

                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(getAuthSuccessHandler())
                .failureUrl("/login?error=true")

                .and()
                .rememberMe().key("remember_me")
                .tokenRepository(getPersistentTokenRepository())
                .tokenValiditySeconds(86400)
                .userDetailsService(userDetailsService)

                .and()
                .logout()
                .logoutSuccessHandler(getLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

                .and()
                .rememberMe().key("remember_me");
        return http.build();
    }
}