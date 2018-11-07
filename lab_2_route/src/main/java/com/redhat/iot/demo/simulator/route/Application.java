package com.redhat.iot.demo.simulator.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.protobuf.ProtobufDataFormat;
import org.eclipse.kura.core.message.protobuf.KuraPayloadProto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application extends RouteBuilder {
    ProtobufDataFormat format = new ProtobufDataFormat(KuraPayloadProto.KuraPayload.getDefaultInstance());
    KuraProcess process = new KuraProcess();


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configure() throws Exception {
        String MQTT_HOSTNAME = System.getenv().getOrDefault("MQTT_HOSTNAME","");
        String MQTT_PORT = System.getenv().getOrDefault("MQTT_PORT","");
        String MQTT_USERNAME = System.getenv().getOrDefault("MQTT_USERNAME","");
        String MQTT_PASSWORD = System.getenv().getOrDefault("MQTT_PASSWORD","");
        String MQTT_MACHINE_TOPIC = System.getenv().getOrDefault("MQTT_MACHINE_TOPIC","");
        String MQTT_PAYLOAD_TOPIC = System.getenv().getOrDefault("MQTT_PAYLOAD_TOPIC","");

        from("mqtt:incoming?host=tcp://"+MQTT_HOSTNAME+":" + MQTT_PORT +"&subscribeTopicName="+MQTT_MACHINE_TOPIC+"&userName="+MQTT_USERNAME+"&password="+MQTT_PASSWORD)
                .unmarshal().gzip()
                .unmarshal(format)
                .process(process)
                .setHeader("CamelMQTTPublishTopic", simple(MQTT_PAYLOAD_TOPIC))
                .to("mqtt:iot-demo?host=tcp://"+MQTT_HOSTNAME+":" + MQTT_PORT +"&userName="+MQTT_USERNAME+"&password="+MQTT_PASSWORD +"&version=3.1.1&qualityOfService=AtMostOnce");

    }
}
