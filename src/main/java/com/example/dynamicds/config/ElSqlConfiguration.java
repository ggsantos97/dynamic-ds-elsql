package com.example.dynamicds.config;

import com.opengamma.elsql.ElSql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ElSqlConfiguration {

    @Bean
    public ElSql elSql() throws IOException {
        return new ElSqlInstance().instance();
    }
}
