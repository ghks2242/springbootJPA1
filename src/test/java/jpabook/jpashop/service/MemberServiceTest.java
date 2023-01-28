package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit 실행할떄 스프링이랑 엮어서 실행하려고
@SpringBootTest // 테스트할떄 스프링부트를 띄운상태에서 실행하려고 없으면 오토와일드가 안됨
@Transactional // 이 테스트 케이스 안에서 작성될때 사용하면 기본적으로 롤백시킴
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        // persist 를 한다고 db에 인서트문을 날리진않는다 db 마다 다르지만 보통은 최종적으로 db 트랜잭션이 커밋될떄 플러쉬가 되면서 인서트문 날라감
        Long saveId = memberService.join(member);

        //then
//        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
//        try {
            memberService.join(member2); // 예외가 발생해야한다!!!
//        } catch (IllegalStateException e) {
//            return;
//        }

        //then
        fail("예외가 발생해야 한다.");
    }
}