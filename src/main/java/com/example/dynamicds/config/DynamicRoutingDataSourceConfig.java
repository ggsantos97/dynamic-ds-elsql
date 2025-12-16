package com.example.dynamicds.config;

import com.example.dynamicds.config.datasource.DynamicDataSourceManager;
import com.example.dynamicds.config.datasource.DynamicRoutingDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DynamicRoutingDataSourceConfig {

    @Bean
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:defaultDb;DB_CLOSE_DELAY=-1")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean
    @Primary
    public DynamicRoutingDataSource dynamicRoutingDataSource(
            DataSource defaultDataSource,
            DynamicDataSourceManager manager
    ) {
        return new DynamicRoutingDataSource(defaultDataSource, manager);
    }
}
