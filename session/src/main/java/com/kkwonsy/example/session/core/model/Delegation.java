package com.kkwonsy.example.session.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Delegation<T> {

    private String appStatus;

    private boolean shouldRouteDirectly;

    private T data;
}
