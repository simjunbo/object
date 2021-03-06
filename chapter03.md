# 역할, 책임, 협력
- 객체지향 패러다임의 관점에서 핵심은 역할(role), 책임(responsibility), 협력(collaboration)
- 위 3가지를 고민하지 않은채 이른 시기에 구현에 초첨을 맞추면 변경하기 어렵고 유연하지 못한 코드를 낳음
  
# 01 협력
## 영화 예매 시스템 돌아보기

#### 그림 3.1 영화 예매 기능을 구현하기 위한 객체들 사이의 상호작용
<img width="616" alt="3-1" src="https://user-images.githubusercontent.com/7076334/105577849-620ba880-5dbf-11eb-88b5-37dd52f0841e.png">

- 객체지향 원칙을 따르는 애플리케이션의 제어 흐름은 어떤 하나의 객체에 의해 통제되지 않고 다양한 객체들 사이에 균형 있게 분배
- **협력** : 객체들이 애플리케이션의 기능을 구현하기 위해 수행하는 상호작용
- **책임** : 객체가 협력에 참여하기 위해 수행하는 로직
- **역할** : 객체들이 협력 안에서 수행하는 책임들이 모여 객체가 수행하는 역할을 구성

## 협력
- 협력은 객체지향의 세계에서 기능을 구현할 수 있는 유일한 방법
- **메시지 전송** 은 객체 사이의 협력을 위해 사용할 수 있는 유일한 커뮤니케이션 수단
  - 객체는 다른 객체의 상세한 구현에 직접 접근할 수 없음
- 메시지를 수신한 객체는 **메서드** 를 실행해 요청에 응답
  - 객체가 메시지를 처리할 방법을 스스로 선택
  
#### 그림 3.2 screening은 메시지를 전송해서 Movie와 협력
<img width="437" alt="3-2" src="https://user-images.githubusercontent.com/7076334/105578310-8026d800-5dc2-11eb-83a3-ec35cfd7d7ce.png">

- 이 협력에서 Screening은 Movie에 calculateMovieFee 메시지를 전송함으로써 예매자 한 명의 요금 계산을 요청
  - Movie에게 처리를 위임하는 이유는 요금을 계산하는데 필요한 기본 요금과 할인 정책을 가장 잘 알고 있는 객체가 Movie
- Screening이 Movie 정보를 이용해 요금을 계산할 경우 정보와 행동이 Movie와 Screening 이라는 별도 객체로 나뉨 (결국 Movie는 수동적인 존재로 전락)
- 자신이 할 수 없는 일을 다른 객체에게 위임하면 협력에 참여하는 객체들의 전체적인 자율성을 향상시킬 수 있음
- 객체를 자율적으로 만드는 가장 기본적인 방법은 내부 구현을 **캡슐화** 하는 것

- 정리
  - 자율적인 객체는 자신에게 할당된 책임을 수행하던 중에 필요한 정보를 알지 못하거나 외부의 도움이 필요한 경우 적절한 객체에게 **메시지**를 전송해서 협력을 요청
  - 이처럼 객체들 사이의 협력을 구성하는 일련의 요청과 응답의 흐름을 통해 애플리케이션의 기능이 구현
  
## 협력이 설계를 위한 문맥을 결정한다
- 객체가 가질 수 있는 **상태**와 **행동**을 어떤 기준으로 결정해야 할까?
  - 객체의 **행동**을 결정하는 것은 객체가 참여하고 있는 협력임
    - 협력은 객체가 필요한 이유와 객체가 수행하는 행동의 동기를 제공
  
  - 객체의 **상태**를 결정하는 것은 행동임
    - 객체의 상태는 그 객체가 행동을 수행하는데 필요한 정보가 무엇인지 결정
    
```java
public class Movie {
    private Money fee;                      // 기본요금
    private DiscountPolicy discountPolicy;  // 할인 정책

    // 기본요금에서 할인 요금 차감
    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}

```
- Movie에서 fee(기본요금)과 discountPolicy(할인 정책)라는 인스턴스 변수를 상태의 일부로 포함하는 이유는 **요금 계산**이라는 행동을 수행하는데 정보들이 필요

- 상태는 행동하는 데 필요한 정보에 의해 결정되고, 행동은 협력 안에서 처리할 메시지로 결정 ( 협력 > 행동 > 상태)
  - 결과적으로 객체가 참여하는 협력이 객체를 구성하는 행동과 상태 모두를 결정함
  - 협력은 객체를 설계하는 데 필요한 일종의 **문맥** 제공

# 02 책임
## 책임이란 무엇인가
- 협력에 참여하기 위해 객체가 수행하는 행동을 **책임** 이라고 부름
- 객체의 책임을 크게 **하는 것(doing)** 과 **아는 것(knowing)** 의 두가지 범주로 나누어 세분화 (크레이그 라만)
  - 하는 것
    - 객체를 생성하거나 계산을 수행하는 등의 스스로 하는 것
    - 다른 객체의 행동을 시작시키는 것
    - 다른 객체의 활동을 제어하고 조절하는 것
  - 아는 것
    - 사적인 정보에 관해 아는 것
    - 관련된 객체에 관해 아는 것
    - 자신이 유도하거나 계산할 수 있는 것에 관해 아는 것

#### 그림 3.3 영화 예매 시스템을 구성하는 역할과 책임
- 역할과 책임을 CRC 카드로 표현

![3-3](https://user-images.githubusercontent.com/7076334/105579231-90da4c80-5dc8-11eb-8e14-936d519751ec.png)

- 협력 안에서 객체에게 할당한 책임이 외부의 인터페이스와 내부의 속성을 결정함
  - 행동 > 상태 를 말하는 듯?

- 책임과 메시지의 크기는 다름
  - 책임은 객체가 수행할 수 있는 행동을 종합적이고 간략하게 서술하기 때문에 메시지보다 추상적이고 개념도 더 큼

- 책임의 관점에서 '아는 것'과 '하는 것'이 밀접하게 연관돼 있음
  - 객체는 자신이 맡은 책임을 수행하는 데 필요한 정보를 알고 있을 책임이 있음 (아는 것)
  - 객체는 자신이 할 수 없는 작업을 도와줄 객체를 알고 있을 책임이 있음 (아는 것)
  - 어떤 책임을 수행하기 위해 그 책임을 수행하는 데 필요한 정보도 함께 알아야 할 책임이 있음 (아는 것)
  
- 책임은 객체지향 설계의 핵심이다.
  - 협력이 중요한 이유는 객체에게 할당할 책임을 결정할 수 있는 문맥을 제공하기 때문
  - 객체에게 얼마나 적절한 책임을 할당하느냐가 설계의 전체적인 품질을 결정
  
### CRC 카드
- 후보(Candidate), 책임(Responsibility), 협력자(Collaborator)의 첫 글자를 따서 만듬
  - 워드 커닝험과 켄트 벡이 객체지향 설계 기법을 가르치기 위해 고안한 기법
- 하나의 CRC 카드는 협력에 참여하는 하나의 후보를 표현
  - 후보는 **역할, 객체, 클래스** 어떤 것이라도 가능
- 카드의 선이 없는 면에는 후보의 목적 기술
  - 목적은 후보가 외부에 제공해야 하는 서비스를 하나의 문장으로 표현
- 선이 있는 면은 상단에 후보 이름, 좌측 하단에는 책임, 우측에는 협력자를 나열
  - 협력자는 후보가 자신의 책임을 완수하기 위해 정보나 기능을 요청할 대상 후보를 의미
- C(Candidate)가 되는 과정
  - Class => Component => Role => Candidate (포괄)
- CRC 사용 이유?
  - 역할을 식별하고, 책임을 할당하며, 협력을 명시적으로 표현하는 구체적이면서도 실용적인 설계 기법
  
## 책임 할당
- INFORMATION EXPERT(정보 전문가) 패턴
  - 자율적인 객체를 만드는 가장 기본적인 방법은 책임을 수행하는 데 필요한 정보를 가장 잘 알고 있는 전문가에게 그 책임을 할당하는 것
  
- 객체에게 책임을 할당하기 위해서는 먼저 협력이라는 문맥을 정의
  - 협력을 설계하는 출발점은 시스템이 사용자에게 제공하는 기능을 시스템이 담당할 하나의 책임으로 바라보는 것
  - 객체지향 설계는 시스템의 책임을 완료하는 데 필요한 더 작은 책임을 찾아내고 이들 객체들에게 할당하는 반복적인 과정을 통해 모양을 갖춰감
  
- 영화 예매 시스템을 예)
  - 1) 제공해야 되는 기능은 '영화를 예매하는 것'
  - 2) 전문가 Screening 에게 책임을 할당
  - 3) 영화를 예매하기 위해서는 예매 가격을 계산해야 하지만 Screening 은 예매 전문가 이지 계산 전문가가 아님
  - 4) 외부에 계산을 요청해 달라는 '가격을 계산하라' 의 이름으로 새로운 메시지 생성
  - 5) 전문가 Movie 에게 계산 책임 할당
  - 6) Movie에서 직접 처리할 수 없어서 외부에 전송
  
- 객체지향 설계는 협력에 필요한 메시지를 찾고 메시지에 적절한 객체를 선택하는 반복적인 과정을 통해 이루어짐
  - 이런 메시지가 메시지를 수신할 객체의 책임을 결정
- 협력을 설계하면서 객체의 책임을 식별해 나가는 과정에서 최종적으로 얻게 되는 결과물은 시스템을 구성하는 객체들의 **인터페이스** 와 **오퍼레이션의 목록**
- 어떤 경우 정보 전문가가 아닌 다른 객체에게 책임을 할당하는게 나을 수도 있지만 정보 전문가에게 책임을 할당하는 것만으로 상태와 행동을 함께 가지는 자율적인 객체를 만들 가능성이 높아짐

## 책임 주도 설계
- 책임 주도 설계 (Responsibility-Driven Design, RDD)
  - 책임을 찾고 책임을 수행할 적절한 객체를 찾아 책임을 할당하는 방식으로 협력을 설계하는 방법
  
- 책임 주도 설계 방법의 과정
  - 시스템이 사용자에게 제공해야 하는 기능은 시스템 책임을 파악한다.
  - 시스템 책임을 더 작은 책임으로 분할한다.
  - 분할된 책임을 수행할 수 있는 적절한 객체 또는 역할을 찾아 책임을 할당한다.
  - 객체가 책임을 수행하는 도중 다른 객체의 도움이 필요한 경우 이를 책임질 적절한 객체(전문가) 또는 역할을 찾는다.
  - 해당 객체 또는 역할에게 책임을 할당함으로써 두 객체가 협력하게 한다.
  
- 책임 주도 설계는 자연스럽게 객체의 구현이 아닌 책임에 집중할 수 있게 함
  - 책임에 집중하는 것이 중요한 이유는 유연하고 견고한 객체지향 시스템을 위해 가장 중요한 재료가 책임이기 때문
  
- 책임을 할당시 고려해야 하는 두 가지 요소
  - 메시지가 객체를 결정한다는 것
  - 행동이 상태를 결정한다는 것

## 메시지가 객체를 결정한다
- 메시지가 객체를 선택하게 해야 하는 두 가지 중요 이유
  - 첫째, 객체의 최소한의 인터페이스를 가질 수 있게 됨
    - 필요한 메시지가 식별될 때까지 객체의 퍼블릭 인터페이스에 어떤 것도 추가하지 않기 때문에 꼭 필요한 크기의 퍼블릭 인터페이스를 가질 수 있음
  - 둘째, 객체는 충분히 추상적인 인터페이스를 가질 수 있게 됨
    - 객체의 인터페이스는 무엇(what)을 하는지 표현해야 하지만 어떻게(how) 수행하는지를 노출하면 안됨
    - 메시지를 식별하면 무엇을 수행할지에 초점을 맞추는 인터페이스를 얻을 수 있음

- 객체가추상적이면서 미니멀리즘을 따르는 인터페이스를 가지게 하고 싶다면 메시지가 객체를 선택하게 하라

## 행동이 상태를 결정한다
- 객체의 행동은 객체가 협력에 참여할 수 있는 유일한 방법
  - 협력에 적합한지 결정하는 것은 상태가 아니라 행동
  - 적절한 객체 => 적절한 책임 할당 => 적절한 협력
  
- 객체의 행동이 아닌 상태에 초점을 맞출 시, 내부 구현이 객체의 퍼블릭 인터페이스에 노출되도록 만들기 떄문에 **캡슐화** 를 저해
- 데이터 주도 설계(Data-Driven Design) 
  - 객체의 내부 구현에 초점을 맞춘 설계 (좋지 않다.)
  
- 개별 객체의 상태와 행동이 아닌 시스템의 기능을 구현하기 위한 협력에 초점을 맞춰야만 응집도가 높고 결합도가 낮은 객체들을 창조할 수 있음

- 객체가 가질 수 있는 상태는 행동을 결정하고 나서야 비로서 결정할 수 있음
- 협력이 객체의 행동을 결정하고 행동이 상태를 결정함
  - 그리고 그 행동이 바로 객체의 책임이 됨
  
# 03 역할
## 역할과 협력
- 객체가 어떤 특정한 협력 안에서 수행하는 책임의 집합을 **역할** 이라고 부른다.
  - 실제로 협력을 모델링할 때는 특정한 객체가 아니라 역할에게 책임을 할당한다고 생각하는게 좋다.
  
- 영화 예매 협력에서 '예매하라'라는 메시지를 처리하기에 적합한 객체로 Screening을 선택
- 이 책임 할당 과정은 실제로 두 개의 독립적인 단계가 합쳐진 것
  - 첫번째 단계는 영화를 예매할 수 있는 적절한 역할이 무엇인가를 찾는 것
  - 두번째 단계는 역할을 수행할 객체로 Screening 인스턴스 선택
- 역할에 특별한 이름은 부여하지 않음

<img width="326" alt="3-3-1" src="https://user-images.githubusercontent.com/7076334/105606092-a9ebf900-5dda-11eb-8c4f-d4f5153eda64.png">



- Screening이 전송하는 가격을 계산하라는 메시지를 수신한 객체는 Movie 인스턴스이지만 사실은 역할을 수행할 객체로 Movie를 선택한 것
- 역할에 특별한 이름은 부여하지 않음

<img width="571" alt="3-3-2" src="https://user-images.githubusercontent.com/7076334/105606094-abb5bc80-5dda-11eb-919c-5387be24393f.png">

- 역할이 없어도 객체만으로 충분히 협력을 설계할 수 있는 것 아닌가?

## 유연하고 재사용 가능한 협력
- 역할이 중요한 이유는 역할을 통해 유연하고 재사용 가능한 협력을 얻을 수 있기 때문

- 역할이라는 개념을 고려하지 않고 객체에게 책임을 할당 한다면?
  - 영화 예매 도메인에서는 금액 할인 정책과 비율 할인 정책 두 가지 종류의 가격 할인 정책이 존재
  - 때문에 두 가지 종류의 객체가 '할인 요금을 계산하라' 메시지에 응답해야됨

![3-3-4](https://user-images.githubusercontent.com/7076334/105606416-b07b7000-5ddc-11eb-98a4-2a423af7b97e.png)

- 이런 방법으로 두 협력을 구현하면 대부분의 코드가 중복이 발생


- 문제를 해결하기 위해서는 객체가 아닌 책임에 초점을 맞춰야함
- 객체라는 존재를 지우고 '할인 요금을 계산하라' 라는 메시지에 응답할 수 있는 대표자를 생각한다면 두 협력을 하나로 통합할 수 있을 것임
- 이 대표자를 협력 안에서 두 종류의 객체를 교대로 바꿔 끼울 수 있는 일종의 슬록으로 생각할 수 있음
  - 이 슬롯이 바로 **역할** 이다.
  

<img width="625" alt="3-3-3" src="https://user-images.githubusercontent.com/7076334/105606223-7067bd80-5ddb-11eb-8c0d-0eccb3fabdd0.png">

- 여기서 역할이 두 종류의 구체적인 객체를 포괄하는 **추상화** 라는 점에 주목하라
  - 역할 이름을 DiscountPolicy로 정함
  
- 역할을 이용하면 불필요한 중복 코드를 제거할 수 있음
- 더 좋은 소식은 협력이 더 유연해졌음

### 역할의 구현
- 역할을 구현하는 가장 일반적인 방법은 **추상 클래스**와 **인터페이스**를 사용하는 것
- 추상 클래스와 인터페이스는 동일한 책임을 수행할 수 있는 객체들을 협력 안에 수용할 수 있는 역할
- 객체에게 중요한 것은 행동이며, 역할은 객체를 추상화해서 객체 자체가 아닌 협력에 초점을 맞출 수 있게 함

## 객체 대 역할
- 오직 한 종류의 객체만 협력에 참여하는 상황에서 역할을 고려하는것이 유용할까?
  - 협력에 적합한 책임을 수행하는 대상이 한 종류라면 간단하게 객체로 간주
  - 여러 종류의 객체들이 참여할 때만 역할이라 부르면 됨
  
#### 그림 3.5 협력, 역할, 객체, 클래스의 관계
![3 5](https://user-images.githubusercontent.com/7076334/105606929-529c5780-5ddf-11eb-87e7-f85f79861281.png)

- 협력은 역할들의 상호작용으로 구성되고, 협력을 구성하기 위해 역할에 적합한 객체가 선택되며, 객체는 클래스를 이용해 구현되고 생성됨

- 지은이의 개인 견해
  - 설계 초반에는 적절한 책임과 협력의 큰 그림을 탐색하는 것이 가장 중요한 목표여야 하고 역할과 객체를 명확하게 구분하는 것은 그렇게 중요하지 않음
  - 애매하면 단순히 객체로 시작하고 반복적인 책임과 협력을 정제해가면서 필요한 순간에 객체로부터 역할을 분리
  
- 트리그비 린스카우는 역할을 설계의 중심 개념으로 보는 **역할 모델링(Role Modeling)** 개념을 제안
  - 역할 모델링 기법은 UML에도 큰 영향을 미췄고 최근의 객체지향 언어와 설계 기법들은 역할을 중요한 구성 요소로 간주하기 시작
  
## 역할과 추상화
- 추상화를 이용한 설계의 장점 두가지
  - 첫 번째, 추상화 계층만을 이용하면 중요한 정책을 상위 수준에서 단순화 가능
    - 추상화를 적절하게 사용하면 불필요한 세부 사항을 생략하고 핵심적인 개념을 강조할 수 있음
  - 두 번째, 설계가 좀 더 유연해 짐
    - 다양한 환경에서 다양한 객체들을 수용할 수 있게 해주므로 협력을 유연하게 만듬
  
- 객체에게 중요한 것은 행동이라는 사실을 기억
- 협력 안에서 역할이라는 추상화를 이용하면 기존 코드를 수정하지 않고도 새로운 행동을 추가할 수 있음
- 프레임워크나 디자인 패턴과 같이 재사용 가능한 코드나 설계 아이디어를 구성하는 핵심적인 요소가 바로 역할

## 배우와 배역
- 객체는 다양한 역할을 가질 수 있다.
- 객체는 협력에 참여할 때 협력 안에서 하나의 역할로 보여진다.
- 객체가 다른 협력에 참여할 때는 다른 역할로 보여진다.
- 협력의 관점에서 동일한 역할을 수행하는 객체들은 서로 대체 가능하다.
- 역할은 특정한 객체의 종류를 캡슐화하기 때문에 동일한 역할을 수행하고 계약을 준수하는 대체 가능한 객체들은 다형적이다.
  
