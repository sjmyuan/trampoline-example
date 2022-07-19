package io.github.sjmyuan.trampoline.v4;

import java.util.function.Function;

public class FlatMap<A, B> implements Trampoline<B> {

    private Trampoline<A> lastResult;

    private Function<A, Trampoline<B>> continuation;

    protected FlatMap(Trampoline<A> lastResult, Function<A, Trampoline<B>> continuation) {

        this.lastResult = lastResult;
        this.continuation = continuation;
    }

    public Trampoline<A> getLastResult() {
        return lastResult;
    }

    public Function<A, Trampoline<B>> getContinuation() {
        return continuation;
    }

    @Override
    public <C> Trampoline<C> flatMap(Function<B, Trampoline<C>> nextContinuation) {
        return new FlatMap<A, C>(lastResult,
                x -> Trampoline.suspend(() -> continuation.apply(x)).flatMap(nextContinuation));
    }
}
