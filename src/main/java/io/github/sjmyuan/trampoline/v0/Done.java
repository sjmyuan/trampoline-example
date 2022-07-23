package io.github.sjmyuan.trampoline.v0;

public class Done implements Trampoline {

    private Long result;

    public Done(Long result) {
        this.result = result;
    }

    public Long getResult() {
        return result;
    }
}
