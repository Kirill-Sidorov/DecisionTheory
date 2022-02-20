package sidorov;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class Runner {
    private Runner() { }
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        new App();
    }
}
