# Instanceof 연산자
![Image](https://github.com/user-attachments/assets/cfb66e29-fcaf-41c8-9e58-cb47c6b57b67)

- 캐스팅의 가능 여부를 판단하기 위해서는 '클래스 사이의 상속 관계', '어떤 생성자로 인스턴스를 생성했는가?'를 판단해야 한다. 프로젝트의 규모가 커지고, 클래스가 많아지면 이러한 조건을 매순간 판단하는 것이 어려워진다.이를 해결하기 위해 Java에서는 캐스팅 가능 여부를 boolean 타입으로 반환해주는 instanceof라는 연산자를 제공한다.
- instanceof 연산자는 Java에서 객체가 특정 클래스의 인스턴스인지, 또는 특정 클래스나 인터페이스를 구현했는지를 확인하는 데 사용한다. 이 연산자는 런타임 시에 객체의 실제 타입을 체크하며, 조건식의 결과는 boolean 타입으로 반환된다. 즉, 해당 객체가 특정 클래스의 인스턴스이면 true, 그렇지 않으면 false를 반환한다.
- A가 B의 인스턴스인지를 판단할 때 A instanceof B 형태로 사용되고, A가 B의 인스턴스라면 true 를, 아니라면 false 를 반환하는 boolean 반환타입을 가진다.
- 바로 하나의 타입이 여러 인스턴스를 가질 수 있을 때 instanceof를 효과적으로 사용할 수 있다. 주로, 상속 관계일 때 instanceof를 효과적으로 사용할 수 있다.

```
class Parent{
}

class Child extends Parent{
}

Parent object = new Child();

if (object instanceof Child){ // object가 Child 인스턴스 형태인가?
}

```

## instanceof를 지양해야하는 이유
### 다형성 위배
- 다형성을 사용하여 같은 코드지만 실제 instance에 따라서 다르게 동작하도록 작성할 수 있다. 그리고 이를 위해서 변수 타입을 interface형으로 다루어 추상화를 시키고 있다.그러나 다형성을 위해서 실제 구현을 모르게 숨겨놨는데, instanceof로 다시 확인/검색 하는 것은 다형성을 위배하는 것이며 코드만 복잡해진다.


```
if (obj instanceof MyClass1) {
    // do something for MyClass1
} else if (obj instanceof MyClass2) {
    // do something for MyClass2
} else if (obj instanceof MyClass3) {
    // do something for MyClass3
} else if (obj instanceof MyClass4) {
    // do something for MyClass4
} else {
    // do something else
}
 
```
 
## 단일 책임 원칙 SRP (Single Responsibility Principle)
- 모든 클래스는 단 하나의 책임을 가진다는 원칙이다. 그러니까 예를 들어, 위의 코드에서 MyClass를 참조하는 클래스에서 instaceof를 통해 동작을 구분하게 된다. 그렇게 되면 MyClass에 대한 동작을 참조하는 쪽에서 지정/수행하는 것처럼 될 수 있다. SRP를 위배하지 않으려면 MyClass에 선언된 동작으로 동작하게 수행해야한다.
 
## 개방 폐쇄의 원칙 OCP (Open Closed Principle)
- 확장에 대해서는 개방 되어 있어야 하지만, 수정에 대해서는 폐쇄 되어야 한다는 원칙이다. 다형성 위배에서 언급하긴 했지만... 새로운 Class가 추가될 때마다 instanceof로 구분하는 코드가 추가 되어야한다. 객체가 확장되거나 추가될 때마다 더 많은 소요/비용이 필요해지게 되어 OCP를 위반할 수도 있다.

## 캡슐화
- 캡슐화는 클래스 안에 서로 연관있는 속성과 기능들을 하나의 캡슐로 만들어 데이터를 외부로부터 보호하는 것을 말한다. 즉, 객체가 가진 상태나 행위를 다른 이가 사용하거나 보지 못하도록 숨기는 것이다. 하지만 instanceof를 사용할 경우, 외부에서 각 객체가 무엇이고 어떤 행위를 하도록 구성되어 있는지 다 알 수 있게 된다. 이 때문에 캡슐화가 깨지고 객체지향적이지 못하게 된다.
- 객체지향에서 말하는 캡슐화란 객체가 가진 상태나 행위를 다른 이가 사용하거나 보지 못하도록 숨기는 것을 의미한다. 하지만 instanceof를 사용하는 경우, 각 객체가 무엇인지, 어떤 점수를 돌려주어야 하는지 불필요한 외부의 객체가 그 정보를 알게 되는 것이다. 때문에 캡슐화가 깨진다는 것을 알 수 있다. 우리는 각 객체가 가진 책임과 역할을 분리해주고, 이로 인해 유지보수, 확장에 있어 편리함을 얻기 위해 객체지향프로그래밍을 한다. 캡슐화가 보장되지 않으면 그 의미가 없어진다. instanceof의 사용을 지양해야 하는 가장 우선적인 이유이다.

## 성능 이슈
- 예시 코드와 같이 pawn, king, empty이 서로 다른 구현이 필요한 경우에 다형성을 적용한 구현을 하게되면 컴파일러는 어떠한 타입의 메서드를 실행해야할지 알 수 없으므로 invokevirtual 바이트코드를 이용해 메서드에 대한 가상의 호출을 한다. 이후 런타임에 특정 타입을 찾아 그에 맞는 구현을 실행한다.
- 반면에 instanceof의 경우 알맞은 타입을 찾을 때까지 컴파일 시에 모든 타입을 돌며 검사해야한다. 그로 인해 다형성을 적용한 성능이 instanceof를 검사하는 성능보다 빠르다. 심지어 확인해야할 객체가 많으면 많을수록 불필요한 instanceof 검사가 더 필요하고 성능의 차이는 점차 커진다.

## 대안책
- 서브 클래스에 isType() 형식으로 추상 메소드를 선언하는 방식으로 해볼 수 있다.

```
class Parent{
  public abstract boolean isChild();
}

class Child extends Parent{

  @Override
  public boolean isChild(){
    return true;
  }
}

```
- 사실 이렇게 보면 결국 instanceof로 작성하던 내용을 isChild로 확인하는 것이라 뭐가 다른가 싶을 수도 있다. 하지만 확인하는 주체와 책임이 참조하는 쪽이 아닌 서브 클래스 쪽에 있다는 점에 있어서 좀 더 보완적인 코드라고 할 수 있다.


### 질문 
#### instanceof 사용이 다형성을 위배하는 이유는 무엇인가?
- 다형성은 객체의 실제 타입을 숨기고 인터페이스나 상위 클래스를 통해 동작하도록 설계하는 원칙이다. 그러나 instanceof를 사용하면 객체의 실제 타입을 직접 확인해야 하므로 다형성을 위배하며, 코드가 타입별로 분기되는 복잡한 구조가 된다.


#### instanceof를 대체할 수 있는 더 객체지향적인 접근 방식은 무엇인가?
- 객체 내부에서 자신의 타입을 판단하는 isType() 같은 메서드를 제공하거나, 다형성을 활용해 공통 인터페이스나 추상 메서드를 정의한 후 각 클래스가 이를 구현하도록 하면 instanceof 없이도 유연한 확장이 가능하다.

--- 

참고링크 

https://dunchi.tistory.com/112

https://seoarc.tistory.com/46

https://ksh-coding.tistory.com/84

https://tecoble.techcourse.co.kr/post/2021-04-26-instanceof/

https://f-lab.kr/insight/java-instanceof-issues-20240820