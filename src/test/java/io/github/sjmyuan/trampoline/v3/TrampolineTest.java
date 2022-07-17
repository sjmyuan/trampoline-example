package io.github.sjmyuan.trampoline.v3;

import org.junit.Test;

public class TrampolineTest {

    private Trampoline<Long> factorialTrampoline(Long n) {
        if (n == 1) {
            return new Done<Long>(1l);
        }
        return new Continuation<Long, Long>(factorialTrampoline(n - 1), x -> new Done<Long>(n * x));
    }

    @Test
    public void trampolineShouldNotThrowStackOverflowError() {

        Trampoline<Long> trampoline = factorialTrampoline(10000l);

        Trampoline.runOptimization(trampoline);

    }
}
