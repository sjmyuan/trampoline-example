package io.github.sjmyuan.trampoline;

import java.util.function.Consumer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CPSTest {

    private void factorial(Long n, Consumer<Long> continuation) {
        if (n == 1) {
            continuation.accept(1l);
            return;
        }
        factorial(n - 1, (Long result) -> continuation.accept(n * result));
    }

    private void factorialOptimization(Long n, Consumer<Long> continuation) {
        Long nParam = n;
        Consumer<Long> continuationParam = continuation;
        while (true) {
            if (nParam == 1) {
                continuationParam.accept(1l);
                return;
            }
            Long nCurrent = nParam;
            nParam = nParam - 1;

            final Consumer<Long> currentContinuation = continuationParam;
            continuationParam = (Long result) -> currentContinuation.accept(nCurrent * result);
        }
    }

    private boolean isEven(Long n) {

        if (n == 0)
            return true;
        return isOdd(n - 1);
    }

    private boolean isOdd(Long n) {

        if (n == 0)
            return false;
        return isEven(n - 1);

    }

    private void isEvenCPS(Long n, Consumer<Boolean> continuation) {
        if (n == 0) {
            continuation.accept(true);
            return;
        }
        isOddCPS(n - 1, (result) -> continuation.accept(result));
    }

    private void isOddCPS(Long n, Consumer<Boolean> continuation) {

        if (n == 0) {
            continuation.accept(false);
            return;
        }
        isEvenCPS(n - 1, (result) -> continuation.accept(result));
    }

    @Test
    public void cpsShouldKeepLogic() {

        factorial(4l, (x) -> assertThat(x).isEqualTo(24));
    }

    @Test
    public void cpsMayAlsoThrowStackOverflowError() {
        factorial(10000l, (x) -> {
        });
    }

    @Test
    public void optimizationShouldKeepLogic() {
        for (Long n = 1l; n < 10; n++) {
            final Long nAlias = n;
            factorialOptimization(n, (x) -> factorial(nAlias, y -> assertThat(x).isEqualTo(y)));
        }
    }

    @Test
    public void cpsOptimizationMayAlsoThrowStackOverflowError() {

        factorialOptimization(10000l, (x) -> {
        });
    }

    @Test
    public void isOddWillThrowStackOverflowError() {
        isOdd(20000l);
    }

    @Test
    public void isOddCPSShouldKeepLogic() {
        for (Long n = 1l; n < 100; n++) {
            final Long nAlias = n;
            isOddCPS(n, x -> assertThat(x).isEqualTo(nAlias % 2 == 1));
        }
    }
}
