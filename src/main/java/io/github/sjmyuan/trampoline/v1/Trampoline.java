package io.github.sjmyuan.trampoline.v1;

public interface Trampoline {

    public boolean needToResume();

    public Long getResult();

    public Trampoline resume();

    public static Long run(Trampoline trampoline) {

        if (!trampoline.needToResume()) {
            return trampoline.getResult();
        }

        return run(trampoline.resume());
    }

    public static Long runOptimization(Trampoline trampoline) {

        Trampoline trampolineParam = trampoline;

        while (true) {

            if (!trampolineParam.needToResume()) {
                return trampolineParam.getResult();
            }

            trampolineParam = trampolineParam.resume();
        }
    }
}
