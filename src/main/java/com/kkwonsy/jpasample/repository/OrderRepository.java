package com.kkwonsy.jpasample.repository;

import com.kkwonsy.jpasample.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        // tip 동적쿼리를 어떻게 할 것이냐!?
        // -> JPQL + join? ... name이 없으면?
//        em.createQuery("select o from Order o join o.member m" +
//            "where o.status = :status" +
//            "and m.name like :name"
//            , Order.class)
//            .setParameter("status", orderSearch.getOrderStatus())
//            .setParameter("name", orderSearch.getMemberName())
//            .setMaxResults(1000)
//            .getResultList();
        // -> QueryDSL!! 2장 강의에서....-_-
        return em.createQuery("select o from Order o")
                .getResultList();
    }
}
