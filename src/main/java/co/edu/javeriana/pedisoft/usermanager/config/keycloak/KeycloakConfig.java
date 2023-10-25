package co.edu.javeriana.pedisoft.usermanager.config.keycloak;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Autowired
    KeycloakConfigHolder keycloakConfigHolder;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(keycloakConfigHolder.getRealm())
                .clientId(keycloakConfigHolder.getClientId())
                .clientSecret(keycloakConfigHolder.getClientSecret())
                .serverUrl(keycloakConfigHolder.getUrl())
                .build();
    }
}