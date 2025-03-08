# this와 this()
- this()는 생성자고 this는 참조변수다. 참조변수 this와 생성자 this()는 비슷하지만 연관이 없다.

## 참조변수 this

```
public class Person {
    private String name; //인스턴스 변수
    private int age;
    private String sex;

    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
```
- this는 인스턴스의 자기자신을 의미하는 참조변수. 인스턴스화 되었을 때 자기자신의 메모리 주소를 담고있는 키워드이다.
- 코드를 보면, Person 생성자 내에서 'this.객체 내의 인스턴스 변수 = 매개변수' 형식으로 this 키워드가 사용되고 있는 것을 볼 수 있다. this 키워드는 생성자의 매개변수와 클래스의 인스턴스 변수의 이름이 같을 때, 생성자 내에서 인스턴스 변수를 가리키기 위해 사용된다. 예를 들어, this.name = name은 생성자의 매개변수 name을 클래스의 인스턴스 변수 name에 할당한다는 것을 나타낸다. static 메서드에서는 this 키워드를 사용할수 없다. 인스턴스 메서드에서만 사용가능하다. 


## this 키워드의 세가지 역할
### 자기 자신의 메모리를 가르킨다.
- 클래스의 멤버 변수와 메서드 내의 매개변수 또는 지역 변수의 이름이 충돌하는 경우, this 키워드를 사용하여 멤버 변수를 참조한다. 이렇게 하면 현재 객체의 멤버 변수임을 명시적으로 나타낼 수 있다.

### 생성자에서 다른 생성자를 호출할 경우 사용한다.
- 생성자 내에서 다른 생성자를 호출 할때 this 키워드를 사용할 수 있다. 이를 통해 생성자 간의 코드 중복을 피하고, 여러 생성자에서 공통된 초기화 작업을 수행할 수 있다. 

### 인스턴스 자신의 주소를 반환할 때 사용한다.

## 생성자 this()
```
public class Person {
    private String name;
    private int age;
    private String sex;
    
    // 첫 번째 생성자
    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    
    // 두 번째 생성자: 위의 생성자를 호출하여 초기화함
    public Person() {
        this("김땡땡", 23, "Female"); // this()를 사용하여 다른 생성자 호출
    }
    
    // 세 번째 생성자
    public Person(String name) {
        this(name, 23, "Female"); // this()를 사용하여 다른 생성자 호출
    }
}
```

-  this()는 한 클래스 내에서 한 생성자에서 다른 생성자를 호출할 때 사용된다.
- 코드를 보면, 두 번째 생성자는 매개변수 없이 호출되지만, this()를 사용하여 같은 클래스의 첫 번째 생성자를 호출하고 있다. 이렇게 함으로써, 매개변수가 없는 생성자를 호출하는 대신, 매개변수가 있는 생성자를 호출하고 기본 값을 전달할 수 있다. 또한 세 번째 생성자처럼 이름(name)만을 받아와서 인스턴스를 초기화할 수도 있다. 따라서, this()를 사용하여 생성자를 호출하면 (클래스 내의 다른 생성자를 호출) 코드의 재사용성을 높일 수 있다.


### 질문
#### 왜 static 메서드에서는 this 키워드를 사용할 수 없을까?
- static 메서드는 클래스 수준에서 호출되며 특정 인스턴스에 속하지 않기 때문이다. this 키워드는 인스턴스의 메모리 주소를 가리키지만, static 메서드는 인스턴스 없이 호출되므로 this를 사용할 수 없다.

#### this()를 사용할 때 반드시 생성자의 첫 번째 줄에서 호출해야 하는 이유는?
- this()를 통해 다른 생성자를 호출하는 것은 객체가 생성될 때 초기화 순서를 보장하기 위해서이다. 생성자가 실행되기 전에 다른 생성자의 초기화가 먼저 이루어져야 하므로, this()는 반드시 생성자의 첫 번째 줄에서 호출해야 한다.

--- 

참고링크 

https://programming-tree.tistory.com/105

https://choicode.tistory.com/24