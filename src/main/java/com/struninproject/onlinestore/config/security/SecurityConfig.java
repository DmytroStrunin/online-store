package com.struninproject.onlinestore.config.security;

import com.struninproject.onlinestore.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return new BCryptPasswordEncoder(8);
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
                .csrf().disable()

                .authorizeRequests()

                .antMatchers(
                                "/images/**",
                                "/css/**",
                                "/js/**",
                                "/",
                                "/login*",
                                "/registration",
                                "/user/registration",
                                "/product/products",
                                "/product/p*"
                ).permitAll()

                .antMatchers(
                                "/order/cart/*",
                                "/order/all"
                ).authenticated()

                .antMatchers(   "/product/**",
                                "/manufacturer/**",
                                "/category/**",
                                "/order/**"
                ).hasAnyAuthority(Role.MANAGER.name(), Role.ADMIN.name())

                .antMatchers("/user/**").hasAnyAuthority(Role.ADMIN.name())

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