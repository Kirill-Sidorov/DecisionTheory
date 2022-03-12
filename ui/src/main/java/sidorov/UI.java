package sidorov;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class UI extends JFrame {

    public final JTextArea initialDataText;
    public final JTextArea resultText;
    public final JButton uploadDataButton;
    public final JButton solveTaskButton;

    public UI(ActionListener radioButtonsActionListener,
              ActionListener uploadDataButtonActionListener,
              ActionListener solveTaskButtonActionListener) {

        setLayout(null);

        initialDataText = new JTextArea();
        initialDataText.setLineWrap(true);
        initialDataText.setWrapStyleWord(true);
        initialDataText.setEditable(false);
        initialDataText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        resultText = new JTextArea();
        resultText.setLineWrap(true);
        resultText.setWrapStyleWord(true);
        resultText.setEditable(false);
        resultText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(initialDataText), new JScrollPane(resultText));
        splitPane.setBounds(45, 30, 700, 350);
        splitPane.setResizeWeight(0.5);

        uploadDataButton = new JButton("Загрузить данные");
        uploadDataButton.setBounds(400, 405, 140, 30);
        uploadDataButton.addActionListener(uploadDataButtonActionListener);

        solveTaskButton = new JButton("Решить задачу");
        solveTaskButton.setBounds(400, 440, 140, 30);
        solveTaskButton.addActionListener(solveTaskButtonActionListener);
        solveTaskButton.setEnabled(false);

        JPanel panelRadio = new JPanel(new GridLayout(0, 2, 0, 0));
        panelRadio.setBorder(BorderFactory.createTitledBorder("Задача"));
        ButtonGroup buttonGroup = new ButtonGroup();
        for (Mode mode : Mode.values()) {
            JRadioButton radioButton = new JRadioButton(mode.text());
            radioButton.setActionCommand(mode.name());
            radioButton.addActionListener(radioButtonsActionListener);
            panelRadio.add(radioButton);
            buttonGroup.add(radioButton);
        }
        buttonGroup.getElements().nextElement().setSelected(true);
        panelRadio.setBounds(45, 400, 350, 150);

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

    public void showErrorMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Сообщение", JOptionPane.INFORMATION_MESSAGE);
    }
}
