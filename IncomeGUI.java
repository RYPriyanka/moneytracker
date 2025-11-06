package Project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

public class IncomeGUI extends JFrame implements ActionListener {

    private Income incomeManager;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DecimalFormat moneyFormat = new DecimalFormat("##,##,##0.00");
    private LocalDate currentDate = LocalDate.now();

    private JLabel balanceLabel;
    private JTextArea outputArea;

    private JTextField oneTimeIncomeTypeField, oneTimeIncomeAmountField;
    private JButton addOneTimeIncomeButton;

    private JTextField recurringIncomeTypeField, recurringIncomeAmountField;
    private JButton addRecurringIncomeButton;

    private JTextField lendToField, lendAmountField, lendRateField, lendMonthsField;
    private JButton lendButton;

    private JButton monthlyUpdateButton, showBalanceButton;


    public IncomeGUI(Income manager) {
        this.incomeManager = manager;

        setTitle("Income Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setEditable(false);

        inputPanel.add(new JLabel("--- One-Time Income ---"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("Type:"));
        oneTimeIncomeTypeField = new JTextField(15);
        inputPanel.add(oneTimeIncomeTypeField);
        inputPanel.add(new JLabel("Amount:"));
        oneTimeIncomeAmountField = new JTextField(15);
        inputPanel.add(oneTimeIncomeAmountField);
        addOneTimeIncomeButton = new JButton("Add One-Time Income");
        addOneTimeIncomeButton.addActionListener(this);
        inputPanel.add(addOneTimeIncomeButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("--- Recurring Income ---"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("Type:"));
        recurringIncomeTypeField = new JTextField(15);
        inputPanel.add(recurringIncomeTypeField);
        inputPanel.add(new JLabel("Amount:"));
        recurringIncomeAmountField = new JTextField(15);
        inputPanel.add(recurringIncomeAmountField);
        addRecurringIncomeButton = new JButton("Add Recurring Income");
        addRecurringIncomeButton.addActionListener(this);
        inputPanel.add(addRecurringIncomeButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("--- Lend Money ---"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("To:"));
        lendToField = new JTextField(15);
        inputPanel.add(lendToField);
        inputPanel.add(new JLabel("Amount:"));
        lendAmountField = new JTextField(15);
        inputPanel.add(lendAmountField);
        inputPanel.add(new JLabel("Rate (%):"));
        lendRateField = new JTextField(5);
        inputPanel.add(lendRateField);
        inputPanel.add(new JLabel("Months:"));
        lendMonthsField = new JTextField(5);
        inputPanel.add(lendMonthsField);
        lendButton = new JButton("Lend Money");
        lendButton.addActionListener(this);
        inputPanel.add(lendButton);
        inputPanel.add(new JLabel(""));

        monthlyUpdateButton = new JButton("Monthly Update");
        monthlyUpdateButton.addActionListener(this);
        buttonPanel.add(monthlyUpdateButton);

        showBalanceButton = new JButton("Show Balance");
        showBalanceButton.addActionListener(this);
        buttonPanel.add(showBalanceButton);

        balanceLabel = new JLabel("Current Balance: " + moneyFormat.format(incomeManager.getBalance()));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(balanceLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
        updateBalanceLabel();
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: " + moneyFormat.format(incomeManager.getBalance()));
    }

    private void displayOutput(String message) {
        outputArea.append(message + "\n");
    }

    private void clearInputFields() {
        oneTimeIncomeTypeField.setText("");
        oneTimeIncomeAmountField.setText("");
        recurringIncomeTypeField.setText("");
        recurringIncomeAmountField.setText("");
        lendToField.setText("");
        lendAmountField.setText("");
        lendRateField.setText("");
        lendMonthsField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addOneTimeIncomeButton) {
                String type = oneTimeIncomeTypeField.getText();
                double amount = Double.parseDouble(oneTimeIncomeAmountField.getText());
                incomeManager.addOneTimeIncome(type, amount, currentDate);
                displayOutput("One-time income added.");
                updateBalanceLabel();
                clearInputFields();
            } else if (e.getSource() == addRecurringIncomeButton) {
                String type = recurringIncomeTypeField.getText();
                double amount = Double.parseDouble(recurringIncomeAmountField.getText());
                incomeManager.addRecurringIncome(type, amount);
                displayOutput("Recurring income added.");
                updateBalanceLabel();
                clearInputFields();
            } else if (e.getSource() == lendButton) {
                String to = lendToField.getText();
                double amount = Double.parseDouble(lendAmountField.getText());
                double rate = Double.parseDouble(lendRateField.getText());
                int months = Integer.parseInt(lendMonthsField.getText());
                incomeManager.lend(to, amount, rate, months);
                displayOutput("Lent money.");
                updateBalanceLabel();
                clearInputFields();
            } else if (e.getSource() == monthlyUpdateButton) {
                incomeManager.monthlyUpdate(currentDate);
                displayOutput("\n--- Monthly Update for " + currentDate.format(dateFormatter) + " ---");
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                System.setOut(ps);
                incomeManager.monthlyUpdate(currentDate);
                System.setOut(System.out); 
                displayOutput(baos.toString());
                updateBalanceLabel();
            } else if (e.getSource() == showBalanceButton) {
                displayOutput("Current Balance: " + moneyFormat.format(incomeManager.getBalance()));
                updateBalanceLabel();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers for amounts, rate, and months.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

