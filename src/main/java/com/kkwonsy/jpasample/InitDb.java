package com.kkwonsy.jpasample;

import com.kkwonsy.jpasample.domain.*;
import com.kkwonsy.jpasample.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = getMember("userA", "seoul", "1", "1111");
            em.persist(member);

            Book book1 = getBook("JPA1 Book", 10000, 50);
            em.persist(book1);

            Book book2 = getBook("JPA2 Book", 20000, 150);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);
            Order order1 = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order1);
        }

        public void dbInit2() {
            Member member = getMember("userB", "sungnam", "2", "2222");
            em.persist(member);

            Book book1 = getBook("SPRING1 Book", 30000, 100);
            em.persist(book1);

            Book book2 = getBook("SPRING2 Book", 40000, 90);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);
            Order order1 = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order1);
        }

        private Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book getBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Member getMember(String user, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(user);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }
}
