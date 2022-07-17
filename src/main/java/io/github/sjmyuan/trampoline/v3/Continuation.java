package io.github.sjmyuan.trampoline.v3;

import java.util.function.Function;

public class Continuation<A, B> implements Trampoline<B> {

    private Trampoline<A> lastResult;

    private Function<A, Trampoline<B>> continuation;

    public Continuation(Trampoline<A> lastResult, Function<A, Trampoline<B>> continuation) {

        this.lastResult = lastResult;
        this.continuation = continuation;
    }

    @Override
    public boolean needToResume() {
        return true;
    }

    @Override
    public B getResult() {
        return null;
    }

    @Override
    public Trampoline<B> resume() {
        if (!lastResult.needToResume()) {
            return continuation.apply(lastResult.getResult());
        }

        Continuation<Object, A> temp = (Continuation<Object, A>) lastResult;
        return new Continuation(temp.lastResult,
                x -> new Continuation(temp.continuation.apply(x), this.continuation));
    }

}
