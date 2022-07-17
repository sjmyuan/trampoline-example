package io.github.sjmyuan.trampoline.v1;

import java.util.function.Function;
import org.junit.Test;

public class TrampolineTest {

    private Trampoline factorialTrampoline(Long n, Function<Long, Trampoline> continuation) {
        System.out.println(n);
        if (n == 1) {
            return new More(() -> continuation.apply(1l));
        }

        return new More(() -> factorialTrampoline(n - 1,
                (Long result) -> new More(() -> continuation.apply(n * result))));

    }

    private Trampoline factorialTrampolineWrongImplementationV1(Long n,
            Function<Long, Trampoline> continuation) {
        System.out.println(n);
        if (n == 1) {
            return new More(() -> continuation.apply(1l));
        }

        return factorialTrampolineWrongImplementationV1(n - 1,
                (Long result) -> continuation.apply(n * result));

    }

    private Trampoline factorialTrampolineWrongImplementationV2(Long n,
            Function<Long, Trampoline> continuation) {
        System.out.println(n);
        if (n == 1) {
            return new More(() -> continuation.apply(1l));
        }

        return new More(() -> factorialTrampolineWrongImplementationV2(n - 1,
                (Long result) -> continuation.apply(n * result)));

    }

    @Test
    public void trampolineShouldNotThrowStackOverflowError() {

        Trampoline trampoline = factorialTrampoline(10000l, x -> new Done(x));

        Trampoline.runOptimization(trampoline);

    }

    @Test
    public void trampolineWrongImplementationV1WillStillThrowStackOverflowError() {

        Trampoline trampoline = factorialTrampolineWrongImplementationV1(10000l, x -> new Done(x));

        Trampoline.runOptimization(trampoline);
    }

    @Test
    public void trampolineWrongImplementationV2WillStillThrowStackOverflowError() {

        Trampoline trampoline = factorialTrampolineWrongImplementationV2(10000l, x -> new Done(x));

        Trampoline.runOptimization(trampoline);
    }
}
