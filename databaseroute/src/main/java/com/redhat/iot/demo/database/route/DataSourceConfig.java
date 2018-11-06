package com.redhat.iot.demo.database.route;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;




@Configuration
public class DataSourceConfig {
    @Bean("dataSource")
    public DataSource getConfig() {
        String url = "jdbc:mysql://192.168.99.100:30308/sampledb";
        String user = "userCQ3";
        String password = "RirqCJ2bnpwQLQUI";

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setUrl(url);
        return ds;
    }
}
