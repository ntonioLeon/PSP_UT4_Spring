package org.educa.airline.configuration;

import lombok.Getter;
import org.educa.airline.services.SecurityService;
import org.educa.airline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Getter
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public SpringSecurityConfig(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults()).authorizeHttpRequests((
                        x -> x.requestMatchers("/user").anonymous()
                                .requestMatchers("/user/{id}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasRole("admin")
                                .requestMatchers(HttpMethod.PUT, "/user/{id}").hasRole("admin")
                                .requestMatchers(HttpMethod.GET, "/user/{id}").hasAnyRole("admin", "personal")
                                .requestMatchers("/user/{id}").hasAnyRole("admin", "personal", "usuario")
                                .requestMatchers("/flights/add", "/flights/{cod}/delete", "/flights/{cod}/update").hasRole("admin")
                                .requestMatchers(HttpMethod.GET, "/flights", "/flights/{cod}").authenticated()
                                .requestMatchers("/flights/{cod}/passenger", "/flights/{cod}/passenger/{nif}" , "/flights/{cod}/passengers").hasAnyRole("admin", "personal")
                                .anyRequest().authenticated()
                ));

        return http.build();
    }

    public SecurityService passwordEncoder() {
        return securityService;
    }
}
