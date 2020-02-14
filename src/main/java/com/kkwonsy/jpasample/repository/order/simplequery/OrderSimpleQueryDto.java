package com.kkwonsy.jpasample.repository.order.simplequery;

import com.kkwonsy.jpasample.domain.Address;
import com.kkwonsy.jpasample.domain.Order;
import com.kkwonsy.jpasample.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name; // LAZY
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address; // LAZY
    }

    public OrderSimpleQueryDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
    }
}
