package sidorov.app;

import sidorov.mode.ModeType;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Set;

public class UI extends JFrame {

    private final JPanel panelRadio;

    public final JTextArea initialDataText;
    public final JTextArea resultText;
    public final JButton uploadDataButton;
    public final JButton solveTaskButton;
    public final JButton createChart;

    public final JTextField textField1;
    public final JTextField textField2;
    public final JLabel labelTextField1;
    public final JLabel labelTextField2;

    public final JSlider alphaSlider;
    public final JSlider betaSlider;
    public final JLabel alphaSliderLabel;
    public final JLabel betaSliderLabel;

    public UI(ActionListener uploadDataButtonActionListener,
              ActionListener solveTaskButtonActionListener,
              ActionListener createChartTaskButtonActionListener) {

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
        uploadDataButton.setBounds(250, 405, 140, 30);
        uploadDataButton.addActionListener(uploadDataButtonActionListener);

        solveTaskButton = new JButton("Решить задачу");
        solveTaskButton.setBounds(250, 440, 140, 30);
        solveTaskButton.addActionListener(solveTaskButtonActionListener);
        solveTaskButton.setEnabled(false);

        JLabel label1 = new JLabel("Исходные данные");
        JLabel label2 = new JLabel("Результат");

        label1.setBounds(50, 5, 150, 15);
        label2.setBounds(400, 5, 150, 15);

        // матричная игра 2xN/Nx2
        createChart = new JButton("Создать график");
        createChart.setBounds(45, 405, 140, 30);
        createChart.addActionListener(createChartTaskButtonActionListener);
        createChart.setEnabled(false);
        createChart.setVisible(false);

        textField1 = new JTextField();
        textField1.setBounds(75, 440, 30, 30);
        textField1.setEnabled(false);
        textField1.setVisible(false);

        textField2 = new JTextField();
        textField2.setBounds(145, 440, 30, 30);
        textField2.setEnabled(false);
        textField2.setVisible(false);

        labelTextField1 = new JLabel("l/t =");
        labelTextField1.setBounds(45, 440, 30, 30);
        labelTextField1.setVisible(false);

        labelTextField2 = new JLabel("s/u =");
        labelTextField2.setBounds(110, 440, 30, 30);
        labelTextField2.setVisible(false);

        panelRadio = new JPanel(new GridLayout(0, 2, 0, 0));
        panelRadio.setBorder(BorderFactory.createTitledBorder("Задача"));
        panelRadio.setBounds(400, 400, 350, 150);

        // статистические игры
        alphaSlider = new JSlider(0, 1000);
        alphaSlider.setBounds(100, 400, 130, 50);
        alphaSlider.setVisible(false);

        alphaSliderLabel = new JLabel("\u03B1 = 0,500");
        alphaSliderLabel.setBounds(45, 401, 50, 50);
        alphaSlider.addChangeListener(e -> {
            int value = ((JSlider)e.getSource()).getValue();
            alphaSliderLabel.setText(String.format("\u03B1 = %.3f", value * 0.001));
        });
        alphaSliderLabel.setVisible(false);

        betaSlider = new JSlider(0, 1000);
        betaSlider.setBounds(100, 450, 130, 50);
        betaSlider.setVisible(false);

        betaSliderLabel = new JLabel("\u03B2 = 0,500");
        betaSliderLabel.setBounds(45, 451, 50, 50);
        betaSlider.addChangeListener(e -> {
            int value = ((JSlider)e.getSource()).getValue();
            betaSliderLabel.setText(String.format("\u03B2 = %.3f", value * 0.001));
        });
        betaSliderLabel.setVisible(false);

        add(label1);
        add(label2);
        add(splitPane);

        add(uploadDataButton);
        add(solveTaskButton);
        add(createChart);
        add(textField1);
        add(textField2);
        add(labelTextField1);
        add(labelTextField2);
        add(panelRadio);

        add(alphaSlider);
        add(alphaSliderLabel);
        add(betaSlider);
        add(betaSliderLabel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ТПР");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void initialize(ActionListener radioButtonsActionListener, Set<ModeType> modeTypes) {
        panelRadio.removeAll();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (ModeType modeType : modeTypes) {
            JRadioButton radioButton = new JRadioButton(modeType.text);
            radioButton.setActionCommand(modeType.name());
            radioButton.addActionListener(radioButtonsActionListener);
            panelRadio.add(radioButton);
            buttonGroup.add(radioButton);
        }
        buttonGroup.getElements().nextElement().setSelected(true);
        setFocusable(true);
        setVisible(true);
    }

    public void showErrorMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Сообщение", JOptionPane.INFORMATION_MESSAGE);
    }
}
