package com.redhat.iot.uiweb.pojo.history;

import lombok.Data;

import javax.validation.Payload;
@Data
public class KapuaMessage {
    private String limitExceeded;
    private Items[] items;
    private String totalCount;
    private String type;
    private String size;
}
