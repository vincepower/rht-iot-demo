package com.redhat.iot.demo.database.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application extends RouteBuilder {
    String url = "jdbc:mysql://localhost:3306/test";
    String user = "username";
    String password = "password";





    @Override
    public void configure() throws Exception {
        DataSource dataSource = setupDataSource(url, user, password);

        SimpleRegistry reg = new SimpleRegistry();
        reg.put("dataSource", dataSource);


        from("mqtt:incoming?host=tcp://ec-broker-mqtt.redhat-iot.svc:1883&subscribeTopicName=Red-Hat/+/cloudera-demo/facilities/+/lines/+/machines/+&userName=demogw&password=RedHat123!@#")
        .to("direct:db", "direct:fwd");


        from("direct:fwd")
            .setHeader("CamelMQTTPublishTopic", simple("Red-Hat/cldr-demo-gw/iot-demo/data"))
            .to("mqtt:iot-demo?host=tcp://ec-broker-mqtt.redhat-iot.svc:1883&userName=demo-gw2&password=RedHat123!@#&version=3.1.1&qualityOfService=AtMostOnce");

        from("direct:db")
                .process(new DBProcessor())
                .to("jdbc:dataSource");
    }

    private static DataSource setupDataSource(String connectURI, String username, String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("user");
        ds.setPassword("password");
        ds.setUrl(connectURI);
        return ds;
    }
}
