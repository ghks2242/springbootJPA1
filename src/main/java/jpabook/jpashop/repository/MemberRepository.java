package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // 이 어노테이션이 있으면 jpa 엔티티매니저 (스프링이 생성한 엔티티매니저) 를 주입해준다.
//    @Autowired// 스프링부트에 스프링 데이터 jpa 를쓰면 똑같이 오토와일드로 주입가능 (원래는 안되지만 스프링 데이터 jpa 가 지원해줌)
    private final EntityManager em;

    // @PersistenceUnit : 이 어노테이샨을 쓰면 EntityManagerFactory 를 주입받을수도있다

    public void save(Member member) {
        // persist 를 한다고 db에 인서트문을 날리진않는다 db 마다 다르지만 보통은 최종적으로 db 트랜잭션이 커밋될떄 플러쉬가 되면서 인서트문 날라감
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        //  첫번쨰가 jpql 두번쨰가 반환타입
        // jpql 이랑 sql 이랑 은 문법도 같고 거의 같지만 다른점은 sql은 테이블을 대상으로(ex Member) 하지만 jpql 은 엔티티 객체를(Member m) 대상으로한다
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
