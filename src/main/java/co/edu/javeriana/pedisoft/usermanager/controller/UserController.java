package co.edu.javeriana.pedisoft.usermanager.controller;

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
    @ResponseBody public ResponseEntity<String> addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User created");
    }

    @DeleteMapping
    @ResponseBody public ResponseEntity<String> deleteUser(@AuthenticationPrincipal @NonNull Jwt jwt,
                                                           @Valid @RequestBody UserDeleteRequest body){
        userService.deleteUser(jwt, body.getUsername());
        return ResponseEntity.ok("Deleted");
    }

}