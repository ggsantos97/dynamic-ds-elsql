package com.example.dynamicds.core.filter;

import com.example.dynamicds.config.context.DataSourceContextHolder;
import com.example.dynamicds.config.context.VaultContextHolder;
import com.example.dynamicds.service.VaultService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MultiTenantFilter implements Filter {
    private final VaultService vaultService;
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
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
            chain.doFilter(req, res);
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}
