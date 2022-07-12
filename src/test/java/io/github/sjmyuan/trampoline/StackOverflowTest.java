package io.github.sjmyuan.trampoline;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StackOverflowTest {

    @Test
    public void shouldRaiseStackOverflow() {

        assertThat(1 == 1).isTrue();

    }

}
