
# StringBuffer / StringBuilder
- StringBuffer / StringBuilder 클래스는 문자열을 연산(추가하거나 변경) 할 때 주로 사용하는 자료형이다.
- String으로도 더할수 있지만 String은 불변 객체이기때문에 계속 새로운 메모리를 할당하여 아웃오브 메모리가 발생할 수 있다. 따라서 문자열을 많이 결합하면 메모리 낭비 및 성능저하가 있을 수 있다. 
- 그래서 자바에서는 이러한 문자열 연산을 전용으로 하는 자료형을 따로 만들어 제공해주는데 StringBuffer 클래스는 내부적으로 Buffer(버퍼)라고 하는 독립적인 공간을 가지게되어 문자열을 바로 추가할 수 있어 공간의 낭비도 없으며 문자열 연산속도도 매우 빠르다는 특징이 있다. 
- 기본적으로 StringBuffer의 버퍼(데이터 공간) 크기의 기본값은 16개의 문자를 저장할 수 있는 크기이며 생성자를 통해 그 크기를 별도로 설정할 수 있다. 
- 만일 문자열 연산 중에 할당된 버퍼의 크기를 넘게 되면 자동으로 버퍼를 증강시키니 걱정안해도 된다. 다만 효율이 떨어질 수 있으므로 버퍼의 크기는 넉넉하게 잡는게 좋다.


## String vs StringBuffer/String builder

### String은 불변이다.
- 기본적으로 자바에서 String 객체의 값은 변경할 수 없다. -> 불변객체 

### StringBuffer/String builder는 가변이다.
- StringBuffer이나 String builder의 경우 문자열 데이터를 다룬다는 점에서 String 객체와 같지만 객체의 공간이 부족해지느 ㄴ경우 버퍼의 크기를 유연하게 늘려주어 가변적이라는 차이점이 있다 
- 두 클래스는 내부 Buffer(데이터를 임시로 저장하는 메모리)에 문자열을 저장해두고 그안에서 추가,수정,삭제 작업을 할 수 있도록 설계되어있다.
- StringBuffer / StringBuilder 는 가변성 가지기 때문에 .append() .delete() 등의 API를 이용하여 동일 객체내에서 문자열 크기를 변경하는 것이 가능.
![image](https://github.com/user-attachments/assets/05994a4f-60b1-4a0f-837b-916e0b8927af)
- String 객체일 경우 매번 별 문자열이 업데이트 될때마다 계속해서 메모리 블럭이 추가되게 되고, 일회용으로 사용된 이 메모리들은 후에 Garbage Collector(GC)의 제거 대상이 되어 빈번하게 Minor GC를 일으켜 Full GC(Major Gc)를 일으킬수 있는 원인이 된다.

## 둘의 차이 
-  StringBuffer와 StringBuilder의 차이는 StringBuffer는 멀티 스레드 완경에서 안전하다는 장점이 있고 StringBuilder는 문자열 파싱 성능이 가장 우수하다는 장점이 있다.
![image](https://github.com/user-attachments/assets/e1820766-d60a-47e2-b9e6-398d8700466e)
- StringBuffer 클래스는 쓰레드에서 안전하다. (thread safe)
- StringBuilder 클래스는 쓰레드에서 안전하지 않다.(thread unsafe) 
-  두 클래스는 문법이나 배열구성도 모두 같지만, 동기화(Synchronization)에서의 지원의 유무가 다르다. 
- StringBuilder는 동기화를 지원하지 않는 반면, StringBuffer는 동기화를 지원하여 멀티 스레드 환경에서도 안전하게 동작할 수 있다.
- 그 이유는 StringBuffer는 메서드에서 synchronized 키워드를 사용하기 때문이다.
    - java에서 synchronized 키워드는 여러개의 스레드가 한 개의 자원에 접근할려고 할 때 현재 데이터를 사용하고 있는 스레드를 제외하고 나머지 스레드들이 데이터에 접근할 수 없도록 막는 역할을 수행함.
- **하지만 현업에서는 자바 어플리케이션을 대부분 멀티 스레드 이상의 환경에서 돌아가기 때문에 왠만하면 안정적인 StringBuffer로 통일하여 코딩하는것이 좋다. (솔직히 StringBuffer 와 StringBuilder 속도 차이는 거의 미미하다)**


## 각각의 사용해야하는 경우 
- String 을 사용해야 할 때 :
    - String은 불변성
    - 문자열 연산이 적고 변하지 않는 문자열을 자주 사용할 경우
    - 멀티쓰레드 환경일 경우 
- StringBuilder 를 사용 해야 할 때 :
    - StringBuilder는 가변성
    - 문자열의 추가, 수정, 삭제 등이 빈번히 발생하는 경우
    - 동기화를 지원하지 않아, 단일 쓰레드이거나 동기화를 고려하지 않아도 되는 경우
    - 속도면에선 StringBuffer 보다 성능이 좋다.

- StringBuffer 를 사용해야 할 때 :
    - StringBuffer는 가변성
    - 문자열의 추가, 수정, 삭제 등이 빈번히 발생하는 경우
    - 동기화를 지원하여, 멀티 스레드 환경에서도 안전하게 동작


### 질문
#### String, StringBuffer, StringBuilder의 차이점은 무엇인가요?
- String은 불변 객체로, 문자열이 변경될 때마다 새로운 메모리를 할당해 새로운 객체를 생성합니다. 이는 빈번한 문자열 수정이 있을 경우 메모리 낭비와 성능 저하를 일으킬 수 있습니다.
- 반면, StringBuffer와 StringBuilder는 가변 객체로, 문자열 수정이 빈번하게 발생할 때 적합합니다. 이 두 클래스는 내부에 버퍼를 사용해 문자열을 수정할 수 있는 공간을 유연하게 할당하여 메모리와 성능 면에서 효율적입니다.
- StringBuffer는 **스레드 안전(thread-safe)**하며 동기화를 지원하여 멀티 스레드 환경에서도 안정적으로 동작합니다. 반면, StringBuilder는 동기화를 지원하지 않아 단일 스레드 환경에서 사용하기에 적합하며, StringBuffer보다 성능이 약간 더 우수합니다.

#### 각각의 클래스를 언제 사용해야 하나요?
- String은 변경이 필요 없는 불변 문자열을 사용할 때 적합하며, 문자열 수정이 적고 멀티 스레드 환경에서 안전해야 하는 경우에도 사용됩니다.
- StringBuilder는 단일 스레드 환경에서 문자열 추가, 수정, 삭제가 빈번히 발생하는 경우 적합하며, 성능이 우수합니다.
- StringBuffer는 멀티 스레드 환경에서 문자열을 수정해야 할 때 사용합니다. 동기화를 지원하여 여러 스레드가 동시에 접근하더라도 안전하게 문자열을 다룰 수 있습니다.

참고링크 및 출처 
https://inpa.tistory.com/entry/JAVA-%E2%98%95-String-StringBuffer-StringBuilder-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%84%B1%EB%8A%A5-%EB%B9%84%EA%B5%90