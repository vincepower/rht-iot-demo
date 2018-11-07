package com.redhat.iot.camel;


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

        from("stream:file?fileName=/deployments/data/Simulator2.csv")
                .unmarshal(csv)
                .delay(1000)
                .process(new Process())
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE,constant("application/json"))

                .to("http://localhost:8080/iot")
                .log("${body}");
           // .to("stream:file?fileName=/home/mcurwen/dev/src/projects/rhsa/test.json");


/*
        from("file:/tmp/in/")
                .to("file:/tmp/out/");
                */
    }
}
