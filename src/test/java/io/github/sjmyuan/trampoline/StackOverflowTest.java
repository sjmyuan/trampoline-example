package io.github.sjmyuan.trampoline;

import org.junit.Test;

public class StackOverflowTest {

    private Long factorial(Long n) {
        if (n == 1) {
            return 1l;
        }
        return n * factorial(n - 1);
    }

    @Test
    public void shouldRaiseStackOverflow() {
        factorial(10000l);
    }

}
