package com.redhat.iot.csvroute;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.eclipse.kura.message.KuraPayload;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class KuraProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        KuraPayload payload = new KuraPayload();
        payload.setTimestamp(new Date());
        List<Map> metrics = (List<Map>) exchange.getIn().getBody();
        Map<String, String> map =  metrics.get(0); //Each line of the file produces a map of name/value pairs, but we only get one line at a time due to the splitter above
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("motorid")) {
                payload.addMetric(entry.getKey(), Double.parseDouble(entry.getValue()));
            }
        }

        exchange.getIn().setBody(payload);
    }
}
