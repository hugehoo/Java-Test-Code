[Google Docs](https://docs.google.com/document/d/1j6mU7Q5gng1mAJZUKUVya4Rs0Jvn5wn_bCUp3rq41nQ/edit)

## 강의 : Junit5 소개

Junit 을 가장 많이 사용한다. 그다음이 mockito.

Q. 이 둘의 차이점은 무엇인가?

J5 는 그 자체로 여러 모듈화가 되어있다. Junit platform 에 Jupiter 와 vintage 로 이루어져있다. Jupiter 는 테스트엔진의 구현체 -> Junit5의 구현체 / Vintage 는
3,4를 지원하는 테스트엔진 구현체 주로 주피터 쪽을 살펴볼 것.

## 강의 : Junit5 시작하기

스프링부트 2.2 부터는 스프링부터 스타터 기본 junit 의존성이 5점대로 바뀌었다.

```java
class StudyTest {

    @Test
    @Disabled // test 무력화
    void create() {
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    void create1() {
        System.out.println("create 1");
    }

    @BeforeAll //  반드시 static 을 사용해야한ㄷ나. 리턴타입도 있으면 안된다. 그냥 static void 로 작성한다고 기억하라.
    static void beforeAll() {
        System.out.println("before All");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    @BeforeEach // 모든 테스트를 실행할 때, 각각의 테스트를 실행하기 이전과 이후에 한번씩 실행됨. ? (이후는 왜?)
    void beforeEach() {
        System.out.println("before each");
    }


    @AfterAll //  반드시 static 을 사용해야한ㄷ나. 리턴타입도 있으면 안된다. 그냥 static void 로 작성한다고 기억하라.
    static void afterAll() {
        System.out.println("after All");
    }
}
```

## 강의 : Junit5 테스트 이름 표시하기
test method 이름이 긴 경우, 가독성이 떨어진다. 
- `@DisplayNameGeneration` :  클래스와 메서드 모두에 사용가능. starategy 를 입력할 수 있다.
  - `@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)` : underscore 를 띄어쓰기로 치환해준다.


- Junit 은 테스트 메서드마다 클래스 인스턴스를 생성한다. -> 테스트 간의 의존성을 없애기 위함. (테스트 순서는 매번 보장할 수 없다.)
- -> 때문에 테스트 간의 의존성을 없애는게 좋다.
- `@TestInstance(TestInstance.Lifecycle.PER_CLASS)` : class 마다 인스턴스를  생성하기에, 모든 테스트가 공통된 인스턴스를 공유한다.
  이걸 사용하면 @BeforeAll 등을 static 하게 선언할 필요가 없다.

- 연결된 유즈케이스를 테스트하려면, 하나의 테스트 클래스 인스턴스만 만들어서 테스트 진행하는 것이 좋다. @TestMethodOrder() 를 사용하여. 
- 상태를 공유하는 시나리오성 테스트에 order annotation 사용하면 좋다.

<hr/>

### 확장 모델
- JUnit4 의 확장 모델은 @RunWith(Runner), TestRule, MethodRule 등 존재
- 5 부터는 Extension, 단 하나로 통일

확장팩 등록 방법
- 선언적 등록 @ExtendWith
- 프로그래밍 등록 @RegisterExtension
- 자동 등록 자바 ServiceLoader 이용


- 기본 스프링부트 플젝은 빈티지 엔진이 빠진 상태로 생성된다.
- 빈티지가 있어야 제이유닛4로 작성된 테스트를 실행할 수 있다. -> Junit4 기반으로 테스트를 생성할 수도 있다. 

- RunWith() : Junit4 의 것. -> Junit5 에서도 돌아가긴 하지만 더이상 쓸 필요가 없다. @SpringBootTest() 에 이미 ExtendWith 가 들어있따.

1. @DisplayName
2. JUnit Platform
3. `@ParameterizedTest` -> `@Tag`
4. assertAll
5. RetentionPolicy.RUNTIME
6. `@Rule`
7. PER_CLASS, OrderAnnotation
8. ParameterizedTest, `@AggregateWith`