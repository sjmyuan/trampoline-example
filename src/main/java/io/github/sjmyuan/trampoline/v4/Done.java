package io.github.sjmyuan.trampoline.v4;

import java.util.function.Function;

public class Done<T> implements Trampoline<T> {

    private T result;

    public Done(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    @Override
    public <B> Trampoline<B> flatMap(Function<T, Trampoline<B>> continuation) {
        return new FlatMap<T, B>(this, continuation);
    }
}
