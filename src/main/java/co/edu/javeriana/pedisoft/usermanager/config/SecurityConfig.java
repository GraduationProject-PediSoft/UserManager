package co.edu.javeriana.pedisoft.usermanager.config;

import co.edu.javeriana.pedisoft.usermanager.config.oidc.OIDCConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoders;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OIDCConfig issuer;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login", "/auth/refresh")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oAuth2 -> oAuth2
                        .jwt(it -> it.jwkSetUri(issuer.getJwks_url()))
                )
                .build();
    }

}
