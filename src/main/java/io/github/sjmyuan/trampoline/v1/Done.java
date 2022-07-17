package io.github.sjmyuan.trampoline.v1;

public class Done implements Trampoline {

    private Long result;

    public Done(Long result) {
        this.result = result;
    }

    @Override
    public boolean needToResume() {
        return false;
    }

    @Override
    public Long getResult() {
        return result;
    }

    @Override
    public Trampoline resume() {
        return this;
    }
}
