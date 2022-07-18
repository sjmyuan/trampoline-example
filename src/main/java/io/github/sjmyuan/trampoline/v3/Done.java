package io.github.sjmyuan.trampoline.v3;

public class Done<T> implements Trampoline<T> {

    private T result;

    public Done(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
