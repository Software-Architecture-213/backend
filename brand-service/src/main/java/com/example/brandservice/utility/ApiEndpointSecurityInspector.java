package com.example.brandservice.utility;

import com.example.brandservice.configuration.PublicEndpoint;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpMethod.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiEndpointSecurityInspector {

    private final RequestMappingHandlerMapping requestHandlerMapping;
    private static final Logger logger = LoggerFactory.getLogger(ApiEndpointSecurityInspector.class);
    @Getter
    private final List<String> publicGetEndpoints = new ArrayList<>();
    @Getter
    private final List<String> publicPostEndpoints = new ArrayList<>();
    @Getter
    private final List<String> publicPutEndpoints = new ArrayList<>();
    @Getter
    private final List<String> publicPatchEndpoints = new ArrayList<>();
    @Getter
    private final List<String> publicDeleteEndpoints = new ArrayList<>();

    @PostConstruct
    public void init() {
        final var handlerMethods = requestHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((requestInfo, handlerMethod) -> {
            if (handlerMethod.hasMethodAnnotation(PublicEndpoint.class)) {
                final var httpMethod = requestInfo.getMethodsCondition().getMethods().iterator().next().asHttpMethod();
                final var apiPaths = requestInfo.getPathPatternsCondition().getPatternValues();

                if (httpMethod.equals(GET)) {
                    publicGetEndpoints.addAll(apiPaths);
                } else if (httpMethod.equals(POST)) {
                    publicPostEndpoints.addAll(apiPaths);
                } else if (httpMethod.equals(PUT)) {
                    publicPutEndpoints.addAll(apiPaths);
                } else if (httpMethod.equals(PATCH)) {
                    publicPatchEndpoints.addAll(apiPaths);
                } else if (httpMethod.equals(DELETE)) {
                    publicDeleteEndpoints.addAll(apiPaths);
                }
            }
        });
    }

    public boolean isUnsecureRequest(@NonNull final HttpServletRequest request) {
        final var requestHttpMethod = HttpMethod.valueOf(request.getMethod());
        var unsecuredApiPaths = getUnsecuredApiPaths(String.valueOf(requestHttpMethod));
        unsecuredApiPaths = Optional.ofNullable(unsecuredApiPaths).orElseGet(ArrayList::new);

        return unsecuredApiPaths.stream().anyMatch(apiPath -> {
            boolean match = new AntPathMatcher().match("/brands" + apiPath, request.getRequestURI());
            logger.info(match ? "Matched" : "Not matched: " + apiPath);
            if (match) {
                logger.info("Match found for path: {}", apiPath);
            }
            return match;
        });
    }

    private List<String> getUnsecuredApiPaths(@NonNull final String httpMethodString) {
        HttpMethod httpMethod = HttpMethod.valueOf(httpMethodString.toUpperCase());

        if (httpMethod.equals(GET)) {
            return publicGetEndpoints;
        } else if (httpMethod.equals(POST)) {
            return publicPostEndpoints;
        } else if (httpMethod.equals(PUT)) {
            return publicPutEndpoints;
        } else if (httpMethod.equals(PATCH)) {
            return publicPatchEndpoints;
        } else if (httpMethod.equals(DELETE)) {
            return publicDeleteEndpoints;
        }
        return Collections.emptyList();
    }
}
