package io.github.sjmyuan.trampoline.v3;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrampolineTest {

    private Trampoline<Long> factorialTrampoline(Long n) {
        if (n == 1) {
            return new Done<Long>(1l);
        }
        return new Continuation<Long, Long>(new More<Long>(() -> factorialTrampoline(n - 1)),
                x -> new Done<Long>(n * x));
    }

    @Test
    public void trampolineShouldNotThrowStackOverflowError() {

        Trampoline<Long> trampoline = factorialTrampoline(10000l);

        Trampoline.runOptimization(trampoline);

    }

    @Test
    public void tooManyLeftAssociateContinuationWillNotThrowError() {
        Trampoline<Long> trampoline = new Done<Long>(1l);
        for (int i = 1; i < 50000; i++) {
            trampoline = new Continuation<Long, Long>(trampoline, x -> new Done<Long>(x));
        }

        assertThat(Trampoline.runOptimization(trampoline)).isEqualTo(1);
    }
}
