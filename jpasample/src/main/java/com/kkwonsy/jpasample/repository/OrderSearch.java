package com.kkwonsy.jpasample.repository;


import com.kkwonsy.jpasample.domain.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
