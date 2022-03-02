package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>,
        MemberRepository{ // JpaRepository<Member, Long> -> id
    // 상속받고 있으며, 자동으로 구현체를 생성, 스프링data jpa가 이것(SpringDataJpaMemberRepository)을 보고 빈에 자동 등록함
    Optional<Member> findByName(String name);
}