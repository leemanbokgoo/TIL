# Optional

- Optional은 NPE(NullPointException)을 방지하기위해 사용한다. 메서드가 반환할 결과값이 없음을 명백하게 표현할 필요가 있고 null을 반환하면 에러를 유발할 가능성이 높은 상황에서 메서드의 반환타입으로 Optional를 사용하자는 것이 Optional를 만든 주된 목적이다. 
- 어떤 메서드가 null을 반환할지 알수없거나 null 처리를 놓쳐서 발생하는 예외를 피할 수 있다. 
- 예를 들어, 데이터베이스에서 특정 항목을 찾는 메서드를 호출할 때, 결과가 없을 수도 있다. 이때 Optional을 사용하면 코드를 더 명확하게 작성할 수 있고, 예외 처리를 더 쉽게 할 수 있어 코드 가독성이 높아지며, 유지보수도 더 편리해진다. 만약 Optional이 없다면, 프로그램에서 더 자주 NPE(NullPointerException)가 발생하게 되고, 이로 인해 프로그램이 중단되는 등 사용자에게 문제를 야기할 수 있다. 그래서 Optional을 사용할 줄 아는 개발자와 아닌 개발자의 작업 효율에서 큰 차이가 발생함.
- 자바 8부터 도입되었으며 **값이 없는 경우**를 표현하기위한 용도로 사용되는 클래스
- 자바 제네릭을 사용하여 만들어져있으므로, 어떤 타입의 객체라도 값이 없을 수 있는 경우에 Optional를 사용하여 표현할 수 있음. 

## Java Optional 사용목적
- Optional은 메서드의 리턴 타입으로 ‘결과 없음’을 명확히 표현하는 용도로 제한적으로 사용하기 위한 용도로 제공됨. 
- Java8전에는 Optional이라는 클래스가 없었으므로, 메서드의 리턴 타입으로 ‘결과 없음’을 표현하기 위한 용도로 null이 사용되었다. 그러나 객체 값이 null인 경우에, null 체크 없이 해당 객체의 메서드를 호출하거나 하는 등, 부주의하게 사용하는 경우에 ‘NPE(NullPointerException)’이 발생하면서 프로그램이 죽어버리는 상황이 발생했다. 이런 상황을 방지하고 안전한 코딩을 위해서 Optional이 만들어지게 되었습니다. 
- null 값을 반환해야 하는 경우에 Optional로 감싼 객체로 empty 값을 표현하여 결괏값을 넘겨주면, null로 인한 오류 문제를 없앨 수 있다.


## Java Optional을 사용했을 때의 이점
- Optional은 ‘리턴 타입’의 용도로 제한적으로 사용하기 위해서 만들어졌기 때문에, 메서드의 ‘리턴 타입’에 사용하여 null 체크의 문제를 없애고, null-safe 한 코드를 제공할 수 있다.
- Optional을 사용하지 않으면, 빈 결과를 반환해야 할 경우에, null이나 예외를 던지는 형태로 처리해야 합니다. 이럴 때 Optional을 반환값에 사용하게 되면,
    1) 빈 결과를 명확히 반환할 수 있고,
    2) null을 반환하는 메서드보다 오류 가능성이 적고,
    3) null 일 때 예외를 던지는 메서드보다 더 사용이 용이한 코드를 만들 수 있다.

## Optional 사용 시 주의사항
####  Optional 변수에 null을 할당하지 않는다.
- Optional은 null을 사용하지 않기 위해서 만들어진 클래스인데, Optional을 null로 초기화하면 Optional의 사용 의도와 맞지 않게 된다. 빈 값으로 Optional을 만들려면 Optional.empty()로 객체를 생성해야 한다.

#### Optional을 필드 타입으로 사용하지 않는다
- Optional은 메서드의 리턴 타입으로 사용하기 위한 용도로 설계되었기 때문에, Optional을 클래스의 필드로 선언하는 것은 잘못된 사용 방식이며, 필드 타입은 객체를 그대로 사용하는 것이 올바른 사용 방식이다.

#### 메서드의 인자로 Optional을 사용하지 않는다.
- Optional은 메서드의 리턴 타입으로 사용하기 위한 용도이므로, Optional을 메서드의 인자로 사용하는 것도 잘못된 사용 방식으로 메서드의 인자도 역시 객체를 그대로 사용하는 것이 올바른 사용 방식.

#### 빈 컬렉션이나 배열을 나타낼 때는 Optional을 사용하지 않는다.
- 빈 컬렉션을 리턴하고 자 하는 경우는 Optional을 사용할 것이 아니라, 빈 컬렉션을 리턴하는 것이 올바른 사용 방식. ArrayList나 HashMap() 등은 최초 객체를 생성하면 비어있는 컬렉션이 생성이 되므로 이를 사용해서 리턴하면 된다. 컬렉션이 ‘비어있음’을 나타내기 위해서 굳이 Optional을 사용하는 것은 적합한 사용 방식이 아니다. 마찬가지로 배열의 경우도 ‘비어있음’을 나타내려면 빈 배열을 생성하여 사용하면 된다.

#### 원시 타입을 값으로 가지는 Optional이 필요할 때는 원시 타입 용도로 만들어진 클래스를 사용한다.
- 원시 타입(primitive type)을 Optional로 사용해야 할 때는 Wrapper 클래스로 된 Optional 클래스를 사용하지 말고, 원시 타입 용도로 만들어진 OptionalInt, OptionalLong, OptionalDouble 형태의 클래스를 사용하는 것이 성능 측면에서 유리함. 또한 Optional, Optional, Optional 형태를 사용하게 되면, boxing, unboxing이 일어나면서 성능이 저하되게 된다.

## 3. Optional의 orElse와 orElseGet 차이 
- Optional API의 단말 연산에는 orElse와 orElseGet 함수가 있다. 비슷해 보이는 두 함수는 엄청난 차이가 있다.
    - orElse: 파라미터로 값을 받는다.
    - orElseGet: 파라미터로 함수형 인터페이스(함수)를 받는다.
- 실제로 Optional 코드를 보면 다음과 orElse와 orElseGet이 각각 구현되어 있음을 확인할 수 있다.

```
public final class Optional<T> {

    ... // 생략

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }
    
}
```

- orElse로는 값이, orElseGet로는 함수가 넘어간다는 것은 상당히 큰 차이가 있다. 이로 인해 호출 결과가 달라질 수 있기 때문인데, 관련된 내용을 코드로 살펴보도록 하자.

#### orElse와 orElseGet의 차이 예시 코드
- 예를 들어 다음과 같은 예시 코드가 있다고 하자. 첫 번째 함수는 값이 비어있을 때 orElse를 호출하도록 되어있고, 두 번째 함수는 orElseGet을 호출하도록 되어있다. 바로 아래로 내려가지 않고, 각각에 의한 출력 결과를 예상해보도록 하자.

```
public void findUserEmailOrElse() {
    String userEmail = "Empty";
    String result = Optional.ofNullable(userEmail)
    	.orElse(getUserEmail());
        
    System.out.println(result);
}

public void findUserEmailOrElseGet() {
    String userEmail = "Empty";
    String result = Optional.ofNullable(userEmail)
    	.orElseGet(this::getUserEmail);
        
    System.out.println(result);
}

private String getUserEmail() {
    System.out.println("getUserEmail() Called");
    return "mangkyu@tistory.com";
}

```
- 위의 함수를 각각 실행해보면 다음과 같은데, 이러한 결과가 발생한 이유를 자세히 살펴보도록 하자.

```
// 1. orElse인 경우
getUserEmail() Called
Empty

// 2. orElseGet인 경우
Empty

```
- 먼저 OrElse인 경우에는 다음과 같은 순서로 처리가 된다.
    - Optional.ofNullable로 "EMPTY"를 갖는 Optional 객체 생성
    - getUserEmail()가 실행되어 반환값을 orElse 파라미터로 전달
    - orElse가 호출됨, "EMPTY"가 Null이 아니므로 "EMPTY"를 그대로 가짐
- 위와 같이 동작하는 이유는 Optional.orElse()가 값을 파라미터로 받고, orElse 파라미터로 값을 넘겨주기 위해 getUserEmail()이 호출되었기 때문이다. 하지만 함수형 인터페이스(함수)를 파라미터로 받는 orElseGet에서는 동작이 달라진다.
    - Optional.ofNullable로 "EMPTY"를 갖는 Optional 객체 생성
    - getUserEmail() 함수 자체를 orElseGet 파라미터로 전달
    - orElseGet이 호출됨, "EMPTY"가 Null이 아니므로 "EMPTY"를 그대로 가지며 getUserEmail()이 호출되지 않음

- orElseGet에서는 파라미터로 넘어간 값인 getUserEmail 함수가 Null이 아니므로 .get에 의해 함수가 호출되지 않는다. 만약 Optional의 값으로 null이 있다면, 다음과 같은 흐름에 의해 orElseGet의 파라미터로 넘어온 getUserEmail()이 실행될 것이다.

```
public void findUserEmailOrElseGet() {
    String result = Optional.ofNullable(null)
    	.orElseGet(this::getUserEmail);
        
    System.out.println(result);
}

private String getUserEmail() {
    System.out.println("getUserEmail() Called");
    return "mangkyu@tistory.com";
}
 
```
    - Optional.ofNullable로 null를 갖는 Optional 객체 생성
    - getUserEmail() 자체를 orElseGet 파라미터로 전달
    - orElseGet이 호출됨, 값이 Null이므로 other.get()이 호출되어 getUserEmail()가 호출됨

### orElse에 의한 발생가능한 장애 예시

- 위에서 살펴보았듯 orElse와 orElseGet은 명확하고 중요한 차이가 있는데, 이를 정확히 인식하지 못하면 장애가 발생할 수 있다. 예를 들어 userEmail을 Unique한 값으로 갖는 시스템에서 아래와 같은 코드를 작성하였다고 하자.
```
public void findByUserEmail(String userEmail) {
    // orElse에 의해 userEmail이 이미 존재해도 유저 생성 함수가 호출되어 에러 발생
    return userRepository.findByUserEmail(userEmail)
            .orElse(createUserWithEmail(userEmail));
}

private String createUserWithEmail(String userEmail) {
    User newUser = new User(userEmail);
    return userRepository.save(newUser);
}
```
 
- 위의 예제는 Optional의 단말 연산으로 orElse를 사용하고 있기 때문에, 조회 결과와 무관하게 createUserWithEmail 함수가 반드시 실행된다. 하지만 Database에서는 userEmail이 Unique로 설정되어 있기 때문에 오류가 발생할 것이다. 그렇기 때문에 위와 같은 경우에는 다음과 같이 해당 코드를 orElseGet으로 수정해야 한다. 이렇게 코드를 수정하였다면 파라미터로 createUserWithEmail 함수 자체가 넘어가므로, 조회 결과가 없을 경우에만 사용자를 생성하는 로직이 호출 될 것이다. 
```
public void findByUserEmail(String userEmail) {
    // orElseGet에 의해 파라미터로 함수를 넘겨주므로 Null이 아니면 유저 생성 함수가 호출되지 않음
    return userRepository.findByUserEmail(userEmail)
           .orElseGet(this::createUserWithEmail(userEmail));
}

private String createUserWithEmail(String userEmail) {
    User newUser = new User(userEmail);
    return userRepository.save(newUser);
}
```
 
- 실제 서비스에서 위와 같은 오류를 범한다면 큰 시스템 장애로 돌아오게 된다. 설령 문제가 없다고 하더라도 orElse는 값을 생성하여 orElseGet보다 비용이 크므로 최대한 사용을 피해야 한다. 그러므로 orElse와 orElseGet의 차이점을 정확히 이해하고 사용하도록 하자.
 
### orElse와 orElseGet의 차이점 및 사용법 정리
#### orElse
- 파라미터로 값을 필요로한다.
- 값이 미리 존재하는 경우에 사용한다.
### orElseGet
- 파라미터로 함수(함수형 인터페이스)를 필요로 한다.
- 값이 미리 존재하지 않는 거의 대부분의 경우에 orElseGet을 사용하면 된다.


### 질문 
#### 1. 왜 Optional을 필드나 메서드의 인자로 사용하면 안 되는가?
- Optional은 메서드의 리턴 타입으로 사용하기 위해 설계된 클래스입니다. 필드로 사용할 경우 불필요하게 복잡해지고, 메서드 인자로 사용할 경우 객체로 직접 전달할 수 있는 간단한 상황에서 코드 가독성이 떨어지며 설계 의도와 어긋납니다.

#### 2. orElse와 orElseGet의 차이점은 무엇이며, 어떤 경우에 각각 사용해야 하는가?
- orElse는 값을 파라미터로 받고, 해당 값이 항상 생성되므로 불필요한 리소스 낭비가 발생할 수 있습니다. 반면 orElseGet은 함수를 파라미터로 받아 필요할 때만 호출되므로 효율적입니다.
값이 미리 존재하는 경우에는 orElse를, 값이 존재하지 않을 가능성이 높은 경우에는 orElseGet을 사용하는 것이 적절합니다.

----

출처 링크 

https://www.elancer.co.kr/blog/detail/265

https://mangkyu.tistory.com/70 