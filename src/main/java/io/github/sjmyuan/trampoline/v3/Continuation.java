package io.github.sjmyuan.trampoline.v3;

import java.util.function.Function;

public class Continuation<A, B> implements Trampoline<B> {

    private Trampoline<A> lastResult;

    private Function<A, Trampoline<B>> continuation;

    public Continuation(Trampoline<A> lastResult, Function<A, Trampoline<B>> continuation) {

        this.lastResult = lastResult;
        this.continuation = continuation;
    }

    public Trampoline<A> getLastResult() {
        return lastResult;
    }

    public Function<A, Trampoline<B>> getContinuation() {
        return continuation;
    }
}
