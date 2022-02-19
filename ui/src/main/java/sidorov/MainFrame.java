package sidorov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private final JTextArea taskInfo;
    private final JTextArea resultInfo;

    public MainFrame() {

        setLayout(null);

        taskInfo = new JTextArea("Многострочное поле", 8, 10);
        taskInfo.setFont(new Font("Dialog", Font.PLAIN, 14));
        taskInfo.setTabSize(10);
        taskInfo.setLineWrap(true);
        taskInfo.setWrapStyleWord(true);

        resultInfo = new JTextArea(15, 10);
        resultInfo.setText("Второе многострочное поле");
        // Параметры переноса слов
        resultInfo.setLineWrap(true);
        resultInfo.setWrapStyleWord(true);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(taskInfo), new JScrollPane(resultInfo));
        splitPane.setBounds(45, 10, 700, 400);
        splitPane.setResizeWeight(0.5);

        JButton button = new JButton("button");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                resultInfo.setText("");
            }
        });
        button.setBounds(100, 500, 100, 50);
        button.setVisible(true);

        add(splitPane);
        add(button);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ТПР");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);
        setVisible(true);
    }
}
