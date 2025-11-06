package Project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

public class ExpenditureGUI extends JFrame implements ActionListener {

    private Expenditure expenditureTracker;
    private DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM-yyyy");
    private DecimalFormat moneyFormat = new DecimalFormat("##,##,##0.00");
    private LocalDate currentDate = LocalDate.now();

    private JLabel balanceLabel;
    private JTextArea outputArea;

    private JTextField oneTimeExpTypeField, oneTimeExpAmountField;
    private JButton addOneTimeExpButton;

    private JTextField recurringExpTypeField, recurringExpAmountField;
    private JButton addRecurringExpButton;

    private JTextField borrowFromField, borrowAmountField, borrowRateField, borrowMonthsField;
    private JButton borrowButton;

    private JTextField investNameField, investMonthlyAmountField, investRateField, investMonthsField;
    private JButton addInvestPlanButton, showInvestPlansButton, showFullInvestDetailsButton;

    private JButton monthlyUpdateButton, showBalanceButton, simulateMonthsButton;
    private JTextField simulateMonthsField;

    public ExpenditureGUI(Expenditure tracker) {
        this.expenditureTracker = tracker;

        setTitle("Expenditure Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(800, 700); 
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); 

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setEditable(false);

        inputPanel.add(new JLabel("--- One-Time Expenditure ---"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("Type:"));
        oneTimeExpTypeField = new JTextField(15);
        inputPanel.add(oneTimeExpTypeField);
        inputPanel.add(new JLabel("Amount:"));
        oneTimeExpAmountField = new JTextField(15);
        inputPanel.add(oneTimeExpAmountField);
        addOneTimeExpButton = new JButton("Add One-Time Exp");
        addOneTimeExpButton.addActionListener(this);
        inputPanel.add(addOneTimeExpButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("--- Recurring Expenditure ---"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("Type:"));
        recurringExpTypeField = new JTextField(15);
        inputPanel.add(recurringExpTypeField);
        inputPanel.add(new JLabel("Amount:"));
        recurringExpAmountField = new JTextField(15);
        inputPanel.add(recurringExpAmountField);
        addRecurringExpButton = new JButton("Add Recurring Exp");
        addRecurringExpButton.addActionListener(this);
        inputPanel.add(addRecurringExpButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("--- Borrow ---"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("From:"));
        borrowFromField = new JTextField(15);
        inputPanel.add(borrowFromField);
        inputPanel.add(new JLabel("Amount:"));
        borrowAmountField = new JTextField(15);
        inputPanel.add(borrowAmountField);
        inputPanel.add(new JLabel("Rate (%):"));
        borrowRateField = new JTextField(5);
        inputPanel.add(borrowRateField);
        inputPanel.add(new JLabel("Months:"));
        borrowMonthsField = new JTextField(5);
        inputPanel.add(borrowMonthsField);
        borrowButton = new JButton("Borrow Money");
        borrowButton.addActionListener(this);
        inputPanel.add(borrowButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("--- Investment Plan ---"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("Name:"));
        investNameField = new JTextField(15);
        inputPanel.add(investNameField);
        inputPanel.add(new JLabel("Monthly Investment:"));
        investMonthlyAmountField = new JTextField(15);
        inputPanel.add(investMonthlyAmountField);
        inputPanel.add(new JLabel("Monthly Rate (%):"));
        investRateField = new JTextField(5);
        inputPanel.add(investRateField);
        inputPanel.add(new JLabel("Months:"));
        investMonthsField = new JTextField(5);
        inputPanel.add(investMonthsField);
        addInvestPlanButton = new JButton("Add Invest Plan");
        addInvestPlanButton.addActionListener(this);
        inputPanel.add(addInvestPlanButton);
        inputPanel.add(new JLabel(""));
        showInvestPlansButton = new JButton("Show Invest Plans");
        showInvestPlansButton.addActionListener(this);
        inputPanel.add(showInvestPlansButton);
        showFullInvestDetailsButton = new JButton("Show Full Invest Details");
        showFullInvestDetailsButton.addActionListener(this);
        inputPanel.add(showFullInvestDetailsButton);
        inputPanel.add(new JLabel(""));

        monthlyUpdateButton = new JButton("Monthly Update");
        monthlyUpdateButton.addActionListener(this);
        buttonPanel.add(monthlyUpdateButton);

        showBalanceButton = new JButton("Show Balance");
        showBalanceButton.addActionListener(this);
        buttonPanel.add(showBalanceButton);

        buttonPanel.add(new JLabel("Simulate Months:"));
        simulateMonthsField = new JTextField(5);
        buttonPanel.add(simulateMonthsField);
        simulateMonthsButton = new JButton("Simulate");
        simulateMonthsButton.addActionListener(this);
        buttonPanel.add(simulateMonthsButton);

        balanceLabel = new JLabel("Current Balance: " + moneyFormat.format(expenditureTracker.getBalance()));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(balanceLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
        updateBalanceLabel();
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: " + moneyFormat.format(expenditureTracker.getBalance()));
    }

    private void displayOutput(String message) {
        outputArea.append(message + "\n");
    }

    private void clearInputFields() {
        oneTimeExpTypeField.setText("");
        oneTimeExpAmountField.setText("");
        recurringExpTypeField.setText("");
        recurringExpAmountField.setText("");
        borrowFromField.setText("");
        borrowAmountField.setText("");
        borrowRateField.setText("");
        borrowMonthsField.setText("");
        investNameField.setText("");
        investMonthlyAmountField.setText("");
        investRateField.setText("");
        investMonthsField.setText("");
        simulateMonthsField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addOneTimeExpButton) {
                String type = oneTimeExpTypeField.getText();
                double amount = Double.parseDouble(oneTimeExpAmountField.getText());
                expenditureTracker.addOneTimeExpenditure(type, amount, currentDate);
                displayOutput("One-time expenditure added.");
                updateBalanceLabel();
                clearInputFields();
            } else if (e.getSource() == addRecurringExpButton) {
                String type = recurringExpTypeField.getText();
                double amount = Double.parseDouble(recurringExpAmountField.getText());
                expenditureTracker.addRecurringExpenditure(type, amount);
                displayOutput("Recurring expenditure added.");
                updateBalanceLabel();
                clearInputFields();
            } else if (e.getSource() == borrowButton) {
                String from = borrowFromField.getText();
                double amount = Double.parseDouble(borrowAmountField.getText());
                double rate = Double.parseDouble(borrowRateField.getText());
                int months = Integer.parseInt(borrowMonthsField.getText());
                expenditureTracker.borrow(from, amount, rate, months);
                displayOutput("Borrowed money added.");
                updateBalanceLabel();
                clearInputFields();
            } else if (e.getSource() == addInvestPlanButton) {
                String name = investNameField.getText();
                double monthlyInvestment = Double.parseDouble(investMonthlyAmountField.getText());
                double monthlyRate = Double.parseDouble(investRateField.getText());
                int months = Integer.parseInt(investMonthsField.getText());
                expenditureTracker.addInvestmentPlan(name, monthlyInvestment, monthlyRate, months);
                displayOutput("Investment plan added.");
                clearInputFields();
            } else if (e.getSource() == showInvestPlansButton) {
                outputArea.append("\n--- Investment Plans ---\n");
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                System.setOut(ps);
                expenditureTracker.showInvestmentPlans();
                System.setOut(System.out); 
                displayOutput(baos.toString());
            } else if (e.getSource() == showFullInvestDetailsButton) {
                outputArea.append("\n--- Investment Plan Full Details ---\n");
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                System.setOut(ps);
                expenditureTracker.showInvestmentPlansFullDetails();
                System.setOut(System.out); 
                displayOutput(baos.toString());
            } else if (e.getSource() == monthlyUpdateButton) {
                expenditureTracker.monthlyUpdate(currentDate);
                displayOutput("\n--- Monthly Update for " + currentDate.format(monthYearFormatter) + " ---");
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                System.setOut(ps);
                expenditureTracker.monthlyUpdate(currentDate);
                System.setOut(System.out); 
                displayOutput(baos.toString());
                updateBalanceLabel();
            } else if (e.getSource() == showBalanceButton) {
                displayOutput("Current Balance: " + moneyFormat.format(expenditureTracker.getBalance()));
                updateBalanceLabel();
            } else if (e.getSource() == simulateMonthsButton) {
                try {
                    int months = Integer.parseInt(simulateMonthsField.getText());
                    LocalDate startDate = LocalDate.now();
                    outputArea.append("\n--- Simulation for " + months + " months ---\n");
                    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                    java.io.PrintStream ps = new java.io.PrintStream(baos);
                    System.setOut(ps);
                    expenditureTracker.simulateMonths(startDate, months);
                    System.setOut(System.out); 
                    displayOutput(baos.toString());
                    updateBalanceLabel();
                    clearInputFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number of months.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers for amounts, rate, and months.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}