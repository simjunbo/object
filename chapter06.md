# 메시지와 인터페이스
- 클래스는 도구일뿐
- 클래스가 아닌 객체를 지향해야 함
  - 객체가 수행하는 책임에 초점

# 01 협력과 메시지
## 클라이언트-서버 모델
- 객체가 다른 객체에 접근할 수 있는 유일한 방법은 메시지 전송 뿐
- 클라이언트-서버 모델
  - 두 객체 사이의 협력 관계를 설명하기 위해 사용하는 전통적인 메타포
- 협력에 참여하는 동안 클라이언트와 서버 역할을 동시에 수행하는 것이 일반적
- 두 객체 사이의 협력을 가능하게 해주는 매개체가 바로 메시지

## 메시지와 메시지 전송
- 메시지 : 객체들이 협력하기 위해 사용할 수 있는 유일한 의사소통 수단
- 메시지 전송 / 메시지 패싱 : 한 객체가 다른 객체에게 도움을 요청하는 것
- 메시지 전송자(클라이언트) : 메시지를 전송하는 객체
- 메시지 수진자(서버) : 메시지를 수신하는 객체
- 메시지는 오퍼레이션명과 인자로 구성되며 메시지 전송은 여기에 메시지 수신자를 추가한 것

## 메시지와 메서드
- 메서드 : 메시지를 수신했을 때 실제로 수행되는 함수 또는 프로시저
- 메시지 전송을 코드상에 표기하는 시점에는 어떤 코드가 실행될 것인지 정확하게 알 수 없음
- 메시지와 메서드의 구분은 메시지 전송자와 메시지 수신자가 느슨하게 결합될 수 있게 함

## 퍼블릭 인터페이스와 오퍼레이션
- 퍼블릭 인터페이스 : 객체가 의사소통을 위해 외부에 공개하는 메시지의 집합
- 오퍼레이션 : 프로그래밍 언어 관점에서 퍼블릭 인터페이스에 포함된 메시지 (추상)
- 메서드 : 메시지를 수신했을 때 실제로 실행되는 코드 (구현)

## 시그니처
- 시그니처 : 오퍼레이션(또는 메서드)의 이름과 파라미터 목록을 합친것
  - 오퍼레이션은 실행 코드 없이 시그니처만 정의한 것
  - 메서드는 시그니처에 구현을 더한 것
- 하나의 오퍼레이션에 다양한 메서드를 구현 (다형성)

# 02 인터페이스와 설계 품질
- 좋은 인터페이스는 최소한의 인터페이스와 추상적인 인터페이스라는 조건을 만족
- 책임 주도 설계 방법은 메시지를 먼저 선택함으로써 협력과 무관한 오퍼레이션이 인터페이스에 스며드는 것을 방지

## 디미터 법칙
- 협력하는 객체의 내부 구조에 대한 결합으로 인해 발생하는 설계 문제를 해결하기 위해 제안된 원칙이 바로 디미터 법칙
- 클래스 내부의 메서드가 아래 조건을 만족하는 인스턴스에게만 메시지 전송
  - this 객체
  - 메서드의 매개변수
  - this 속성
  - this의 속성인 컬렉션의 요소
  - 메서드 내에서 생성된 지역 객체

- 부끄럼 타는 코드란
  - 불필요한 어떤 것도 다른 객체에게 보여주지 않으며, 다른 객체의 구현에 의존하지 않는 코드

- 디미터 법칙은 객체가 자기 자신을 책임지는 자율적인 존재여야 한다는 사실을 강조 (응집도가 높은 객체 만들어짐)

### 디미터 법칙과 캡슐화
- 캡슐화 원칙이 클래스 내부의 구현을 감춰야 한다는 사실을 강조한다면 디미터 법칙은 협력하는 클래스의 캡슐화를 지키기 위해 접근해야 하는 요소를 제한

## 묻지 말고 시켜라
- 디미터 법칙은 훌륭한 메시지는 객체의 상태에 관해 묻지 말고 원하는 것을 시켜야 한다는 사실을 강조
- 묻지 말고 시켜라 원칙을 따르면 객체의 정보를 이용하는 행동을 객체의 외부가 아닌 내부에 위치시키기 때문에 자연스럽게 정보와 행동을 동일 클래스 안에 두게 됨
  - 정보 전문가에게 책임을 할당하게 되고 높은 응집도를 가진 클래스를 얻을 확률이 높아짐

- 인터페이스는 객체가 어떻게 하는지가 아니라 무엇을 하는지를 서술

## 의도를 드러내는 인터페이스
- 켄트백 명명 법
  - 메서드가 작업을 어떻게 수행하는지를 나타내도록 이름 짓기 (내부 구현 드러냄)
    - 메서드에 대해 제대로 커뮤니케이션 하지 못함
    - 메서드 수준에서 캡슐화 위반
  - '어떻게'가 아니라 '무엇'을 하는지를 드러내는 것

- 어떻게 수행하는지 드러내는 이름 => 결과적으로 협력을 설계하기 시작하는 이른 시기부터 클래스의 내부 구현을 고민
- 무엇을 하는지 드러내는 이름 => 객체가 협력 안에서 수행햐아 하는 책임에 관해 고민

- 메서드가 어떻게 수행하느냐가 아닌 무엇을 하느냐에 초점을 맞추면 클라이언트 관점에서 동일한 작업을 수행하는 메서드들을 하나의 타입 계층으로 묶을 수 있는 가능성이 커짐

- 켄트백 훈련법
  - 서로 다른 구현에 동일한 메서드 이름을 붙인다고 상상하면? 그 순간 가장 추상적인 이름을 메서드에 붙일 것이다.

## 함께 모으기
- 근본적으로 디미터 법칙을 위반하는 설계는 인터페이스와 구현의 분리 원칙을 위반한다.
- 객체의 구조는 다양한 요구사항에 의해 변경되기 쉽기 때문에 디미터 법칙을 위반한 설계는 요구사항 변경에 취약해짐


# 03 원칙의 함정
- 설계는 트레이드오프의 산물

## 디미터 법칙은 하나의 도트(.)를 강제하는 규칙이 아니다
```
IntStream.of(1, 15, 20, 3, 9).filter(x -> x > 10).distinct().count()
```
- 위 코드에서 of, filter, distinct 메서드는 모두 IntStream 이라는 동일한 클래스의 인스턴스를 반환
  - 캡슐화 유지되기 때문에 디미터 법칙 위반 아님
  - 객체의 내부 구현에 대한 어떤 정보도 외부로 노출하지 않음

## 결합도와 응집도의 충돌
- 묻지말고 시켜라와 디미터 법칙을 준수하는 것이 항상 긍정적인 결과는 아니다
  - 모든 상황에서 맹목적으로 위임 메서드를 추가하면 같은 퍼블릭 인터페이스 안에 어울리지 않는 오퍼레이션들이 공존하게 됨
  
- 로버트 마틴의 클린 코드
  - 디미터 법칙의 위반 여부는 묻는 대상이 객체인지, 자료구조인지에 달려 있다.
  - 자료구조라면 당연히 내부를 노출해야 하므로 디미터 법칙을 적용할 필요 없음

# 04 명령-쿼리 분리 원칙
- 루틴 : 어떤 절차를 묶어 호출 가능하도록 이름을 부여한 기능 모듈
  - 루틴은 프로시저와 함수로 구분할 수 있음
- 프로시저 : 부수효과를 발생시킬 수 있지만 값을 반환할 수 없다 (명령)
- 함수 : 값을 반환할 수 있지만 부수효과를 발생시킬 수 없다 (쿼리)

- 명령과 쿼리 분리 규칙
  - 객체의 상태를 변경하는 명령은 반환값을 가질 수 없다.
  - 객체의 정보를 반환하는 쿼리는 상태를 변경할 수 없다.

- 명령-쿼리 인터페이스 : 명령-쿼리 분리 원칙에 따라 작성된 객체의 인터페이스

## 반복 일정의 명령과 쿼리 분리하기
- P205 예제들
- 명령과 쿼리를 뒤섞으면 실행 결과를 예측하기 어려워 짐
- 가장 깔끔한 해결책은 명령과 쿼리를 명확하게 분리

## 명령-쿼리 분리와 참조 투명성
- 명령과 쿼리를 엄격하게 분류하면 객체의 부수효과를 제어하기 수월해짐
- 명령과 쿼리를 분리함으로써 명령형 언어의 틀 안에서 참조 투명성의 장점을 제한적이나마 누릴 수 있음
- 참조 투명성이란? (내부적으로 상태값이 없는)
  - 어떤 표현식 e가 있을 때 e의 값으로 e가 나타나는 모든 위치를 교체하더라도 결과가 달라지지 않는 특성
- 어떤 값이 변하지 않는 성질을 불병성이라고 부른다.

- 불변성, 부수효과, 참조투명성 사이의 관계
  - 불변성은 부수효과의 발생을 방지하고 참조 투명성을 만족

## 책임에 초점을 맞춰라
- 메시지를 먼저 선택하고 그 후에 메시지를 처리할 객체를 선택
  - 앞의 의도들을 다 만족할 수 있는

- 책임 주도 설계 방법에 따라 메시지가 객체를 결정하게 하라

- 계약에 의한 설계 (버트란드 마이어)
  - 협력을 위해 두 객체가 보장해야 하는 실행 시점의 제약을 인터페이스에 명시할 수 있는 방법이 존재하지 않는 문제를 해결하기 위한 개념




