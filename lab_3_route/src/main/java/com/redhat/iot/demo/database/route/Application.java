package com.redhat.iot.demo.database.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.protobuf.ProtobufDataFormat;
import org.eclipse.kura.core.message.protobuf.KuraPayloadProto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application extends RouteBuilder {
    String url = "jdbc:mysql://mariadb:3306/sampledb";
    String user = "userCQ3";
    String password = "RirqCJ2bnpwQLQUI";

    KuraProcess kurProcess = new KuraProcess();
    DBProcessor dbProcessor = new DBProcessor();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configure() throws Exception {
        ProtobufDataFormat format = new ProtobufDataFormat(KuraPayloadProto.KuraPayload.getDefaultInstance());
        String MQTT_HOSTNAME = System.getenv().getOrDefault("MQTT_HOSTNAME","");
        String MQTT_PORT = System.getenv().getOrDefault("MQTT_PORT","");
        String MQTT_USERNAME = System.getenv().getOrDefault("MQTT_USERNAME","");
        String MQTT_PASSWORD = System.getenv().getOrDefault("MQTT_PASSWORD","");
        String MQTT_MACHINE_TOPIC = System.getenv().getOrDefault("MQTT_MACHINE_TOPIC","");
        String MQTT_PAYLOAD_TOPIC = System.getenv().getOrDefault("MQTT_PAYLOAD_TOPIC","");


        //from("mqtt:incoming?host=tcp://ec-broker-mqtt.iot-demo-371b.apps.rhpds310.openshift.opentlc.com:31884&subscribeTopicName=+/+/cloudera-demo/facilities/+/lines/+/machines/machine-1&userName=ec-sys&password=ec-password")
         from("mqtt:incoming?host=tcp://"+MQTT_HOSTNAME+":" + MQTT_PORT +"&subscribeTopicName="+MQTT_MACHINE_TOPIC+"&userName="+MQTT_USERNAME+"&password="+MQTT_PASSWORD + "&connectWaitInSeconds=60")
                .unmarshal().gzip()
                .unmarshal(format)
                .process(kurProcess)
                .to("direct:db", "direct:fwd");


        from("direct:fwd")
                //.setHeader("CamelMQTTPublishTopic", simple("/iot-demo/db/data"))
               // .to("mqtt:iot-demo?host=tcp://ec-broker-mqtt.iot-demo-371b.apps.rhpds310.openshift.opentlc.com:31884&userName=ec-sys&password=ec-password&version=3.1.1&qualityOfService=AtMostOnce");
                .setHeader("CamelMQTTPublishTopic", simple(MQTT_PAYLOAD_TOPIC))
                .to("mqtt:iot-demo?host=tcp://"+MQTT_HOSTNAME+":" + MQTT_PORT +"&userName="+MQTT_USERNAME+"&password="+MQTT_PASSWORD +"&version=3.1.1&qualityOfService=AtMostOnce");

        from("direct:db")
                .process(dbProcessor)
                .to("jdbc:dataSource");
    }
}
