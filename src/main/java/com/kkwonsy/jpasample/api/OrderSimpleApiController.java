package com.kkwonsy.jpasample.api;

import com.kkwonsy.jpasample.domain.Address;
import com.kkwonsy.jpasample.domain.Order;
import com.kkwonsy.jpasample.domain.OrderStatus;
import com.kkwonsy.jpasample.repository.OrderRepository;
import com.kkwonsy.jpasample.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        // tip Order안에 Member가 있고 Member안에 Order가 있어서 무한루프에 빠짐
        // 해결책은 한쪽에 @JsonIgnore를 해야 함
        // 엔티티에다가??

        // JsonIgnore해도 문제가 있음
        // fetch가 LAZY이다 보니 하이버네이트가 null인 Member를 PROXY를 통해 new Member를 해버림
        // 문제는 Jackson lib가 Member가 ByteBuddyInterceptor로 생성되어 있어서 파싱 오류를 뱉어버림
        // 고걸 막는게 Hibernate5Module임
        // 요너석 lib를 dependency에 추가하고, Bean으로 등록해야함 (여튼 쓰면 안됨)
        // 여튼 이걸해도 LAZY 하게 불러와야 하는 녀석들이 다~ 불러와짐

        // 그래서 아래 로직을 넣으면 Lazy 강제 초기화가 됨
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }

        // 그래도 너무 많은 것들이 노출되고 있음. 운영 헬

        return all;
    }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getMember().getAddress();
        }
    }


}
