package io.github.sjmyuan.trampoline.v0;

import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.Test;

public class TrampolineTest {

    // can not compile
    // private Trampoline factorialTrampoline(Long n, Consumer<Long> continuation) {
    // System.out.println(n);
    // if (n == 1) {
    // return new More(() -> continuation.accept(1l));
    // }

    // return new More(() -> factorialTrampoline(n - 1,
    // (Long result) -> new More(() -> continuation.accept(n * result))));

    // }
}
