package com.kkwonsy.jpasample.service;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kkwonsy.jpasample.domain.Address;
import com.kkwonsy.jpasample.domain.Member;
import com.kkwonsy.jpasample.domain.Order;
import com.kkwonsy.jpasample.domain.OrderStatus;
import com.kkwonsy.jpasample.domain.item.Book;
import com.kkwonsy.jpasample.exception.NotEnoughStockException;
import com.kkwonsy.jpasample.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// tip. 좋은 test 는 spring도 안엮고 db도 없이 단위 테스트를 하는거다.
// 비즈니스 메서드들을 기준으로 테스트 케이스를 만들면 좋을 듯
// 여기서는 JPA를 보기위해 엮어서 한거임.

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember("kwon");

        Book book = createBook("my JPA", 30000, 10);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "OrderStatus should be ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "OrderStatus should be ORDER");
        assertEquals(30000 * orderCount, getOrder.getTotalPrice(), "OrderTotalPrice should be ...");
        assertEquals(8, book.getStockQuantity(), "OrderSize should be 8");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("seoul", "sungnam", "123-123"));
        em.persist(member);
        return member;
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember("kwon");
        Book book = createBook("my jpa", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(10, book.getStockQuantity());
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember("kwon");
        Book book = createBook("my JPA", 30000, 10);

        int orderCount = 11;
        // when
        // then
        assertThrows(NotEnoughStockException.class, () -> {
            Long getOrder = orderService.order(member.getId(), book.getId(), orderCount);
        });
    }


}