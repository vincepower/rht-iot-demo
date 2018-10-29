package com.redhat.iot.camel.aggreagate;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class Aggregate implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange exchange, Exchange exchange1) {
        Message newIn = exchange1.getIn();
        Object newBody = newIn.getBody();
        ArrayList list = null;
        if (exchange == null) {
            list = new ArrayList();
            list.add(newBody);
            newIn.setBody(list);
            return exchange1;
        } else {
            Message in = exchange.getIn();
            list = in.getBody(ArrayList.class);
            list.add(newBody);
            return exchange;
        }
    }
}
