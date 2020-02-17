package com.kkwonsy.jpasample.api;

import com.kkwonsy.jpasample.domain.Address;
import com.kkwonsy.jpasample.domain.Order;
import com.kkwonsy.jpasample.domain.OrderItem;
import com.kkwonsy.jpasample.domain.OrderStatus;
import com.kkwonsy.jpasample.repository.OrderRepository;
import com.kkwonsy.jpasample.repository.OrderSearch;
import com.kkwonsy.jpasample.repository.order.query.OrderQueryDto;
import com.kkwonsy.jpasample.repository.order.query.OrderQueryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;


    // tip 노출하면 안되는 엔티티 노출 예
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            // 강제 초기화(Lazy니까)
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();// 얘가 중요
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    // tip dto로 해서 깔끔하게 나왔지만 쿼리가 너무 많다. 역시나 (지연로딩때문)
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        // dto로 변환
        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
    }

    /**
     * V3.1 엔티티를 조회해서 DTO로 변환 페이징 고려
     * - ToOne 관계만 우선 모두 페치 조인으로 최적화
     * - 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
     * tip 1:n:m -> 1:1:1 로 만들어버림!! 우왕굳
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    // O(N)
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        List<OrderQueryDto> orderQueryDtos = orderQueryRepository.findOrderQueryDtos();
        return orderQueryDtos;
    }

    // O(1)
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        List<OrderQueryDto> orderQueryDtos = orderQueryRepository.findAllByDto_optimization();
        return orderQueryDtos;
    }

    @Getter
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems; // 요게 중요

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(i -> new OrderItemDto(i))
                    .collect(Collectors.toList());
        }
    }

    // tip 원하는 api spec만 깔끔하게
    @Getter
    static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;


        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }

}
