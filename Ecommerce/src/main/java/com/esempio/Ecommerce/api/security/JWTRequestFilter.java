package com.esempio.Ecommerce.api.security;

import com.esempio.Ecommerce.model.Entity.LocalUser;
import com.esempio.Ecommerce.model.repository.LocalUserRepository;
import com.esempio.Ecommerce.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final LocalUserRepository localUserRepository;

    public JWTRequestFilter(JWTService jwtService, LocalUserRepository localUserRepository) {
        this.jwtService = jwtService;
        this.localUserRepository = localUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer")) {
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<LocalUser> opUser = localUserRepository.findByUsernameIgnoreCase(username);
                if (opUser.isPresent()) {
                    LocalUser user = opUser.get();

                    // Estrazione dei ruoli
                    List<String> roles = jwtService.getRoles(token);
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception ex) {
                // Gestione delle eccezioni
                ex.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}
