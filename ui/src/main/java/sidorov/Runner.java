package sidorov;

import com.formdev.flatlaf.FlatIntelliJLaf;
import sidorov.app.AppController;

public class Runner {
    private Runner() {
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        new AppController();
    }
}
