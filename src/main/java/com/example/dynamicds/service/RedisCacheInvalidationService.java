package com.example.dynamicds.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RedisCacheInvalidationService {

    private final CacheManager cacheManager;
    public void receiveMessage(String tenant) {
        System.out.println("Atualização recebida no Redis para tenant: " + tenant);

        Objects.requireNonNull(cacheManager.getCache("vault-secrets"))
                .evict(tenant);

        System.out.println("Cache do tenant '" + tenant + "' invalidado com sucesso.");
    }
}
