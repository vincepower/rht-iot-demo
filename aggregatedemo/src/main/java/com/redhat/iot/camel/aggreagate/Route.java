package com.redhat.iot.camel.aggreagate;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;



public class Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        final CsvDataFormat csv =
                new CsvDataFormat()
                        .setIgnoreEmptyLines(true)
                .setUseMaps(true)
                .setCommentMarker('#')
                .setHeader(new String[]{"offset","sensor0","sensor1","sensor2","sensor3","sensor4","sensor5","sensor6"});

        from("stream:file?fileName=/home/mcurwen/dev/src/projects/rhsa/java/iot_demo/aggregatedemo/src/data/Simulator1.csv")
                .unmarshal(csv)
                .delay(1000)
                .process(new Process())
                .to("direct:aggregator")
                .log("${body}").end();

        from("stream:file?fileName=/home/mcurwen/dev/src/projects/rhsa/java/iot_demo/aggregatedemo/src/data2/Simulator2.csv")
                .unmarshal(csv)
                .delay(1000)
                .process(new ProcessTwo())
                .to("direct:aggregator")
                .log("${body}").end();


        from("direct:aggregator")
                .aggregate(constant(true),new Aggregate())
                .completionTimeout(3000L)
                .marshal().json(JsonLibrary.Jackson)
                //.to("stream:file?fileName=/home/mcurwen/dev/src/projects/rhsa/test.json");
                .to("mqtt:okalert?host=tcp://ec-broker-mqtt.redhat-iot.svc:1883&userName=demo-gw2&password=RedHat123!@#&version=3.1.1&qualityOfService=AtMostOnce");

/*
        from("file:/tmp/in/")
                .to("file:/tmp/out/");
                */
    }
}
