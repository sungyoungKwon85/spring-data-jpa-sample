package com.kkwonsy.example.session.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SamplePojo implements Serializable {

    String str;
    Integer id;
}
