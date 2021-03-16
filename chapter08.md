# 의존성 관리하기
- 잘 설계된 객체지향 애플리케이션은 작고 응집도 높은 객체들로 구성
  - 작고 응집도 높은 객체란 책임의 초점이 명확하고 한 가지 일만 잘하는 객체
  - 단위가 작기 때문에 다른 객체와 협력

- 협력은 객체가 다른 객체에 대해 알것을 강요
  - 객체 사이의 의존성 발생

- 협력을 위해서 의존성이 필요하지만 과도한 의존성은 애플리케이션을 수정하기 어렵게 만듬
  - 객체지향 설계의 핵심은 협력을 위해 필요한 의존성은 유지하면서 변경을 방해하는 의존성을 제거하는 것

# 01 의존성 이해하기
## 변경과 의존성
- 의존성은 실행 시점과 구현 시점에 서로 다른 의미를 가짐
  - 실행 시점 : 의존하는 객체가 정상적으로 동작하기 위해서는 실행 시에 의존 대상 객체가 반드시 존재해야 함
  - 구현 시점 : 의존 대상 객체가 변경될 경우 의존하는 객체도 함께 변경됨

```
public class PeriodCondition implements DiscountCondition {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public boolean isSatisfiedBy(Screening screening) {
        return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
                startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
                endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0;
    }
}
```

- 실행 시점에 PeriodCondition의 인스턴스가 정상적으로 동작하기 위해서 Screening의 인스턴스가 존재해야됨
- 이 처럼 어떤 객체가 예정된 작업을 정상적으로 수행하기 위해 다른 객체를 필요로 하는 경우 두 객체 사이의 의존성이 존재한다고 말함
  - 의존성은 항상 단방향 이다.

- Screening이 변경되면 PeriodCondition이 영향 받지만 그 역은 성립하지 않음
![8 1](https://user-images.githubusercontent.com/7076334/111326808-35358c80-86b0-11eb-86f3-66ff56ef477f.png)

- 설계와 관련된 대부분의 용어들이 변경과 관련이 있음 (의존성 역시 마찬가지)

- 위의 예제에서 PeriodCondition은 DayOfWeek, LocalTime, Screening에 대한 의존성을 가짐
  - 그림 8.2

- 의존성의 종류를 구분 가능하도록 서로 다른 방식으로 표현
<img width="500" alt="8 3" src="https://user-images.githubusercontent.com/7076334/111321360-331cff00-86ab-11eb-8e5d-e0d268dde13c.png">

### UML과 의존성
- 그림 8.3에는 실체화 관계, 연관 관계, 의존 관계가 포함
- 이번장의 '의존성'은 UML의 의존 관계와 다름
  - UML은 두 요소 사이에 존재할 수 있는 다양한 관계의 하나로 '의존 관계'를 정의
  - 의존성은 두 요소 사이에 변경에 의해 영향을 주고받는 힘의 역학관계가 존재한다는 사실에 초점
- 의존성은 UML에서 정의하는 모든 관계가 가지는 공통적인 특성으로 바라봐야 함

## 의존성 전이
- Screening은 Movice, LocalDateTime, Customer에 의존
- 의존성 전이가 의미하는 것은 PeriodCondition이 Screening에 의존할 경우, Screening이 의존하는 대상에 대해서도 자동적으로 의존하게 된다는 것
- PeriodCondition이 간적접으로 Movice, LocalDateTime, Customer에 의존

![8 4](https://user-images.githubusercontent.com/7076334/111326816-3666b980-86b0-11eb-8fd9-651decd75391.png)

- 의존성이 모든 경우 전이되는 것은 아님
  - 의존성이 실제로 전이될지 여부는 변경의 방향과 캡슐화의 정도에 따라 달라짐
  - 의존성 전이는 변경에 의해 영향이 널리 전파될 수도 있따는 경고일 뿐

- 의존성의 종류를 직접 의존성과 간접 의존성으로 나누기도 함
  - 직접 의존성이란 말 그대로 한 요소가 다른 요소에 직접 의존하는 경우 
    - PeriodCondition => Screening (의존)
  - 간접 의존성이란 직접적인 관계는 존재하지 않지만 의존성 전이에 의해 영향이 전파되는 경우
    - PeriodCondition 코드안에 명시적으로 드러나있지 않음

- 변경과 관련이 있는 어떤 것에도 의존성이라는 개념을 적용할 수 있음
  - 의존성의 대상은 객체일 수도 있고 모듈이나 더 큰 규모의 실행 시스템일 수도 있음
  - 하지만 본질은 같음
    - 의존성이란 의존하고 있는 대상의 변경에 영향을 받을 수 있는 가능성이다.

## 런타임 의존성과 컴파일타임 의존성
- 런타임 의존성
  - 애플리케이션이 실행되는 시점을 가리킴
  - 런타임 의존성이 다루는 주제는 객체 사이의 의존성
- 컴파일타임 의존성
  - 작성된 코드를 컴파일하는 시점을 가리키지만 문맥에 따라서는 코드 그 자체를 가리키기도 함
  - 동적 타입 언어의 경우 컴파일타임이 존재하지 않기 때문에 실제로 컴파일 수행되는 시점으로 이해하면 의미가 모호해질 수 있음
  - 컴파일타임 의존성이 다루는 주제는 클래스 사이의 의존성

- 런타임 의존성과 컴파일타임 의존성이 다를 수 있음
  - 유연하고 재사용 가능한 코드를 설계하기 위해서는 두 종류의 의존성을 서로 다르게 만들어야 함

- 영화 예매 시스템 예)

<img width="500" alt="8 5" src="https://user-images.githubusercontent.com/7076334/111326365-d7a14000-86af-11eb-9337-7a9d3256a523.png">

- 코드상에서 Movie 클래스에서 AmountDiscountPolicy, PercentDiscountPolicy 클래스로 향하는 어떤 의존성도 존재하지 않음
- 오직 추상 클래스인 DiscountPolicy 에만 의존


<img width="500" alt="8 6" src="https://user-images.githubusercontent.com/7076334/111326374-d96b0380-86af-11eb-988f-80e044979c2f.png">


- 실행 시점의 Movie 인스턴스는 AmountDiscountPolicy, PercentDiscountPolicy 인스턴스와 협력할 수 있어야 함
- Movie 클래스가 AmountDiscountPolicy, PercentDiscountPolicy 클래스 둘 모두 의존하는 것은 결합도를 높일뿐만 아니라 새로운 할인 정책을 추가하기 어려움
- 대신 두 클래스를 포괄하는 DiscountPolicy 라는 추상 클래스에 의존하도록 만들고 실행 시에 PercentDiscountPolicy나 AmountDiscountPolicy 인스턴스에 대한 런타임 의존성으로 대체해야 함

- 어떤 클래스의 인스턴스가 다양한 클래스의 인스턴스와 협력하기 위해서는 협력할 인스턴스의 구체적인 클래스를 알아서는 안 됨
  - 실제 협력할 객체는 런타임에 해결해야됨
- 컴파일타임 구조와 런타임 구조 사이의 거리가 멀면 멀수록 설계가 유연해지고 재사용 가능해짐

```
시스템의 실행 시점 구조는 언어가 아닌 설계자가 만든 타입들 간의 관련성으로 만들어진다. 
그러므로 객체와 타입 간의 관계를 잘 정의해야 좋은 실행 구조를 만들어낼 수 있다.
```

## 컨텍스트 독립성
- 클래스 자신과 협력할 객체의 구체적인 클래스에 대해 알아서는 안됨
  - 구체적인 클래스를 알면 알수록 그 클래스가 사용되는 특정한 문맥에 강한게 결합되기 때문

- 클래스가 특정 문맥에 강하게 결합될수록 다른 문맥에서 사용하기 는 더 어려워짐
- 클래스가 사용될 특정한 문맥에 대해 최소한의 가정만으로 이뤄져 있다면 다른 문맥에서 재사용하기 더 수월해짐 (이를 컨텍스트 독립성)

- 설계가 유연해지기 위해서는 가능한 자신이 실행될 컨텍스트에 대한 구체적인 정보를 최대한 적게 알아함
  - 컨텍스트에 대한 정보가 적으면 적을수록 더 다양한 컨텍스트에서 재사용될 수 있기 때문에 설계는 더 유연해지고 변경에 탄력적으로 대응할 수 있게 됨

- 클래스가 실행 컨텍스트에 독립적인데 어떻게 런타임에 실행 컨텍스트에 적절한 객체들과 협력할 수 있을까?
  - 의존성 해결

## 의존성 해결하기
- 컴파일타임 의존성을 실행 컨텍스트에 맞는 적절한 런타임 의존성으로 교체하는 것을 **의존성 해결** 이라고 부름
- 의존성을 해결 하기 위한 세 가지 방법
  - 객체를 생성하는 시점에 생성자를 통해 의존성 해결
  - 객체 생성 후 setter 메서드를 통해 의존성 해결
  - 메서드 실행 시 인자를 이용해 의존성 해결

- 생성자 방식
```
Movie avatar = new Movie("아바타",
                         Duration.ofMinutes(120),
                         Money.wons(10000),
                         new AmountDiscountPolicy());
                         
public class Movie {
    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}                  
```
- Movie 객체를 생성할 때 AmountDiscountPolicy의 인스턴스를 Movie의 생성자에 인자로 전달
- 이를 위해 DiscountPolicy 타임의 인자를 받는 생성자를 정의

- setter 방식
```
Movie avater = new Movie();
avater.setDiscountPolicy(new AmountDiscountPolicy());      
...
avater.setDiscountPolicy(new PercentDiscountPolicy());  


public class Movie {
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
```
- Movie 인스턴스가 생성된 후에도 DiscountPolicy를 설정할 수 있는 setter 메서드를 제공해야됨
- setter 메서드를 이용하는 방식은 객체를 생성한 이후에도 의존하고 있는 대상을 변경할 수 있는 가능성을 열어 놓고 싶은 경우 유용
- 단점은 객체가 생성된 후에 협력에 필요한 의존 대상을 설정하기 때문에 객체를 생성하고 의존 대상을 설정 전까지 객체의 상태가 불완전할 수 있음 (NPE 발생)


- 생성자 방식 + setter 혼합
```
Movie avater = new Movie(new PercentDiscountPolicy());
...
avater.setDiscountPolicy(new AmountDiscountPolicy());  
}
```
- 시스템의 상태를 안정적으로 유지하면서도 유연성을 향상시킬 수 있기 때문에 의존성 해결을 위해 가장 선호되는 방법

- 메서드 인자 방식
```
public class Movie {
    public Money calculateMovieFee(Screening screening, DiscountPolicy discountPolicy) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```
- 메서드 인자 방식은 협력 대상에 대해 지속적으로 의존 관계를 맺을 필요 없이 메서드가 실행되는 동안만 일시적으로 의존 관계가 존재해도 무방하거나, 메서드가 실행될 때마다 의존 대상이 매번 달라져야 하는 경우 유용


# 02 유연한 설계
## 의존성과 결합도
- 객체지향 패러다임의 근간은 협력!
- 객체들이 협력하기 위해서는 서로의 존재와 수행 가능한 책임을 알아야 함
  - 이런 지식들이 객체 사이의 의존성을 발생 (과하면 문제가 됨)

```
public class Movie {
    private PercentDiscountPolicy percentDiscountPolicy;

    public Movie(String title, Duration runningTime, Money fee, PercentDiscountPolicy percentDiscountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.percentDiscountPolicy = percentDiscountPolicy;
    }

    public Money calculateMovieFee(Screening screening) {
        return fee.minus(percentDiscountPolicy.calculateDiscountAmount(screening));
    }
}
```
- Movie가 PercentDiscountPolicy 에 의존한다는 것을 코드를 통해 명시적으로 드러냄
- 이 의존성이 객체 사이의 협력을 가능하게 만들기 때문에 존재 자체는 바람직
  - 문제는 의존성의 존재가 아니라 의존성의 정도!
  - 
- PercentDiscountPolicy라는 구체 클래스에 의존하게 되서 다른 종류의 할인 정책이 필요한 문맥에서 Movie를 재사용할 수 없게 됨
  - 해결방법은 의존성을 바람직하게 만드는 것
  - 추상 클래스인 DiscountPolicy는 calculateDiscountAmount 메시지를 이해할 수 있는 타입을 정의함으로써 이 문제를 해결
- 위의 예제는 의존성은 협력을 위해 반드시 필요하지만 바람직하지 못한 의존성이 문제일 뿐이다.

- 바람직한 의존성이란?
  - 바람직한 의존성은 재사용성과 관련이 있음
  - 어떤 의존성이 다양한 환경에서 클래스를 재사용할 수 없도록 제한한다면 그 의존성은 바람직하지 못함
  - 어떤 의존성이 다양한 환경에서 재사용할 수 있다면 그 의존성은 바람직함
  - 정리하면 컨텍스트에 독립적인 의존성은 바람직한 의존성이고 특정한 컨텍스트에 강하게 결합된 의존성은 바람직하지 않은 의존성

- 특정한 컨텍스트에 강하게 의존하는 클래스를 다른 컨텍스트에 재사용할 수 있는 유일한 방법은 구현을 변경하는 것뿐
  - 결국 이것은 바람직하지 못한 의존성을 바람직하지 못한 또 다른 의존성으로 대체한 것일 뿐

- 결합도 (의존성에 대한 좀 더 세련된 용어)
  - 두 요소 사이에 존재하는 의존성이 바람직할 때 두 요소가 느슨한 결합도 또는 약한 결합도를 가진다고 말함
  - 두 요소 사이의 의존성이 바람직하지 못할 때 단단한 결합도 또는 강한 결합도를 가진다고 말함

### 의존성과 결합도
- 의존성은 두 요소 사이의 관계 유무를 설명
  - 의존성이 존재한다 / 의존성이 존재하지 않는다 라고 표현
- 결합도는 두 요소 사이에 존재하는 의존성의 정도를 상대적으로 표현
  - 결합도가 강하다 / 결합도가 느슨하다 라고 표현

 
## 지식이 결합을 낳는다
- 결합도의 정도는 한 요소가 자신의 의존하고 있는 다른 요소에 대해 알고 있는 정보의 양으로 결정
  - 한 요소가 다른 요소에 대해 더 많은 정보를 알고 있을 수록 두 요소는 강하게 결합
  - 한 요소가 다른 요소에 대해 더 적은 정보를 알고 있을 수록 두 요소는 약하게 결합

- 서로에 대해 알고 있는 지식의 양이 결합도를 결정
  - Movie가 구현 클래스 PercentDiscountPolicy에 의존하는 경우
    - 비율 할인 정책에 따라 할인 요금을 계산한다는 사실을 알게됨
  - Movie가 추상 클래스 DiscountPolicy에 의존하는 경우 (지식의 양이 적기 때문에 결합도가 느슨해짐)
    - 구체적인 계산 방법은 모르고, 그저 할인 요금을 계산한다는 사실만 알고 있음

- 더 많이 알수록 더 많이 결합되고 더 많이 알고 있다는 것은 더 적은 컨텍스트에 재사용 가능하다는 것을 의미
  - 이 목적을 달성할 수 있는 가장 효과적인 방법은 추상화

## 추상화에 의존하라
- 추상화란 어떤 양상, 세부사항, 구조를 좀 더 명확하게 이해하기 위해 특정 절차나 물체를 의도적으로 생략하거나 감춤으로써 복잡도를 극복하는 방법
- 추상화를 사용하면 현재 다루고 있는 문제를 해결하는 데 불필요한 정보를 감출 수 있음
  - 지식의 양을 줄일 수 있기 때문에 결합도를 느슨하게 유지

- 추상화와 결합도 관점에서 의존 대상 구분 (아래로 갈수록 결합도 느슨)
  - 구체 클래스의 의존성
  - 추상 클래스의 의존성
  - 인터페이스 의존성

- 추상 클래스는 구체 클래스에 비해 결합도가 더 낮음 
  - 하지만 클라이언트는 여전히 협력하는 대상이 속한 클래스 상속 계층이 무엇인지에 대해서 알고 있어야 함 
- 인터페이스에 의존하면 상송 계층을 모르더라도 협력이 가능해짐
  - 인터페이스 의존성은 협력하는 객체가 어떤 메시지를 수신할 수 있는지에 대한 지식만을 남기기 때문에 추상 클래스 의존성보다 결합도가 낮음

- 실행 컨텍스트에 대해 알아야 하는 정보를 줄일수록 결합도가 낮아짐
- 의존하는 대상이 더 추상적일수록 결합도는 더 낮아짐

## 명시적인 의존성
```
public class Movie {
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee) {
        this.discountPolicy = new AmountDiscountPolicy();
    }
}
```
- discountPolicy는 DiscountPolicy 타입으로 선언돼 있지만 생성자에서 구체 클래스인 AmountDiscountPolicy의 인스턴스를 직접 생성해서 대입하고 있음
- Movie는 추상 클래스 DiscountPolicy 뿐만 아니라 구체 클래스 AmountDiscountPolicy 에도 의존

![8 7](https://user-images.githubusercontent.com/7076334/111338803-93676d00-86ba-11eb-805e-940d4a804ab2.png)

- 결합도를 느슨하게 만들기 위해서는 인스턴스 변수의 타입을 추상 클래스나 인터페이스로 선언하는 것만으로는 부족
- 클래스 안에서 구체 클래스에 대한 의존성을 제거해야 됨
  - 앞에서 얘기한 생성자, setter 메서드, 메서드 인자를 사용하는 세가지 방법 이용

```
public class Movie {
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
```
- 인스턴스를 생성자의 인자로 선언하는 형태로 변경
- 인스턴스 변수의 타입과 생성자의 인자 타입 모두 추상 클래스로 선언되어 있음

- 의존성의 대상을 생성자의 인자로 전달받는 방법과 생성자 안에서 직접 생성하는 방법의 가장 큰 차이점은 퍼블릭 인터페이스를 통해 할인 정책을 설정할 수 있는 방법을 제공하는지 여부
  - 명시적인 의존성 : 생성자나 setter, 메서드 인자를 통해 명시적으로 퍼블릭 인터페이스에 노출되는 경우
  - 숨겨진 의존성 : 의존성이 퍼블릭 인터페이스에 표현되지 않는 경우

- 의존성이 명시적이지 않으면 의존성 파악을 위해 내부 구현을 직접 살펴봐야 함
  - 더 커다란 문제는 다른 컨텍스트에서 재사용하기 위해 내부 구현을 직접 변경해야 함

- 의존성은 명시적으로 표현돼야 함
  - 의존성을 구현 내부에 숨겨두지 마라
  - 유연하고 재사용 가능한 설계란 퍼블릭 인터페이스를 통해 의존성이 명시적으로 드러나는 설계
  - 명시적인 의존성을 사용해야만 퍼블릭 인터페이스를 통해 컴파일타임 의존성을 적절한 런타임 의존성으로 교체가능

## new는 해롭다


## 가끔은 생성해도 무방하다


## 표준 



