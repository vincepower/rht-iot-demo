package com.redhat.iot.demo.database.route;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.eclipse.kura.core.message.protobuf.KuraPayloadProto;

import java.util.List;


public class KuraProcess implements Processor{
    @Override
    public void process(Exchange msg) throws Exception {

        KuraPayloadProto.KuraPayload payload = (KuraPayloadProto.KuraPayload) msg.getIn().getBody();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addSerializer(new KuraPayloadSerializer(KuraPayloadProto.KuraPayload.class));
        mapper.registerModule(module);
        List<KuraPayloadProto.KuraPayload.KuraMetric> names = payload.getMetricList();


        String json = mapper.writeValueAsString(payload);
        msg.getIn().setBody(json);
        System.out.println("debug");
    }
}
