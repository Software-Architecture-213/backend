package com.example.brandservice.filter;

import com.example.brandservice.client.IdentityClient;
import com.example.brandservice.dto.ApiResponse;
import com.example.brandservice.dto.response.ValidatedTokenResponse;
import com.example.brandservice.exception.AppException;
import com.example.brandservice.exception.ErrorCode;
import com.example.brandservice.utility.ApiEndpointSecurityInspector;
import com.example.brandservice.utility.JsonUtility;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private  final IdentityClient identityClient;

    private final ApiEndpointSecurityInspector apiEndpointSecurityInspector;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_CLAIM = "user_id";

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) {

        final var unsecuredApiBeingInvoked = apiEndpointSecurityInspector.isUnsecureRequest(request);

        // Only handle authentication for secured API endpoints
        if (Boolean.FALSE.equals(unsecuredApiBeingInvoked)) {
            final var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

            // Check if the Authorization header is present and starts with Bearer
            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
                final var token = authorizationHeader.replace(BEARER_PREFIX, "");
                ValidatedTokenResponse validatedTokenResponse = identityClient.introspectToken(token);
                if (validatedTokenResponse == null || !validatedTokenResponse.isValidated()) {
                    throw new AppException(ErrorCode.TOKEN_VERIFICATION_FAILURE);
                }
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + validatedTokenResponse.getRole()));
                final var authentication = new UsernamePasswordAuthenticationToken(validatedTokenResponse.getUserId(), null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, ResponseEntity<ApiResponse> errorResponse) throws JsonProcessingException, IOException {
        response.setStatus(errorResponse.getStatusCodeValue());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JsonUtility.convertObjectToJson(errorResponse.getBody()));
        out.flush();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }
}
