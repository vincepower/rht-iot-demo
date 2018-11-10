package com.redhat.iot.uiweb.controller;

import com.redhat.iot.uiweb.pojo.auth.Auth;
import com.redhat.iot.uiweb.pojo.auth.AuthToken;
import com.redhat.iot.uiweb.pojo.history.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class HistoryContoller {
    private final String APIBASE = System.getenv().getOrDefault("ESF_HOSTNAME",""); //"http://ec-api-iot-demo-371b.apps.rhpds310.openshift.opentlc.com";
    private final String USERNAME= System.getenv().getOrDefault("ESF_USERNAME",""); //"user1";
    private final String PASSWORD= System.getenv().getOrDefault("ESF_PASSWORD",""); //PASSWORD = "Infosys123!@#";

    private final String AUTHURL = APIBASE + "/v1/authentication/user";
    private final String HISTORYURL =APIBASE + "/v1/_/data/messages?offset=0&limit=50";

    @GetMapping("/history/{sensor}")
    public ChartData getSensorHistory(@PathVariable String sensor) {
       // List<Double> data = new ArrayList<>();
        ChartData retval = new ChartData();

        switch (sensor.toLowerCase()) {
            case "current":
                retval.setLowerLimit(15);
                retval.setUpperLimit(55);
                break;
            case "temperature":
                retval.setLowerLimit(10);
                retval.setUpperLimit(90);
                break;
            case "noise":
                retval.setLowerLimit(30);
                retval.setUpperLimit(40);
                break;
            case "speed":
                retval.setLowerLimit(1500);
                retval.setUpperLimit(200);
                break;
            case "vibration":
                retval.setLowerLimit(0.05);
                retval.setUpperLimit(0.5);
                break;
            case "voltage":
                retval.setLowerLimit(200);
                retval.setUpperLimit(250);
                break;
        }

        KapuaMessage message = new KapuaMessage();

        RestTemplate authTemplate = new RestTemplate();

        List <MediaType> accepts = new ArrayList<>();
        accepts.add(MediaType.APPLICATION_JSON);

       *** Auth Code****

        if (null != token) {


            if (null != historyResponse && null != historyResponse.getBody()) {
                DecimalFormat df = new DecimalFormat("#.##");
                message = historyResponse.getBody();
                for (Items item : message.getItems()) {
                    ChartPoint point = new ChartPoint();
                    boolean found = false;
                    for (Metric metric : item.getPayload().getMetrics().getMetric()) {
                        if (sensor.equalsIgnoreCase(metric.getName())) {
                            if ("double".equalsIgnoreCase(metric.getValueType())) {
                                double ind = Double.valueOf(metric.getValue());
                                point.setVal_0(Double.valueOf(df.format(ind)));
                                found = true;
                            }
                        } else {
                            if ("timestamp".equalsIgnoreCase(metric.getName())) {
                                long timestamp = (long)(Double.valueOf(metric.getValue()).doubleValue());
                                System.out.println(timestamp);
                                point.setX(timestamp);
                            }
                        }
                    }

                    if (found) {
                        retval.getDataset0().add(point);
                    }
                }
            }
        } else {
            retval.setHasData(false);
        }

        return retval;
    }


}
