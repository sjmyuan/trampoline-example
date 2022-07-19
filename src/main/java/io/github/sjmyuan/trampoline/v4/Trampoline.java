package io.github.sjmyuan.trampoline.v4;

import java.util.function.Function;
import java.util.function.Supplier;;

public interface Trampoline<T> {

    public static <A> Trampoline<A> of(A v) {
        return new Done<A>(v);
    }

    public static <A> Trampoline<A> suspend(Supplier<Trampoline<A>> thunk) {
        return new More<A>(thunk);
    }

    public static <S> S run(Trampoline<S> trampoline) {

        if (trampoline instanceof Done) {
            return ((Done<S>) trampoline).getResult();
        } else if (trampoline instanceof More) {
            return run(((More<S>) trampoline).getThunk().get());
        } else {

            FlatMap<Object, S> continuation = (FlatMap<Object, S>) trampoline;

            Trampoline<Object> lastResult = continuation.getLastResult();
            Function<Object, Trampoline<S>> continuationFunc = continuation.getContinuation();

            if (lastResult instanceof FlatMap) {

                FlatMap<Object, Object> lastResultContinuation =
                        (FlatMap<Object, Object>) lastResult;

                return run(
                        lastResultContinuation.getLastResult().flatMap(x -> lastResultContinuation
                                .getContinuation().apply(x).flatMap(continuationFunc)));
            } else if (lastResult instanceof More) {
                return run(((More<Object>) lastResult).getThunk().get().flatMap(continuationFunc));
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

                FlatMap<Object, S> continuation = (FlatMap<Object, S>) trampolineParam;

                Trampoline<Object> lastResult = continuation.getLastResult();
                Function<Object, Trampoline<S>> continuationFunc = continuation.getContinuation();

                if (lastResult instanceof FlatMap) {

                    FlatMap<Object, Object> lastResultContinuation =
                            (FlatMap<Object, Object>) lastResult;

                    trampolineParam = lastResultContinuation.getLastResult()
                            .flatMap(x -> lastResultContinuation.getContinuation().apply(x)
                                    .flatMap(continuationFunc));
                } else if (lastResult instanceof More) {
                    trampolineParam =
                            ((More<Object>) lastResult).getThunk().get().flatMap(continuationFunc);
                } else {
                    trampolineParam =
                            continuationFunc.apply(((Done<Object>) lastResult).getResult());
                }
            }
        }
    }

    <B> Trampoline<B> flatMap(Function<T, Trampoline<B>> continuation);
}
