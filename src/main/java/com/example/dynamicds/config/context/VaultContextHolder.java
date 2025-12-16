package com.example.dynamicds.config.context;

import java.util.Map;

public class VaultContextHolder {
    private static final ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<>();

    public static void setPropsVault(Map<String, Object> vaultProps) {
        CONTEXT.set(vaultProps);
    }

    public static Map<String, Object> getProps() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
