package sidorov.common.result;

public class Result {
    public final Status status;
    public final String text;

    public Result(Status status, String text) {
        this.status = status;
        this.text = text;
    }
}
