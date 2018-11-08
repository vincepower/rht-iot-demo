package com.redhat.iot.uiweb.pojo.config;

import lombok.Data;

@Data
public class Config {
    String mqtt_host;
    String mqtt_port;
    String mqtt_payload_topic;
    String mqtt_username;
    String mqtt_password;
    String mqtt_user_username;
    String mqtt_user_password;
    String esf_hostname;
    String esf_port;
}
