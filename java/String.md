
# String 클래스
- string 클래스는 문자열이다. 클래스이기때문에 자바의 기본 자료형에 속하지않는다.
- String 클래스를 비교할떄는 equals() 메소드를 사용해서 비교해야만 한다. 이렇게 하는 이유는 자바의 Constant Pool이라는 것이 존재하기때문이다. 자바에서는 객체를 재사용하기위해 Constant Pool이 만들어져있고 String의 경우 동일한 값을 갖는 객체가 있으면 이미 만든 객체를 재사용한다. 밑에서 text와 text2객체는 실제로 같은 객체다.

```
String text = 'test';
String text2 = 'test';
```


### 특징
- 불변성 : 자바에서 String은 불변하는 객체이다. 만약 a라는 String의 값을 b로 바꾼다면 기존의 가지고있던 객체는 버려지고 새로운 객체가 생성된다. 그러므로 계속 String을 새로 만들어 더하는 작업을 하면 계속 스레기를 만들어내는 게 된다. 밑의 경우 hello라는 단어를 갖고 있는 객체는 더이상 사용할 수 없다. 즉, 쓰레기가 되며 나중에 GC(가비지 컬렉션)의 대상이 된다. 

```
String text = 'hello';
text = text + "world";
```
- 공유성 : 붋변성을 가지고있기때문에 가지는 특성으로 같은 값을 가진 String변수들은 같은 메모리주소를 바라보고 공유한다. 

---

# StringBuffer과 StringBuilder
- StringBuffer/StringBuilder 클래스는 주로 문자열을 연산(추가하거나 변경)할떄 사용하는 자료형.
- String의 문제 (쓰레기값을 만들어내는 문제)를 해결하기위한 것으로 문자열 연산을 전용으로 하는 자료형을 따로 만들어제공해주는데 StringBuffer클래스는 내부적으로 버퍼(buffer)라고 하는 독립적인 공간을 가지게 되어 문자열을 바로 추가할 수 있어 공간의 낭비도 없으며 문자열 연산속도도 매우 빠르다.

- StringBuffer나 StringBuiler의 경우 문자열 데이터를 다룬다는 점에서 String객체와 같지만 객체의 공간이 부족해지는 경우 버퍼의 크기를 유연하게 늘려주어 **가변적** 이라는 차이점이 있다.
- 두 클래스는 내부 Buffer(데이터를 임시로 저장하는 메모리)에 문자열을 저장해두고 그안에서 추가,수정,삭제 작업을 할 수 있도록 설게되어있다.

- String 객체는 한번 생성되면 불변적인 특징때문에 값을 업데이트 하면 매 연산시마다 새로운 문자열을 가진 String 인스턴스가 생성되어 메모리 공간을 차지하게 되지만 StringBuffer/ StringBuilder는 가변성을 가지기때문에 .append(), .delete()등의 API를 이용하여 **동일한 객체내에서 문자열 크기를 변경**하는 것이 가능

- 따라서 값이 변경될때마다 새롭게 객체를 생성하는 String보다 빠르기때문에 **문자열의 추가,수정,삭제가 빈번하게 발생**하는 경우라면 String클래스가 아닌 StringBuffer/StringBuilder를 사용하는 것이 이상적.

## StringBuffer와 StringBuilder의 차이점

- 둘다 가변적인 특성을 가지고 있고 메서드 똑같고 사용법도 동일. 둘의 차이는 **멀티 쓰레드 환경에서 안전한지의 여부**
![image](https://github.com/user-attachments/assets/3adbec49-2c2e-470c-a661-ed6495930380)


- StringBuffer 클래스는 쓰레드에서 안전하다 
- StringBuilder 클래스는 쓰레드에서 안전하지않다.
- 두 클래스는 문법이나 배열구성도 모두 같지만 **동기화** 지원의 유무가 다르다.


### 밑의 코드는 뭐가 다를까? 

```
String aa = 'test'
String aa = new String('test')

```
- 두방식은 메모리 처리 방식과 성능에 있다 

#### 리터럴 방식
- 해당 방식은 문자열 상수풀을 이용하여 메모리를 효율적으로 관리. "test"라는 리터럴 문자열이 상수풀에 저장. -> 만약 상수풀에 "test"가 이미 존재한다면 기존 객체를 재사용하고 새로 생성하지않음. 즉 동일한 문자열이 여러번 사용되더라도 상수풀에서 하나의 객체만 생성됨으로 메모리 절약 가능 -> 이것을 **interning**이라고 함.

```
String aa = "test";
String bb = "test";
System.out.println(aa == bb); // true (같은 객체를 가리킴)

```
- 리터럴 방식으로 String을 선언하면 JVM은 String Pool에 객체를 생성하고 해당 참조를 stack에 저장한다. -> Constant Pool은 문자열이 저장되는 특별한 메모리 영역이다.

### String Constant Pool
- 자바 7 이상 부터 String Constant Pool은 힙위에 올라가 GC의 영향을 받는다.

![image](https://github.com/user-attachments/assets/c510cb98-c933-4a46-8127-537461616372)
 
```
String str = "hello"; // String constant pool에 저장
String str2 = "hello"; // String constant pool에서 재사용
String str3 = new ("hello"); // 별도의 Heap 메모리에 저장

System.out.println(str == str2);	// true (같은 객체를 재사용하기 때문에)
System.out.println(str == str3);	// false
System.out.println(str.equals(str3));	// true

```
![image](https://github.com/user-attachments/assets/c73806cf-5c8d-488d-965f-606e1a7d35a5)

참고링크 https://deveric.tistory.com/123

#### 객체 생성 방식
- 명시적으로 새로운 String 객체를 생성함.
- 상수풀에 저장된 "test"를 사용하더라도 **힙메모리에 새로운 String객체를 생성**
- 항상 새로운 객체를 생성하여 상수풀에 이미 동일한 문자열이 존재하더라도 이를 무시하고 별도의 객체를 만듬.

```
String aa = new String("test");
String bb = new String("test");
System.out.println(aa == bb); // false (서로 다른 객체)

```

- 리터럴 방식은 메모리를 효율적으로 사용하고 성능이 더 좋음. 상수 풀을 이용해 같은 문자열에 대해 중복 객체를 생성하지 않는다.
- 객체 생성 방식은 새로운 객체를 무조건 생성하므로 메모리 효율이 떨어지고, 일반적으로 사용하지 않는 방식

---

참고링크 

https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-StringBuffer-StringBuilder-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%84%B1%EB%8A%A5-%EB%B9%84%EA%B5%90

https://velog.io/@paulhana6006/%EC%9E%90%EB%B0%94-%EB%AC%B8%EC%9E%90%EC%97%B4-%EC%93%B8-%EB%95%8C-%EC%9D%B4%EA%B1%B0%EB%8A%94-%EC%95%8C%EA%B3%A0-%EC%93%B0%EC%9E%90