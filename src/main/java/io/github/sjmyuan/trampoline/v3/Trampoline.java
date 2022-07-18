package io.github.sjmyuan.trampoline.v3;

import java.util.function.Function;;

public interface Trampoline<T> {

    String Function = null;

    public static <S> S run(Trampoline<S> trampoline) {

        if (trampoline instanceof Done) {
            return ((Done<S>) trampoline).getResult();
        } else if (trampoline instanceof More) {
            return run(((More<S>) trampoline).getThunk().get());
        } else {

            Continuation<Object, S> continuation = (Continuation<Object, S>) trampoline;

            Trampoline<Object> lastResult = continuation.getLastResult();
            Function<Object, Trampoline<S>> continuationFunc = continuation.getContinuation();

            if (lastResult instanceof Continuation) {

                Continuation<Object, Object> lastResultContinuation =
                        (Continuation<Object, Object>) lastResult;

                return run(new Continuation<Object, S>(lastResultContinuation.getLastResult(),
                        x -> new Continuation<Object, S>(
                                lastResultContinuation.getContinuation().apply(x),
                                continuationFunc)));
            } else if (lastResult instanceof More) {

                return run(new Continuation<Object, S>(((More<Object>) lastResult).getThunk().get(),
                        continuationFunc));
            } else {
                return run(continuationFunc.apply(((Done<Object>) lastResult).getResult()));
            }
        }
    }

    public static <S> S runOptimization(Trampoline<S> trampoline) {

        Trampoline<S> trampolineParam = trampoline;

        Long index = 1l;

        while (true) {

            System.out.println("loop:" + index);
            index++;

            if (trampolineParam instanceof Done) {
                return ((Done<S>) trampolineParam).getResult();
            } else if (trampolineParam instanceof More) {
                trampolineParam = ((More<S>) trampoline).getThunk().get();
            } else {

                Continuation<Object, S> continuation = (Continuation<Object, S>) trampolineParam;

                Trampoline<Object> lastResult = continuation.getLastResult();
                Function<Object, Trampoline<S>> continuationFunc = continuation.getContinuation();

                if (lastResult instanceof Continuation) {

                    Continuation<Object, Object> lastResultContinuation =
                            (Continuation<Object, Object>) lastResult;

                    trampolineParam =
                            new Continuation<Object, S>(lastResultContinuation.getLastResult(),
                                    x -> new Continuation<Object, S>(
                                            lastResultContinuation.getContinuation().apply(x),
                                            continuationFunc));
                } else if (lastResult instanceof More) {

                    trampolineParam = new Continuation<Object, S>(
                            ((More<Object>) lastResult).getThunk().get(), continuationFunc);
                } else {
                    trampolineParam =
                            continuationFunc.apply(((Done<Object>) lastResult).getResult());
                }
            }
        }
    }
}
