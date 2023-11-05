package co.edu.javeriana.pedisoft.usermanager.service;

import co.edu.javeriana.pedisoft.usermanager.config.keycloak.KeycloakConfigHolder;
import co.edu.javeriana.pedisoft.usermanager.entity.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {
    //REF: https://gist.github.com/thomasdarimont/c4e739c5a319cf78a4cff3b87173a84b
    @Autowired
    private Keycloak keycloak;

    @Autowired
    private KeycloakConfigHolder keycloakConfig;

    /**
     * Register user in the system
     * @param user User entity with the new user info
     */
    public void addUser(@NonNull User user){

        val credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(user.getPassword());

        val userR = new UserRepresentation();
        userR.setUsername(user.getUsername());
        userR.setEmail(user.getEmail());
        userR.setCredentials(List.of(credentials));
        userR.setEnabled(true);

        try(val response = keycloak.realm(keycloakConfig.getRealm()).users().create(userR)){
            val status = HttpStatus.valueOf(response.getStatus());
            if(status.is4xxClientError()){
                throw new ResponseStatusException(status, status.getReasonPhrase());
            }
        }
    }

    /**
     * Delete user
     * @param jwt The jwt token which is used to extract the username from the request
     * @param username The username that represents the user to delete, in cannot be the same as the extracted
     *                 in the jwt because it will throw 400 status code
     */
    public void deleteUser(Jwt jwt, String username) {
        @NonNull val actualUsername = (String) jwt.getClaims().get("preferred_username");
        if(Objects.equals(actualUsername, username)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot Delete same username");
        }
        val id = keycloak.realm(keycloakConfig.getRealm()).users().list().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .map(UserRepresentation::getId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not EXIST"));


        try(val response = keycloak.realm(keycloakConfig.getRealm()).users().delete(id)){
            val status = HttpStatus.valueOf(response.getStatus());
            if(status.is4xxClientError()){
                throw new ResponseStatusException(status, status.getReasonPhrase());
            }
        }
    }
}