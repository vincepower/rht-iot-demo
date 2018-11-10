package com.redhat.iot.demo.simulator.route;

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

        ***Student Code ****

        msg.getIn().setBody(json);
    }
}
