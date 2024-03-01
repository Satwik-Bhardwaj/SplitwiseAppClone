package com.satwik.splitwiseclone.configuration.filter;

import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import com.satwik.splitwiseclone.configuration.security.LoggedInUser;
import com.satwik.splitwiseclone.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoggedInUser loggedInUser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // read the token from the header
        String token = request.getHeader("Authorization");

        if(token != null) {
            // get the user id using the token
            String userId = jwtUtil.getUserId(token);
            // username should not be empty, cont-auth must be empty
            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null)  {

                // get the user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                // validate token
                boolean isValid = jwtUtil.validateToken(token, userDetails.getUsername());

                if(isValid) {
                    loggedInUser.setUserId(UUID.fromString(userId));

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, userDetails.getPassword(), userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
        }

        filterChain.doFilter(request, response);
    }
}
