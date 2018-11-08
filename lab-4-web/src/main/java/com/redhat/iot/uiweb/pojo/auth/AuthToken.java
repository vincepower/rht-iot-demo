package com.redhat.iot.uiweb.pojo.auth;

import lombok.Data;

@Data
public class AuthToken {
    private String modifiedBy;
    private String refreshToken;
    private String type;
    private String optlock;
    private String id;
    private String createdOn;
    private String modifiedOn;
    private String tokenId;
    private String createdBy;
    private String refreshExpiresOn;
    private String expiresOn;
    private String userId;
    private String scopeId;
}
