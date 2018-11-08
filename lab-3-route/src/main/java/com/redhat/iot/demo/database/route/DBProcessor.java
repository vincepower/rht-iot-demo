package com.redhat.iot.demo.database.route;

import org.apache.camel.Exchange;

public class DBProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) {
        String payload = (String)exchange.getIn().getBody();
        exchange.getIn().setBody("INSERT into payload (payload) values ('" + payload + "')");
    }
}
