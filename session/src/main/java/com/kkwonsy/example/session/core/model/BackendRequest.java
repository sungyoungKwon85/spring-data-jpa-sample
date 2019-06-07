package com.kkwonsy.example.session.core.model;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class BackendRequest<T extends Parameter> {

    @NotNull
    private String version;

    private Action<T> action;

    @NotNull
    private Context context;

    private Profile profile;

}
