
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



---

참고링크 

https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-StringBuffer-StringBuilder-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%84%B1%EB%8A%A5-%EB%B9%84%EA%B5%90