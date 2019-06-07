package com.kkwonsy.example.session.core.model;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Context {

    private Device device;
    private JsonNode supportedInterfaces;
    private Session session;

    private Delegation delegation;
}
