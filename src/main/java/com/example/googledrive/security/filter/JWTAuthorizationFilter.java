package com.example.googledrive.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authorization filter responsible for authenticating users based on the JWT token present in the request.
 * Extends OncePerRequestFilter to ensure it is invoked only once per request.
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userService;

    public JWTAuthorizationFilter(UserDetailsService userService) {
        this.userService = userService;
    }

    /**
     * Performs the actual JWT authentication by extracting, verifying, and validating the token.
     * If authentication is successful, sets the authentication details in the security context.
     *
     * @param request     The HTTP servlet request.
     * @param response    The HTTP servlet response.
     * @param filterChain The filter chain.
     * @throws ServletException if an error occurs during servlet processing.
     * @throws IOException      if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var algorithm = Algorithm.HMAC256("secret code");
            var verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            var jwt = verifier.verify(authHeader);

            var user = this.userService.loadUserByUsername(jwt.getSubject());

            var auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
        } catch (JWTVerificationException exception) {
            throw new IllegalStateException("Failed to authenticate");
        }
    }
}
