package com.esempio.Ecommerce.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.esempio.Ecommerce.model.LocalUser;
import com.esempio.Ecommerce.model.dao.LocalUserDAO;
import com.esempio.Ecommerce.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final LocalUserDAO localUserDAO;

    public JWTRequestFilter(JWTService jwtService, LocalUserDAO localUserDAO) {
        this.jwtService = jwtService;
        this.localUserDAO = localUserDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer")) {
            String token = tokenHeader.substring(7);
            try {
                String username =jwtService.getUsername(token);
                Optional<LocalUser> opUser= localUserDAO.findByUsernameIgnoreCase(username);
                if(opUser.isPresent()){
                    LocalUser user=opUser.get();
                    UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (JWTDecodeException ex) {
            }

        }
        filterChain.doFilter(request, response);
    }
}