package com.redhat.iot.demo.simulator.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.protobuf.ProtobufDataFormat;
import org.eclipse.kura.core.message.protobuf.KuraPayloadProto;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application extends RouteBuilder {
    ProtobufDataFormat format = new ProtobufDataFormat(KuraPayloadProto.KuraPayload.getDefaultInstance());
    KuraProcess process = new KuraProcess();

    @Override
    public void configure() throws Exception {

        from("mqtt:incoming?host=tcp://ec-broker-mqtt.redhat-iot.svc:1883&subscribeTopicName=Red-Hat/+/cloudera-demo/facilities/+/lines/+/machines/+&userName=demogw&password=RedHat123!@#")
                .unmarshal().gzip()
                .unmarshal(format)
                .process(process)
                .setHeader("CamelMQTTPublishTopic", simple("/iot-demo/data"))
                .to("mqtt:iot-demo?host=tcp://ec-broker-mqtt.redhat-iot.svc:1883&userName=demo-gw2&password=RedHat123!@#&version=3.1.1&qualityOfService=AtMostOnce");

    }
}
