# Stack

![image](https://github.com/user-attachments/assets/ee33b845-8ac8-4ed6-b6c7-724d953b1439)

- stakc은 데이터를 쌓아 올리듯이 저장하는 자료구조다.
- stack은 마지막에 저장한 데이터를 가장 먼저 꺼낸다. 이런 특징을 후입선출(LIFO)라고 한다. Last In, First Out
- 예를 들어 가장 마지막에 들어간 총알이 가장 먼저 발사되는 것과 같은 이치이다.

### stack의 사용사례
- 웹브라우저 방문기록(뒤로가기)
- 실행 취소
- 역순 문자열 만들기
- 후위 표기법 계산 

## 자바에서 Stack이란?
- 자바에서는 stack 자료구조를 잘 사용하지않는다. 그이유는 stack 클래스는 디자인이 잘못되었기때문이다.
- 자바의 stack 클래스는 대표적으로 상속을 잘못 받은 클래스로 상위 클래스로 Vector를 상속받는다.(여기서 문제가 발생)
- Vector는 ArrayList와 같이 List 인터페이스를 구현한 컬렉션 프레임워크이며, 쓰레드 안전 여부를 제외하고는 ArrayList와 거의 동일하다. 

#### synchronized로 인한 성능 저하
- synchronized란? 쓰레드를 안전하기 사용하기위해 제공하는 자바의 예약어, 메서드에 선언하거나 블록 형태로 사용할 수 있음.

- 이로 인해 단일 환경 쓰레드 환경에서 stack을 사용하면 불필요한 동기화 작업으로 인해 성능저하가 일어남. -> 보통 동기화가 필요한 작업은 묶어서 처리 -> 메서드 단위로 동기화가되어있으면 멀티 쓰레드 환경에서도 효율적으로 사용할 수 없다.

#### Why Stack extends Vector in JDK?
https://stackoverflow.com/questions/37314298/why-stack-extends-vector-in-jdk


참고링크 : 
https://velog.io/@jhl221123/%EC%9E%90%EB%B0%94%EC%9D%98-Stack%EC%9D%80-%EC%99%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EC%A7%80-%EC%95%8A%EB%8A%94-%EA%B1%B8%EA%B9%8C