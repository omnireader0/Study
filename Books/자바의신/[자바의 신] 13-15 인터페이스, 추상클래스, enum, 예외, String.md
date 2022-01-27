# 13장 인터페이스와 추상클래스, enum

### **방법론**

시스템을 어떻게 만들지 절차를 설명하고 어떤 문서를 작성해야 하는지 정리해 놓은 공동 절차

- 분석 : 시스템을 분석, 어떻게 개발하기를 원하는지 물어본다.
- 설계 : 대략적인 그림을 프로그램으로 만들 수 있도록 설계, `이 단계에서 인터페이스를 만들어 두면 개발할 때 메소드의 이름을 어떻게 할지, 매개 변수 어떻게 할지 고민하지 않아도 됨`
- 개발 및 테스트 : 개발하고, 필요한 기능이 제대로 동작하는지 확인 및 테스트
- 시스템 릴리즈 : 시스템을 사용자들에게 제공하는 단계. 시스템 오픈 시스템 오픈 이후 운영/유지보수 단계를 거치면서 문제가 있는 부분들을 수정

### **인터페이스**

인터페이스 용도 1 : 선언 과 구현을 구분할 수 있다. (설계 단계에서 인터페이스 만들고, 개발 단계에서 실제 기능 만듦)

- implements는 해당 클래스에서 구현해야 하는 인터페이스들을 정의한다.
- **인터페이스를 구현할 경우(implements 뒤에 인터페이스 이름을 나열할 경우)에는반드시 인터페이스에 정의된 메소드들의 몸통을 만들어 줘야한다.**
- 인터페이스 용도 2 :  외부에 노출되는 것을 정의해 놓는다

```java
 📃 MemberManager.java
public interface MemberManager {
	public boolean addMember(MemberDTO member);
	public boolean removeMember(String name, String phone);
    	public boolean updateMember(MemberDTO member);
}
 📃 MemberMangerImpl.java
public class MemberMangerImpl implements MemberManager {
	@Override
	public boolean addMember(MemberDTO member){
    		return false;
    	}
    	@Override
	public boolean removeMember(String name, String phone){
        	return false;
    	}
    	@Override
    	public boolean updateMember(MemberDTO member){
            	return false;
    	}
}
// Main문

// MemberManager member = new MemberManager();
MemberManager = member = new MemberManageImpl();
```

겉으로 보기에 member 타입은 MemberManager 이지만, 인터페이스에 선언된 메소드들이 구현되어 있는 곳은 MemberManagerImpl 클래스다. 그래서 member에 선언된 메소드들을 실행하면 사실은 MemberManagerImpl에 있는 메소드들이 실행된다.

### **abstract 클래스**

```java
public abstract class MemberManagerAbstract{
 	public abstract boolean addMember(MemberDTO member);
 	public abstract boolean removeMember(String name, String phone);
 	public abstract boolean updateMember(MemberDTO member);
 	public void pringLog(String data){
    	System.out.println("Data="+data);
    }
 }
```

- 추상적인, 메소드가 구현되어 있지 않은 클래스(몸통이 없이 선언한 것)

- abstract 클래스

  - class 예약어 앞에 abstract 사용
  - abstract로 선언한 메소드가 1개 이상있을 때 선언한다. 구현되어 있는 메소드(몸통있는) 있어도 상관없다.
  - static , final 메소드 있어도 된다. (인터페이스는 안됨)

- abstract method

  - 몸통이 없는 메소드
  - 메소드명 앞에 abstract 예약어를 명시한다.

- extends라는 예약어를 사용해 abstract클래스를 상속받을 수 있다.

  이때, abstract 메소드들을 구현하지 않으면 컴파일 에러 발생

- 인터페이스 선언하다 보니 어떤 메소드는 미리 만들어 놓아도 문제가 없는 경우가 발생하는데 그럴 때 사용 → 이럴 때 아주 공통적인 기능 미리 구현해 놓기 위해 사용

### **인터페이스, abstract 클래스 사용 이유**

- 설계시 선언해 두면 개발할 때 기능 구현에 집중 가능
- 개발자의 역량에 따른 메소드의 이름과 매개 변수 선언의 격차를 줄일 수 있음
- 공통적인 인터페이스와 abstract 클래스를 선언해 놓으면, 선언과 구현을 구분할 수 있음
- 설계 단계에서 인터페이스만 만들어 놓고, 개발 단계에서 실제 작업을 수행하는 메소드를 만들면 설계 단계의 산출물과 개발 단계의 산출물이 효율적으로 관리됨

### final

- 클래스, 메소드, 변수에 선언 가능
- final class
  - 더 이상 확장하면 안되는 클래스, 상속해서 변경되면 안되는 클래스 선언할 때 final로 선언
  - **상속 못함**
- final method
  - **더 이상 오버라이딩할 수 없음**
- final variable
  - 상수, **바꿀 수 없음**, 따라서 변수 생성과 동시에 초기화
  - 인스턴스 변수, 클래스 변수는 생성과 동시에 초기화해야 한다.
  - 매개 변수나 지역 변수는 반드시 선언할 때 초기화할 필요가 없다.  `매개 변수는 이미 초기화가 되어서 넘어 오고`, `지역 변수는 메소드를 선언하는 중괄호 내에서만 참조`되므로 다른 곳에서 변경할 일이 없다.
- 해당 클래스가 final이고 그 안에 있는 인스턴스 변수나 클래스 변수가 final이 아닐 때, 변수들은 수정할 수 있다.

```java
public class FinalReferenceType {
	final MemberDTO dto = new MemberDTO();
	re.checkDTO();
}
public void checkDTO() {
	System.out.println(dto);
	//dto = new MemberDTO();  //dto가 final이기 때문에 값을 할당할 수 없음
}
```

- final 적용 → 기본 자료형과 마찬가지로 참조 자료형도 두 번 이상 값을 할당하거나 새로 생성자를 사용하여 초기화할 수 없다.
  - 새로 생성한 dto 객체는 안에 있는 것이 final로 선언된 것이 아니다.

### enum 클래스

```java
public enum OverTimeValues{
    THREE_HOUR(18000),
    FIVE_HOUR(30000),
    WEEKEND_FOUR_HOUR(400000),
    WEEKEND_EIGHT_HOUR(60000);
    private final int amount;
    OverTimeValues(int amount){ // 생성자, public 접근 안됨, enum 클래스 내에서만 사용 가능
    	this.amount=amount;
    }
    public int getAmount(){
    	return amount;
    }
}
```

- final로 문자열이나 숫자 값을 고정시킬 수 있었다. 어떤 클래스가 상수만으로 이뤄져 있다면 반드시 class로 선언할 필요 없다.
- 상수의 집합으로 이뤄진 클래스
- 타입이지만 클래스의 일종이다.
- enum클래스에 있는 상수들은 타입, 값을 지정할 필요 없이 상수들의 이름만 콤마로 구분하여 나열해 준다.
- 가장 효과적으로 enum 클래스를 사용하는 방법은 switch문에서 사용하는 것이다.
- enum 클래스 이름.상수 이름 을 지정함으로써 클래스의 객체 생성이 완료된다.
- enum클래스는 생성자를 만들 수 있지만, 생성자를 통하여 객체를 생성할 수는 없다.
- 값 지정이 가능하지만 동적으로 할당하는 것은 불가능하다.
- 생성자를 사용할 수 있지만아무것도 명시하지 않는 package-private과 private만 접근 제어자로 사용할 수 있다. enum 클래스 내에서만 사용 가능
- java.lang.Enum 클래스의 상속을 받는다. 별도의 클래스를 extends하면 안 된다.
- Enum클래스의 생성자
  - protected Enum(String name, int ordinal)
  - name : enum 상수의 이름
  - ordinal : enum의 순서 상수가 선언된 순서대로 0부터 증가한다.

### 요약

1. 인터페이스에 선언되어 있는 메소드는 body(몸통)이 있어도 되나요?

- 안됨

1. 메소드의 일부만 완성되어 있는 클래스를 무엇이라고 하나요?

- 추상클래스

1. 클래스를 final로 선언하면 어떤 제약이 발생하나요?

- 상속 불가

1. 메소드를 final로 선언하면 어떤 제약이 발생하나요?

- 오버라이딩 불가

1. 변수를 final로 선언하면 어떤 제약이 발생하나요?

- 변경 불가