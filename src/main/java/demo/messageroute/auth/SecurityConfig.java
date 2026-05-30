package demo.messageroute.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/tiers").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tiers/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/tiers/**").authenticated()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public UserDetailsService users(PasswordEncoder encoder){
        UserDetails anna = User.builder()
                .username("anna")
                .password(encoder.encode("ninja123"))
                .roles("USER")
                .build();

        UserDetails emilia = User.builder()
                .username("emilia")
                .password(encoder.encode("agent007"))
                .roles("USER")
                .build();

        UserDetails filippa = User.builder()
                .username("filippa")
                .password(encoder.encode("bananer4"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(anna, emilia, filippa);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
