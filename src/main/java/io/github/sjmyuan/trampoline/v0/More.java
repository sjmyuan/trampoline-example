package io.github.sjmyuan.trampoline.v0;

import java.util.function.Supplier;

public class More implements Trampoline {

    private Supplier<Trampoline> thunk;

    public More(Supplier<Trampoline> thunk) {

        this.thunk = thunk;
    }

    public Supplier<Trampoline> getThunk() {
        return thunk;
    }
}
