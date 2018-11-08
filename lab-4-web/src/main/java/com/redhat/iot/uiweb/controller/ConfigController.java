package com.redhat.iot.uiweb.controller;

import com.redhat.iot.uiweb.pojo.config.Config;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @GetMapping("/config")
    public Config getConfig()
    {
        Config conf = new Config();
        conf.setEsf_hostname(System.getenv().getOrDefault("ESF_HOSTNAME",""));
        conf.setEsf_port(System.getenv().getOrDefault("ESF_PORT",""));

        conf.setMqtt_host(System.getenv().getOrDefault("MQTT_WEBSOCKET",""));
        conf.setMqtt_port(System.getenv().getOrDefault("MQTT_WEBSOCKET_PORT",""));
        conf.setMqtt_user_password(System.getenv().getOrDefault("MQTT_USER_PASSWORD",""));
        conf.setMqtt_user_username(System.getenv().getOrDefault("MQTT_USER_USERNAME",""));
        conf.setMqtt_username(System.getenv().getOrDefault("MQTT_USERNAME",""));
        conf.setMqtt_password(System.getenv().getOrDefault("MQTT_PASSWORD",""));
        conf.setMqtt_payload_topic(System.getenv().getOrDefault("MQTT_PAYLOAD_TOPIC",""));

        return conf;
    }
}
