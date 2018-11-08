package com.redhat.iot.uiweb.pojo.history;

import lombok.Data;

@Data
public class Metric {
    private String name;
    private String value;
    private String valueType;
}
