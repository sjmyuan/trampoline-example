package io.github.sjmyuan.trampoline;

import java.util.function.Function;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CPSTest {

    private Long factorial(Long n, Function<Long, Long> continuation) {
        if (n == 1) {
            return continuation.apply(1l);
        }
        return factorial(n - 1, (Long result) -> continuation.apply(n * result));
    }

    private Long factorialOptimization(Long n, Function<Long, Long> continuation) {
        Long nParam = n;
        Function<Long, Long> continuationParam = continuation;
        while (true) {
            if (nParam == 1) {
                return continuationParam.apply(1l);
            }
            Long nCurrent = nParam;
            nParam = nParam - 1;

            final Function<Long, Long> currentContinuation = continuationParam;
            continuationParam = (Long result) -> currentContinuation.apply(nCurrent * result);
        }
    }

    @Test
    public void cpsShouldKeepLogic() {

        assertThat(factorial(4l, (x) -> x)).isEqualTo(24);

    }

    @Test
    public void cpsMayAlsoThrowStackOverflowError() {
        factorial(10000l, (x) -> x);
    }

    @Test
    public void optimizationShouldKeepLogic() {
        for (Long n = 1l; n < 10; n++) {
            assertThat(factorialOptimization(n, (x) -> x)).isEqualTo(factorial(n, (x) -> x));
        }
    }

    @Test
    public void cpsOptimizationMayAlsoThrowStackOverflowError() {

        factorialOptimization(10000l, (x) -> x);
    }
}
