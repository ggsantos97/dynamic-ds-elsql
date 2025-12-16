package com.example.dynamicds.config.vault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class VaultConfig {
    @Value("${spring.vault.url}")
    private String url;
    @Value("${spring.vault.port}")
    private Integer port;
    @Value("${spring.vault.token}")
    private String token;
    @Bean
    public VaultTemplate vaultTemplate(VaultEndpoint endpoint, ClientAuthentication clientAuth) {
        return new VaultTemplate(endpoint, clientAuth);
    }

    @Bean
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.create(url, port);
    }

    @Bean
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication(token);
    }
}
