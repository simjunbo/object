package movie.pricing;

import money.Money;
import movie.DiscountPolicy;
import movie.Screening;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 중복 적용이 가능한 할인 정책
 */
public class OverlappedDiscountPolicy extends DiscountPolicy {
    private List<DiscountPolicy> discountPolicies = new ArrayList<>();

    public OverlappedDiscountPolicy(DiscountPolicy... discountPolicies) {
        this.discountPolicies = Arrays.asList(discountPolicies);
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        Money result = Money.ZERO;
        for (DiscountPolicy each : discountPolicies) {
            result = result.plus(each.calculateDiscountAmount(screening));
        }
        return result;
    }
}
