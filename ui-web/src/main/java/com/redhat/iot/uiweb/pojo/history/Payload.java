package com.redhat.iot.uiweb.pojo.history;

import lombok.Data;

@Data
public class Payload {
    private Metrics metrics;
    private String type;
}
