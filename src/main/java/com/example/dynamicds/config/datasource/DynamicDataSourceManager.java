package com.example.dynamicds.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
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

        try {

            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:h2:mem:" + tenant + ";DB_CLOSE_DELAY=-1");
            ds.setUsername("sa");
            ds.setPassword("");
            // ---- Rodar as Migrations do Flyway ----
            Flyway.configure()
                    .dataSource(ds)
                    .locations("classpath:db/migration")
                    .load()
                    .migrate();

// Configurações recomendadas
            ds.setMaximumPoolSize(50);
            ds.setMinimumIdle(10);
            ds.setConnectionTimeout(30000);
            ds.setIdleTimeout(600000);
            ds.setMaxLifetime(1800000);


            return ds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
