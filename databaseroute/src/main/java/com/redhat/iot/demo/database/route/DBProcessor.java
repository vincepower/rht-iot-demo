package com.redhat.iot.demo.database.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;

public class DBProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) {
        JSONObject json = new JSONObject(exchange.getIn().getBody());
        String payload = json.toString();
        exchange.getIn().setBody("INSERT into payload (payload) values ('" + payload + "')");
    }
}
