package com.example.googledrive.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public LoginController(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("/login")
    public String login(@RequestHeader String username, @RequestHeader String password) {
        var auth = new UsernamePasswordAuthenticationToken(username, password);
        var result = authenticationProvider.authenticate(auth);

        if (result.isAuthenticated()) {
            var algoritm = Algorithm.HMAC256("secret code");
            var token = JWT.create()
                    .withSubject(username)
                    .withIssuer("auth0")
                    .withClaim("favoritMat", "tacos")
                    .sign(algoritm);

            return token;
        }

        return "Failed to login.";
    }
}
