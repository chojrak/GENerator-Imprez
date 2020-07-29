package com.generatorimprez.GEN.Controller;

import com.generatorimprez.GEN.Model.Postgres;
import com.generatorimprez.GEN.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class Security extends WebSecurityConfigurerAdapter {


    @Autowired
    DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bcEncoder = new BCEncoder();
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, true from users where username=?")
                .authoritiesByUsernameQuery("select username, admin role_id from users where username=?")
                .passwordEncoder(bcEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable().authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/resources/**", "/img/**", "/font/**", "/css/**", "/js/**", "/static/**", "/**", "/zaloguj/**", "/o-nas/**", "/zarejestruj/**", "/zorganizuj-impreze/**").permitAll().anyRequest().hasRole("admin")
                .and()
                .formLogin().loginPage("/zaloguj");
    }


    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(Postgres.getUrl());
        dataSource.setUsername(Postgres.getUser());
        dataSource.setPassword(Postgres.getPassword());
        return dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCEncoder();
    }
}

class BCEncoder extends BCryptPasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String hashed = User.encodePass(rawPassword.toString());
        return hashed;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return (encode(rawPassword).equals(encodedPassword));
    }
}


