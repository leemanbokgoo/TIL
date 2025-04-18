# item2 생성자에 매개변수가 많다면 빌더를 고려하라.
- 정적 패터리와 생성자에는 똑같은 제약이 하나 있다. 선택적 매개변수가 많을 떄 적절히 대응하기 어렵다는 점이다.

### 점층적 생성자 패턴 (Telescoping Constructor Pattern)
- 필수 매개변수만 받는 생성자, 필수 매개변수와 선택 매개변수 1개를 받는 생성자. 형태로 생성자를 늘려가는 방식이다.
- 확장하기 힘들고, 매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다는 단점이 있다.
- 클라이언트에서 실수로 매개변수의 순서를 바꿔 건네줘도 컴파일러는 알아채지 못하고 결국 런타임에 엉뚱한 동작을 하게 된다.

``` 
public class NutritionFacts {

    private final int serving;          // 필수
    private final int servings;         // 필수
    private final int calories;         // 선택
    private final int fat;              // 선택
    private final int sodium;           // 선택
    private final int carbohydrate;     // 선택

    public NutritionFacts(int serving, int servings) {
        this(serving, servings, 0);
    }

    public NutritionFacts(int serving, int servings, int calories) {
        this(serving, servings, calories, 0);
    }

    public NutritionFacts(int serving, int servings, int calories, int fat) {
        this(serving, servings, calories, fat, 0);
    }

    public NutritionFacts(int serving, int servings, int calories, int fat, int sodium) {
        this(serving, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int serving, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.serving = serving;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}

// 사용 예시
NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);
``` 

### 자바빈즈 패턴 (JavaBeans Pattern)
- 매개변수가 없는 생성자로 객체를 생성하고, setter를 호출해서 원하는 매개변수 값을 설정하는 방식이다.
- 객체 1개를 만들려면 메서드 n개를 호출해야 하고, 객체가 완전해지기 전까지는 일관성(consistency)이 무너진 상태에 놓이게 된다는 단점이 있다. 점증척 생성자 패턴에서는 매개변수들이 유효한지를 생성자에서만 확인하면 일관성을 유지할 수 있었는데 그 장치가 완전히 사라진 것. 일관성이 깨진 객체가 만들어지면 버그를 심은 코드와 그 버그 떄문에 런타임에 문제를 겪는 코드가 물리적으로 멀리 떨어져있을 것이므로 디버깅도 어렵다.
- 이처럼 일관성이 무너지는 문제 때문에 클래스를 불변으로 유지할 수 없으며 스레드 안정성을 얻으려면 프로그래머가 추가 작업을 해주어야만 한다.
- 이러한 단점을 완화하고자 생성이 끝난 객체를 수동으로 얼리고 얼리기전에는 사용할 수 없도록 하기도하지만 이 방법은 다루기 어려워 실무에선 거의 쓰이지않음.

``` 
public class NutritionFacts {

    private final int serving = -1;         // 필수, 기본값 없음
    private final int servings = -1;        // 필수, 기본값 없음
    private final int calories = 0;         // 선택, 기본값 있음
    private final int fat = 0;              // 선택, 기본값 있음
    private final int sodium = 0;           // 선택, 기본값 있음
    private final int carbohydrate = 0;     // 선택, 기본값 있음

    public NutritionFacts() {
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}

// 사용 예시
NutritionFacts cocaCola = new NutritionFacts();
cocaCola.setServing(240);
cocaCola.setServings(8);
cocaCola.setCalories(100);
cocaCola.setSodium(35);
cocaCola.setCarbohydrate(27);
``` 

### 빌더 패턴 (Builder Pattern)
- 점층적 생성자 패턴의 안전성과 자바빈즈 패턴의 가독성을 겸비했다.
- 클라이언트는 필수 매개변수만 받는 생성자 또는 정적 팩터리 메서드를 호출해서 빌더 객체를 얻고, 이 객체가 제공하는 일종의 setter로 원하는 선택 매개변수를 설정한다. 마지막에는 매개변수가 없는 build()를 호출해서 우리에게 필요한 객체를 얻는다. 해당 객체는 보통 불변이다.
- 빌더의 setter는 빌더 자신을 반환한다. 그래서 연쇄적으로 호출할 수 있다.
- 이를 메서드 호출이 흐르듯 연결된다고 하여 플루언트 API(fluent API) 또는 메서드 연쇄(method chaining)라 한다.
- 한편, 객체를 만들기에 앞서 빌더부터 만들어야 한다는 단점이 있다. 또한 빌더 생성 비용이 크지는 않지만 성능에 민감한 상황에서는 영향을 끼칠 수 있고, 코드가 장황해서 매개변수가 4개 이상은 돼야 값어치를 한다는 단점도 존재한다.

``` 
public class NutritionFacts {

    private final int serving;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    private NutritionFacts(Builder builder) {
        serving = builder.serving;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static class Builder {
        
        // 필수
        private final int serving;
        private final int servings;
    
        // 선택
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int serving, int servings) {
            this.serving = serving;
            this.servings = servings;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder fat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        public Builder carbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }    
}

// 사용 예시
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
        .calories(100)
        .sodium(35)
        .carbohydrate(27)
        .build();
``` 

# item3 private 생성자나 열거타입으로 싱글턴임을 보장하라.
- 클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기 어려워 질 수 있다.
    - 싱글톤을 이용하는 경우 대부분 인터페이스가 아닌 콘크리트 클래스의 객체를 미리 생성해놓고 정적 메소드를 이용하여 사용하게 된다. 이는 여러 SOLID원칙을 위반할 수 있는 가능성을 열어둠과 동시에, 싱글톤을 사용하는 곳과 싱글톤 클래스 사이에 의존성이 생기게 된다. 클래스 사이에 강한 의존성, 즉 높은 결합이 생기게 되면 수정, 단위테스트의 어려움 등 다양한 문제가 발생한다.
    - 싱글톤은 만들어지는 방식이 제한적이기 때문에 테스트에서 사용될 때 mock 오브젝트 등으로 대체하기가 힘들다 (토비의 스프링 3.1 1장)
    - 클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다. 타입을 인터페이스로 정의한 다음 그 인터페이스를 구현해서 만든 싱글턴이 아니라면 싱글턴 인스턴스를 가짜 구현(mock object)으로 대체할 수 없기 때문이다. Mockito는 정적 메서드를 mock할 수 없기 때문에 가짜(mock)를 주입하기 어렵다. 대신 static 메소드를 mocking할 수 있는 PowerMock같은 도구를 사용하면 가능해진다.(이펙티브 자바 item3)

## 싱글턴 만드는 방식 

### ppublic static 멤버가 final 필드인 방식
- private 생성자는 public static final 필드를 초기화할 때 딱 1번만 호출된다.
- public/protected 생성자가 없으므로 해당 클래스의 객체가 전체 시스템에서 1개뿐임이 보장된다.
- 단, 권한이 있는 클라이언트는 리플렉션 API를 이용하여 private 생성자를 호출할 수 있다.
- 이러한 공격은 생성자를 수정하여 클래스의 2번째 인스턴스가 생성되려 할 때 예외를 발생함으로써 방지할 수 있다.

### 장점
- 해당 클래스가 싱글턴임이 API에 명백히 드러난다. public static 필드가 final이니 절대로 다른 객체를 참조할 수 없다.
- 간결하다.
### 단점
- 직렬화하기 어렵다.
- 단순히 Serializable 인터페이스를 구현한다고 선언하는 것만으로는 부족하다.
- 모든 필드에 transient 키워드를 선언하고, readResolve()를 제공해야 한다.
- 아니면, 직렬화된 인스턴스를 역직렬화할 때마다 새로운 인스턴스가 만들어진다.
``` 
public class Elvis {

    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public void dance() {
        ...
    }
}
``` 

## 정적 팩터리 방식
- getInstance()는 항상 같은 객체의 참조를 반환하여 클래스의 2번째 인스턴스는 결코 만들어지지 않는다.
- 한편, 이때에도 리플렉션 API를 통한 예외는 똑같이 적용된다.

### 장점
- 요구사항이 변경되면, API를 바꾸지 않고도 싱글턴이 아니게 수정할 수 있다. 유일한 인스턴스를 반환하던 팩터리 메서드가 호출하는 스레드별로 다른 인스턴스를 넘겨주게 할 수 있다.
- 원한다면 정적 팩터리를 제네릭 싱글턴 팩터리로 만들 수 있다.
- 정적 팩터리의 메서드 참조를 공급자(supplier)로 사용할 수 있다. 예를 들면 Elvis::getInstance를 Supplier<Elvis>로 사용하는 식이다. 이러한 장점들이 굳이 필요하지 않다면 public 필드 방식이 좋다.

### 단점
- 직렬화하기 어렵다.
- 단순히 Serializable 인터페이스를 구현한다고 선언하는 것만으로는 부족하다.
- 모든 필드에 transient 키워드를 선언하고, readResolve()를 제공해야 한다.
- 아니면, 직렬화된 인스턴스를 역직렬화할 때마다 새로운 인스턴스가 만들어진다.

## Enum 타입 방식
- 조금 부자연스러울 수는 있다.
- 그렇지만, 대부분 상황에서는 원소가 1개뿐인 Enum 타입이 싱글턴을 만드는 가장 좋은 방법이다.
### 장점
- 더 간결하다.
- 추가 노력 없이 직렬화할 수 있다.
- 리플렉션 API의 공격에도 클래스의 2번째 인스턴스가 생기는 일을 완벽히 막아준다.
### 단점
- 싱글턴이 Enum 타입 외의 클래스를 상속해야 한다면, 이는 활용할 수 없다.그러나 Enum 타입이 인터페이스를 구현하도록 선언할 수는 있다.

---

참고링크

https://da-nyee.github.io/posts/effective-java-item-2/