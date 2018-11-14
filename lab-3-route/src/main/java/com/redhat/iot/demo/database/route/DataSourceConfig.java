package com.redhat.iot.demo.database.route;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;




@Configuration
public class DataSourceConfig {
    @Bean("dataSource")
    public DataSource getConfig() {
        String MARIADB_SERVICE = System.getenv().getOrDefault("MARIADB_SERVICE","");
        String MARIADB_USERNAME = System.getenv().getOrDefault("MARIADB_USERNAME","");
        String MARIADB_PASSWORD = System.getenv().getOrDefault("MARIADB_PASSWORD","");
        String MARIADB_PORT = System.getenv().getOrDefault("MARIADB_PORT","");
        String MARIADB_DATABASE = System.getenv().getOrDefault("MARIADB_DATABASE","");
        /*
        String url = "jdbc:mysql://192.168.99.100:30308/sampledb";
        String user = "userCQ3";
        String password = "RirqCJ2bnpwQLQUI";

*/
        String url = "jdbc:mysql://"+MARIADB_SERVICE+":" + MARIADB_PORT +"/" + MARIADB_DATABASE ;
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername(MARIADB_USERNAME);
        ds.setPassword(MARIADB_PASSWORD);
        ds.setUrl(url);
        return ds;
    }
}
