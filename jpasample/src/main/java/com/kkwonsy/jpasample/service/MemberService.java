package com.kkwonsy.jpasample.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkwonsy.jpasample.domain.Member;
import com.kkwonsy.jpasample.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
// tip JPA의 모든 데이터 변경은 같은 트랙잭션안에서 실행되어야 한다.
// tip 이 애노테이션은 두개가 있는데 spring꺼를 쓰는게 더 좋다. 쓸 수 있는 옵션들이 많다.
//@Transactional
@Transactional(readOnly = true) // tip 읽기가 많아서 위로 올렸다
@RequiredArgsConstructor
public class MemberService {

    // tip field injection은 테스트할 때 바꾸고 해야 하는데 바꿀 수가 없다. 그래서 setter injection이 낫다
    // setter injection 단점은 runtime 시점에 바꿔버릴 수 있다(거의 없지만)
    // construct injection이 젤 낫다 (어디에 의존하는지 명확하게 알 수 있다)
//    @Autowired
//    private MemberRepository memberRepository;
    // tip final을 해주면 컴파일 시점에 알 수 있어서 더욱 좋다
    private final MemberRepository memberRepository;

    // tip 스프링이 생성자에 의존성이 하나만 있으면 자동으로 해준다.
    // tip RequiredArgsConstructor 애노테이션을 쓰는 것도 좋은 방법이다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가입
     */
    @Transactional // tip 위에서 readOnly가 true여서 따로 또 등록한거임
    public Long join(Member member) {
        validateDuplicateMember(member);

        // tip: command와 query를 분리하라
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 전체 조회
     */
//    @Transactional(readOnly = true) // tip dirty checking 등을 안해서 성능상 유리하다
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     */
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> byName = memberRepository.findByName(member.getName());
        if (!byName.isEmpty()) {
            throw new IllegalStateException("Already exists member");
        }

    }

}
