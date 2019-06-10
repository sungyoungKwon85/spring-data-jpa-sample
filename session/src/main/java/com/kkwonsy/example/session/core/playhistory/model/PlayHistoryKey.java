package com.kkwonsy.example.session.core.playhistory.model;


import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PlayHistoryKey implements Serializable {

    private String play;
    private String deviceId;
    private String customId;
}
