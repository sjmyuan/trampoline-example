package io.github.sjmyuan.trampoline.v4;

import java.util.function.Function;
import java.util.function.Supplier;

public class More<T> implements Trampoline<T> {

    private Supplier<Trampoline<T>> thunk;

    public More(Supplier<Trampoline<T>> thunk) {
        this.thunk = thunk;
    }

    public Supplier<Trampoline<T>> getThunk() {
        return thunk;
    }

    @Override
    public <B> Trampoline<B> flatMap(Function<T, Trampoline<B>> continuation) {
        return new FlatMap<T, B>(this, continuation);
    }
}
