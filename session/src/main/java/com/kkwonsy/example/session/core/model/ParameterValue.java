package com.kkwonsy.example.session.core.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParameterValue {

    private String type;
    private String value;

    @Override
    public String toString() {
        return type + "." + value;
    }
}
