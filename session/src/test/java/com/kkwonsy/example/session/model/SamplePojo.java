package com.kkwonsy.example.session.model;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SamplePojo implements Serializable {

    String str;
    Integer id;
    List<String> list;
}
