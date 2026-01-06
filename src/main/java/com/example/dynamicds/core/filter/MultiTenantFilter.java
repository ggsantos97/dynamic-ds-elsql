package com.example.dynamicds.core.filter;

import com.example.dynamicds.config.context.DataSourceContextHolder;
import com.example.dynamicds.config.context.VaultContextHolder;
import com.example.dynamicds.service.VaultService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MultiTenantFilter extends OncePerRequestFilter {
    private final VaultService vaultService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final Set<String> EXCLUDED_PATHS = Set.of(
            "/actuator/**",
            "/docs",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/error",
            "/h2-console/**"
    );

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        var requestURI = request.getRequestURI();

        if (isExcludedPath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tenant = request.getHeader("X-DS");
        if (StringUtils.isEmpty(tenant) && tenant.isBlank()) {
            throw new RuntimeException("Header X-DS obrigatório");
        }
        try {
            // tentar chamar vault
            var props= vaultService.readSecret(tenant);
            VaultContextHolder.setPropsVault(props);
            DataSourceContextHolder.setTenant(tenant);
        } catch (Exception e) {
            throw new RuntimeException("Nao foi possivel obter as configuraçoes do tenant");
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            DataSourceContextHolder.clear();
        }

    }

    private boolean isExcludedPath(String requestURI) {
        return EXCLUDED_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }
}
