# item7 다 쓴 객체 참조를 해제하라

```
public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 10;
	
	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}
	
	public void push(Object e) {
		ensureCapcity();
		elements[size++] = e;
	}
	
	public Object pop() {
		if(size == 0) {
			throw new EmptyStackException();
		}
		// 스택이 커졌다가 줄어들었을때 스택에서 꺼내진 객체들을 가비지 컬렉터가 회수하지 않음 => 메모리 누수 발생
		return  elements[--size];	
	}
	
	private void ensureCapcity() {
		if(elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}
}
```
- 위의 코드에는 메모리 누수 라는 문제가 있다. 해당 코드를 사용하는 프로그램을 오래 실행하다보면 점차 가바지 컬렉션 활동과 메모리 사용량이 늘어나 결국 성능이 저하될 것이다.
- 위의 코드에서는 스택이 커졌다가 줄어들었을 때 스택에서 꺼내진 객체들을 가비지 컬렉터가 회수하지않는다. 이 스택이 그 객체들의 다 쓴 참조를 여전히 가지고 있기때문이다. 여기서 다 쓴 참조란 문자 그대로 앞으로 다시 쓰지않을 참조를 뜻한다.
- 가비지 컬렉션 언어에서는(의도치않게 객체를 살려두는) 메모리 누수를 찾기가 아주 까다롭다. 객체 참조 하나를 살려두면 가비지 컬렉터는 그 객체뿐아니라 그 객체가 참조하는 모든 객체(그리고 또 그 객체들이 참조하는 모든 객체)를 회수해가지 못한다. 그래서 단 몇개의 객체가 매우 많은 객체를 회수되지 못하게 할 수 있고 잠재적으로 성능에 악영향을 줄 수 있다.
- 해법은 간단하다. 해당 참조를 다 썼을 때 null처리(참조해제)하면 된다.
- 다 쓴 참조를 null 처리하면 다른 이점도 따라온다. 만약 NULL처리한 참조를 실수로 사용하려 하면 프로그램은 즉시 NullpointException을 던지며 종료된다.(미리 null처리하지 않았다면 아무 내색 없이 무언가 잘못된 일을 수행할 것이다)

## 메모리 누수 원인
### 자기 메모리를 직접 관리하는 클래스
- Stack과 같은 클래스가 메모리 누수에 취약한 이유는 Stack 클래스가 자기 메모리를 직접 관리하기 때문이다. 메모리 누수가 발생하지 않기 위해서는 어떤 객체가 비활성 영역이 되는 순간 null 처리를 해서 해당 객체가 더는 쓰이지 않을 것임을 가비지 컬렉터에게 알려야 한다

### 캐시(Cache)
- 객체 참조를 캐시에 넣고 나서, 그 객체를 다쓴 뒤로도 그냥 놔두는 일을 접할 수 있다. 이 문제를 해결하는 방법 중 하나로는 캐시 외부에서 키(key)를 참조하는 동안만(값이 아닌) 엔트리가 살아 있는 캐시가 필요한 상황이라면 WeakHashMap 클래스를 사용해 캐시를 만드는 방법이 있다. 다 사용한 엔트리는 그 즉시 자동으로 제거된다.

### 리스너(Listener) 혹은 콜백(Callback)
- 클라이언트가 콜백을 등록만 하고 명확히 해지하지 않는다면, 콜백은 계속 쌓여갈 것이다. 이럴때 콜백은 약한 참조(weak reference)로 저장하면 가비지 컬렉터가 즉시 수거해간다. 예를 들어 WeakHashMap에 키로 저장하면 된다.

# item8 finalizer와 cleaner 사용을 피하라.
- 자바는 두가지 객체 소멸자를 제공한다. 그중 finalizer는 예측 할 수 없고 상황에 따라 위험할 수 있어 일반적으로 불필요하다. 오동작,낮은 성능, 이식성 문제의 원인이 되기도 하다. finalizer는 나름의 쓰임새가 몇가지가 있긴 하지만 기본적으로 쓰지말아야한다.
- cleaner는 finalizer보다 덜 위험하지만 여전히 예측할 수 없고 느리고 일반적으로 불필요하다.

### finalizer와 cleaner를 사용하지말아야하는 이유
- finalizer와 cleaner는 즉시 수행된다는 보장이 없다. 객체에 접근할 수 없게 된 후 finalizer와 cleaner가 실행되기까지 얼마나 걸릴지 알 수 없다. finalizer와 cleaner로는 제때 실행되어야 하는 작업은 절대 할 수 없다.
- 자바 언어 명세는 finalizer와 cleaner의 수행 시점 뿐만 아니라 수행여부 조차 보장하지않는다. 접근할 수 없는 일부 객체에 딸린 종료 작업을 전혀 수행하지 못한 채 프로그램이 중단 될 수 도 있다는 얘기다. 따라서 프로그램 생애주기와 상관없는 상태를 영구적으로 수정하는 작업에서는 절대 에 의존해서는 안된다.
- finalizer와 cleaner는 심각한 성능 문제도 동반한다. finalizer가 가비지 컬렉터의 효율을 떨어뜨리기때문이다.
- finalizer를 사용한 클래스는 finalizer 공격에 노출되어 심각한 보안 문제를 일으킬 수도 있다. final이 아닌 클래스를 finalizer 공격으로부터 방어하려면 아무일도 하지않는 finalize 메서드를 만들고 final로 선언하자.

### finalizer와 cleaner를 대신해줄 묘안
- AutoCloseable을 구현해주고 클라이언트에서 인스턴스를 다 쓰고 나면 close 메서드를 호출하면 된다.

### finalizer와 cleaner를 사용하는 상황
- 자원의 소유자가 close 메서드를 호출하지않는 것에 대비한 안정만 역할이다.
- 네이티브 피어와 연결된 객체에서다. 네이티브 피어란 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체를 말한다. 네이티브 피어는 자바 객체가 아니니 가비지 컬렉터는 그 존재를 알지 못한다. 그 결과 자바 피어를 회수할때 네이티브 객체까지 회수하지 못한다. 단 성능 저하를 감당할 수 있고 네이티브 피어가 심각한 자원을 가지고 있지 않을 때에만 해당된다.

# item9 try-finally 보다는 try-with-resources를 사용하라
- 자바 라이브러리에는 InputStream, OutputStream, java.sql.Connection 등과 같은 입출력 클래스는 close 메서드를 호출해 직접 닫아줘야 하는 자원이 많다. 전통적으로 자원이 제대로 닫힘을 보장하는 수단으로 try-finally 방식을 사용하여 인스턴스가 실행 도중 예외가 발생하거나 메서드에서 반환되는 경우를 포함하여 자원을 안정적으로 회수할 수 있도록 한다.

```
// try-finally 구문을 활용한 일반적인 자원 회수
	static String firstLineOfFile(String path) throws IOException {
		
		// 회수해야할 자원
		BufferedReader br = new BufferedReader(new InputStreamReader(TryFinallyTest.class.getResourceAsStream(path)));
		
		try {
			return br.readLine();
		}finally {
			br.close();
		}
	}
```

### try-finally보다 try-with-resources 방식을 사용해야 하는 이유
- try-finally 방식에서 try 블럭과 finally 블럭에서 둘다 예외가 발생하면 try 블럭에서 발생한 예외는 무시되고 finally 블럭에서 발생한 예외만 출력된다.
- close해야 할 자원이 둘 이상이라면 try-finally 구문이 복잡해진다.

### try-with-resources 방식
- try-with-resources 방식은 try에 자원 객체를 전달하면 try 코드 블록이 종료되면 자동으로 자원을 close하는 방식이다.

```
// try-with-resources : 자원을 회수하는 최선책
	static String firstLineOfFile(String fileName) throws IOException {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(
												   TryWithResourceTest.class.getResourceAsStream(fileName)
												   ))
			){
			return br.readLine();
		}
	}
	
	// 복수의 자원을 처리하는 try-with-resources
	static void copy(String src, String dst) throws IOException {
		try(InputStream in = TryWithResourceTest.class.getResourceAsStream(src);
			OutputStream out = new FileOutputStream(dst)){
			
			byte[] buf = new byte[BUFFER_SIZE];
			int n;
			while((n = in.read(buf)) >= 0) {
				out.write(buf, 0, n);
			}
		}
	}

```

### try-with-resources 방식의 장점
- 다중 예외가 발생한 경우 무시되지 않고 기록된다. 예를 들어 readLine과 close 호출 양쪽에서 예외가 발생하면 close에서 발생한 예외는 숨겨지고 readLine에서 발생한 예외가 기록된다. close에서 발생한 예외는 suppressed라는 꼬리표를 달고 출력된다.
- finally문을 사용하지 않기 때문에 복잡해지지 않아 가독성이 좋아진다.
