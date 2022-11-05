### Mockito

Mock : 진짜 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체
Mockito : Mock 객체를 쉽게 만들고 관리하고 검증할 수 있는 방법을 제공

### 모든 mock 객체의 행동
- null 을 리턴한다 (Optional 타입은 Optional.empty() 리턴)
- Primitive 타입은 기본 Primitive 값
- 콜렉션은 비어있는 콜렉션
- void 메서드는 예외를 던지지 않고 아무런 일도 발생하지 않는다. 


