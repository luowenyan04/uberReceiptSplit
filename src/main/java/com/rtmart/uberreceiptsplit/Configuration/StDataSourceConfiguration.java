package com.rtmart.uberreceiptsplit.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;


@PropertySource(value = "classpath:config/database.properties")
@PropertySource(value = "classpath:config/host.properties")
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.st")
public class StDataSourceConfiguration {

    Map<String, HikariConfig> ue;

    public Map<String, HikariConfig> getUe() {
        return ue;
    }

    public void setUe(Map<String, HikariConfig> ue) {
        this.ue = ue;
    }

    @Bean
    public Map<String, HikariConfig> fpMap() {
        return ue;
    }

    @Bean
    public Map<String, HikariDataSource> stSource(@Qualifier("fpMap") final Map<String, HikariConfig> configMap) {
        Map<String, HikariDataSource> dataSourceMap = new HashMap<String, HikariDataSource>();
        for (String storeNo : configMap.keySet()) {
            dataSourceMap.put(storeNo, new HikariDataSource(configMap.get(storeNo)));
        }
        return dataSourceMap;
    }

    @Bean(name = "stJDBC")
    public Map<String, NamedParameterJdbcTemplate> stJDBC(@Qualifier("stSource") final Map<String, HikariDataSource> dataSourceMap) {
        Map<String, NamedParameterJdbcTemplate> jdbcTemplateMap = new HashMap<String, NamedParameterJdbcTemplate>();
        for (String storeNo : dataSourceMap.keySet()) {
            jdbcTemplateMap.put(storeNo, new NamedParameterJdbcTemplate(dataSourceMap.get(storeNo)));
        }
        return jdbcTemplateMap;
    }
}
