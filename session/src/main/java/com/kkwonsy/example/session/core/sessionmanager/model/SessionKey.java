package com.kkwonsy.example.session.core.sessionmanager.model;


import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class SessionKey implements Serializable {

    private String play;
    private String deviceId;
    private String customId;
}
