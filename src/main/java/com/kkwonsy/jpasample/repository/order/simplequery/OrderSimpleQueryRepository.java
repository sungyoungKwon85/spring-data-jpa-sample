package com.kkwonsy.jpasample.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
// tip 기본 repository에 있으면 좀 그러해서 분리했음
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    // tip V3랑 비슷하지만 원하는 컬럼만 가지고 왔음
    // tip fetch 조인은 아무래도 다 들고와서 약간의 성능 차이가 있음(미비하다)
    // tip 재사용성이 낮음
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery( // tip select o 이면 OrderQueryDto로 매핑이 안됨...
                "select new com.kkwonsy.jpasample.repository.order.simplequery.OrderSimpleQueryDto(" +
                        "o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }
}
