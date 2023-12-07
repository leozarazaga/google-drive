package com.example.googledrive.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user login.
 * Provides an endpoint for user authentication and token generation.
 *
 * The class is annotated with Spring's RestController, indicating that it handles RESTful API requests.
 * Each method is documented to describe its purpose and functionality.
 */
@RestController
public class LoginController {
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public LoginController(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Endpoint for user login authentication.
     *
     * @param username The username obtained from the request header.
     * @param password The password obtained from the request header.
     * @return A JSON Web Token (JWT) for successful authentication, or an error message for failed login.
     */
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
