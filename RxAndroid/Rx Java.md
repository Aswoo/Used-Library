Rx java 

Observerable 에서 시작해 Observerable로 끝난다고 해도 과언이 아님

Rx java 2.x

- Observable
- Maybe
- Flowable



## Observable 이란?

- 옵저버 패턴을 구현
- 옵저버 패턴 : 객채의 상태 변화를 관찰하는 관찰자(옵서버) 목록을 객체에 등록
- 그리고 상태 변화가 있을 때마다 메서드를 호출하여 객체가 직접 목록의 옵서버에게 변화를 알려준다.
- 라이프사이클은 존재하지 않으며 단일 함수를 통해 변화만 알림



#### Observable 알림

- onNext :  Observable이 데이터의 발행을 알립니다. 기존의 옵서버 패턴과 동일
- onComplete : 모든 데이터의 발행을 완료했음을 알립니다. onComplete 이벤트는 단 한번만 발생하며 발생된 후에는 더이상 onNext  이벤트는 발생 X
- onError : 어떤 연유로 에러가 바생했음을 알림 onError 이벤트 발생후에는 onNext,onComplete 이벤트는 발생하지 않는다.

##### SubScribe() 함수

RxJava는 내가 동작 시키기 원하는 것을 사전에 정의해둔 다음 실제 그것이 실행되는 시점을 조절하는데 이 때. Subscribe 함수가 사용된다. 이 함수를 호출해야 실제로 데이터를 발행한다.

##### 단일 객체가 아닌 다중 객체는. fromXXX 함수 사용

- fromArray
- fromIterable
- fromCallalbe
- fromFuture

fromIterable - ArrayList,ArrayBlockingQueue,HashSet,LinkedList,Stack,TreeSet etc...

List example

```java
List<String> names = new ArrayList<>();
names.add("Jerry");
names.add("William");
names.add("Bob");

Obeservable<String> source = Observable.fromIterable(names);
source.subscribe(Sytem.out::println);
```

#### fromCallable

```java
Callable<String> callable = () -> {
    Thread.sleep(1000);
    return "Hello Callable";
}

Obeservable<String> source = Observable.fromCallable(names);
source.subscribe(Sytem.out::println);
```



Subject 클래스

차가운 Observable 을 뜨거운 Observable 로 변경

- AsyncSubject : 마지막 데이터만 발행
- BehaviorSubject : 최근 데이터 발행
- PublishSubject : 해당시간에 발생한 데이터를 그대로 발행
- ReplaySubject : 구독자가 새로 생기면 데이터의 처음부터 끝까지 발행하는 것을 보장





### 리엑티브 연산자 입문!

- 리엑티브 연산자의 특징은 언어 특성과 크게 연관이 없다!
- 따라서 익혀놓으면 javascript, scala,.NET,Clojure,Swift 등의 리엑티브 프로그래밍도 쉽게 익힐 수 있다.
- 여기서는 많은 연산자들이 있지만 4개만 기억하자

------

1. map
2. flatMap
3. filter
4. reduce

1.map() 

입력값을 어떤 함수에 넣어서 원하는 값으로 변환하는 함수.

```java
public static string getDiamond(String ball) {
    return ball + "<>"
}
```

```
String balls = {"1","2","3","5"};
Obserbalbe<String> soure = Observable.fromArray(balls)
.map(ball -> ball + "<>");
source.subscribe(Log::i);
```

2.flatMap()

```java
Oberservable<String> source = Observable.just(dan)
.flatMap(num -> Observable.range(1,9)
.map(row -> num + "*" + row + " = " + dan*row));
```

3.filter()

- 원하는 데이터 만 걸러낸다.

```java
Integer[] data = {100,35,27,99,50};
Observable<Integer> source = Observable,fromArray(data)
    .filter(number -> number % 2 == 0); // 짝수만 추출
source.subscribe(System.out::println);
```

4.reduce()

- 발생한 데이터를 모두 사용하여 어떤 최종결과 데이터를 합성할 때 활용

```java
String [] balls = {"1","3","5"};
Maybe<String> source = Observable.fromArray(balls)
    .reduce(ball1,ball2) -> ball2 + "(" + ball1 + ")");
source.subscribe(System.out::println);

```



## 리엑티브 연산자의 활용

1. Interval() 함수

   일정시간 간격으로 데이터 흐름을 생성

```java
CommoUtils.exampleStart();
Observable<Long> source = Observalbe.interval(100L,TimeUnit.MILLISECONDS)
    .map(data -> (data + 1) * 100)
    .take(5);
source.subscribe(Log::it);
CommoUtils.sleep(1000);
```

2. Timer() 함수

    interval() 함수와 유사하지만 한번만 실행하는 함수입니다,

```java
CommonnUtils.exampleStart();
Observable<String> source = Observable.timer(500L, TimeUnit.MILLISECONDNS)
    .map(notUsed -> {
        retur enw SimpleDataFormat("yyyy/MM.dd HH:mm:ss")
            .format(new Date());
    });
source.subscribe(Log::it);
CommonUtils.sleep(1000);
//500ms 쉬고 실행
```

3. range() 함수

   주어진 값 n부터 m개의 integer 객체를 발행 합니다.

```java
Observable<Innteger> source = Observable.rangne(1,10)
    .filter(number -> number %2 == 0);
source.subscribe(Log::i);
//1부터 10까지의 숫자를 생성한 후 짝수만 걸러낸다.
```

4. intervalRange()함수

   주어진 범위만큼 interval 함수를 실행

5. defer 함수 

   데이터 흐름 생성을 구독자가 subscribe()함수를 호출할때까지 미룰수 있습니다. 



### 5. 스케쥴러 

 실무에서는 앞의 대부분의 예제를 비동기로 바꾸어야한다. 이전의 스레드를 만들면서 경쟁 조건,synchronized 키워드 에서 매우 간결하게 바뀜

```java
String[] Ojbs = {"1-S","2-T","3-P"};
Observalbe<String> source = Observable,fromArray(objs)
    .doOnNext(data -> Log.v("Original data = " + data))
    .subscribeOn(Schedulers.newThread())
    .observeOn(Schedulers.newThread())
    .map(Shape::flip);
source.subscribe(Log::i);
commonUtils.sleep(500);
```

# Callback Hell

다음은 첫번째 URL을 호출한뒤 성공할 경우 두번째 URL을 호출하는 OKHttp3 기반의 코드이다.

```java
public class CallbackHell {
	private static final String FIRST_URL = "https://api.github.com/zen";
	private static final String SECOND_URL = GITHUB_ROOT + "/samples/callback_hell";

	private final OkHttpClient client = new OkHttpClient();
	
	private Callback onSuccess = new Callback() {
		@Override
		public void onFailure(Call call, IOException e) {
			e.printStackTrace();
		}

		@Override
		public void onResponse(Call call, Response response) throws IOException {
			Log.i(response.body().string());
		} 
	};
	
	public void run() { 
		Request request = new Request.Builder()
		        .url(FIRST_URL)
		        .build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.i(response.body().string());
				
				//add callback again
				Request request = new Request.Builder()
				        .url(SECOND_URL)
				        .build();
				client.newCall(request).enqueue(onSuccess);				
			}			
		});		
	}
		
	public static void main(String[] args) { 
		CallbackHell demo = new CallbackHell();
		demo.run();
	}
}
```

요랫던 것이 RXjava2 를 이용하면 

```java
public class CallbackHeaven {
	private static final String FIRST_URL = "https://api.github.com/zen";
	private static final String SECOND_URL = GITHUB_ROOT + "/samples/callback_heaven";
	
	public void usingConcat() { 
		CommonUtils.exampleStart();
		Observable<String> source = Observable.just(FIRST_URL)
			.subscribeOn(Schedulers.io())
			.map(OkHttpHelper::get)
			.concatWith(Observable.just(SECOND_URL)
					           .map(OkHttpHelper::get));
		source.subscribe(Log::it);
		CommonUtils.sleep(5000);
		CommonUtils.exampleComplete();
	}

	public void usingZip() { 
		CommonUtils.exampleStart();
		Observable<String> first = Observable.just(FIRST_URL)
				.subscribeOn(Schedulers.io())
				.map(OkHttpHelper::get);
		Observable<String> second = Observable.just(SECOND_URL)
				.subscribeOn(Schedulers.io())
				.map(OkHttpHelper::get);
		
		Observable.zip(first, second, 
				(a, b) -> ("\n>>" + a + "\n>>" + b))
			.subscribe(Log::it);
		CommonUtils.sleep(5000);
	}
	
	public static void main(String[] args) { 
		CallbackHeaven demo = new CallbackHeaven();
//		demo.usingConcat();
		demo.usingZip();
	}
}
```

이렇게 간단하게 바뀐다. zip,Concat은 선택이다.



#### 스케쥴러의 종류

- 뉴 스레드 스케쥴러 : newThread()
- 싱글 스레드 스케줄러 : single()
- 계산 스케줄러 : computation()
- IO 스케줄러 : io()
- 트램펄린 스케줄러 : trampoline()
- 메인 스레드 스케줄러 : 지원 안함(v 2.x)

스케줄러를 선택하고 

- subscribeOn()

- observeOn()

  둘중 하나를 골라서 실행 시킬 수 있다.
