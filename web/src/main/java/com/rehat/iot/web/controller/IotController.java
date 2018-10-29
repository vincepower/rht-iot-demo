package com.rehat.iot.web.controller;

import com.rehat.iot.web.pojo.SensorData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class IotController {
    static SensorData current = new SensorData();

    @PostMapping("/iot")
    public SensorData post(@RequestBody SensorData data) {
            current = data;
        return data;
    }

    @GetMapping("/iot")
    public SensorData get() {
        return current;
    }

}
