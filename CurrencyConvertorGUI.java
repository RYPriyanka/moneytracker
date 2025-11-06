package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConvertorGUI extends JDialog {
    private JTextArea outputArea;
    private JComboBox<String> sourceCurrencyCombo;
    private JComboBox<String> targetCurrencyCombo;
    private JTextField amountField;
    
    private final double[] rates = {
        1.0,     // Indian Rupee (INR) - base rate
        0.012,   // US Dollar (USD)
        0.011,   // European Euro (EUR)
        1.63,    // Japanese Yen (JPY)
        0.0096,  // British Pound Sterling (GBP)
        0.016,   // Canadian Dollar (CAD)
        0.011,   // Swiss Franc (CHF)
        0.22,    // South African Rand (ZAR)
        0.019,   // New Zealand Dollar (NZD)
        0.0096,  // Gibraltar Pound (GIP)
        0.083    // Chinese Yuan (CNY)
    };

    private final String[] currencies = {
        "Indian Rupee (INR)",
        "US Dollar (USD)",
        "European Euro (EUR)",
        "Japanese Yen (JPY)",
        "British Pound Sterling (GBP)",
        "Canadian Dollar (CAD)",
        "Swiss Franc (CHF)",
        "South African Rand (ZAR)",
        "New Zealand Dollar (NZD)",
        "Gibraltar Pound (GIP)",
        "Chinese Yuan (CNY)"
    };

    public CurrencyConvertorGUI(JFrame parent, JTextArea outputArea) {
        super(parent, "Currency Exchange", true);
        this.outputArea = outputArea;
        
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        sourceCurrencyCombo = new JComboBox<>(currencies);
        targetCurrencyCombo = new JComboBox<>(currencies);
        amountField = new JTextField();
        JButton convertButton = new JButton("Convert");
        JButton closeButton = new JButton("Close");
        
        inputPanel.add(new JLabel("From Currency:"));
        inputPanel.add(sourceCurrencyCombo);
        inputPanel.add(new JLabel("To Currency:"));
        inputPanel.add(targetCurrencyCombo);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(convertButton);
        inputPanel.add(closeButton);

        convertButton.addActionListener(new ConvertAction());
        closeButton.addActionListener(e -> dispose());
        
        add(inputPanel, BorderLayout.CENTER);
        
        JLabel instructionLabel = new JLabel("Select currencies and enter amount to convert");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(instructionLabel, BorderLayout.SOUTH);
    }
    
    private class ConvertAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int sourceIndex = sourceCurrencyCombo.getSelectedIndex();
                int targetIndex = targetCurrencyCombo.getSelectedIndex();
                double amount = Double.parseDouble(amountField.getText());

                double convertedAmount = amount * (rates[targetIndex] / rates[sourceIndex]);
                
                String result = String.format("%.2f %s = %.2f %s", 
                    amount, currencies[sourceIndex],
                    convertedAmount, currencies[targetIndex]);
                
                outputArea.append("\n[Currency Exchange] " + result + "\n");
                
                JOptionPane.showMessageDialog(CurrencyConvertorGUI.this, 
                    result, "Conversion Result", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CurrencyConvertorGUI.this, 
                    "Please enter a valid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}