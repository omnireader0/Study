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

# 14장 예외 처리

### 예외

예상한, 혹은 예상치 못한 일이 발생하는 것을 미리 예견하고 안전장치를 하는 것

컴파일은 되지만 실행할 때 오류가 난다.

### try catch 문

- try 뒤에 중괄호로 예외가 발생하는 문장들을 묶어 주고, catch 괄호 안에 예외가 발생했을 때 처리를 해준다.

- try 블록 안에서 예외 발생되면 그 뒤 문장은 실행되지 않고 catch블록으로 넘어간다.

- try 블록 안에서 예외가 발생하지 않으면 try 내에 모든 문장이 실행되고 try-catch문장 이후의 내용이 실행된다.

- catch블록은 여러 개 작성해도 된다.

- catch블록은 순서가 매우 중요하다.

- catch문장에서 사용할 변수는 try앞에 미리 선언해 놓는다. try문 안에 선언되어 있으면 사용할 수 없다.

- try catch finally 문

  - finally : 어떠한 경우에도 반드시 실행해야 하는 코드 블록

- 모든 예외의 부모 클래스는 java.lang.Exception 클래스다.

- **가장 마지막 catch블록에 Exception클래스를 catch하는 것을 권장한다.** 미처 생각하지 못한 예외가 발생했을 때 처리하기 위함

  catch 블록 중 발생한 예외와 관련있는 블록이 없으면, 예외가 발생되면서 해당 쓰레드는 끝난다. 따라서, 마지막 catch블록에는 Exception 클래스로 묶어주는 버릇을 들여 놓아야 안전한 프로그램이 될 수 있다.

```java
public void multicatch(){
      int[] intArray = new int[5];
      try{
    	  System.out.println(intArray[5]);
    	} catch (ArrayIndexOutOfBoundsException e){
    	  System.out.println("ArrayIndexOutOfBoundsException occurred");
    	} catch (Exception e){
    	  System.out.println(intArray.length);
    	}
  }

  // 출력
  // ArrayIndexOutOfBoundsException occurred
```

- **위 예제이서 두 개의 catch문 순서를 바꾸면 컴파일 에러 발생**
- Exception 클래스는 모든 클래스의 부모 클래스이고, ArrayIndexOutOfBoundsException 클래스는 Exception 클래스의 자식 클래스이기 때문이다. 부모 예외 클래스가 이미 catch 하고, 자식 클래스가 그 아래에서 catch하도록 되어 있는 경우 자식 클래스가 예외를 처리할 수 없어 컴파일 에러가 발생한다.

### 예외 종류 3가지

- ```
  checked exception
  ```

  - error, runtime exception(unchecked exception) 제외한 모든 에러

- ```
  error
  ```

  - 자바 프로그램 밖에서 발생한 예외
  - Error와 Exception으로 끝나는 오류의 가장 큰 차이는 프로그램 안에서 발생했는지, 밖에서 발생했는지 여부
  - Error는 프로세스에 영향을 주고, Exception은 쓰레드에만 영향을 준다.

- ```
  runtime exception 혹은 unchecked exception
  ```

  - 예외가 발생한 것을 미리 감지하기 못했을 때 발생
  - RuntimeException을 확장한 예외들
  - 이 예외를 묶어주지 않는다고 해서 컴파일할 때 예외가 발생하지 않지만실행시에는 발생할 가능성이 있다.
  - 호출하는 메소드에 try-catch로 묶어 주는 것이 좋다.

### Throwable

Exception과 Error 클래스는 java.lang.Throwable클래스를 상속 받는다 그래서 Exception이나 Error를 처리할 때 Throwable로 처리해도 무관하다.

이렇게 상속 관계가 되어 있는 이유는 Exception, Error 클래스는 성격은 다르지만 동일한 이름의 메소드를 사용하여 처리할 수 있기 위함

- Throwable
  - Throwable()
  - Throwable(String message)
  - Throwable(String message, Throwable cause)
    - 예외 메시지, 예외의 원인인 Throwable 객체
  - Throwable(Throwable cause)
- Throwable 클래스에 선언되어 있고, Exception 클래스에서 Overriding한 메소드 중 가장 많이 사용하는 몇몇 메소드
  - getMessage()
    - 예외 메시지를 String 형태로 제공 받는다.
  - toString()
    - 예외 메시지를 String 형태로 제공 받는다.getMessage()보다 더 자세하게 예외 클래스 이름도 같이 제공한다.
  - printStackTrace()
    - 가장 첫 줄에는 예외 메시지를 출력하고, 두 번째 줄부터는 예외가 발생하게 된 메소드들의 호출 관계(스택 트레이스)를 출력해준다.
    - 많은 양의 로그가 쌓이기 떄문에 개발할 때만 사용하는 것을 권고

### throws

- 예외 발생시키는 방법
  - try블록 내에 throw라고 명시한 후 개발자가 예외 클래스의 객체를 생성 하거나 생성되어있는 객체 명시한다. 그러면 throw한 문장 이후에 있는 try블록 내의 문장들은 수행 되지 않고, catch블록으로 이동한다.
  - catch블록 중에 throw한 예외와 동일하거나 상속 관계에 있는 예외가 있다면 그 블록에서 예외를 처리할 수 있다.
- 만약, 해당하는 예외가 없다면 발생된 예외는 메소드 밖(즉 예외가 발생된 메소드를 호출한 메소드로)으로 던진다.

```java
 public void throwsException(int number) throws Exception {
              if(number>12)
                      throw new Exception("Number is over than 12");
              System.out.println("Number is " + number);
      }
```

- throws 구문
  - 이렇게 메소드 선언하면, 예외가 발생했을 때 try-catch로 묶어주지 않아도 그 메소드를 호출한 메소드로 예외 처리를 위임한다.
  - throws 뒤에 선언한 예외가 발생하면 호출한 메소드로 예외가 전달된다.
  - 여러 개일 때 콤마로 구분
  - catch블록에서 예외를 throw할 경우에도 메소드 선언의 throws 구문에 해당 예외가 정의되어 있어야 한다.
  - throws 문장으로 컴파일 오류가 생겼을 경우 해결 방법
    - 호출한 메소드를 try-catch로 묶는다.
    - 호출한 메소드에서도 다시 throws 한다. (권장x)

### 자바 예외 처리 전략

![https://user-images.githubusercontent.com/93963499/151953003-dbd4d722-82d2-4ffd-b608-2011e271ec68.png](https://user-images.githubusercontent.com/93963499/151953003-dbd4d722-82d2-4ffd-b608-2011e271ec68.png)

- 임의의 예외 클래스를 만들 때 Throwable이나 그 자식 클래스(Exception , ... )의 상속을 받아야 한다.
- 자바에서 예외를 처리할 떄에는 표준을 정해 두어야 한다. catch문 내에서 어떻게 처리할지를 명시적으로 선언해 두어야 한다.
- 임의의 예외 클래스를 만들 때에는, 반드시 try-catch로 묶어줄 필요가 있을 경우에만 Exception 클래스를 확장한다. 일반적으로 실행시 예외를 처리할 수 있는 경우에는 RuntimeException 클래스를 확장하는 것을 권장한다.
- catch문에서 로그에 남기는 등의 작업을 하고 예외를 throw를 이용해 던져 주어야 문제가 발생한 정확한 원인을 찾을 수 있다.

### 요약

1. 예외의 종류 세가지는 각각 무엇인가요?

- error, unchecked exception, checked exception

1. 프로세스에 치명적인 영향을 주는 문제가 발생한 것을 무엇이라고 하나요?

- error

1. try나 catch 블록 내에서 예외를 발생시키는 예약어는 무엇인가요?

- throws

1. 메소드 선언시 어떤 예외를 던질 수도 있다고 선언할 때 사용하는 키워드는 무엇인가요?

- throws

1. 직접 예외를 만들 때 어떤 클래스의 상속을 받아서 만들어야만 하나요?

- RuntimeException