package io.github.sjmyuan.trampoline.v0;

public interface Trampoline {

public static Long run(Trampoline trampoline) {

    if (trampoline instanceof Done) {
        return ((Done) trampoline).getResult();
    }

    return run(((More) trampoline).getThunk().get());
}

    public static Long runOptimization(Trampoline trampoline) {

        Trampoline trampolineParam = trampoline;

        while (true) {

            if (trampolineParam instanceof Done) {
                return ((Done) trampolineParam).getResult();
            }

            trampolineParam = ((More) trampolineParam).getThunk().get();
        }
    }
}
