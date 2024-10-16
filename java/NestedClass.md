# NestedClass 중첩 클래스

- 자바는 한 클래스 내에서 다른 클래스를 정의할 수 있는데 이를 Nested클래스라고 한다. Nested클래스는 다시 static 클래스와 non-static(inner Class)클래스로 나눌 수 있다.

```
class OutClass{
    class Innerclass{

    }

    static class StaticClass{

    }
}
```

## Inner Class 이너 클래스 (Non-static Nested 클래스)
I- nner 클래스는 외부 클래스의 인스턴스에 종속적이며, 외부 클래스의 멤버(필드와 메서드)에 자유롭게 접근할 수 있다. 이를 사용하는 주된 이유는 Inner 클래스가 외부 클래스의 인스턴스와 밀접하게 연관된 작업을 수행하기 때문.

# 사용하는 이유:
- 외부 클래스와의 긴밀한 연관성: Inner 클래스는 외부 클래스의 인스턴스와 밀접하게 연관된 작업을 할 때 유용함. Inner 클래스는 외부 클래스의 인스턴스 필드에 접근할 수 있다.
- 캡슐화: 내부적으로만 사용해야 하는 기능을 제공하기 위해 사용되며, 외부에서 Inner 클래스를 직접 접근할 수 없게 만들어 더 높은 수준의 캡슐화를 제공.
- 이벤트 핸들링: GUI 프로그래밍에서 종종 사용되며, 예를 들어 버튼 클릭 이벤트 처리와 같은 작업을 Inner 클래스로 구현할 수 있다.

```

class Outer {
    private String message = "Hello, World!";

    class Inner {
        void printMessage() {
            // 외부 클래스의 필드에 접근 가능
            System.out.println(message);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.printMessage();  // "Hello, World!" 출력
    }
}

```

##  Nested 클래스 (Static Nested 클래스)
- Static Nested 클래스는 외부 클래스의 인스턴스에 종속되지 않으며, 외부 클래스의 정적 멤버(static 필드와 메서드)에만 접근할 수 있다. 이를 사용하는 이유는 외부 클래스의 인스턴스 없이도 동작해야 하는 유틸리티 성격의 작업을 수행하기 위함.

### 사용하는 이유:
- 독립적 기능: Nested 클래스는 외부 클래스의 인스턴스와 관련이 없는 독립적인 기능을 수행할 때 사용. 외부 클래스의 정적 맥락에서 동작할 수 있다.
- 코드 구조화: 외부 클래스와 관련된 작업을 하되, 외부 클래스의 인스턴스에 의존하지 않는 코드를 깔끔하게 조직화할 수 있다.
- 성능 최적화: Static Nested 클래스는 메모리 사용 측면에서 Inner 클래스보다 더 효율적일 수 있다. 외부 클래스의 인스턴스 없이 생성 가능하기 때문에 불필요한 메모리 사용을 줄일 수 있다.


```
class Outer {
    private static String message = "Hello from static nested class!";

    static class StaticNested {
        void printMessage() {
            // 외부 클래스의 정적 필드에만 접근 가능
            System.out.println(message);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Outer.StaticNested nested = new Outer.StaticNested();
        nested.printMessage();  // "Hello from static nested class!" 출력
    }
}

```

## Inner 클래스와 Nested 클래스를 사용하는 상황
- Inner 클래스는 외부 클래스의 상태(인스턴스 필드)에 종속적인 로직을 구현할 때 사용된다. 예를 들어, 외부 객체의 상태에 따라 동작해야 하는 기능을 내부적으로 처리하는 경우가 이에 해당.

- Static Nested 클래스는 외부 클래스의 상태에 종속적이지 않은 유틸리티 메서드나 정적 컨텍스트에서 동작하는 로직을 구현할 때 유용.
이렇게 Inner 클래스와 Nested 클래스는 사용하려는 시나리오에 따라 외부 클래스의 인스턴스와의 연관성에 따라 선택되어 사용.