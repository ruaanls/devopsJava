package br.com.fiap.globalSolution.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/").permitAll()
                                        .requestMatchers("/motos").permitAll()
                                        .requestMatchers("/vagas").permitAll()
                                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                        .anyRequest().authenticated()
                ).oauth2Login(oauth2 ->
                        oauth2.defaultSuccessUrl("/"))
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
