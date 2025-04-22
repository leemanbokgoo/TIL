# item10 equals는 일반 규약을 지켜 재정의하라

## equals를 재정의하지않는 것이 최선인 상황
- 각 인스턴스가 본질적으로 고유하다. 값을 표현하는 게 아니라 동작하는 개체를 표현하는 클래스가 여기에 해당한다. ex) Thread
- 인스턴스의 논리적 동치성을 검사할 일이 없다.
- 상위 클래스에서 재정의한 equals가 하위 클래스에도 딱 들어맞는다
- 클래스가 private거나 paxkage-private이고 equals 메서드를 호출 할 일이 없다.
    - equals() 메서드를 지원하지 않을 때 아래와 같이 표현할 수 있다.
    ```
    @Override
    public boolean equals(Object o) {
    throw new AssertionError(); // 호출 금지
    }
    ```
## equals를 재정의해야할 상황
- 객체 식별성(두 객체가 물리적으로 같은가)이 아니라 논리적 동치성을 확인해야하는데 상위 클래스의 equals가 논리적 동치성을 비교하도록 재정의하지않았을때. 주로 값 클래스들이 여기 해당된다.
    - 값 클래스란 Integer와 String처럼 값을 표현하는 클래스를 말한다. 이 경우엔 동치 비교는 물론, Map의 키, Set의 원소로도 활용할 수 있다.
- 값 클래스라 해도, 값이 같은 인스턴스가 둘 이상 만들어지는지 않음을 보장하는 인스턴스 통제 클래스라면 equals를 재정의하지 않아도 된다.
    - enum도 여기에 해당한다.
    - 인스턴스가 2개 이상 만들어지지 않으니 논리적 동치성과 객체 식별성이 사실상 똑같은 의미가 된다.

## equals를 재정의할때 따라야하는 일반규약
- equals메서드는 동치관계를 구현하며 다음을 만족한다.
    - 반사성(reflectivity): null이 아닌 모든 참조값 x에 대해 x.equals(x)는 true여야 한다.
    - 대칭성(symmetry):  null이 아닌 모든 참조값 x, y에 대해 x.equals(y)가 true면, y.equals(x)도 true여야 한다.
    - 추이성(transivity): null이 아닌 모든 참조값 x, y에 대해 x.equals(y)가 true이고, y.equals(z)도 true면, x.equals(z)도 true여야 한다.
    - 일관성(consistency): null이 아닌 모든 참조값 x, y에 대해 x.equals(y)를 얼마나 반복하든, 결과는 항상 같아야 한다.
    - null-아님: null이 아닌 모든 참조 값 x에 대해 x.equals(null)은 false이다. null이 아니란 전제 하에 아래 조건들이 지켜져야 한다.
- 자바 API에서 equals()를 이용하는 내용은 모든 클래스가 위 규약을 지키고 있다고 가정한다.
- Object에서 말하는 동치 관계란 무엇일까? 쉽게 말해, 집합을 서로 같은 원소들로 이뤄진 부분집합으로 나누는 연산이다. 이 부분집합을 동치류(동치 클래스)라 한다. 모든 원소가 같은 동치류에 속한 어떤 원소와도 서로 교환할 수 있어야 한다. 동치 관계를 만족시키기 위한 다섯 요건을 자세히 다뤄보자.
- 위 규약 중에서 대칭성, 추이성, 일관성만 주목하자. 반사성과 null이 아님 은 문제되는 경우가 별로 없다.


### 반사성
- 객체는 자기 자신과 같아야한다는 뜻이다. 이 요건을 일부러 어기는 경우가 아니라면 만족시키지 못하기가 더 어려워 보인다. 이 요건을 어긴 클래스의 인스턴스를 컬렉션에 넣은 다음 contains메서드를 호출하면 방금 넣은 인스터스가 없다고 답할 것이다.

### 대칭성
- 두 객체는 서로에 대한 동치여부에 똑같이 답해야하는 뜻.
```
public class Item10Test {
    static class CaseInsensitiveString {
        private final String s;

        public CaseInsensitiveString(String s) {
            this.s = Objects.requireNonNull(s);
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof CaseInsensitiveString) {
                return s.equalsIgnoreCase(
                        ((CaseInsensitiveString) o).s
                );
            }

            if(o instanceof String) {
                return s.equalsIgnoreCase((String) o);
            }

            return false;
        }
    }

    @Test
    @DisplayName("대칭성을 위배하는 예시")
    public void symmetryViolation() {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString("abc");
        String string = "AbC";

        boolean caseInsensitiveEquals = caseInsensitiveString.equals(string);
        System.out.println("caseInsensitiveEquals = " + caseInsensitiveEquals); // true

        boolean stringEquals = string.equals(caseInsensitiveString);
        System.out.println("stringEquals = " + stringEquals); // false
    }
}
```
- seInsensitiveString 클래스와 String 클래스를 비교할 때는 대소문자 구분없이 비교가 된다.
- String 클래스는 CaseInsensitiveString 타입을 비교대상으로 받는 경우 무조건 false가 나타난다.
- 한쪽 클래스만 다른쪽 클래스와 비교할 준비가 된 상태이다. 대칭성이 위배된다.
- equals() 메서드를 잘못 구현하면, 단순히 equals() 메서드만 문제가 생기는 것이 아니고, 클래스를 받아 equals()를 활용하는 모든 곳에서 문제가 생긴다. ex) List, Map, Set

### 추이성
- 첫번째 객체와 두번째 객체가 같고 두번째 객체와 세번째 객체가 같다면 첫번째 객체와 세번째 객체도 같아야한다는 뜻이다.
- 간단히 2차원에서의 점을 표현하는 클래스를 예로 들어보자.
```
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point)o;
        return p.x == x && p.y == y;
    }

}
```
- 이제 이 클래스를 확장해서 점에 색상을 더해보자.

```
public class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    // 코드 10-2 잘못된 코드 - 대칭성 위배! (57쪽)
    @Override public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        return super.equals(o) && ((ColorPoint) o).color == color;
    }

	...
}
```
- equals 메서드는 어떻게 해야 할까? 그대로 둔다면 Point의 구현이 상속되어 색상 정보는 무시한 채 비교를 수행한다. equals 규약을 어긴 것은 아니지만, 중요한 정보를 놓치게 되니 받아들일 수 없는 사오항이다. 다음 코드처럼 비교 대상이 또 다른 ColorPoint이고 위치와 색상이 같은 때만 true를 반환하는 equals를 생각해보자.

```
// 코드 10-2 잘못된 코드 - 대칭성 위배!
@Override public boolean equals(Object o) {
	if (!(o instanceof ColorPoint))
		return false;
	return super.equals(o) && ((ColorPoint) o).color == color;
}
```
- 이 메서드는 일반 Point를 ColorPoint에 비교한 결과와 그 둘을 바꿔 비교한 결과가 다를 수 잇다. Point의 equals는 색상을 무시하고, ColorPoint의 equals는 입력 매개변수의 클래스 종류가 다르다면 매번 false만 반환할 것이다.
```
public static void main(String[] args) {
        // 첫 번째 equals 메서드(코드 10-2)는 대칭성을 위배한다. (57쪽)
        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.RED);
        System.out.println(p.equals(cp) + " " + cp.equals(p)); // 두 연산의 결과가 다르게 된다!!!
}
```
- p.equals(cp)는 true, cp.equals(p)는 false를 반환한다. 그렇다면 ColorPoint.equals가 Point와 비교할땐 색상을 무시하도록 하면 해결될까?
```
// 코드 10-3 잘못된 코드 - 추이성 위배!
@Override public boolean equals(Object o) {
	if (!(o instanceof Point))
		return false;

	// o가 일반 Point면 색상을 무시하고 비교한다.
	if (!(o instanceof ColorPoint))
		return o.equals(this);

	// o가 ColorPoint면 색상까지 비교한다.
	return super.equals(o) && ((ColorPoint) o).color == color;
}
```
- 이 방식은 대칭성은 지켜주지만, 추이성을 깨버린다.

```
// 두 번째 equals 메서드(코드 10-3)는 추이성을 위배한다.
ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
Point p2 = new Point(1, 2);
ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
System.out.printf("%s %s %s%n", p1.equals(p2), p2.equals(p3), p1.equals(p3));

```
- p1.equals(p2)와 p2.equals(p3)는 true를 반환하지만, p1.equals(p3)는 false 를 반환한다. 추이성을 명백히 위배한다. p1과 p2, p2와 p3에선 색상을 무시했지만 p1과 p3비교에선 색상까지 고려했기 때문이다.
- 또한 이 방식은 무한 재귀에 빠질 위험도 있다. Point의 또 다른 하위 클래스로 SmellPoint를 만들고, equals는 같은 방식으로 구현했다고 해보자. 그런 다음 myColorPoint.equals(mySmellPoint)를 호출하면 StackOverflowError를 일으킬 것이다. 이 설명만 보면 이해가 쉽지 않을 텐데 아래 코드에서보면 target객체의 equals를 계속 서로 호출할 것이기 떄문이다.

```
// o가 일반 Point면 색상을 무시하고 비교한다.
if (!(o instanceof ColorPoint))
	return o.equals(this);
```
- 해법은 무엇일까? 사실 이 현상은 모든 객체 지향 언어의 동치 관계에서 나타나는 근본적인 문제이다. 구체 클래스를 확장해 새로운 값을 추가하면서 equals 규약을 만족시킬 방법은 존재하지 않는다. 객체 지향적 추상화의 이점을 포기하지 않는 한은 말이다.
- 이 말은 얼핏, equals 안의 instanceof 검사를 getClass 검사로 바꾸면 규약도 지키고 값도 추가하면서 구채 클래스를 상속할 수 있다는 뜻으로 들린다.

```
@Override public boolean equals(Object o) {
	if (o == null || o.getClass() !== getClass()) {
		return false;
	}

	Point p = (Point) p;
	return p.x == x && p.y == y;
}
```
- 이번 equals는 같은 구현 클래스의 객체와(ColorPoint) 비교할 때만 true를 반환한다. 괜찮아 보이지만 실제로 활용할 순 없다. 왜냐? LSP(리스코프 치환 원칙)를 위반하기 때문이다. LSP에 따르면 Point의 하위 클래스는 정의상 여전히 Point이므로 어디서든 Point로써 활용될 수 있어야 한다를 설명하고 있다.
- Point의 하위 클래스는 정의상 여전히 Point이므로 어디서든 Point로써 활용 될 수 있어야 한다. 그런데 이 방식에서는 그렇지 못하다.
- 예를 들어, 주어진 점이 (반지름이 1인) 단위 원 안에 있는지를 판별하는 메서드가 필요하다고 해보자.

```
// CounterPoint를 Point로 사용하는 테스트 프로그램
public class CounterPointTest {
// 단위 원 안의 모든 점을 포함하도록 unitCircle을 초기화한다. (58쪽)
private static final Set<Point> unitCircle = Set.of(
		new Point( 1,  0), new Point( 0,  1),
		new Point(-1,  0), new Point( 0, -1));

public static boolean onUnitCircle(Point p) {
	return unitCircle.contains(p);
}
```
- 그리고 이제 값을 추가하지 않는 방식으로 point를 확장해보자. AtomicInteger를 이용하여 만들어진 인스턴스의 개수를 생성자에서 세보도록 하자.

```
// Point의 평범한 하위 클래스 - 값 컴포넌트를 추가하지 않았다.
public class CounterPoint extends Point {
    private static final AtomicInteger counter =
            new AtomicInteger();

    public CounterPoint(int x, int y) {
        super(x, y);
        counter.incrementAndGet();
    }
    public static int numberCreated() { return counter.get(); }
}
```
- 그런데 CounterPoint의 인스턴스를 onUnitCircle 메서드에 넘기면 false를 반환할 것이다. 왜 그럴까? 컬렉션 구현체에서 주어진 원소를 담고 있는지를 확인할때 equals메서드를 활용하는데 구체 클래스가 다르기 때문이다
- 이 문제를 해결하기위해서는 상속 대신 컴포지션(조합)을 활용하면 된다. Point를 상속하는 대신 Point를 ColorPoint의 private필드로 두고, ColorPoint와 같은 위치의 일반 Point를 반환하는 뷰(view) 메서드를 public으로 추가하자.

```
// 코드 10-5 equals 규약을 지키면서 값 추가하기
public class ColorPoint {
    private final Point point;
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        point = new Point(x, y);
        this.color = Objects.requireNonNull(color);
    }

    /**
     * 이 ColorPoint의 Point 뷰를 반환한다.
     */
    public Point asPoint() {
        return point;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        ColorPoint cp = (ColorPoint) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }

    ...
}
```
- 그렇게 되면 컴포지션을 통해 LSP를 위반하지 않게 된다. 더 이상 ColorPoint는 Point의 하위 클래스가 아니다. 그러면서도 Point의 메서드들을 활용할 수 있게 된다.

### 일반 규약을 지키지 않는 잘못된 자바 API들
- java.sql.Timestamp는 java.util.Date를 확장한 후에 필드를 추가해서 일반 규약을 지킬 수 없다.
- 그래서 컬렉션에 넣거나, 대칭성 있는 비교를 수행하려 하면 디버그하기 까다로운 현상을 만날 수도 있다.
- equals()에 신뢰할 수 없는 자원이 끼어들게 하면 안된다.
- java.net.URL은 equals()가 일반규약을 지키지 않는다.
- 호스트 이름이 같으면, equlas()가 참이 나오는데, 사실 이건 네트워크를 거치지 않으면 알 수 없는 정보이다.
- 위와 같은 문제를 피하려면, 항시 메모리에 존재하는 객체만을 사용한 결정적 계산만 수행해야 한다.

### 일관성
- 두 객체가 같다면(어느하나 혹은 두 객체 모두가 수정되지않는 한) 앞으로도 영원히 같아야한다는 뜻이다. 가변 객체는 비교시점에 따라 서로 다를 수도 혹은 같을 수도 있는 반면 불변 객체는 한번 다르면 끝까지 달라야한다.
- 클래스가 불변이든 가변이든 equals의 판단에 신뢰할 수 없는 자원이 끼어들게 해서는 안된다.
    - java.net.URL의 equals는 주어진 URL과 매핑된 호스트의 IP주소를 이용해 비교한다.호스트 이름을 IP주소로 바꾸려면 네트워크를 통해야 하는데, 결과가 항상 같다고 보장할 순 없다.이는 설계 실수이니 따라해선 안된다.

### null이 아님
- null이 아님은 이름처럼 모든 객체가 null과 같지않아야한다는 뜻이다. 일부러 null검사를 아래와 같이 할 필요 없다.

```
// 명시적 null검사 - 필요 없다!
@Override public boolean equals(Object o) {
    if (o == null) {
        return flase;
    }
}
```
- 동치성을 검사하려면 equals는 건네받은 객체를 적절히 형변환 후 필수 필드의 값을 알아내야 한다. 그러려면 형변환에 앞서 instanceof 연산자로 파라미터가 올바른 타입인지 검사해야 한다. 이때 instsanceof 연산자에서 null일 경우 false를 반환하도록 되어 있기 때문이다.

## 양질의 equals 메서드 구현 방법
- 1.== 연산자를 사용해 입력이 자기 자신의 참조인지 확인한다.
    - 자기 자신이면 true 리턴한다. 단순한 성능 최적화용으로, 비교 작업이 복잡한 상황일떄 값어치 있다.
- 2.instanceof 연산자로 입력이 올바른 타입인지 확인한다.
    - 가끔은 그 클래스가 구현한 특정 인터페이스가 될 수도 있다.
    - 어떤 인터페이스는 자신을 구현한 클래스끼리도 비교할 수 있도록 equals 규약을 수정하기도 한다.
    - 이런 인터페이스를 구현한 클래스라면 equals 규약을 수정하기도 한다.
    - 이런 인터페이스를 구현한 클래스라면 equals에서 (클래스가 아닌) 해당 인터페이스를 사용해야 한다.
    - Set, List, Map.Entry 등의 컬렉션 인터페이스들이 여기 해당한다.
- 3.입력을 올바른 타입으로 형변환한다.
    - 위에서 instaceof 연산자로 검사를 했기에 백프로 성공하게 되어있다
- 4.입력 객체와 자기 자신의 대응 되는 ‘핵심’ 필드들이 모두 일치하는지 하나씩 검사한다.
    - 2단계에서 인터페이스를 사용했다면 입력의 필드 값을 가져올 떄도 그 인터페이스의 메서드를 사용해야 한다.
    - 타입이 클래스라면 (접근 권한에 따라) 해당 필드에 직접 접근할수도 있다.
    - float와 double 을 제외한 기본 타입 필드는 ==연산자로 비교하고, 참조 타입은 equals 메서드로 비교한다.
        - flaot와 double은 Float.NaN, -0.0f, 특수한 부동소수 값등을 다뤄야 하기 때문에 Float.compare(float, float)와 Double.compare(double, double)을 사용해야 한다.
    - Float.equals와 Double.equals 메서드를 사용할 수도 있지만 이는 오토박싱을 수반할 수 있으니 성능상 좋지 않다.

## equals 메서드를 재정의시 주의사항
- 어떤 필드를 먼저 비교하느냐가 equals의 성능을 좌우하기도한다. 다를 가능성이 더 크거나 비교하는 비용이 싼 필드를 먼저 비교하라.
- 동기화용 락(lock) 필드 같이 객체의 논리적 상태와 관련 없는 필드는 비교하면 안됨
- 핵심 필드로부터 계산해 낼 수 있는 파생 필드 역시 굳이 비교할 필욘없지만, 파생 필드를 비교하는 쪽이 더 빠를 떄도 있으니 참고하라. (파생 필드가 객체 전체의 상태를 대표하는 경우)
예컨대 자신의 영역을 캐시해두는 Polygon 클래스가 있다고 했을 경우, 모든 변과 정점을 일일이 비교할 필요 없이 캐시해둔 영역만 비교하면 됨
- equals를 다 구현했다면 세 가지만 자문해보자. 대칭적인가? 추이성이 있는가? 일관적인가?자문에서 끝내지 말고 단위 테스트를 작성해보자. 반사성과 null이 아님은 문제되는 경우가 별로 없다.
- equals를 재정의할 땐 hashcode도 반드시 재정의하자.(아이템 11)
- 너무 복잡하게 해결하려 들지말자. 필드들의 동치성만 검사해도 equals 규약을 어렵지 않게 지킬 수 있다. 오히려 너무 공격적으로 파고들다가 문제를 일으키기도 한다. 일반적으로 별칭(alias)은 비교하지 않는게 좋다. 예컨데 File 클래스라면, 심볼릭 링크를 비교해 같은 파일을 가리키는지를 확인하려 들면 안된다
- Object 외의 타입을 매개변수로 받는 equals메서드는 선언하지 말자.

```
// 잘못된 예 - 입력 타입은 반드시 Object 여야 한다.
@Override public boolean equals(MyClass o) {
     ...
}
```
- 이 메서드는 Object.equals를 재정의한게 아니다. 재정의가 아니라 다중 정의(아이템52) 한 것이다. 하위 클래스에서의 @Override 애너테이션이 긍정 오류(false positive; 거짓 양성)를 내게 하고 보안 측면에서도 잘못된 정보를 준다. @Override 애너테이션을 일관되게 사용하면 이러한 실수를 예방할 수 있다.

- 구글이 만든 AutoValue 프레임워크를 통해 단위 테스트를 수행하자. equals(hascode도 마찬가지)를 작성하고 테스트하는 일은 지루하고 항상 뻔하다. 다행히 이 작업을 대신해줄 오픈소스가 있는데 구글이 만든 AutoValue 프레임워크이다. 클래스에 애너테이션 하나만 추가하면 AutoValue가 이 메서드들을 알아서 작성해주며, 개발자가 직접 작성하는 것과 근본적으로 똑같은 코드를 ㅁ나들어줄 것이다.
- 대다수의 IDE도 같은 기능을 제공하지만, 생성된 코드가 AutoValue만큼 깔끔하거나 읽기 좋진 않다. 또한 IDE는 나중에 클래스가 수정된 걸 자동으로 알아채지는 못하니 테스트 코드를 작성해둬야 한다. 이러한 단점을 감안하더라도 개발자가 직접 작성해서 실수하는 것보단 IDE에 맡기는게 더 낫다.
AutoValue 깃허브 레포 와 실제 사용법이 정리된 포스팅 도 참고해보자.
- 깃허브레포와 AutoValue와 관련된 많은 레퍼런스를 찾아보니 테스트 자동화 프레임워크라기보단 equals, hashcode와 같은 메서드를 자동으로 생성해주는 lombok과 뭔가 비슷한 라이브러리인 것 같다.
- 클래스에 어노테이션 하나만 추가하면 AutoValue가 equals, hashcode와 같은 메서드들을 알아서 작성해주는데, 개발자가 직접 작성하는 것과 근본적으로 똑같은 코드를 만들어주는 것 같다.
- 꼭 필요한 경우가 아니면 equals를 재정의하지 말자. 많은 경우에 Object의 equals가 개발자가 원하는 비교를 정확히 수행해준다. 재정의해야할 때는 그 클래스의 핵심 필드 모두를 빠짐 없이, 다섯 가지 규약을 확실히 지켜가며 비교해야 한다.


---

참고링크 

[jeonyoungho](https://jeonyoungho.github.io/posts/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-%EC%95%84%EC%9D%B4%ED%85%9C10-equals%EB%8A%94-%EC%9D%BC%EB%B0%98-%EA%B7%9C%EC%95%BD%EC%9D%84-%EC%A7%80%EC%BC%9C-%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%98%EB%9D%BC/)