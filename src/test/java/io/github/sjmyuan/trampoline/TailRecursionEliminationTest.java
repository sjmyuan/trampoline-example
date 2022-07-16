package io.github.sjmyuan.trampoline;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TailRecursionEliminationTest {

public Long fibonacci(Long n, Long a, Long b) {
    if (n == 0) {
        return a;
    }
    if (n == 1) {
        return b;
    }
    return fibonacci(n - 1, b, a + b); // tail recursion
}

public Long fibonacciOptimization(Long n, Long a, Long b) {
    Long nParam = n;
    Long aParam = a;
    Long bParam = b;
    while (true) {
        if (nParam == 0) {
            return aParam;
        }
        if (nParam == 1) {
            return bParam;
        }
        nParam = nParam - 1;
        Long aCurrent = aParam;
        aParam = bParam;
        bParam = aCurrent + bParam;
    }
}

    @Test
    public void optimizationShouldKeepLogic() {
        for (Long i = 0l; i < 100l; i++) {

            assertThat(fibonacciOptimization(i, 0l, 1l)).isEqualTo(fibonacci(i, 0l, 1l));

        }
    }

}
