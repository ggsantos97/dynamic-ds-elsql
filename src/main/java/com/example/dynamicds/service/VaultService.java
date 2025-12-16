package com.example.dynamicds.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VaultService {

    private final VaultTemplate vaultTemplate;

    @Cacheable(value = "vault-secrets", key = "#tenant")
    public Map<String, Object> readSecret(String tenant) {
        return Objects.requireNonNull(vaultTemplate
                        .opsForKeyValue("kv", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                        .get(tenant))
                .getData();
    }
}
