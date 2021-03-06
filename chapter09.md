# 유연한 설계
- 이번장은 다양한 의존성 관리 기법들을 원칙이라는 관점에서 정리

# 01 개방-폐쇄 원칙
- 로버트 마틴은 확장 가능하고 변화에 유연하게 대응할 수 있는 설계를 만들 수 있는 원칙 중 하나로 개방-폐쇄 원칙(OCP) 고안
  - 소프트웨어 개체(클래스, 모듈 함수 등등)는 확장에 대해 열려 있고, 수정에 대해서는 닫혀 있음

- 확장 수정이 말하는것은?
  - 확장에 대해 열려 있다 : 애플리케이션의 요구사항이 변경될 때 이 변경에 맞게 새로운 '동작'을 추가해서 애플리케이션의 기능을 확장할 수 있음
  - 수정에 대해 닫혀 있다 : 기존의 '코드'를 수정하지 않고도 애플리케이션의 동작을 추가하거나 변경할 수 있음

- 어떻게 코드를 수정하지 않고 새로운 동작을 추가하는가?

## 컴파일타임 의존성을 고정시키고 런타임 의존성을 변경하라
- 개방-폐쇄 원칙은 런타임 의존성과 컴파일타임 의존성에 관한 이야기
  - 유연하고 재사용 가능한 설계에서 런타임 의존성과 컴파일 타임 의존성은 서로 다른 구조를 가짐

- Movie 예제 (개방-폐쇄 원칙)
  - DiscountPolicy(추상클래스)를 사용해서 새로운 할인 정책을 추가시 기능을 확장할 수 있도록 허용
    - '확장에 대해서 열려 있다' 
  - 기존 코드 수정할 필요 없이 클래스를 추가하는 것만으로 새로운 할인 정책을 확장
    - '수정에는 닫혀 있다'

- 개방-폐쇄 원칙을 수용하는 코드는 컴파일타임 의존성을 수정하지 않고도 런타임 의존성을 쉽게 변경할 수 있음

- 의존성 관점에서 개방-폐쇄 원칙을 따르는 설계란 컴파일타임 의존성은 유지하면서 런타임 의존성의 가능성을 확장하고 수정할 수 있는 구조라 할 수 있음

## 추상화가 핵심이다
- 개방-폐쇄 원칙의 핵심은 **추상화에 의존하는 것**
- 추상화란 핵심적인 부분만 남기고 불필요한 부분은 생략함으로써 복잡성을 극복하는 기법
  - 추상화 과정을 거치면 문맥이 바뀌더라도 변하지 않는 부분만 남게 되고 문맥에 따라 변하는 부분은 생략됨 

- 개방-폐쇄 원칙의 관점에서 생략되지 않고 남겨지는 부분은 다양한 상황에서의 공통점을 반영한 추상화의 결과물

- 변하지 않는 부분을 고정하고 변하는 부분을 생략하는 추상화 메커니즘이 개방-폐쇄 원칙의 기반이 됨

- 단순 어떤 개념을 추상화 했다고 수정에 닫혀 있는것은 아님
  - 개방-폐쇄 원칙에서 폐쇄를 가능하게 하는 것은 의존성의 방향
  - 수정에 대한 영향을 최소화하기 위해서 모든 요소가 추상화에 의존해야 함

- 추상화를 했다고 모든 수정에 대해 설계가 폐쇄되는 것은 아님
  - 변하는 것과 변하지 않는 것이 무엇인지를 이해하고 이를 추상화의 목적으로 삼아야 함


# 02 생성 사용 분리
- 결합도가 높아질수록 개방-폐쇄 원칙을 따르는 구조를 설계하기 어려워짐 (알아야 하는 지식이 많으면 결합도도 높아짐)

- 동일한 클래스 안에서 객체 생성과 사용 이라는 두 가지 이질적인 목적을 가진 코드가 공존하는 것이 문제

- 유연하고 재사용 가능한 설계를 원한다면 객체 생성과 객체 사용하는 부분을 분리 (생성과 사용을 분리)
  - 가장 보편적인 방법은 객체를 생성할 책임을 클라이언트로 이동

## FACTORY 추가하기
- 생성과 사용을 분리하기 위해 객체 생성에 특화된 객체를 FACTORY라고 부름

- Movie 생성 예제 (P.290)
  - FACTORY를 사용하면 Movie와 AmountDiscountPolicy를 생성하는 책임 모두 FACTORY로 이동할 수 있음
  
## 순수한 가공물에게 책임 할당하기
- 책임 할당의 가장 기본이 되는 원칙은 책임을 수행하는 데 필요한 정보를 가장 많이 알고 있는 INFORMATION EXPERT(정보 전문가) 에게 책임을 할당하는 것
  - 도메인 모델은 정보전문가를 찾기 위해 참조할 수 있는 일차적인 재료
  - 책임 할 당 시, 도메인 모델 안에서 적절한 후보 존재여부 확인

- FACTORY는 도메인 모델에 속하지 않는다(순수하게 기술적인 결정)
  - 결합도를 낮추고 재사용성을 높이기 위해 도메인 개념에게 할당돼 있던 객체 생성 책임을 가공의 객체로 이동시킨 것

- 시스템을 객체로 분해하는데 크게 두 가지 방식 (크레이그 라만)
  - 표현적 분해 : 도메인에 존재하는 사물 또는 개념을 표현하는 객체들을 이용해 시스템을 분해하는 것
    - 모든 책임을 도메인 객체에 할당하면 낮은 응집도, 높은 결합도, 재사용성 저하와 같은 심각한 문제점에 봉착하게 될 가능성이 높아짐
  - 행위적 분해 : 책임을 할당하기 위해 창조되는 도메인과 무관한 인공적인 객체를 PURE FABRICATION(순수한 가공물) 이라 부름
    - 어떤 행동을 추가하려고 하는데 이 행동을 책임질 마땅한 도메인 개념이 존재하지 않는 경우 사용

- 객체지향이 실세계 모방이라는 말은 옳지 않음
  - 도메인 개념 뿐만 아니라 설계자들이 임의적으로 창조한 인공적인 추상화들을 포함

- 객체지향 비유
  - 도시의 본질은 그 안에 뿌리를 내리고 살아가는 자연과 인간에게 있지만 도시의 대부분은 인간의 생활을 편리하게 만들기 위한 수 많은 인공물들로 채워져 있음

- 도메인 개념을 표현하는 객체와 순수하게 창조된 가공의 객체들이 모여 자신의 역할과 책임을 다하고 조화롭게 협력하는 애플리케이션을 설계하는 것이 목표

- 도메인 개념이 만족스럽지 못하다면 주저하지 말고 인공적인 객체를 창조하라

- 대부분의 디자인 패턴은 PURE FABRICATION을 포함한다

### PURE FABRICATION 패턴
- PURE FABRICATION은 INFORMATION EXPERT 패턴에 따라 책임을 할당한 결과가 바람직하지 않을 경우 대안으로 사용

# 03 의존성 주입
- 사용하는 객체가 아닌 외부의 독립적인 객체가 인스턴스를 생성한 후 이를 전달해서 의존성을 해결하는 방법을 **의존성 주입** 이라고 부름

- 의존성 주입은 의존성을 해결하기 위해 의존성을 객체의 퍼블릭 인터페이스에 명시적으로 드러내서 외부에서 필요한 런타임 의존성을 전달할 수 있도록 만드는 방법을 포괄하는 명칭

- 의존성 주입 (8장에서 얘기한)
  - 생성자 주입 : 객체를 생성하는 시점에 생성자를 통한 의존성 해결
  - setter 주입 : 객체 생성 후 setter 메서드를 통한 의존성 해결
  - 메서드 주입 : 메서드 실행 시 인자를 이용한 의존성 해결

### 프로퍼티 주입과 인터페이스 주입
- C# 진영에는 setter 주입 대신 프로퍼티 주입이라는 용어 사용
- 인터페이스 주입 기법 : 주입할 의존성을 명시하기 위해 인터페이스 사용
  - setter 주입과 동일하나 어떤 대상을 어떻게 주입할지 인터페이스를 통해 명시적으로 선언

### 숨겨진 의존성은 나쁘다
- SERVICE LOCATOR는 의존성을 해결할 객체들을 보관하는 일종의 저장소

```
SERVICE LOCATOR 패턴은 서비스를 사용하는 코드로부터 서비스가 누구인지(서비스를 구현한 구체 클래스의 타입이 무엇인지), 어디에 있는지(클래스 인스턴스를 어떻게 얻을지)를 몰라도 되게 해준다
```

- SERVICE LOCATOR 예제 (P296)

- SERVICE LOCATOR의 단점
  - 의존성을 감춘다.
  - 의존성을 구현 내부로 감출 경우 의존성과 관련된 문제가 컴파일타임이 아닌 런타임 가서야 발견된다.
  - 단위 테스트 작성도 어렵다.
    - 정적 변수를 사용하기 때문에 단위 테스트는 서로 고립돼야 한다는 단위 테스트의 기본 원칙을 위반
  - 캡슐화 위반

- SERVICE LOCATOR 는 숨겨진 의존성이 캡슐화를 위반
- 클래스의 퍼블릭 인터페이스만으로 사용 방법을 이해할 수 있는 코드가 캡슐화의 관점에서 훌륭한 코드

- 숨겨진 의존성이 가지는 가장 큰 문제는 의존성을 이해하기 위해 코드의 내부 구현을 이해할 것을 강요
- 숨겨진 의존성은 의존성의 대상을 설정하는 시점과 의존성이 해결되는 시점을 멀리 떨어트려 놓음
  - 코드를 이해하고 디버깅하기 어렵게 만듬

- 의존성 주입은 SERVICE LOCATOR 문제를 깔끔하게 해결
  - 의존성은 퍼블릭 인터페이스에 명시적으로 드러남
  - 코드 내부를 읽을 필요가 없기 때문에 객체의 캡슐을 단단하게 보호
  - 컴파일 시점에 의존성 관련 문제 찾을 수 있음
  - 단위 테스트에도 용이

- 명시적인 의존성이 숨겨진 의존성보다 좋다

- SERVICE LOCATOR를 사용해야 되는 경우
  - 의존성 주입을 지원하는 프레임워크를 사용하지 못하는 경우
  - 깊은 호출 계층에 걸쳐 동일한 객체를 계속 전달해야 되는 경우 

# 04 의존성 역전 원칙
## 추상화와 의존성 역전
- 객체 사이의 협력이 존재할 때 그 협력의 본질을 담고 있는 것은 상위 수준의 정책
- 상위 수준의 클래스가 하위 수준의 클래스에 의존한다면 하위 수준의 변경에 의해 상위 수준 클래스가 영향을 받게 될 것

- 그림 9.7
  - 의존성의 방향이 잘못됨
  - 재사용성에도 문제가 됨
  
- 상위 수준의 변경에 의한 하위 수준이 변경되는 것은 납득할 수 있지만 하위 수준의 변경으로 인해 상위 수준이 변경돼서는 곤란

- 그림 9.8
  - 추상화를 통해 문제 해결
  - 상위 수준의 클래스와 하위 수준의 클래스 모두 추상화에 의존

- 정리
  - 상위 수준의 모듈은 하위 수준의 모듈에 의존해서는 안 된다. 둘 모두 추상화에 의존해야 한다.
  - 추상화는 구체적인 사항에 의존해서는 안 된다. 구체적인 사항은 추상화에 의존해야 한다.

- 이를 의존성 역전 원칙이라 부른다.

## 의존성 역전 원칙과 패키지
- 불필요한 클래스들을 같은 패키지에 두는 것은 전체적인 빌드 시간을 가파르게 상승시킴

- 그림 9.10 (SEPARATED INTERFACE 패턴)
  - 추상화를 별도의 독립적인 패키지가 아니라 클라이언트가 속한 패키지에 포함시켜야 한다.
  - 함께 재사용될 필요가 없는 클래스들은 별도의 독립적인 패키지로 모아야 함

- 의존성 역전 원칙에 따라 상위 수준의 협력 흐름을 재사용하기 위해서는 추상화가 제공하는 인터페이스의 소유권 역시 역전시켜야 함

- 전통적인 설계 패러다임은 인터페이스의 소유권을 클라이언트 모듈이 아닌 서버 모듈에 위치
- 반면 잘 설계된 객체지향 애플리케이션에서는 인터페이스의 소유권을 서버가 아닌 클라이언트에 위치

- 유연하고 재사용 가능하며 컨텍스트에 독립적인 설계는 전통적인 패러다임이 고수하는 의존성의 방향을 역전 시킴

# 05 유연성에 대한 조언
## 유연한 설계는 유연성이 필요할 때만 옳다
- 유연하고 재사용 가능한 설계가 항상 좋은 것은 아님
  - 변경하기 쉽고 확장하기 쉬운 구조를 만들기 위해서는 단순함과 명확함의 미덕을 버리게 될 가능성이 높다

- 유연성은 항상 복잡성을 수반
  - 객체지향 코드에서 특정 시점의 객체 구조를 파악하는 유일한 방법은 클래스를 사용하는 클라이언트 코드 내에서 객체를 생성하거나 변경하는 부분을 확인

- 설계가 유연할수록 클래스 구조와 객체 구조 사이의 거리는 점점 멀어짐 (추상화)
  - 유연함은 단순성과 명확성의 희생 위에서 자란다.
  - 유연한 설계를 단순하고 명확하게 만드는 유일한 방법은 사람들 간의 긴밀한 커뮤니케이션 뿐

- 불필요한 유연성은 불필요한 복잡성을 낳는다
  - 유연성은 코드를 읽는 사람들이 복잡함을 수용할 수 있을 때만 가치가 있다.

## 협력과 책임이 중요하다
- 설계를 유연하게 만들기 위해서는 협력에 참여하는 객체가 다른 객체에게 어떤 메시지를 전송하는지가 중요하다.
- 설계를 유연하게 만들기 위해서는 먼저 역할, 책임, 협력에 초점을 맞춘다.

- 자주 저지르는 실수 중 하나는 객체의 역할과 책임이 자리를 잡기 전에 너무 성급하게 객체 생성에 집중하는 것
  - 객체 생성과 관련된 불필요한 세부사항에 객체를 결합
  - 객체를 생성할 책임을 담당할 객체나 객체 생성 메커니즘을 결정하는 시점은 책임 할당의 마지막 단계로 미뤄야 한다.
  - 중요한 비즈니스 로직을 처리하기 위해 책임을 할당하고 협력의 균형을 맞추는 것이 객체 생성에 관한 책임을 할당하는 것보다 우선

- 객체를 생성하는 방법에 대한 결정은 모든 책임이 자리를 잡은 후 가장 마지막 시점에 내리는 것이 적절함

- 의존성을 관리해야 하는 이유는 역할, 책임, 협력의 관점에서 설계가 유연하고 재사용 가능해야 하기 때문
  - 따라서 역할, 책임, 협력에 먼저 집중 




