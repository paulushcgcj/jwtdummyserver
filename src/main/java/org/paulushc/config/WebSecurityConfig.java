package org.paulushc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.paulushc.filters.JWTAuthenticationFilter;
import org.paulushc.filters.JWTLoginFilter;
import org.paulushc.model.RandomUserResponse;
import org.paulushc.model.UserModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Paths;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()

                // filtra requisições de login
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),UsernamePasswordAuthenticationFilter.class)

                // filtra outras requisições para verificar a presença do JWT no header
                .addFilterBefore(new JWTAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        if(Paths.get("./userdatabase.json").toFile().exists()){

            log.info("Using userdatabase.json file with users");
            UserModel[] users = new ObjectMapper().readValue(Paths.get("./userdatabase.json").toFile(), UserModel[].class);

            populateUserDatabase(auth, users);

        }else {

            log.info("No userdatabase.json file found, consuming from randomuser.me and generating userdatabase.json");

            RestTemplate restTemplate = new RestTemplate();
            RandomUserResponse randomUserResponse = restTemplate.getForObject("https://randomuser.me/api/?results=10", RandomUserResponse.class);
            new ObjectMapper().writeValue(Paths.get("./userdatabase.json").toFile(),randomUserResponse.getResults());
            populateUserDatabase(auth, randomUserResponse.getResults());

        }
    }

    private void populateUserDatabase(AuthenticationManagerBuilder auth, UserModel[] users) throws Exception {
        InMemoryUserDetailsManagerConfigurer inMemAuth = auth.inMemoryAuthentication();

        Arrays.stream(users).forEach(userModel -> inMemAuth
            .withUser(userModel.getLogin().getUsername())
            .password(userModel.getLogin().getPassword())
            .roles("ADMIN"));
    }
}