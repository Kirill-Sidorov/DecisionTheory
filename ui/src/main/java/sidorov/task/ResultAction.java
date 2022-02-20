package sidorov.task;

import sidorov.common.result.Result;

@FunctionalInterface
public interface ResultAction {
    void processResult(Result result);
}
