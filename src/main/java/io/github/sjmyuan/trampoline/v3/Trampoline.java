package io.github.sjmyuan.trampoline.v3;

public interface Trampoline<T> {

    public boolean needToResume();

    public T getResult();

    public Trampoline<T> resume();

    public static <S> S run(Trampoline<S> trampoline) {

        if (!trampoline.needToResume()) {
            return trampoline.getResult();
        }

        return run(trampoline.resume());
    }

    public static <S> S runOptimization(Trampoline<S> trampoline) {

        Trampoline<S> trampolineParam = trampoline;

        while (true) {

            if (!trampolineParam.needToResume()) {
                return trampolineParam.getResult();
            }

            trampolineParam = trampolineParam.resume();
        }
    }
}
