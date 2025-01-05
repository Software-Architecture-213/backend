package com.example.identityservice.filter;

import com.example.identityservice.client.FirebaseAuthClient;
import com.example.identityservice.dto.ApiResponse;
import com.example.identityservice.exception.GlobalExceptionHandler;
import com.example.identityservice.exception.TokenVerificationException;
import com.example.identityservice.utility.ApiEndpointSecurityInspector;
import com.example.identityservice.utility.JsonUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private GlobalExceptionHandler globalExceptionController;

    private final FirebaseAuthClient firebaseAuthClient;
    private final ApiEndpointSecurityInspector apiEndpointSecurityInspector;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String userId_CLAIM = "userId";

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) {

        final var unsecuredApiBeingInvoked = apiEndpointSecurityInspector.isUnsecureRequest(request);

        // Only handle authentication for secured API endpoints
        if (Boolean.FALSE.equals(unsecuredApiBeingInvoked)) {
            final var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

            // Check if the Authorization header is present and starts with Bearer
            if (StringUtils.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX)) {
                final var token = authorizationHeader.replace(BEARER_PREFIX, StringUtils.EMPTY);

                try {
                    // Verify the Firebase token
                    final var tokenData = firebaseAuthClient.verifyIdToken(token);

                    if (tokenData == null || !tokenData.isValidated()) {
                        ResponseEntity<ApiResponse> errorResponse = globalExceptionController.handleTokenVerificationException(new TokenVerificationException());
                        writeErrorResponse(response, errorResponse);
                        return;
                    }

                    String role = tokenData.getRole();
                    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
                    final var authentication = new UsernamePasswordAuthenticationToken(tokenData.getUserId(), null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (IllegalStateException e) {
                    logger.error("Authentication failed: {}", e);

                    ResponseEntity<ApiResponse> errorResponse = globalExceptionController.handleTokenVerificationException(new TokenVerificationException());
                    response.setStatus(errorResponse.getStatusCodeValue());

                    PrintWriter out = response.getWriter();
                    out.print(JsonUtility.convertObjectToJson(errorResponse.getBody()));
                    out.flush();

                    return;

                } catch (IllegalArgumentException e) {
                    // Handle token format errors or other argument-related exceptions
                    logger.error("Invalid token format: {}", e);
                    /*response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid token format");
                    return; // Prevent further processing of the request*/

                    ResponseEntity<ApiResponse> errorResponse = globalExceptionController.handleTokenVerificationException(new TokenVerificationException());
                    writeErrorResponse(response, errorResponse);

                    return;
                } catch (Exception e) {
                    // Log the error and respond with a 401 Unauthorized status
                    logger.error("Authentication failed: {}", e);
                    /*response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Authentication failed: " + e);
                    return; // Prevent further processing of the request*/

                    ResponseEntity<ApiResponse> errorResponse = globalExceptionController.handleTokenVerificationException(new TokenVerificationException());
                    writeErrorResponse(response, errorResponse);

                    return;
                }
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
