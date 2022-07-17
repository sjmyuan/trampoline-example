package io.github.sjmyuan.trampoline.v3;

public class Done<T> implements Trampoline<T> {

    private T result;

    public Done(T result) {
        this.result = result;
    }

    @Override
    public boolean needToResume() {
        return false;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public Trampoline<T> resume() {
        return this;
    }
}
