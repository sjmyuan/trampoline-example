package io.github.sjmyuan.trampoline.v1;

import java.util.function.Supplier;

public class More implements Trampoline {

    private Supplier<Trampoline> thunk;

    public More(Supplier<Trampoline> thunk) {

        this.thunk = thunk;

    }

    public Supplier<Trampoline> getK() {
        return thunk;
    }

    @Override
    public boolean needToResume() {
        return true;
    }

    @Override
    public Long getResult() {
        return null;
    }

    @Override
    public Trampoline resume() {
        return thunk.get();
    }

}
