package com.example.dynamicds.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicDataSourceManager {


    private final Map<String, DataSource> cache = new ConcurrentHashMap<>();

    public boolean exists(String tenant) {
        return cache.containsKey(tenant);
    }

    public DataSource get(String tenant) {
        return cache.get(tenant);
    }

    public DataSource getOrCreate(String tenant) {
        return cache.computeIfAbsent(tenant, this::create);
    }

    public DataSource create(String tenant) {
//        HikariDataSource ds = new HikariDataSource();
//        ds.setJdbcUrl("jdbc:h2:mem:" + tenant + ";DB_CLOSE_DELAY=-1");
//        ds.setUsername("sa");
//        ds.setPassword("");
        // ---- Rodar as Migrations do Flyway ----
//        Flyway.configure()
//                .dataSource(ds)
//                .locations("classpath:db/migration")
//                .load()
//                .migrate();
        try {

            // pegar dados aqui do ContextHolder ou ir na api do valt aqui
            HikariDataSource ds = new HikariDataSource();
            var database = tenant + "_v8db";
            ds.setJdbcUrl("jdbc:postgresql://172.30.1.228:5432/" + database);
            ds.setUsername("elaw");
            ds.setPassword("elawpw");

            ds.setDriverClassName("org.postgresql.Driver");

// Configurações recomendadas
            ds.setMaximumPoolSize(10);
            ds.setMinimumIdle(1);
            ds.setConnectionTimeout(30000);
            ds.setIdleTimeout(600000);
            ds.setMaxLifetime(1800000);


            return ds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
