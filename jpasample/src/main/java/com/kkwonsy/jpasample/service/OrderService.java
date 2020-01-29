package com.kkwonsy.jpasample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkwonsy.jpasample.domain.Delivery;
import com.kkwonsy.jpasample.domain.Member;
import com.kkwonsy.jpasample.domain.Order;
import com.kkwonsy.jpasample.domain.OrderItem;
import com.kkwonsy.jpasample.domain.item.Item;
import com.kkwonsy.jpasample.repository.ItemRepository;
import com.kkwonsy.jpasample.repository.MemberRepository;
import com.kkwonsy.jpasample.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    // 롬복의 파워~

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        // tip 따로 가져와야 하지만 예제라서 생략
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // tip 왜 order만 save했냐? cascade가 ALL 이기 때문임.
        // cascade 범위를 어케 해야 하냐?
        // order가 delivery를 관리하고 orderItem을 관리한다.
        // 라이프사이클 측면에서 다른애들은 안쓸때는 ALL을 하는게 좋다.
        // 다른애들이 쓰면 삭제 했을 때 사이드 이펙트가 발생할 것이다.
        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     * 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }

    // tip 참고
    // 비지니스 로직을 대부분 엔티티로 넘겼다
    // create* 메서드가 특히 복잡하다.
    // 이런 스타일 = 도메인 모델 패턴
    // 서비스 계층은 단순히 위임하는 역할을 한다

    // 반대로 서비스 계층에서 대부분의 비지니스 로직을 처리하는 것을 트랜잭션 스크립트 패턴이라고 한다
}
