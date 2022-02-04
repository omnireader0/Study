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

# 15장 String

### String 클래스 선언

```java
public final class String extends Object
implements Serializable, Comparable<String>, CharSequence
```

- 클래스 final로 선언 : 더 이상 이 클래스는 확장할 수 없음, 자식 클래스 양산 못함

- 인터페이스들

  - Serializable

    구현해야 하는 메소드가 하나도 없는 특별한 인터페이스다.

    Serializable를 implements하면 해당 객체를 파일로 저장하거나 다른 서버에 전송 가능한 상태가 된다.

  - Comparable

    compareTo() 메소드 하나만 선언되어 있다. 매개 변수로 넘어가는 객체와 현재 객체가 같은지 비교 같으면0, 순서 상으로 앞에 있으면 -1, 뒤에 있으면 1을 리턴객체의 순서를 처리할 떄 유용하게 사용될 수 있다.

  - CharSequence

    해당 클래스가 문자열을 다루기 위한 클래스 라는 것을 명시적으로 나타낸다.

### String 생성자

charset : 문자의 집합

### String 문자열을 byte로 변환하기

- getBytes(Charset charset)
  - 지정한 캐릭터 셋 객체 타입으로 바이트 배열을 생성
  - 현재의 문자열 값을 byte배열로 변환하는 메소드
  - 플랫폼의 기본 캐릭터 셋으로 변환
  - 자바에서 많이 사용하는 캐릭터 셋 - UTF-16

```java
 public void convertUTF16(){
          try{
              String korean = "한글";
              byte[] array1 = korean.getBytes("UTF-16");
              printByteArray(array1);
              System.out.println(korean2);
          }catch(Exception e){
              e.printStackTrace();
          }
      }

      public void printByteArray(byte[] array){
          for(byte data:array){
              System.out.print(data+" ");
          }
          System.out.println();
      }

      //-2 -1 -43 92 -82 0
      //한글
```

- 캐릭터 셋을 지정하는 메소드 및 생성자들은 존재하지 않는 캐릭터 셋의 이름을지정하는 경우 UnsupportedEncodingException을 발생시킬 수 있다.  따라서 반드시 try-catch감싸주거나 메소드 선언시 throws 구문을 추가해준다.

### 객체의 널체크 필수

- String 메소드를 사용하기 전 널 체크를 반드시 해야 한다. (다른 모든 객체도 마찬가지)
- 객체가 널이라는 것은 **아무런 초기화가 되어 있지않으며**, 클래스에 선언되어 있는 어떤 메소드도 사용할 수 없다는 의미
- 특히, 메소드의 매개변수로 넘어오는 객체가 널이 될 확률이 있다면 체크해볼 필요

### String 클래스의 객체의 내용을 비교, 검색하는 메소드

- **문자열의 길이를 확인하는 메소드**

  - 배열도 객체이지만. 메소드는 없는 특수한 객체로 length 사용

- **문자열이 비어 있는지 확인하는 메소드**

- **문자열이 같은지 비교하는 메소드**

  - 매개변수로 넘어온 값과 String 객체가 같은지 비교!!

  - 자바에서 **String 클래스를 비교할 떄 ==로 비교 가능(equals()와 같은 결과)** : 그 이유는

    자바에서는 객체들을 재사용하기 위해 Constant Pool이 있고, String의 경우 동일한 값을

    갖는 객체가 있다면 이미 만든 객체를 재사용한다.

- **특정 조건에 맞는 문자열 있는지 확인하는 메소드**

매개 변수로 넘겨준 값으로 시작하는지 확인

매개 변수로 넘어온 값으로 해당 문자열이 끝나는지 확인

매개 변수로 넘어온 값이 문자열에 존재하는지 확인

매개 변수로 넘어온 값이 정규 표현식(공식에 따라 만든 식)으로 되어 있어야한다.

문자열 중에서 특정 영역이 매개 변수로 넘어온 문자열과 동일한지를 확인하는 데 사용

ignoreCase : true일 경우 대소문자 구분을 하지 않고, 값을 비교한다.

toffset : 비교 대상 문자열의 확인 시작 위치를 지정한다.

other : 존재하는지를 확인할 문자열을 의미한다.

ooffset : other 객체의 확인 시작 위치를 지정한다.

len : 비교할 char의 개수를 지정한다.

### String 내에서 위치를 찾아내는 방법

- indexOf()
  - 앞에서부터 **해당 객체의 특정 문자열이나 char가 있는 위치**를 알 수 있다. 없으면 -1을 리턴한다.
- int indexOf(int ch, int fromIndex)
- int indexOf(String str)
- int indexOf(String str, int fromIndex)
- int lastIndexOf(int ch)
  - 뒤에서부터 문자열이나 char 찾는다. 시작 위치는 가장 왼쪽에서 부터이다.
- int lastIndexOf(int ch, int fromIndex)
- int lastIndexOf(String str)
- int lastIndexOf(String str, int fromIndex)

### String 값의 일부를 추출하기 위한 메소드들

- char 단위의 값을 추출하는 메소드
  - char charAt(int index) : 특정 위치의 char값 리턴
- String의 값을 char 배열로 변환
  - chrar[] toCharArray() : 문자열을 char 배열로 변환
- 문자열의 일부 값을 잘라내는 메소드
  - String substring(int beginIndex)
    - beginIndex부터 끝까지 대상 문자열을 잘라 String으로 리턴
  - String substring(int beginIndex, in endIndex)
    - beginIndex부터 endIndex까지 대상 문자열을 잘라 String으로 리턴
  - CharSequence subSequence(int beginIndex, int endIndex)
    - beginIndex부터 endIndex까지 대상 문자열을 잘라 CharSequence타입으로 리턴
- 문자열을 여러 개의 String 배열로 나누는 메소드
  - String[] spilit(String regex)
    - regex에 있는 정규 표현식에 맞추어 문자열을 잘라 String의 배열로 리턴
  - String[] split(String regex, int Limit)
    - regex에 있는 정규 표현식에 맞추어 문자열을 잘라 String의 배열로 리턴. 이때 String 배열의 크기는 limit보다 커서는 안된다.
  - 문자열을 여러개의 문자열의 배열로 나누는 방법은 split()과 StringTokenizer가 있다.

### String 값을 바꾸는 메소드

- **문자열을 합치는 메소드와 공백을 없애는 메소드**

  - String trim()

    - 문자열의 맨 앞과 맨 뒤에 있는 공백들을 제거한 문자열 객체 리턴

    ```jsx
    String text = " a ";
    if(text!=null && text.trim().length() > 0) {
    	System.out.println("ok");
    }
    // ok
    ```

- **내용을 교체하는 메소드**

  - String repalce(char oldChar, char newChar)
    - 해당 문자열에 있는 oldChar의 값을 newChar로 대치
  - String replace(CharSequence target, CharSequence replacement)
    - 해당 문자열에 있는 target과 같은 값을 replacement로 대치
  - String replaceAll(String regex, String replacement)
    - 해당 문자열의 내용 중 regex에 표현된 정규 표현식에 포함되는 모든 내용을 replacement로 대치
  - String replaceFirst(String regex, String replacement)
    - 해당 문자열의 내용 중 regex에 표현된 정규 표현식에 포함되는 첫번째 내용을 replacement로 대치
  - replace~~ 시작되는 메소드는 기존 문자열의 값을 바꾸지는 않는다.

- **특정 형식에 맞춰 값을 치환하는 메소드**

  - 정해진 기준에 맞춘 문자열이 있으면, 그 기준에 있는 내용을 변환
  - %s는 String, %d는 정수형, %f는 소수점이 있는 숫자, %%는 %를 의미한다.
  - staitc String format(String format, Object.. args)
    - format에 있는 문자열의 내용 중 변환해야 하는 부분을 args의 내용으로 변경
  - static String format(Locale l, String format, Object... args)
    - format에 있는 문자열의 내용 중 변환해야 하는 부분을 args의 내용으로 변경단, 첫 매개 변수인 Locale타입의 l에 선언된 지역에 맞추어 출력

- **대소문자를 바꾸는 메소드**

  - String toLowerCase()
    - 모든 문자열의 내용을 소문자로 변경
  - String toLowerCase(Locale locale)
    - 지정한 지역 정보에 맞추어 모든 문자열의 내용을 소문자로 변경
  - String toUpperCase()
    - 모든 문자열의 내용을 대문자로 변경
  - String toUpperCase(Locale locale)
    - 지정한 지역 정보에 맞추어 모든 문자열의 내용을 대문자로 변경

- **기본 자료형을 문자열로 변환하는 메소드**

  - static String valueOf(...)
    - 기본 자료형을 String타입으로 변환
    - 객체가 null이면 "null"이라는 문자열을 리턴

### ==, equals() 메소드

- String 클래스에서 =="비교문자열", equals("비교문자열") 메소드가 동일한 결과가 나오는 이유는 자바에서는 객체들을 재사용하기 위해서 문자열 Pool이라는 것이 만들어져 있고, String의 경우 동일한 값을 갖는 객체가 있으면 이미 만든 객체를 사용
- new String("")으로 객체를 생성하면 값이 같은 String객체를 생성한다고 하더라도 Constant Pool의 값을 재활용하지 않고 별도의 객체를 생성한다.

```
    public void check(){
        String text1 = "java basic";
        String text2 = "java basic";
        String text3 = new String("java basic");
        System.out.println(text1==text2);
        System.out.println(text2==text3);
        System.out.println(text1.equals(text3));
    }

    결과
    true
    false
    true

    [해설]
    text1, text2와 같이 객체를 생성하면,
    String 클래스에서 관리하는 문자열 풀에 해당 값이 있으면 기존에 있는 객체를 참조하고,
    text3와 같이 String 객체를 생성하면 같은 문자열이 풀에 있든 말든 새로운 객체를 생성한다.
```

- equals()메소드로 비교하는 것보다 ==로 비교하는게 빠르다

### StringBuffer, StringBuilder

- String은 한 번 만들어지면 더 이상 값을 바꿀수 없는 immutable한 객체다.
- StringBuffer
  - Thread safe하다.
  - StringBuilder보다 안전하다
  - `문자열을 더해도 새로운 객체를 생성하지 않는다.`
  - append()메소드를 이용해 문자열을 추가
- StringBuilder
  - Thread safe하지 않다
  - StringBuffer보다 빠르다.
  - `문자열을 더해도 새로운 객체를 생성하지 않는다.`
  - append()메소드를 이용해 문자열을 추가
- JDK5 이상에서는 String의 더하기 연산을 할 경우, 컴파일할 때 자동으로 해당 연산을 StringBuilder로 변환해준다. 따라서, 일일이 더하는 작업을 변환해 줄 필요는 없으나, for루프와 같이 반복 연산을 할 때에는 자동으로 변환 해주지 않으므로, 꼭 필요하다.

### String, StringBuilder, StringBuffer

- 공통점 : 모두 문자열을 다루고 CharSequence인터페이스를 구현했다.
- 세 가지 중 하나의 클래스를 사용하여 매개 변수로 받는 작업을 할 때 CharSequence타입으로 받는게 좋다.
- 일반적으로 하나의 메소드 내에서 문자열을 생성해 더할 경우 StringBuilder 사용
- 어떤 클래스에 문자열을 생성하여 더하기 위한 문자열을 처리하기 위해 인스턴스 변수가 선언되었고, 여러 쓰레드에서 이 변수를 동시에 접근하는 일이 있을 경우에는 반드시 StringBuffer를 사용해야만 한다.

### 요약

1. String 클래스는 final 클래스인가요? 만약 그렇다면, 그 이유는 무엇인가요?

- 더 이상 클래스를 확장할 수 없게 하기 위해, 상속x

1. String 클래스가 구현한 인터페이스에는 어떤 것들이 있나요?

- Serializable, Comparable, CharSequence

1. String 클래스의 생성자 중에서 가장 의미없는 (사용할 필요가 없는) 생성자는 무엇인가요?

- 기본생성자

1. String 문자열을 byte 배열로 만드는 메소드의 이름은 무엇인가요?

- getBytes()

1. String 문자열의 메소드를 호출하기 전에 반드시 점검해야 하는 사항은 무엇인가요?

- null

1. String 문자열의 길이를 알아내는 메소드는 무엇인가요?

- length()

1. String 클래스의 equals() 메소드와 compareTo() 메소드의 공통점과 차이점은 무엇인가요?

- 공통점은 객체의 주소값이 아닌 **값만 가지고 비교**한다는 것이고 리턴값은 다르다 equals() 는 boolean, compareto()는 같을떈 0 다를땐 문자들의 차이의 값

1. 문자열이 "서울시"로 시작하는지를 확인하려면 String의 어떤 메소드를 사용해야 하나요?

- startsWith()

1. 문자열에 "한국"이라는 단어의 위치를 찾아내려고 할 때에는 String의 어떤 메소드를 사용해야 하나요?

- indexOf()

1. 위의 문제의 답에서 "한국"이 문자열에 없을 때 결과값은 무엇인가요?

- 1

1. 문자열의 1번째부터 10번째 위치까지의 내용을 String으로 추출하려고 합니다. 어떤 메소드를 사용해야 하나요?

- substring()

1. 문자열의 모든 공백을 표시로 변환하려고 합니다. 어떤 메소드를 사용하는 것이 좋을까요?

- replaceAll

1. String의 단점을 보완하기 위한 두개의 클래스는 무엇인가요?

- StringBuffer StringBuilder

1. 문제의 답에서 문자열을 더하기 위한 메소드의 이름은 무엇인가요?

- append