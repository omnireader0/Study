# 22장 컬렉션 - List

> 컬렉션을 잘 아느냐 모르냐에 따라 면접 당락이 결정되기도 한다. 중요하니까 외우자

### 자바 컬렉션

- 목록성 데이터 처리하는 자료구조
- 자바의 자료 구조 : 여러 데이터 담을 때 사용
  - List : 순서 있는 리스트
  - Set : 순서 중요하지 않은 셋
  - Queue : 먼저 들어온 것이 먼저 나가는 큐
  - Map : key-value 로 저장되는 맵
  - List, Set, Queue는 java.util.Collection 인터페이스를 구현한다.

![https://user-images.githubusercontent.com/93963499/154792073-5c368bb3-c7e3-4981-9203-5e477210d974.png](https://user-images.githubusercontent.com/93963499/154792073-5c368bb3-c7e3-4981-9203-5e477210d974.png)

### Collection 인터페이스

- Collection 인터페이스는 Iterable이라는 인터페이스를 확장한 것으로, Iterator 인터페이스를 사용해서 데이터를 순차적으로 가져올 수 있음

```java
- 구조
  public interface Collection<E> extends Iterable<E>
- 주요 메소드
    - boolean addJAVA(E e)
      요소를 추가
    - boolean addAll(Collection)
      컬렉션의 모든 요소를 추가
    - void clear()
      컬렉션에 모든 요소 데이터를 지운다
    - boolean contains(Object)
      매개 변수로 넘어온 객체가 해당 컬렉션에 있는지 확인. 있으면 true 리턴
    - boolean containsAll(Collection)
      매개변수로 넘어온 객체들이 해당 컬렉션에 있는지 확인. 모두 있으면 true 리턴
    - boolean equals(Object)
      매개 변수로 넘어온 객체와 같은 객체인지 확인
    - int hashCode()
      해시 코드값을 리턴
    - boolean isEmpty()
      컬렌션이 비어있는지 확인. 비어있으면 true 리턴
    - Iterator iterator()
      데이터를 한 건씩 처리하기 위한 Iterator 객체를 리턴
    - boolean remove(Object)
      매개 변수와 동일한 객체 삭제
    - boolean removerAll(Collection)
      매개변수로 넘어온 객체들을 해당 컬렉션에서 삭제
    - boolean retainAll(Collection)
      매개 변수로 넘어온 객체들만 컬렉션에 남겨 둔다
    - int size()
      요소의 개수를 리턴
    - Object[] toArray()
      컬렉션에 있는 데이터들을 배열로 복사
    - <T> t[] toArray(T[])
      컬렉션에 있는 데이터들을 지정한 타입의 배열로 복사
```

- Iterable 인터페이스에 있는 메소드

```java
   Iterator<T> iterator()
```

- iterator()메소드는 Iterator 인터페이스 리턴

```java
- 구조
   public interface Iterator<E>

- 메소드
   - boolean hasNext()
   - E next()
   - void remove()
```

### List 인터페이스

- List 인터페이스는 Collection인터페이스를 확장했다.
- 주로 ArrayList, Stack, LinkedList를 많이 사용한다.
- ArrayList는 Thread safe 하지 않고, Vector는 Thread safe하다. (ArrayList의 객체는 여러 명이 달려들어 값을 변경하려고 하면, 문제가 생길 수 있고, Vector는 그렇지 않음)
- Stack
  - Vector 클래스를 확장했다.
  - LIFO (Last In First Out)
- LinkedList
  - List, Queue에 속한다

### ArrayList 클래스

- 상속 관계
  - Object 제외한 상위 클래스는 추상클래스이고, 추상 클래스란 몇몇 메소드는 자식에서 구현하라고 abstract로 선언한 메소드가 있는 클래스

```java
java.lang.Object
     java.util.AbsractCollection<E>
           java.util.AbstractList<E>
                 java.util.ArrayList<E>
```

- ArrayList가 구현한 인터페이스들

```java
- Serializable : 원격으로 객체를 전송하거나,파일에 저장할 수 있음을 지정
- Cloneable : Object 클래스의 clone() 메소드가 제대로 수행될 수 있음을 지정
              즉, 복제가 가능한 객체임을 의미
- Iterable<E> : 객체가 foreach문을 사용할 수 있음을 지정
- Collection<E> : 여러 개의 객체를 하나의 객체에 담아 처리할 때의 메소드 지정
- List<E> : 목록형 데이터를 처리하는 것과 관련된 메소드 지정
- RandomAccess : 목록형 데이터에 보다 빠르게 접근할 수 있도록
                 임의로 접근하는 알고리즘이 적용된다는 것을 지정
```

- 생성자

```java
- ArrayList() : 객체를 저장할 공간이 10개인 ArrayList를 만든다
                매개 변수를 넣지 않으면 초기 크기는 10이다. 10개 이상의 데이터가 들어오면
                크기를 늘이는 작업이 ArrayList 내부에서 자동으로 수행된다
- ArrayList(Collection<? extends E> c) : 매개변수로 넘어온 컬렉션 객체가 저장되어 있는
                                         ArrayList를 만든다.
- ArrayList(int initialCapacity) : 매개 변수로 넘어온 initialCapacity 개수만큼의
                                   저장 공간을 갖는 ArrayList를 만든다.
```

- 보통 한 가지 타입의 객체를 저장한다.
- 여러 종류의 객체를 하나의 객체에 담을 떄에는 되도록 DTO 객체 만들어서 담자! → 컬렉션 관련 클래스의 객체들을 선언할 때에는 제네릭을 사용해 선언하는 것을 권장
  - 제네릭 사용하면 타입 문제를 컴파일 시점에 걸러 줌
- ArrayList 객체 선언할 때 매개변수를 넣지 않으면, 기본 크기는 10, 데이터가 10 넘게 들어가면 자동 확장, 그래서 크기가 예측 가능하다면, 초기 크기 설정하면 좋음

```java
    ex) ArrayList<String> list1 = new ArrayList<String>();
```

### ArrayList 데이터 추가

```java
boolean add(E e) : 매개 변수로 넘어온 데이터를 가장 끝에 담는다.
void add(int index, E e) : 매개 변수로 넘어온 데이터를 지정된 index위치에 담는다.
boolean addAll(Collection<? extends E> c) : 매개 변수로 넘어온 컬렉션 데이터를
                                            가장 끝에 담는다
boolean addAll(int index, Collection<? extends E> c) : 매개 변수로 넘어온 컬렉션
                                                       데이터를 index 위치부터 담는다
```

> ArrayList<String> list1 = new ArrayList<String>(list2);

이게 가능한 이유는 ArrayList에는 Collection 데이터를 넘길 수 있는 생성자 있음

### 얕은 복사와 깊은 복사

```java
public void checkArrayList4() {
	ArrayList<String> list = new ArrayList<String> ();
	list.add("a");
	
	ArrayList<String> list2 = list;
	list.add("b");
	for (String l : list2) {
		System.out.println(l);
	}
}

// 출력
// a
// b
```

list2 = list는 list라는 객체가 참조하고 있는 주소까지도 사용하겠다..

자바는 1) 모든 객체가 생성되면, 2) 객체에 주소가 내부적으로 할당된다. toString() 메소드를 구현하지 않은 클래스의 toString() 메소드 호출 결과를 보면 주소가 나오는 것을 알 수 있다. 따라서 list2 = list는 변수는 다르지만 같은 주소를 참조하기 때문에 하나의 객체가 변경되면 다른 변수도 변경된다.

- 얕은 복사 : 다른 객체에 원본 객체의 주소값을 할당
- 깊은 복사 : 객체의 모든 것을 복사하여 복제된 객체에 있는 값을 변경해도 원본 값이 변경되지 않는 것

### ArrayList 데이터 꺼내기

```java
int size() : ArrayList 객체에 들어가 있는 데이터의 개수를 리턴
E get(int index) : 매개 변수에 지정한 위치에 있는 데이터 리턴
int indexOf(Object o) : 매개 변수로 넘어온 객체와 동일한 데이터의 위치를 리턴
int lastIndexOf(Object o) : 매개 변수로 넘어온 객체와 동일한 마지막 데이터의 위치를 리턴
Object[] toArray() : ArrayList객체에 있는 값들을 Object[] 타입의 배열로 만듦
<T> T[] toArray(T[] a) : ArrayList객체에 있는 값들을 매개 변수로 넘어온 T타입의 배열로 만듦
                         ArrayList객체의 크기가 매개변수 배열 객체의 크기보다 클 경우
                         매개 변수 배열의 모든 값이 null로 채워진다.
                         그러므로, 크기가 0인 배열을 넘겨주는 것이 가장 좋다.
                         ex) String[] strList = list.toArray(new String[0]);
```

### ArrayList 데이터 삭제

```java
void clear() : 모든 데이터를 삭제
E remove(int index) : 매개 변수에서 지정한 위치에 있는 데이터를 삭제하고
                      삭제한 데이터를 리턴
boolean remove(Object o) : 매개 변수에서 넘어온 객체와 동일한 첫 번째 데이터 삭제
boolean removeAll(Collection<?> c) : 매개 변수로 넘어온 컬렉션 캑체에 있는 데이터와
                                     동일한 모든 데이터 삭제
```

### ArrayList 데이터 교체

```java
E set(int index, E element) : index 위치에 데이터를 두 번째 매개 변수로 넘긴 값으로 변경
                              그리고, 해당 위치에 있던 데이터를 리턴
```

### Stack

- LIFO 기능을 구현할 때 사용하는 클래스
- 상속 관계

```java
    java.lang.Object
         java.util.AbstractCollection<E>
             java.util.AbstractList<E>
                 java.util.Vector<E>
                     java.util.Stack<E>
```

- 구현한 인터페이스

```java
  - Serializable
  - Cloneable
  - Iterable<E>
	- Collection<E>
	- List<E>
	- RandomAccess
```

- 생성자

```java
  Stack() : 아무 데이터도 없는 Stack 객체를 만듦
```

- 메소드

```java
  boolean empty() : 객체가 비어있는지 확인
  E peek() : 객체의 가장 위에 있는 데이터 리턴
  E pop() : 객체의 가장 위에 있는 데이터 지우고 리턴
  E push(E item) : 매개 변수로 넘어온 데이터를 가장 위에 저장
  int search(Object o) : 매개 변수로 넘어온 데이터의 위치 리턴
```

### 더 공부할 내용

- thread - safe, thread - unsafe

# 23장 Set, Queue

### Set 인터페이스

- 순서에 상관 없이 **어떤 데이터가 존재하는지 확인** 위한 용도로 많이 사용
- **중복 방지하고 원하는 값이 포함되어 있는지 확인하는 것이 주용도**
- Set 인터페이스를 구현한 주요 클래스
  - HashSet
    - 순서가 필요 없는 데이터를 해시 테이블에 저장
    - Set 중에 가장 성능이 좋다
  - TreeSet
    - 저장된 데이터의 값에 따라 정렬되는 셋
    - red-black 이라는 트리 타입으로 값이 저장
    - HashSet보다 약간 성능이 느리다
  - LinkedHashSet
    - 연결된 목록 타입으로 구현된 해시 테이블에 데이터를 저장
    - 저장된 순서에 따라 값이 정렬
    - 성능이 셋 중 가장 나쁘다.
  - 성능 차이 발생이유는 데이터 정렬 때문

### HashSet 클래스

set은 Object 클래스에 선언된 equals(), hashCode()를 사용할 수 있으며, 이 메소드들은 매우 중요

Object 클래스에 선언된 removeAll()는 컬렉션을 매개 변수로 받아 매개 변수 컬렉션에 포함된 데이터를 지울 때 사용한다.

- 상속 관계

```java
  java.lang.Object
      java.til.AbstractCollection<E>
          java.util.AbstractSet<E>
              java.util.HashSet<E>
```

- 구현한 인터페이스

```java
  - Serializable : 원격으로 객체를 전송하거나, 파일에 저장할 수 있음
  - Cloneable : Object 클래스의 clone() 메소드가 제대로 수행될 수 있음
                즉, 복제가 가능한 객체임을 의미
  - Iterable<E> : 객체가 foreach문을 사용할 수 있음
  - Collection<E> : 여러 개의 객체를 하나의 객체에 담아 처리할 때의 메소드
  - Set<E> : 셋 데이터를 처리하는 것과 관련된 메소드
```

- 생성자

```java
  - HashSet() : 데이터를 저장할 수 있는 16개의 공간과 0.75의 로드 팩터를 갖는 객체 생성
    *** 로드 팩터 : 데이터의 개수/저장공간
                   데이터 개수가 증가해 로드 팩터보다 커지면, 해시 재정리 작업을 해야하다.
                   이때, 내부에 갖고 있던 자료 구조를 다시 생성하는 단계를 거쳐
                   성능에 영향이 발생한다.
								   로드팩터 값이 클수록 공간은 넉넉해지지만, 데이터 찾는 시간은 증가한다.
									 그래서 초기 겅긴 게스어ㅣ
  - HashSet(Collection<? extends E> c) : 매개 변수로 받은 컬랙션 객체의 데이터를
                                         HashSet에 담는다
  - HashSet(int initalCapacity) : 매개 변수로 받은 개수만큼의 데이터 저장 공간과
                                  0.75의 로드 팩터를 갖는 객체를 생성
  - HashSet(int initalCapacity, float loadFactor) : 첫 매개 변수로 받은 개수만큼의
                                                    데이터 저장 공간과
                                                    두 번째 매개 변수로 받은 만큼의
                                                    로드 팩터를 갖는 객체 생성
```

- 주요 메소드

```java
  - boolean add(E e) : 데이터 추가
  - void clear() : 모든 데이터 삭제
  - Object clone() : HashSet 객체 복제. 하지만, 담겨 있는 데이터들은 복제하지 않는다.
  - boolean contains(Object o) : 지정한 객체 존재하는지 확인
  - boolean isEmpty() : 데이터가 있는지 확인
  - Iterator<E> iterator() : 데이터를 꺼내기 위한 Iterator 객체를 리턴
  - boolean remove(Object o) : 매개 변수로 넘어온 객체를 삭제
  - int size() : 데이터 개수 리턴
```

### 더 공부할 내용

- red-black 트리
- set이 빠른 이유