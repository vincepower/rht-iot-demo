package com.redhat.iot.demo.simulator.route;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.eclipse.kura.core.message.protobuf.KuraPayloadProto;

import java.io.IOException;
import java.util.List;

public class KuraPayloadSerializer extends StdSerializer<KuraPayloadProto.KuraPayload> {
    protected KuraPayloadSerializer(Class<KuraPayloadProto.KuraPayload> t) {
        super(t);
    }

    public void serialize(KuraPayloadProto.KuraPayload payload, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("offset", payload.getTimestamp());
        //jsonGenerator.writeStringField("body", new String(payload.getBody()));
        jsonGenerator.writeObjectFieldStart("position");
        if (null !=  payload.getPosition()) {
            jsonGenerator.writeNumberField("altitude", payload.getPosition().getAltitude());
            jsonGenerator.writeNumberField("heading", payload.getPosition().getHeading());
            jsonGenerator.writeNumberField("latitude", payload.getPosition().getLatitude());
            jsonGenerator.writeNumberField("longitude", payload.getPosition().getLongitude());
            jsonGenerator.writeNumberField("precision", payload.getPosition().getPrecision());
            jsonGenerator.writeNumberField("sattelites", payload.getPosition().getSatellites());
            jsonGenerator.writeNumberField("speed", payload.getPosition().getSpeed());
            jsonGenerator.writeNumberField("heading", payload.getPosition().getStatus());
            jsonGenerator.writeNumberField("timestamp", payload.getPosition().getTimestamp());
        }
        jsonGenerator.writeEndObject();
        jsonGenerator.writeObjectFieldStart("metrics");
        List<KuraPayloadProto.KuraPayload.KuraMetric> metrics = payload.getMetricList();
        for (KuraPayloadProto.KuraPayload.KuraMetric metric : metrics) {
            if (!"timestamp".equalsIgnoreCase(metric.getName())) {
               jsonGenerator.writeNumberField(metric.getName(), metric.getDoubleValue());
            }
        }
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();


    }
}
