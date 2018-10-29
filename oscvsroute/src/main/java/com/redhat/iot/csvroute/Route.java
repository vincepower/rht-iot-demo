package com.redhat.iot.csvroute;


import com.redhat.iot.csvroute.KuraProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ServiceStatus;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.eclipse.kura.camel.cloud.KuraCloudComponent;
import org.eclipse.kura.camel.component.Configuration;
import org.eclipse.kura.camel.runner.CamelRunner;
import org.eclipse.kura.camel.runner.ServiceConsumer;
import org.eclipse.kura.cloud.CloudService;
import org.eclipse.kura.configuration.ConfigurableComponent;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.eclipse.kura.camel.component.Configuration.asBoolean;
import static org.eclipse.kura.camel.component.Configuration.asLong;
import static org.eclipse.kura.camel.component.Configuration.asString;


public class Route implements ConfigurableComponent {
    private static final Logger logger = LoggerFactory.getLogger(Route.class);
    private static String KURA = "cloud:";
    private static String TOPIC = "simulator-test/assets";
    private static Map<String, String> machineState;
    private static Processor kuraProcessor = new KuraProcessor();
    private Map<String, Object> properties = null;

    static
    {
        machineState = new HashMap<>();
        machineState.put("machine-1", "normal");
        machineState.put("machine-2", "normal");
        machineState.put("machine-3", "normal");
    }

    private static final RouteBuilder NO_ROUTES = new RouteBuilder() {

        @Override
        public void configure() throws Exception {
        }
    };

    private CamelRunner camel;

    private String cloudServiceFilter;

    public void start(final Map<String, Object> properties) throws Exception {
        logger.info("Start: {}", properties);

        this.properties = properties;

        // create new filter and instance

        final String cloudServiceFilter = makeCloudServiceFilter(properties);
        this.camel = createCamelRunner(cloudServiceFilter);

        // set routes

        this.camel.setRoutes(fromProperties(properties));

        // start

        this.camel.start();
    }

    public void stop() throws Exception {
        if (this.camel != null) {
            this.camel.stop();
            this.camel = null;
        }
    }

    private CamelRunner createCamelRunner(final String fullFilter) throws InvalidSyntaxException {
        final BundleContext ctx = FrameworkUtil.getBundle(Route.class).getBundleContext();

        this.cloudServiceFilter = fullFilter;

        // create a new camel CamelRunner.Builder

        final CamelRunner.Builder builder = new CamelRunner.Builder();

        // add service dependency

        builder.dependOn(ctx, FrameworkUtil.createFilter(fullFilter),
                new ServiceConsumer<CloudService, CamelContext>() {

                    @Override
                    public void consume(final CamelContext context, final CloudService service) {
                        context.addComponent("cloud", new KuraCloudComponent(context, service));
                    }
                });

        // return un-started instance

        return builder.build();
    }


    private static String makeCloudServiceFilter(final Map<String, Object> properties) {
        final String filterPid = Configuration.asStringNotEmpty(properties, "cloudService",
                "org.eclipse.kura.cloud.CloudService");
        final String fullFilter = String.format("(&(%s=%s)(kura.service.pid=%s))", Constants.OBJECTCLASS,
                CloudService.class.getName(), filterPid);
        return fullFilter;
    }

    protected RouteBuilder fromProperties(final Map<String, Object> properties) {

        if (!asBoolean(properties, "enabled")) {
            return NO_ROUTES;
        }

        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                // Normal Data
                from(asString(properties, "filespec.normal") + "?include=.*.csv&" + asString(properties, "filespec.options")) //.threads(4) //Poll for file and delete when finished
                        .routeId("normal")
                        .split().tokenize("\\n")//.streaming() //Process each line of the file separately, and stream to keep memory usage down
                        .setHeader("demo.machine", simple("${file:name.noext}"))
                        .process(exchange -> exchange.getIn().setHeader("demo.machineState", machineState.get(exchange.getIn().getHeader("demo.machine"))))
                        .delay(asLong(properties, "interval")).asyncDelayed() //Delay 1 second between processing lines
                        .choice()
                        .when(header("demo.machineState").isEqualTo("normal"))
                        .unmarshal(new CsvDataFormat()
                                .setIgnoreEmptyLines(true)
                                .setUseMaps(true)
                                .setCommentMarker('#')
                                .setHeader(new String[]{"timestamp", "motorid", "speed", "voltage",
                                        "current", "temp", "noise", "vibration"}))
                        .process(kuraProcessor)
                       // .toD("cloud:" + asString(properties, "topic.prefix") + "/${file:name.noext}");
                        .to("mqtt:okalert?host=tcp://ec-broker-mqtt.redhat-iot.svc:1883&userName=demo-gw2&password=RedHat123!@#&version=3.1.1&qualityOfService=AtMostOnce");
//                .to("log:Control?showAll=true&multiline=true");
            }
        };
    }

}
