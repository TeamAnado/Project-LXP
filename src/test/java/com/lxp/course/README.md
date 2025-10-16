# Course Domain Unit Tests

이 디렉토리는 Course 도메인의 단위 테스트를 포함합니다.

## 테스트 구조

```
src/test/java/com/lxp/course/
├── model/
│   └── CourseTest.java              # Course 모델 클래스 테스트
├── service/
│   ├── CourseServiceTest.java       # CourseService 비즈니스 로직 테스트 (Mockito 사용)
│   └── dto/
│       └── CourseDtoTest.java       # DTO 클래스들의 테스트
├── dao/
│   └── CourseDAOIntegrationTest.java # DAO 통합 테스트 (데이터베이스 필요)
└── README.md
```

## 테스트 실행 방법

### 1. 모든 Course 도메인 테스트 실행
```bash
./gradlew test --tests "*course*"
```

### 2. 개별 테스트 클래스 실행
```bash
# 모델 테스트
./gradlew test --tests "CourseTest"

# 서비스 테스트 (Mock 사용)
./gradlew test --tests "CourseServiceTest"

# DTO 테스트
./gradlew test --tests "CourseDtoTest"

# 통합 테스트 (데이터베이스 설정 필요)
./gradlew test --tests "CourseDAOIntegrationTest"
```

### 3. 단위 테스트만 실행 (통합 테스트 제외)
```bash
./gradlew test --tests "*course*" --exclude-tags "integration"
```

## 테스트 유형 설명

### 1. Unit Tests (단위 테스트)
- **CourseTest**: Course 모델의 생성자, getter 메소드 테스트
- **CourseDtoTest**: DTO 클래스들의 데이터 처리 테스트
- **CourseServiceTest**: 비즈니스 로직 테스트 (Mock DAO 사용)

### 2. Integration Tests (통합 테스트)
- **CourseDAOIntegrationTest**: 실제 데이터베이스와의 상호작용 테스트
- 기본적으로 `@Disabled` 처리됨 (데이터베이스 설정 필요)

## 테스트에 사용된 라이브러리

### JUnit 5
- `@Test`: 테스트 메소드 표시
- `@DisplayName`: 테스트 설명
- `@BeforeEach/@AfterEach`: 테스트 전후 처리
- `assertAll()`: 여러 assertion을 그룹화

### AssertJ
```java
assertThat(actual).isEqualTo(expected);
assertThat(list).hasSize(2);
assertThat(exception).isInstanceOf(SQLException.class);
```

### Mockito
```java
@Mock private CourseDAO courseDAO;
when(courseDAO.save(any())).thenReturn(1L);
verify(courseDAO, times(1)).save(any());
```

## 테스트 작성 패턴

### Given-When-Then 패턴
```java
@Test
void createCourse_ShouldSaveSuccessfully() {
    // Given - 테스트 준비
    CreateCourseDto dto = new CreateCourseDto("Title", "Description", Category.DEVELOPMENT, 1L);
    
    // When - 실행
    Long result = courseService.createCourse(dto);
    
    // Then - 검증
    assertThat(result).isEqualTo(1L);
}
```

## Mock vs Integration 테스트

### Mock 테스트 (CourseServiceTest)
- 빠른 실행
- 외부 의존성 없음
- 비즈니스 로직에 집중
- 유닛 테스트에 적합

### Integration 테스트 (CourseDAOIntegrationTest)
- 실제 데이터베이스 필요
- 실제 환경과 유사한 테스트
- 설정이 복잡함
- E2E 테스트에 적합

## 실행 결과 예시

```
CourseTest > createNewCourse_ShouldSetFieldsCorrectly() PASSED
CourseTest > createCourseFromDatabase_ShouldSetAllFieldsCorrectly() PASSED
CourseServiceTest > createCourse_ShouldSaveSuccessfully() PASSED
CourseServiceTest > findCourseById_WhenCourseNotExists_ShouldThrowLXPException() PASSED

BUILD SUCCESSFUL in 3s
4 actionable tasks: 4 executed
```