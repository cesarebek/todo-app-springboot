package com.cezarybek.todoApp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Inject dependencies
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //Extracting the token
            String token = authHeader.substring("Bearer ".length());

            //Validate JWT
            jwtTokenProvider.validateToken(token);

            //Retrieve Username from JWT
            String username = jwtTokenProvider.getUsernameFromJWT(token);

            //Load user associated with JWT
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            //Setting user's info in Spring Boot
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            //??
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //Set Spring Security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    // Bearer <JWT>
    private String getJWTFromRequest(HttpServletRequest request) {

        return null;
    }
}
