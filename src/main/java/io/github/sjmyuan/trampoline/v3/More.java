package io.github.sjmyuan.trampoline.v3;

import java.util.function.Supplier;

public class More<T> implements Trampoline<T> {

    private Supplier<Trampoline<T>> thunk;

    public More(Supplier<Trampoline<T>> thunk) {
        this.thunk = thunk;
    }

    public Supplier<Trampoline<T>> getThunk() {
        return thunk;
    }
}
