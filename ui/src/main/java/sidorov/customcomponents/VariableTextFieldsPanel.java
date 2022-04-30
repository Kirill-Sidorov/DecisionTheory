package sidorov.customcomponents;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;

public class VariableTextFieldsPanel extends JScrollPane {

    private final JPanel panel;
    private final String symbol;

    private JTextField[] textFields;

    public VariableTextFieldsPanel(final JPanel panel, final String symbol) {
        super(panel);
        this.panel = panel;
        this.symbol = symbol;
    }

    public void createNewTextFields(int number) {
        panel.removeAll();
        textFields = new JTextField[number];
        for (int i = 0; i < number; i++) {
            JLabel label = new JLabel(symbol + i + " = ");
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(50, 25));
            panel.add(label);
            panel.add(textField);
            textFields[i] = textField;
        }
    }

    public String[] getValues() {
        String[] values = new String[textFields.length];
        for (int i = 0; i < textFields.length; i++) {
            values[i] = textFields[i].getText();
        }
        return values;
    }
}
