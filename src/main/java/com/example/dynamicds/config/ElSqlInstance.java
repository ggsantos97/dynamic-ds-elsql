package com.example.dynamicds.config;

import com.opengamma.elsql.ElSql;
import com.opengamma.elsql.ElSqlConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;

@Slf4j
public class ElSqlInstance {

    public ElSql instance() {
        try {
            Resource[] resources = getResources();

            if (resources.length > 0) {
                URL[] urls = new URL[resources.length];
                for (int i = 0; i < resources.length; i++) {
                    urls[i] = resources[i].getURL();
                }

                return ElSql.parse(ElSqlConfig.POSTGRES, urls);
            }
        } catch (Exception ex) {
            log.info("ElSqlInstance", ex);
        }

        return ElSql.of(ElSqlConfig.POSTGRES, this.getClass());
    }

    private Resource[] getResources() throws IOException {
        ClassLoader classLoader = MethodHandles.lookup().getClass().getClassLoader();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
        return resolver.getResources("classpath*:elsql/**/*.elsql");
    }

}
