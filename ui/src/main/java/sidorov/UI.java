package sidorov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UI extends JFrame {

    private final JTextArea taskInfo;
    private final JTextArea resultInfo;
    private final JButton uploadDataButton;
    private final JButton solveTaskButton;

    public UI(ActionListener radioButtonsActionListener,
              ActionListener uploadDataButtonActionListener,
              ActionListener solveTaskButtonActionListener) {

        setLayout(null);

        taskInfo = new JTextArea();
        taskInfo.setLineWrap(true);
        taskInfo.setWrapStyleWord(true);
        taskInfo.setEditable(false);
        taskInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        resultInfo = new JTextArea();
        resultInfo.setLineWrap(true);
        resultInfo.setWrapStyleWord(true);
        resultInfo.setEditable(false);
        resultInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(taskInfo), new JScrollPane(resultInfo));
        splitPane.setBounds(45, 30, 700, 350);
        splitPane.setResizeWeight(0.5);

        uploadDataButton = new JButton("Загрузить данные");
        uploadDataButton.setBounds(50, 440, 150, 30);
        uploadDataButton.addActionListener(uploadDataButtonActionListener);

        solveTaskButton = new JButton("Решить задачу");
        solveTaskButton.setBounds(220, 440, 150, 30);
        solveTaskButton.addActionListener(solveTaskButtonActionListener);
        solveTaskButton.setEnabled(false);

        JPanel panelRadio = new JPanel(new GridLayout(0, 1, 0, 0));
        panelRadio.setBorder(BorderFactory.createTitledBorder("Задача"));
        ButtonGroup buttonGroup = new ButtonGroup();
        for (Task task : Task.values()) {
            JRadioButton radioButton = new JRadioButton(task.text());
            radioButton.setActionCommand(task.name());
            radioButton.addActionListener(radioButtonsActionListener);
            panelRadio.add(radioButton);
            buttonGroup.add(radioButton);
        }
        buttonGroup.getElements().nextElement().setSelected(true);
        panelRadio.setBounds(400, 410, 350, 150);

        JLabel label1 = new JLabel("Исходные данные");
        JLabel label2 = new JLabel("Результат");

        label1.setBounds(50, 5, 150, 15);
        label2.setBounds(400, 5, 150, 15);

        add(label1);
        add(label2);
        add(splitPane);
        add(uploadDataButton);
        add(solveTaskButton);
        add(panelRadio);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ТПР");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public JTextArea getTaskInfo() {
        return taskInfo;
    }

    public JTextArea getResultInfo() {
        return resultInfo;
    }

    public JButton getUploadDataButton() {
        return uploadDataButton;
    }

    public JButton getSolveTaskButton() {
        return solveTaskButton;
    }

    public void showErrorMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Сообщение", JOptionPane.INFORMATION_MESSAGE);
    }
}
