package com.kkwonsy.jpasample.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter // tip 아무대나 Setter를 열어두면 유지보수가 매우 어려워진다. 필요한 로직에 대해서 메서드를 열어야 한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // tip 한곳에서만 생성하도록 할려고 기본 생성자를 protected로 함
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id") // DBA가 관례상 prefix를 붙힌다고 한다.
    private Long id;

    //    @ManyToOne(fetch = FetchType.EAGER) // tip EAGER로 하면 한방에 다 들고 오므로 성능에 매우 문제가 많다. 무조건 LAZY!!
//    @ManyToOne // tip 기본 fetch가 EAGER다 조심!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //    @BatchSize(size = 1000)
    // tip cascade: 전파,
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // tip 기본 fetch가 EAGER다 조심!!
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    // tip 연관관계 메서드
    // 로직을 작성하다보면 한쪽에만 하는 실수를 범하므로 이런 메서드를 작성해두면 좋다
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // tip 생성 메서드
    // 앞으로 생성하는 지점이 있을 때 여기서 관리하는게 좋다
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // tip 비지니스 로직

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("Already delivering.. can't cancel this");
        }
        this.setStatus(OrderStatus.CANCEL); // tip ibatis 같은 걸 쓰면 바깥에서 sql짜서 올려야함. JPA를 쓰면 알아서 해줌 (dirty checking)
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // tip 조회 로직

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream()
            .mapToInt(OrderItem::getTotalPrice)
            .sum();
    }
}
