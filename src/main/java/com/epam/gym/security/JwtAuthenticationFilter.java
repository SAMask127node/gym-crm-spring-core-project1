// src/main/java/com/epam/gym/security/JwtAuthenticationFilter.java
package com.epam.gym.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final BruteForceService bruteForceService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils,
                                   CustomUserDetailsService uds,
                                   BruteForceService bfs) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = uds;
        this.bruteForceService = bfs;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String username = jwtUtils.getUsernameFromJwtToken(token);

            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null
                    && !bruteForceService.isBlocked(username)
                    && jwtUtils.validateJwtToken(token)) {

                var userDetails = userDetailsService.loadUserByUsername(username);
                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
