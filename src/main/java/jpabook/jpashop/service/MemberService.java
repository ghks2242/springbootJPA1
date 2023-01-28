package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 클래스 레벨에서 트랜잭션을 걸면 public 은 트랜잭션이 먹힌다
//@AllArgsConstructor // 생성자를 만들어준다
@RequiredArgsConstructor // final 이 붙은필드만 생성자를 만들어준다.
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired // 스프링 최신버전에서는 생성자가 하나일떄 오토와일드를 생략할수있다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional // default 는 readOnly = false 이다 스프링은 세부적인설정이 우선권을 갖기때문에 이부분은 readOnly = false 가 적용됨
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
//    @Transactional(readOnly = true)  // 읽기문에는 readOnly = true 넣어주면 jpa 가 조회하는 곳에서 조금더 성능 최적화가됨 읽기에만 넣어야됨
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
