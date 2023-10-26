package co.edu.javeriana.pedisoft.usermanager.controller;

import co.edu.javeriana.pedisoft.usermanager.entity.StatusInfo;
import co.edu.javeriana.pedisoft.usermanager.entity.User;
import co.edu.javeriana.pedisoft.usermanager.entity.UserDeleteRequest;
import co.edu.javeriana.pedisoft.usermanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public StatusInfo addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return new StatusInfo("User created");
    }

    @DeleteMapping
    public StatusInfo deleteUser(@AuthenticationPrincipal @NonNull Jwt jwt,
                                                           @Valid @RequestBody UserDeleteRequest body){
        userService.deleteUser(jwt, body.getUsername());
        return new StatusInfo("User Deleted");
    }

}