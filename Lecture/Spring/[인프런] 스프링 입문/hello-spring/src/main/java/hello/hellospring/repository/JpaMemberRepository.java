package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // gradle에 jpa 라이브러리 등록하면 자동 생성됨

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    public Member save(Member member) {
        em.persist(member); // 영구저장하다 persist
        return member;
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // id 조회
        return Optional.ofNullable(member);
    }

    public List<Member> findAll() {
        // Member 엔티티를 조회, Member as m, m이라는 객체 자체를 조회
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }
}