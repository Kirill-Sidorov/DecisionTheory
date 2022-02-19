package sidorov;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class App {
    private App() { }
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        new MainFrame();
    }
}
