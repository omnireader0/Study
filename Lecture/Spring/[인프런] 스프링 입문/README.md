# [인프런] 스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

## 프로젝트 환경 설정

###  프로젝트 설정

환경 

- java11.0.11

- gradle

- dependences : spring web, thymleaf(템플릿 엔진)

프로젝트 실행

- 스프링 부트는 8080포트의 톰캣 서버가 내장되어 있다.

- preference 단축키 : ctrl + alt+s
- gradle 거치지 않고 인텔리제이 실행 : preference -> gradle -> intelliJ 로 바꾸기 , gradle 거치면 느림

### 테스트 라이브러리

- spring-boot-starter-test
  - junit5버전 : 테스트 프레임워크로 5시리즈를 주로 사용
  - mockito : 목 라이브러리
  - assertj : 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
  - spring-test : 스프링 통합 테스트 지원 (junit 을 스프링과 통합하여 테스트를 도와줌)

### view 환경 설정

- cotroller/HelloController.class

  ```java
  @Controller
  public class HelloController {
   @GetMapping("hello")
   public String hello(Model model) {
   model.addAttribute("data", "hello!!");
   return "hello";
   }
  }
  /*
  스프링의 웹의 진입점은 Controller다. 
  어노테이션 GetMapping을 통해 "/hello" url을 연결하고, Model 메서드를 선언한다.
  이 메서드는 "data"라는 키를 통해 "hello!"를 전달하며, return 값은 "hello.html"로 리턴하라는 의미다.
  */
  ```

- resources/templates/hello.html

  ```html
  <!DOCTYPE HTML>
  <html xmlns:th="http://www.thymeleaf.org">
  <head>
   <title>Hello</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  <body>
  <p th:text="'안녕하세요. ' + ${data}" >안녕하세요. 손님</p>
  </body>
  </html>
  <!-- 태그 p에서 ${"data"}는 coctroller 모델에서 전달받은 키이며, 키에 대한 값을 리턴한다.-->
  ```

### 동작 환경

![image](https://user-images.githubusercontent.com/38436013/126335373-015dbdeb-f333-4ace-ba9c-d9e752b993a3.png)

> 웹에 url을 검색 ->  톰캣이 먼저 받고->  /hello과 매핑된 컨트롤러에 전달 -> 컨트롤러는 model의 data 키 값을 html에 리턴 ->  html에서 화면 처리한다.

### 빌드와 실행

서버 배포할 때 jar 복사하고 자바 실행시키면 서버에서 동작함 [윈도우버전]

1. gradlew build : 빌드 하기
2. cd build/lib
3. java -jar hello-spring-0.0.1-SNAPSHOT.jar
4. 실행 확인
5. gradlew clean build : 빌드 지우기



## 스프링 웹 개발 기초

- **정적컨텐츠** : 파일 그냥 웹 브라우저에 전달, 서버 관여 x
- **MVC와 템플릿 엔진** : 서버의 관여, HTML 변환 후 전달
- **API** : 데이터포맷(JSON)으로 클라이언트에게 전달하는 방식

### 정적컨텐츠

![image](https://user-images.githubusercontent.com/38436013/126434128-6fae20a7-ad63-417f-bbdb-b37a71b4794c.png)

> 웹 브라우저에서 위 url로 검색-> 내장 톰캣서버가 요청 받음 -> 서버가 요청을 스프링 컨테이너에게 넘김 -> 1)hello-mvc 컨트롤러 없으므로, 2)html 파일을 찾음 -> html 파일을 웹에 반환

### MVC와 템플릿 엔진

Controller는 모델 + 비지니스 로직 처리에 집중,  View는 화면만 그려냄

![image](https://user-images.githubusercontent.com/93963499/155953142-c56e07cb-afa1-42cc-8fdb-0b46f180dbe3.png)

![image](https://user-images.githubusercontent.com/38436013/126435999-7bf06354-e7cb-4258-89ae-ef10c3e43b57.png)

> 2.1과 다른 점은, viewResolver는 컨트롤러에서 리턴된 html을 찾아서 **템플릿 엔진 처리 후 변환된 html을 웹 브라우저에 리턴**한다.

### API

@ResponseBody

- 뷰 리졸버 사용 안함, 템프릿 엔진을 거치지 않음

- 문자 반환 : 텍스트 형태로 리턴
- 객체 반환 : 객체를 json 으로 변환하여 리턴

![image](https://user-images.githubusercontent.com/38436013/126440324-efa4ce69-d1be-4831-9059-6f90459cffeb.png)

## 회원 관리 예제 - 백엔드 개발

### 비지니스 요구 사항 정리

- 데이터 : 회원 ID, 이름
- 기능 : 회원 등록, 조회
- DB : 아직 선정 X

![image](https://user-images.githubusercontent.com/93963499/156114946-67c75608-29a0-4754-b57e-e81069e36224.png)

- 컨트롤러 :  웹 mvc
- 서비스 : 비지니스 로직
- 리포지토리 : DB접근, 도메인 객체를 DB에 저장하고 관리
- 도메인 : 비지니스 도메인 객체 예) 회원, 주문 , 쿠폰 등 DB에 저장하고 관리됨

![image](https://user-images.githubusercontent.com/93963499/156114973-b41ac15a-da76-46e5-b7ef-9590edba3461.png)

- 아직 db 선정 못해서 인터페이스로 구현 클래스 변경할 수 있도록 설계함.

- MemoryMemberRepository 구현체로 가벼운 메모리 기반의 데이터 저장소 사용

### 회원 도메인과 리포지토리 만들기

회원 도메인 객체 만들기 -> 회원 리포지토리 인터페이스 만들기 -> 회원 리포지토리 구현체 만들기

```java
package hello.hellospring.domain;

public class Member {

    private Long id; // 로그 id, 진짜 id 아님
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

```java
package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원 저장
    Optional<Member> findById(Long id); // 저장소에서 id 찾음
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
```

```java
package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.*;

/*
 동시성 문제가 고려되어 있지 않기 때문에, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
*/

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // id는 시스템이 정해주는 것
        store.put(member.getId(), member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        // null 이 반환될 가능성 -> optional로 감싸서 리턴
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void cleanStore() {
        store.clear();
    }
}
```

### 회원 리포지토리 테스트 케이스 작성

위 로직이 동작하는 것을 테스트 케이스로 통해 확인해보기 , junit 사용

@afterEach를 이용해 각 테스트 메서드가 끝나면 리포지토리 메모리를 청소해주는 기능 필요 -> 그 이유는 리포지토리를 공유하기 때문임

```java
package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

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
```

### 회원 서비스 개발

repository는 데이터 넣었다 뺏다 하는 느낌이라면, service는 비지니스에 가깝다.

```java
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 회원가입
    public Long join(Member member) {
        // 중복 회원 검증하고, 통과하면 저장
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    // 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
```

### 회원 서비스 테스트

- 기존에는 MemberService가 MemoryMemberRepository 객체를 직접 생성

```java
public class MemberService {
 private final MemberRepository memberRepository = new MemoryMemberRepository();
}
```

~~~java
class MemberServiceTest {

    MemberService  memberService ;
    MemoryMemberRepository memberRepository = new MemoryMemberRepository(); // 리포지토리 청소 위해 생성
~~~

-> 이 경우 MemoryService가 생성한 memberRepository랑 테스트에서 만든  memberRepository랑 서로 다름

사실 사실 MemoryMemberRepository의 변수가 static(클래스 변수)이라 문제 없지만 같은 것으로 테스트하고자 함

- MemberRepository의 코드가 MemberService 코드를 DI 가능하게 변경

```JAVA
class MemberServiceTest {

    MemberService  memberService ;
    MemoryMemberRepository memberRepository; // 리포지토리 청소하려고

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
```

```java
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
```

MemberServiceTest에서 memberRepository를 만들고 MemberService(memberRepository) 형태로 MemberService 파라미터에 넣어준다.

MemberService 클래스에서 보면 `MemberService(MemberRepository memberRepository)`확인 가능

```java
package hello.hellospring.service;

import hello.hellospring.domain.Member;

import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


class MemberServiceTest {

    MemberService  memberService ;
    MemoryMemberRepository memberRepository; // 리포지토리 청소하려고

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        // member에 저장한 객체의 이름과 join을 한(리포지에 있는) findMember의 이름이 같은지 테스트
        // given
        Member member = new Member();
        member.setName("Spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        // 단순 등록 말고, 예외 처리 매우 중요
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        
        // then
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then
        // 단순 에러 처리
//       try {
//            memberService.join(member2);
//            fail();
//        }catch(IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
    }

//......
}
```

## 스프링 빈과 의존관계

#### 목표 : 스프링 빈을 등록하는 2가지 방법

- 컴포넌트 스캔과 자동 의존 관계 설정
- 자바 코드로 직접 스프링 빈 등록하기

### 1. 컴포넌트 스캔과 자동 의존관계 설정

회원 컨트롤러가 회원 서비스와 회원 리포지토리를 사용할 수 있게 의존관계를 만들어보자!

**회원 컨트롤러에 의존관계 추가**

~~~JAVA
package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/*
@Controller -> 컴포넌트를 사용하게 되면 스프링에서
스프링 컨테이너는 MemberController 객체를 생성해서 넣어둠 & 스프링이 맡아서 관리
=> 스프링 빈이 관리된다고 표현
* */
@Controller
public class MemberController {
    // new 객체를 생성하지 않고, 스프링 컨테이너에 등록하여 받아서 쓰는 형태로 구현함
    private final MemberService memberService;

    // @Autowired : 스프링이 스프링 컨테이너에 있는 MemberService를 연결시킴
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
~~~

- 생성자에 @Autowired 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어줌. 이렇게 객체 의존관계를 외부에서 넣어주는 것을 DI (Dependency Injection), 의존성 주입이라 한다.
- 이전 테스트에서는 개발자가 직접 주입했고, 여기서는 **@Autowired에 의해 스프링이 주입해준다.**

**오류 발생**

~~~
Consider defining a bean of type 'hello.hellospring.service.MemberService' in your configuration.
~~~

**memberService가 스프링 빈으로 등록되어 있지 않다.**

<img src="https://user-images.githubusercontent.com/38436013/126494877-4cc12b96-59b5-4469-9f06-64e9f7d560e2.png" alt="image" style="zoom:50%;" />

>  해결 방법은 아래를 통해 알아보자!

**컴포넌트 스캔 원리**

- `@Component` 애노테이션이 있으면 **스프링 빈으로 자동 등록된다.** 

- `@Controller` 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.

- `@Component` 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다.
  - `@Controller,  @Service,  @Repository`

**회원 서비스 스프링 빈 등록**

```java
// @Service로 이 클래스가 스프링 연관 객체임을 나타냄
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
```

**회원 리포지토리 스프링 빈 등록**

```java
@Repository
public class MemoryMemberRepository implements MemberRepository {}
```

**싱글톤**

![image](https://user-images.githubusercontent.com/93963499/156183099-41b18374-61ee-4e31-9c65-5c9c188ae4fc.png)

스프링은 스프링 컨테이너에 스프링 빈 등록할 때, 싱글톤으로 등록한다.(유일하게 하나만 등록해서 공유) 따라서 같은 스프링 빈이면 모두 같은 인스턴스다.

> 정리
>
> 컨트롤러 통해서 외부 요청 받고, 서비스 통해 비지니스 로직 처리하고, 리포지토리 통해 데이터 저장하는 일련의 과정을 자세히 설명하면 아래와 같다.
>
> 스프링 뜰 때 컨트롤러, 서비스, 리포지토리를 가져온다. 컨트롤러와 서비스 연결을 위해 Autowired를 생성한다. Autowired를 생성자에 붙이면, 컨트롤러가 생성될 때, 스프링 빈에 등록된 서비스 객체를 가져와 스프링 컨테이너에 주입한다. 이것이 바로 의존성 주입이다.
>
> 서비스와 리포지토리 연결을 위해 Autowired를 생성한다. 서비스 생성자에 Autowired를 붙이면, 스프링이 서비스를 생성할 때, 생성자를 호출하는데 이때, Autowired가 있는 리포지토리를 확인하여 스프링 컨테이너에 리포지토리를 주입한다.

### 2. 자바 코드로 직접 스프링 빈 등록하기

회원 서비스와 회원 리포지토리의 @Service, @Repository, @Autowired 애노테이션을 제거하고 진행한다.

~~~java
package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
~~~

- @Bean를 통해 memberService, memberRepository를 스프링 빈에 올려준다.
- MemberService(memberRepository()) 는 Autowired처럼 스프링 빈에 등록된 리포지토리를 넣어준다.

실무에서는 주로 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다. 그리고 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.

#### 여기서 컴포넌트 스캔 방식 대신 자바 코드로 스프링 빈 사용한 이유는 향후 메모리 리포지토리를 다른 리포지토리로 변경할 예정이기 때문이다.

## 회원 관리 예제 - 웹 MVC 개발

### 홈 화면 추가

> index.html이 무시되는 이유
>
> 요청이 들어오면 관련 컨트롤러 찾고 없으면 static을 찾는다.
>
> -> 컨트롤러가 정적 파일보다 우선순위 높음

- 홈 컨트롤러 추가

~~~java
package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
}

~~~

- 회원 관리형 홈

~~~html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<div class="container">
    <div>
        <h1>Hello Spring</h1>
        <p>회원 기능</p>
        <p>
            <a href="/members/new">회원 가입</a>
            <a href="/members">회원 목록</a>
        </p>
    </div></div> <!-- /container -->
</body>
</html>
~~~

### 등록 기능

#### 회원 등록 폼 개발

- 회원 등록 폼 컨트롤러

~~~java
@Controller
public class MemberController {
    
    private final MemberService memberService;

   
    @Autowired
    public MemberController(MemberService memberService){

        this.memberService = memberService;
    }

    @GetMapping(value="members/new")
    public String CreateForm() {
        return "members/createMemberForm";
    }
~~~

- 회원 등록 폼 HTML ( resources/templates/members/createMemberForm )

~~~html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org"><body>
<div class="container">
    <form action="/members/new" method="post">
        <div class="form-group">
            <label for="name">이름</label>
            <input type="text" id="name" name="name" placeholder="이름을
입력하세요">
        </div>
        <button type="submit">등록</button>
    </form>
</div> <!-- /container -->
</body>
</html>
~~~

#### 회원 등록 컨트롤러

- 웹 등록 화면에서 데이터를 전달 받을 폼 객체

~~~java
package hello.hellospring.controller;
public class MemberForm {
    private String name;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
~~~

- 회원 컨트롤러에서 회원을 실제 등록하는 기능

~~~~java
@PostMapping(value="members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
}
~~~~

### 조회 기능

- 회원 컨트롤러에서 조회 기능

~~~java
@GetMapping(value="/members")
public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/membersList";
    }
~~~

- 회원 리스트 HTML

~~~html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<div class="container">
    <div>
        <table>
            <thead> <tr>
                <th>#</th>
                <th>이름</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="member : ${members}">
                <td th:text="${member.id}"></td>
                <td th:text="${member.name}"></td>
            </tr>
            </tbody>
        </table>
    </div>
</div> <!-- /container -->
</body>
</html>
~~~

> **html 보기** ->  ${members} 은 모델 안의 값을 가져오는 것으로, members는 위 컨트롤러 코드에서 확인할 수 있다. 
>
> **컨트롤러 보기** -> 컨트롤러에서 members는 members라는 키의 members 값으로, 이것은 리스트로 모든 회원을 다 가져온다.
>
> 다시 **html 보기** -> th: each는 반복문 같은 것으로, 루프를 돌면서 members에서 회원 정보를 하나씩 꺼내온다. 여기서 id와 이름을 getId(), getName()으로 가져와 출력한다.

## 스프링 DB 접근 기술

### H2 DB 설치

### JPA

- orm기술로, 객체와 관계형db를 매핑해준다. -> 매핑은 어노테이션으로!
- JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
- JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환을 할 수 있다.
- JPA를 사용하면 개발 생산성을 크게 높일 수 있다

#### build.gradle 파일에 JPA, h2 데이터베이스 관련 라이브러리 추가

~~~java
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
~~~

#### 스프링 부트에 JPA 설정 추가 : resources/application.properties

~~~
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=none
~~~

>show-sql : JPA가 생성하는 SQL을 출력한다. 
>
>ddl-auto : JPA는 테이블을 자동으로 생성하는 기능을 제공하는데 none 를 사용하면 해당 기능을 끈다. create 를 사용하면 엔티티 정보를 바탕으로 테이블도 직접 생성해준다. 

#### JPA 엔티티 매핑

~~~java
@Entity
public class Member {
	// // identity는 db가 생성해주는 id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 로그 id, 진짜 id 아님
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
~~~

#### JPA 회원 리포지토리

~~~java
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
        // Member 엔티티를 조회, Member as m
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
~~~

#### 서비스 계층에 트랜잭션 추가

~~~java
import org.springframework.transaction.annotation.Transactional
    
@Transactional
public class MemberService {}
~~~

> org.springframework.transaction.annotation.Transactional 를 사용하자
>
> 스프링은 해당 클래스의 `메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을 커밋한다.` 
>
> 만약 런타임 예외가 발생하면 롤백한다. 
>
> **JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행**해야 한다

#### JPA를 사용하도록 스프링 설정 변경

~~~java
@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }
    @Bean
    public MemberRepository memberRepository(){
        return new JpaMemberRepository(em); 
    }
~~~

### 스프링 데이터 JPA

>  **스프링 부트와 JPA**만 사용해도 개발 생산성이 정말 많이 증가하고, 개발해야할 코드도 확연히 줄어든다.  여기에 **스프링 데이터 JPA**를 사용하면,  리포지토리에 구현 클래스 없이 인터페이스 만으로 개발`을 완료할 수 있다.반복 개발해온 기본 CRUD 기능도 스프링 데이터 JPA가 모두 제공`한다. 

#### 스프링 데이터 JPA 회원 리포지토리

~~~java
package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>,
        MemberRepository{ // JpaRepository<Member, Long> -> id
    // 상속받고 있으며, 자동으로 구현체를 생성, 스프링data jpa가 이것(SpringDataJpaMemberRepository)을 보고 빈에 자동 등록함
    Optional<Member> findByName(String name);
}
~~~

#### 스프링 데이터 JPA 회원 리포지토리를 사용하도록 스프링 설정 변경

위에서 자동으로 구현체 생성되었기 때문에 주입이 가능해짐

~~~java
@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
}
~~~

#### 스프링 데이터 JPA 제공 클래스

<img src="https://user-images.githubusercontent.com/38436013/126629333-e2585a04-509e-4d30-b972-527a83ceb5e6.png" alt="image" style="zoom:80%;" />

#### 스프링 데이터 JPA 제공 기능

- 인터페이스를 통한 기본적인 CRUD 
- findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공 
- 페이징 기능 자동 제공

> `실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, `  `복잡한 동적 쿼리는 Querydsl이라는 라이브러리` 를 사용하면 된다. Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다. 이 조합으로 해결하기 `어려운 쿼리는 JPA가 제공하는 네이티브 쿼리`를 사용하거나, `앞서 학습한 스프링 JdbcTemplate를 사용`하면 된다.

## AOP

### AOP가 필요한 상황

- 모든 메소드의 호출 시간을 측정하고 싶다면?
- 회원 가입 시간, 회원 조회 시간을 측정하고 싶다면?

#### MemberService 회원 조회 시간 측정 추가

AOP 적용 전, 수동으로 적용

~~~java
@Transactional //  jpa를 사용하려면 트랜잭션 어노테이션 설정, 트랜잭션 안에서 데이터 저장변경
public class MemberService {


    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 회원가입
    public Long join(Member member){

        long start = System.currentTimeMillis();
        try{
            // 중복 회원 검증하고, 통과하면 저장
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();

        }
        finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 회원 조회
    public List<Member> findMembers(){
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
~~~

#### 문제

시간 측정 기능은 핵심 관심 사항이 아니며, 공통 관심 사항이다.

시간 측정 로직과 비즈니스 로직이 섞여서 유지 보수 어렵다.

### AOP 적용

AOP: Aspect Oriented Programming

공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리

시간 측정 로직을 한 곳에 모아두고, 원하는 곳 지정함 - AOP 적용

![image](https://user-images.githubusercontent.com/93963499/157189247-8c4c1a67-7004-4d28-8734-6099b0957eee.png)

#### memberService 수정

~~~JAVA

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 회원가입
    public Long join(Member member) {
        // 중복 회원 검증하고, 통과하면 저장
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    // 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}

~~~

#### AOP 코드

~~~java
@Component
@Aspect
public class TimeTraceAop {
    @Around("execution(* hello.hellospring..*(..))")  // 원하는 조건 : 패키지 내에 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        System.out.println("START: "+ joinPoint.toString());

        try {
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: "+joinPoint.toString()+ " "+ timeMs + "ms");
        }
    }
}
~~~

> START: execution(String hello.hellospring.controller.MemberController.list(Model))
> START: execution(List hello.hellospring.service.MemberService.findMembers())
> START: execution(List org.springframework.data.jpa.repository.JpaRepository.findAll())
> Hibernate: select member0_.id as id1_0_, member0_.name as name2_0_ from member member0_
> END: execution(List org.springframework.data.jpa.repository.JpaRepository.findAll()) 22ms
> END: execution(List hello.hellospring.service.MemberService.findMembers()) 26ms
> END: execution(String hello.hellospring.controller.MemberController.list(Model)) 34ms

실행해서 회원 가입해보면 터미널에 이런 로그가 뜨는데, 여기서 시간을 확인하여 어디서 병목이 생기는지 알 수 있다.

#### 해결

핵심 관심 사항과 시간 측정하는 공통 관심 사항 분리 성공

![image](https://user-images.githubusercontent.com/93963499/157192427-4db961cc-bb5a-4a1f-8c9b-38958d4de7cc.png)

> AOP 적용하면 가짜 프록시를 생성한다.
>  스프링 컨테이너는 빈 등록할 때, 가짜 스프링 빈 먼저 세우고 끝나면, 실제 빈을 호출한다.

## 개념 정리 필요

- 도메인이란? 도메인 객체란?
- 도메인, 엔티티, 테이블 개념 정리

- Optional 클래스란? 

- isEqualTo() ?
- assertThat() , Assertions.assertEquals()

- 테스트 주도 개발 vs 도메인 주도 개발

- 의존성 주입
- 스프링 컨테이너와 스프링 빈?

- joinPoint

## 질문 정리

- `private final MemberRepository memberRepository = new MemoryMemberRepository();`  꼭 final 써야 했는가?

- 정리중 

  

### 단축키 정리(윈도우 버전)

- getter, setter or 테스트 메소드 생성 : alt + insert

- 메소드 추출 : 블록 잡고 ctrl + alt+m

- implements 할 메소드 : alt + enter
- 테스트 코드 생성 : alt + enter
- 심볼의 선언으로 이동 : ctrl + b

### 에러 

~~~
org.h2.jdbc.JdbcSQLInvalidAuthorizationSpecException: Wrong user name or password [28000-200]
~~~

https://www.inflearn.com/questions/94189

