package com.kkwonsy.jpasample.repository;


import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.kkwonsy.jpasample.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // tip spring boot JPA를 쓰면 PersistenceContext 대신 Autowired가 가능하다.
    // MemberService와 마찬가지로 final + RequiredArgsConstructor를 쓰면 일관성 있음

//    private EntityManager em;

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // tip JPQL: SQL과 조금 다르다. 결국 SQL로 변역된다. SQL은 테이블 대상 쿼리를 한다면 얘는 엔티티를 기준으로 쿼리한다
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
