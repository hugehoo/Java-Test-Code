package inflearn.thejavatest;

import inflearn.thejavatest.domain.Study;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


// 이걸 사용하면 @BeforeAll 등을 static 하게 선언할 필요가 없다.
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // class 마다 인스턴스를  생성하기에, 모든 테스트가 공통된 인스턴스를 공유한다.
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@ExtendWith(SlowTestExtension.class) // 선언적 방법 -> customizing 이 불가능
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

    @RegisterExtension // 선언적 방법 대신 커스터마이징 한 것.
    static SlowTestExtension slowTestExtension = new SlowTestExtension(1000L);

    int value = 0;

//    @Disabled // test 무력화
//    @SlowTest
    @Test
    void 스터디를_생성하라() throws InterruptedException {
        Thread.sleep(1005L);
        Study study = new Study(10);
        assertNotNull(study);
    }

    @Test
    @Order(2)
    @DisplayName("테스트 만들기 ✅")
    void create1() {
        System.out.println(value++);
        System.out.println("create 1");
    }

    @BeforeAll //  반드시 static 을 사용해야한ㄷ나. 리턴타입도 있으면 안된다. 그냥 static void 로 작성한다고 기억하라.
    static void before_all() {
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

    @Test
    @Order(1)
    void create_new_study() {
        Study study = new Study(10, "Java");
        System.out.println(value++);

//        assertNotNull(study);
//        assertEquals(StudyStatus.DRAFT, study.getStatus(),
//                "스터디를 처음 만들면 상태값이 DRAFT");

        // 문자열을 그대로 쓰면, 테스트가 성공하든 아니든 모두 연산하지만
        // () -> "msg" 처럼 람다식을 사용하면 실패할 때만 문자식을 연산한다.
        // expected 가 먼저, actual 을 후자에 -> 뒤바뀌어도 사실 테스트는 제대로 동작한다. 그래도 제대로 알고 사용하자.

        // 중간에 실패하던 말던, assert 문을 모두 실행한다. 무엇이 실패했는지 한눈에 알 수 있다.
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus()),
                () -> assertTrue(study.getLimitCount() > 0, "스터디 참석 인원은 0보다 커야함")
        );
    }

    @Test
    @DisplayName("예외 처리 확인")
    void throws_Exception_When_Limit_Negative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Study(-10));
        assertEquals("0 미만 금지", exception.getMessage());
    }

    @Test
    @DisplayName("타임 아웃 테스트")
    void timeout_Test() {
        assertTimeout(Duration.ofMillis(200), () -> {
            new Study(10);
            Thread.sleep(190);
        });
    }

    @Test
    @EnabledOnOs(OS.MAC)
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void test_environment_variable() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));
        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            Study study = new Study(199);
            assertTrue(study.getLimitCount() > 10);
        });
    }

    @Test
    @DisabledOnOs(OS.MAC)
    void test_depends_OS() {
        System.out.println("not working");
    }

    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println(repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    // junit4 에서는 써드파티를 사용해야만 쓸 수 있던 기능, 5에서는 기본으로 제공
    @DisplayName("Parameterized Test")
    @ParameterizedTest(name = "{index} {displayName}  message = {0}")
    @ValueSource(strings = {"날씨가", "많이 ", "춤네여"})
    void parameterizedTest(String msg) {
        System.out.println(msg);
    }


    @DisplayName("Parameterized Test")
    @ParameterizedTest(name = "{index} {displayName}  message = {0}")
    @ValueSource(strings = {"날씨가", "많이 ", "춤네여"})
    @EmptySource
    @NullSource
    @NullAndEmptySource
    void parameterizedTest2(String msg) {
        System.out.println(msg);
    }


    @DisplayName("Parameterized Test")
    @ParameterizedTest(name = "{index} {displayName}  message = {0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest3(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimitCount());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "can only convert to Study class");
            return new Study(Integer.parseInt(source.toString()));
        }
    }


    @DisplayName("Parameterized Test")
    @ParameterizedTest(name = "{index} {displayName}  message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, spring"})
//    void parameterizedTest4(Integer limit, String name) {
    void parameterizedTest4(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(
                argumentsAccessor.getInteger(0),
                argumentsAccessor.getString(1));
        System.out.println(study);

    }

    @Order(3)
    @DisplayName("Parameterized Test")
    @ParameterizedTest(name = "{index} {displayName}  message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, spring"})
    void parameterizedTest5(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(value++);
        System.out.println(study);
    }


    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(
                    argumentsAccessor.getInteger(0),
                    argumentsAccessor.getString(1));
        }
    }

}
