package io.github.sjmyuan.trampoline.v4;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrampolineTest {

    private Long factorialOriginal(Long n) {
        if (n == 1) {
            return 1l;
        }
        return n * factorialOriginal(n - 1);
    }

    private Trampoline<Long> factorialTrampoline(Long n) {
        if (n == 1) {
            return Trampoline.of(1l);
        }
        return Trampoline.suspend(() -> factorialTrampoline(n - 1))
                .flatMap(x -> Trampoline.of(n * x));
    }

    @Test
    public void trampolineShouldBeCorrect() {

        for (Long i = 1l; i <= 100; i++) {

            Trampoline<Long> trampoline = factorialTrampoline(i);

            assertThat(Trampoline.runOptimization(trampoline)).isEqualTo(factorialOriginal(i));
        }

    }

    @Test
    public void trampolineShouldNotThrowStackOverflowError() {

        Trampoline<Long> trampoline = factorialTrampoline(10000l);

        Trampoline.runOptimization(trampoline);

    }

    @Test
    public void tooManyLeftAssociateContinuationWillNotThrowError() {
        Trampoline<Long> trampoline = Trampoline.of(1l);
        for (int i = 1; i < 50000; i++) {
            trampoline = trampoline.flatMap(x -> Trampoline.of(x));
        }

        assertThat(Trampoline.runOptimization(trampoline)).isEqualTo(1);
    }
}
