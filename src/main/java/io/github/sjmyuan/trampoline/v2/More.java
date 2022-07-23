package io.github.sjmyuan.trampoline.v2;

import java.util.function.Supplier;

public class More<T> implements Trampoline<T> {

    private Supplier<Trampoline<T>> thunk;

    public More(Supplier<Trampoline<T>> thunk) {

        this.thunk = thunk;

    }

    @Override
    public boolean needToResume() {
        return true;
    }

    @Override
    public T getResult() {
        return null;
    }

    @Override
    public Trampoline<T> resume() {
        return thunk.get();
    }

}
