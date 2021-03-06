package movie.step02.factory;

import money.Money;
import movie.step02.Movie;

/**
 * 생성과 사용을 분리
 */
public class Client {
    private Factory factory;

    public Client(Factory factory) {
        this.factory = factory;
    }

    public Money getAvatarFee() {
        Movie avatar = factory.createAvatarMovie();
        return avatar.getFee();
    }
}