package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 전체 테스트를 실행하면, 테스트는 순서를 보장하지 않기 때문에 오류 발생.
    // 각 테스트 메서드마다 실행 끝나면 메모리 clear 해주는 메서드를 불러오는 콜백함수
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save(){
        // 저장된 객체 member와 리포지토리에서 꺼낸 객체 result를 비교
        Member member = new Member();
        member.setName("spring");
        repository.save(member);
        Member result=repository.findById(member.getId()).get();
        //Assertions.assertEquals(member, result); // junit.jupiter.api.Assertions;
        assertThat(member).isEqualTo(result); // org.assertj.core.api.Assertions.*;
    }

    @Test
    public void findByName(){

        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        Member result = repository.findByName("Spring1").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}