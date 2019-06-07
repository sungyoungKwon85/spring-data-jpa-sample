package com.kkwonsy.example.session.core.model;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Device {

    private String type;
    private Map<String, Object> state;

}
