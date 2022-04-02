package sidorov.task;

import sidorov.common.Result;

@FunctionalInterface
public interface ResultAction {
    void processResult(Result result);
}
