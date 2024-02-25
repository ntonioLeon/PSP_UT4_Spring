package org.educa.airline.security;

import lombok.Getter;
import org.educa.airline.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Getter
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final UserServiceImpl userService;

    public SpringSecurityConfig(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults()).authorizeHttpRequests((
                        x -> x.requestMatchers("/flights/add", "/flights/{cod}/delete", "/flights/{cod}/update").hasRole("admin")
                                .requestMatchers("/flights/{cod}/passenger", "/flights/{cod}/passenger({nif}", "/flights/{cod}/passengers").hasAnyRole("admin", "personal")
                                .requestMatchers("/flights", "/flights/{cod}").authenticated()
                ));

        return http.build();
    }

    public Security passwordEncoder() {
        return new Security();
    }
}
