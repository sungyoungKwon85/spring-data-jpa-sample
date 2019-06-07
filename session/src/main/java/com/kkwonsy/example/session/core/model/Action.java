package com.kkwonsy.example.session.core.model;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Action<T extends Parameter> {

    @NotNull
    private String actionName;

    private T parameters;

}
