package io.github.sjmyuan.trampoline.v0;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

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

    public Supplier<Void> factorial(Long n, Consumer<Long> continuation) {
        System.out.println(n);
        if (n == 1) {
            Supplier<Void> thunk = () -> {
                continuation.accept(1l);
                return null;
            };
            return thunk;
        }
        Supplier<Void> thunk = () -> {
            Supplier<Void> thunk1 =
                    factorial(n - 1, (Long result) -> continuation.accept(n * result));
            thunk1.get();
            return null;
        };
        return thunk;
    }

    @Test
    public void thunkShouldKeepLogic() {

        Supplier<Void> thunk = factorial(4l, (x) -> assertThat(x).isEqualTo(24));
        thunk.get();
    }

    @Test
    public void thunkThrowStackOverflowError() {

        Supplier<Void> thunk = factorial(10000l, (x) -> {
        });
        thunk.get();
    }
}
