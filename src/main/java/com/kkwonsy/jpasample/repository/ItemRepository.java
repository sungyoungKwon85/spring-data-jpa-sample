package com.kkwonsy.jpasample.repository;

import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.kkwonsy.jpasample.domain.item.Item;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        // tip Item은 JPA에 저장하기 전까지 id가 없음. (새로 생성되므로)
        // id가 있으명 이미 db에 있었던 거지
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
            .getResultList();
    }
}
