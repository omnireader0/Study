# 10장 상속

> **상속**과 **메소드 오버라이딩**, **참조 자료형의 형변환**과 **다형성**을 확실히 이해

**상속**

- 부모 클래스에서는 기본 생성자를 만들어 놓아야 함
- 자식 클래스 생성자가 호출되면, **자동으로 부모 클래스의 매개 변수 없는 생성자가 실행**됨
- 자식 클래스에서는 부모 클래스에 있는 public, protected로 선언된 모든 인스턴스 및 클래스 변수와 메소드 사용 가능
- 다중 상속 안됨

**부모 클래스에서 매개변수가 있는 생성자를 만들고, 매개변수가 없는 생성자를 만들지 않은 경우**

- 이 경우 자식클래스에서 실행시키면 부모 클래스에 기본 생성자가 없다는 에러가 발생하므로 두가지 방법으로 해결
  - 부모 클래스에 매개 변수 없는 생성자 만들기
  - 자식 클래스에서 부모 클래스의 생성자를 명시적으로 지정하는 super()를 사용

**super 사용**

- ```
  부모 클래스의 생성자를 호출한다는
  ```

   의미

  - super(”Child”); : string 타입을 매개변수로 받을 수 있는 생성자 호출됨
  - super.printName(); :  부모 클래스의 printName() 호출
  - super(null); : 참조가 모호하다는 에러 발생, null 보다는 생성자의 기본 타입을 넘기는 것이 좋다. null은 어떤 클래스를 찾아가야 하는지 자바 컴파일러가 마음대로 정할 수 없다....

- 부모 클래스에 매개변수가 있는 생성자만 있다면, super()를 이용하여 부모 생성자 호출

- 자식 클래스의 생성자에서 super()를 지정하지 않으면 컴파일시 자동 추가됨

- super()는 자식클래스 생성자에서 가장 첫줄에 위치

**메소드 오버라이딩**

- 자식 클래스에서 부모 클래스에 있는 메소드와 동일하게 선언하는 것
- 접근 제어자. 리턴 타입, 메소드 이름, 매개 변수 타입과 개수 동일
- 오버라이딩하면 자식 클래스의 메소드만 실행
- 생성자의 경우 부모 클래스에 있는 생성자를 호출하는 super()가 자동 추가되나, 메소드는 아님
- 오버라이딩 할 때 자식클래스에서 접근제어자 확대는 가능

**오버로딩 오버라이딩**

오버로딩 : 확장... 메소드의 매개변수들을 확장하기 때문

오버라이딩 : 덮어 쓰기... 부모 클래스의 메소드 시그니처를 복제해서 자식 클래스에서는 새로운 기능을 만들고 그것을 사용

**상속에서 참조 자료형의 형변환**

```java
ParentCasting obj = new ChildCasting();
// 가능 
// 자식 클래스의 타입을 부모 클래스의 타입으로 형변환하면 
//부모 클래스의 메소드들을 자식 클래스에서 사용 가능

ChildCasting obj2 = new ParentCasting();
// 불가능
// 부모 클래스에서 자식 클래스의 모든 메소드를 사용할 수 있지 않기 때문
// 자식 객체를 생성할 때 부모 생성자 사용은 안됨!!!! -> 명시적 형변환 해야함
```

- 명시적 형변환 불가능 케이스

```java
ParentCasting parent = new ParentCasting();
ChildCasting obj2 = (ChildCasting)parent;
// 컴파일 에러는 없으나 실패
// parent 객체는 P~ 클래스의 객체이고, C~ 클래스의 메소드나 변수를 함부로 사용할 수 없다.
 
```

- 명시적 형변환 가능 케이스

```java
ChildCasting child = new ChildCasting();
ParentCasting parent2 = child;
ChildCasting child = (ChildCasting)parent2;
// parent2는 child 대입한 것
// child는 C~ 클래스의 객체이고, 이것을 대입해도 사실은 C~ 클래스의 객체다.
// 그래서 parent2를 형변환해도 문제 없다.
```

- 왜 명시적 형변환 하는가??  언제 사용하는가?

일반적으로 여러 개의 값을 처리하거나, 매개 변수로 값을 전달할 때 **부모 타입**으로 보낸다.

parentArray가 어떤 타입인지 알 수 있는 방법 : instanceof 사용

→ 객체 instanceof 클래스 → boolean 형태의 결과 제공

```java
ParentCasting[] parentArray = new ParentCasting[3];
parentArray[0] = new ChildCasting();
parentArray[1] = new ParentCasting();
parentArray[2] = new ChildCasting();

for(ParentCasting t : parentArray) {
	if(t instanceof ChildCasting) {
		System.out.println("ChildCasting");
} else if(t instanceof ParentCasting) {
		System.out.println("ParentCasting");
	}
}

// ChildCasting
// ParentCasting
// ChildCasting
```

- parentArray[0]의 타입은 ChildCasting이지만 ParentCasting 가능한가?
  - 가능하다,. 그렇게 때문에 parentArray에 넣을 수 있었다.
  - 그렇기 때문에 instanceof는 하위 자식 타입부터 먼저 점검해야 한다!

```java
for(ParentCasting t : parentArray) {
	if(t instanceof ParentCasting) {
		System.out.println("ParentCasting");
	} else if(t instanceof ChildCasting) {
		System.out.println("ChildCasting");
		ChildCasting tc = (ChildCasting)t;
		tc.printAge();
	} 
}

// 
```

- 정리
  - 참조 자료형도 형변환 가능
  - 자식 타입의 객체를 부모 타입으로 형변환 가능
  - 부모 타입의 객체를 자식 타입으로 형변환 하는 경우 명시적 타입 지정과 부모 타입의 실제 객체는 자식 타입이어야 함
  - instanceof 사용하면 객체 타입 확인 가능
  - instanceof 로 타입 확인하는 경우 부모 타입도 true 제공

**다형성**

**형변환을 하더라도 실제 호출되는 것은 원래 객체에 있는 메소드가 호출됨**

(상속 관계에 있는)생성자 다른 3개의 변수를 부모 타입의 클래스로 선언하고 오버라이딩한 메소드를 호출하면, 실제로 호출된 메소드는 생성자를 사용한 클래스에 있는 것이 호출된다.

**자식 클래스에서 할 수 있는 일 정리**

- 생성자
  - 자식 클래스의 생성자가 호출되면 자동으로 부모 클래스의 매개변수가 없는 기본 생성자가 호출됨, super() 지정 가능
  - 부모 클래스의 생성자 명시적 호출하려면 super() 사용
- 변수
  - 부모 클래스에 private 선언한 변수 제외한 모든 변수를 자신의 클래스에 선언된 것처럼 사용 가능
  - 부모 클래스에 선언된 변수와 동일한 이름을 가지는 변수 선언 가능, 권장 x
  - 부모 클래스에 없는 변수 선언 가능
- 메소드
  - 부모 클래스의 모든 메소드를 자신의 클래스에 선언된 것처럼 사용 가능
  - 부모 클래스에 선언된 메소드와 동일한 시그니처 사용함으로써 메소드 오버라이딩
  - 부모 클래스에 없는 메소드 선언 가능

### 요약

1. 부모 클래스의 생성자를 자식 클래스가 직접 선택하려고 할 때 사용하는 예약어?

- super()

1. 메소드 오버로딩과 오버라이딩

- 오버로딩(overloading) : 메소드 이름은 동일하지만 매개변수의 유형, 개수가 다르도록 하는 기술
- 오버라이딩(overriding) : 상위 클래스의 메소드를 하위 클래스가 재정의해서 사용하는 기술

1. A가 부모, B가 자식 클래스라면 A a=new B(); 의 형태로 객체 생성이 가능한가요?

- 그렇다.
- 자식 클래스의 타입을 부모 클래스의 타입으로 형변환하면, 부모 클래스에서 호출할 수 있는 메소드들은 자식 클래스에서도 호출이 가능하므로 이 경우 객체 생성 가능하다.

1. 명시적으로 형변환하기 전에 타입 확인하는 예약어

- instanceof
- 참조변수 instanceof 클래스
- boolean 타입 으로 결과 제공

1. Polymorphism은 무엇인가?

- 다형성이란 하나의 객체가 여러 타입을 가질 수 있는 것을 말한다.  ..?
- 형변환 하더라도 실제 호출되는 것은 원래 객체에 있는 메소드가 호출되는 것

# 12장 Object 클래스

> `모든 자바 클래스의 부모 클래스` → Object 클래스에 있는 메소드들을 통해 클래스의 기본적 행위를 정의할 수 있음

아무것도 상속받지 않으면 Object 클래스 확장한다는 것임.

**Object 클래스에서 제공하는 메소드의 종류**

객체 처리위한 메소드와 쓰레드 위한 메소드로 나뉨

- 객체 처리 위한 메소드 → `접근제어자 리턴타입 메소드이름(매개변수)`

| 메소드                            | 설명                                                   |
| --------------------------------- | ------------------------------------------------------ |
| protected Object clone()          | 객체의 복사본을 만드는 메소드                          |
| public boolean equals(Object obj) | 현재 객체와 매개변수의 객체가 동일한지 확인하는 메소드 |
| public int hasCode()              | 객체의 메모리 주소를 16진수로 표현한 것을 리턴한다     |
| public String toString()          | 객체를 문자열로 표현한 값을 리턴한다                   |