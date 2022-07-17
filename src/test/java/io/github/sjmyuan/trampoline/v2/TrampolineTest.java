package io.github.sjmyuan.trampoline.v2;

import java.util.function.Function;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrampolineTest {

    private Trampoline<Boolean> isEvenCPS(Long n,
            Function<Boolean, Trampoline<Boolean>> continuation) {
        if (n == 0)
            return new More<Boolean>(() -> continuation.apply(true));
        return new More<Boolean>(() -> isOddCPS(n - 1,
                (result) -> new More<Boolean>(() -> continuation.apply(result))));
    }

    private Trampoline<Boolean> isOddCPS(Long n,
            Function<Boolean, Trampoline<Boolean>> continuation) {

        if (n == 0)
            return new More<Boolean>(() -> continuation.apply(false));
        return new More<Boolean>(() -> isEvenCPS(n - 1,
                (result) -> new More<Boolean>(() -> continuation.apply(result))));

    }

    @Test
    public void genericTrampolineShouldWork() {
        Trampoline<Boolean> trampoline = isEvenCPS(10000l, (x) -> new Done<Boolean>(x));
        assertThat(Trampoline.runOptimization(trampoline)).isTrue();
    }

}
