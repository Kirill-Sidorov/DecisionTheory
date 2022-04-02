package sidorov.common;

import sidorov.lab4.Function;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private final Status status;
    private final String text;

    private List<Function> functions;

    public Result(Status status, String text) {
        this.status = status;
        this.text = text;
    }

    public Result(List<Function> functions) {
        this(Status.SUCCESS, "Данные для графика получены успешно");
        this.functions = functions;
    }

    public Status status() {
        return status;
    }

    public String text() {
        return text;
    }

    public List<Function> functions() {
        if (functions == null) {
            return new ArrayList<>();
        } else {
            return functions;
        }
    }
}
