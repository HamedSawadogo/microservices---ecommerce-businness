package org.ecommerce.productservice.infrastructure.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthorizationFilter customAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) {
        return httpSecurity.authorizeHttpRequests(req -> {
           // req.requestMatchers("/api/v1/products").hasAnyRole("USER", "ADMIN");
            req.anyRequest().permitAll();
        }).csrf(AbstractHttpConfigurer::disable)
               // .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))
                .formLogin(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
             //   .oauth2ResourceServer(o -> o.jwt())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             //   .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }


    @Component
    public static class  CustomAuthorizationFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            var authContent = request.getHeader("Authorization");
            if (authContent != null  && authContent.startsWith("Bearer ")) {
               var token = authContent.substring(7);
               if (token.isBlank() || !token.equals("Hamed")) {
                   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid acess");
                   return;
               }
               Authentication authentication = new UsernamePasswordAuthenticationToken("hamed", "13193477",
                      List.of(
//                              new SimpleGrantedAuthority("ROLE_USER"),
//                              new SimpleGrantedAuthority("ROLE_ADMIN")
                      ));
               SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
    }
}
