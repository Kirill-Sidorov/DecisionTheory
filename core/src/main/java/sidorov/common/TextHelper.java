package sidorov.common;

import java.util.List;

public class TextHelper {
    private TextHelper() {
    }

    public static String getSaddlePointsText(List<Element> saddlePoints) {
        StringBuilder result = new StringBuilder();
        result.append("Z = X * Y = { ");
        for (Element point : saddlePoints) {
            result.append(String.format("<%d;%d> ", point.i + 1, point.j + 1));
        }
        result.append("}\n");
        return result.toString();
    }
}
