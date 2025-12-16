package com.example.dynamicds.config.datasource;

import com.example.dynamicds.config.context.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final DynamicDataSourceManager manager;

    public DynamicRoutingDataSource(DataSource defaultDs,
                                    DynamicDataSourceManager manager) {
        this.manager = manager;

        Map<Object, Object> map = new HashMap<>();
        map.put("DEFAULT", defaultDs);

        super.setDefaultTargetDataSource(defaultDs);
        super.setTargetDataSources(map);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {

        String tenant = DataSourceContextHolder.getTenant();

        if (tenant == null) {
            return "DEFAULT";
        }

        return tenant;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        String tenant = DataSourceContextHolder.getTenant();

        if (tenant == null) {
            // Hibernate boot está pedindo conexão antes de qualquer request
            return super.getResolvedDefaultDataSource();
        }

        return manager.getOrCreate(tenant);
    }

    @Override
    public void afterPropertiesSet() {
        // Override vazio para evitar validação do mapa "targetDataSources"
    }
}
