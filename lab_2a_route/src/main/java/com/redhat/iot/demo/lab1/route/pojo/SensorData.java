package com.redhat.iot.demo.lab1.route.pojo;

import lombok.Data;

@Data
public class SensorData {
    String offset;
    String name;
    Sensor [] sensors = new Sensor[7];
}
