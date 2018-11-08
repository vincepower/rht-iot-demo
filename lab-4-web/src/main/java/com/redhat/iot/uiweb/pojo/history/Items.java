package com.redhat.iot.uiweb.pojo.history;

import lombok.Data;

@Data
public class Items {
    private String timestamp;
    private String capturedOn;
    private String receivedOn;
    private Payload payload;
    private String datastoreId;
    private String type;
    private String scopeId;
    private Channel channel;
    private String deviceId;
    private String clientId;
    private String sentOn;
}
