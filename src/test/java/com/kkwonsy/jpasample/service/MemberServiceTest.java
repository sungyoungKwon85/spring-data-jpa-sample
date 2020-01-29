package com.kkwonsy.jpasample.service;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kkwonsy.jpasample.domain.Member;
import com.kkwonsy.jpasample.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    //    @Transactional // tip 테스트가 끝나면 롤백해버림
//    tip insert문이 없음. 커밋이 되야 저장이 됨. 기본적으로 rollback 해버림
//    @Rollback(false) // tip 요거 넣으면 롤백안함
    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kwon");
        // when
        Long savedId = memberService.join(member);
        // then
//        em.flush(); // tip 요거 하면 커밋까지 함!!
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kwon");

        Member member2 = new Member();
        member2.setName("kwon");
        // when
        memberService.join(member1);
        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2); // must occur exception
        });
    }
}