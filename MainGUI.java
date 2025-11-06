package Project;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainGUI extends JFrame implements ActionListener {

	private Expenditure financeManager = new Expenditure();
	private BusinessGUI businessManager = new BusinessGUI();
    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private JTextArea outputArea;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel homePanel;
    private JPanel financePanel;
    private JPanel propertyPanel;	

    public MainGUI() {
        setTitle("Personal Finance and Property Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        homePanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        homePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel homeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton financeButton = new JButton("Finance Management");
        financeButton.setActionCommand("finance");
        financeButton.addActionListener(this);
        homeButtonPanel.add(financeButton);

        JButton propertyButton = new JButton("Property Management");
        propertyButton.setActionCommand("property");
        propertyButton.addActionListener(this);
        homeButtonPanel.add(propertyButton);

        JButton currencyButton = new JButton("Currency Exchange");
        currencyButton.setActionCommand("currency");
        currencyButton.addActionListener(this);
        homeButtonPanel.add(currencyButton);

        homePanel.add(homeButtonPanel, BorderLayout.SOUTH);
        homePanel.add(new JLabel("Welcome! Today's date: " + currentDate.format(dateFormatter)), BorderLayout.NORTH);

        financePanel = createFinancePanel();

        propertyPanel = createPropertyPanel();

        mainPanel.add(homePanel, "home");
        mainPanel.add(financePanel, "finance");
        mainPanel.add(propertyPanel, "property");

        add(mainPanel);
        setVisible(true);

        outputArea.append("Welcome! Today's date: " + currentDate.format(dateFormatter) + "\n");
    }

    private JPanel createFinancePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton backToHomeButton = new JButton("Back to Home");
        backToHomeButton.setActionCommand("home");
        backToHomeButton.addActionListener(this);
        panel.add(backToHomeButton);

        JButton addOneTimeIncomeButton = new JButton("Add One-Time Income");
        addOneTimeIncomeButton.addActionListener(e -> addOneTimeIncome());
        panel.add(addOneTimeIncomeButton);

        JButton addRecurringIncomeButton = new JButton("Add Recurring Income");
        addRecurringIncomeButton.addActionListener(e -> addRecurringIncome());
        panel.add(addRecurringIncomeButton);

        JButton lendMoneyButton = new JButton("Lend Money");
        lendMoneyButton.addActionListener(e -> lendMoney());
        panel.add(lendMoneyButton);

        JButton addOneTimeExpenditureButton = new JButton("Add One-Time Expenditure");
        addOneTimeExpenditureButton.addActionListener(e -> addOneTimeExpenditure());
        panel.add(addOneTimeExpenditureButton);

        JButton addRecurringExpenditureButton = new JButton("Add Recurring Expenditure");
        addRecurringExpenditureButton.addActionListener(e -> addRecurringExpenditure());
        panel.add(addRecurringExpenditureButton);

        JButton borrowMoneyButton = new JButton("Borrow Money");
        borrowMoneyButton.addActionListener(e -> borrowMoney());
        panel.add(borrowMoneyButton);

        JButton addInvestmentPlanButton = new JButton("Add Investment Plan");
        addInvestmentPlanButton.addActionListener(e -> addInvestmentPlan());
        panel.add(addInvestmentPlanButton);

        JButton monthlyUpdateButton = new JButton("Monthly Update");
        monthlyUpdateButton.addActionListener(e -> monthlyUpdate());
        panel.add(monthlyUpdateButton);

        JButton simulateMonthsButton = new JButton("Simulate Months Ahead");
        simulateMonthsButton.addActionListener(e -> simulateMonths());
        panel.add(simulateMonthsButton);

        JButton showBalanceButton = new JButton("Show Balance");
        showBalanceButton.addActionListener(e -> showBalance());
        panel.add(showBalanceButton);

        JButton showInvestmentPlansButton = new JButton("Show Investment Plans");
        showInvestmentPlansButton.addActionListener(e -> showInvestmentPlans());
        panel.add(showInvestmentPlansButton);

        JButton setCustomDateButton = new JButton("Set Custom Date");
        setCustomDateButton.addActionListener(e -> setCustomDate());
        panel.add(setCustomDateButton);

        return panel;
    }

    private JPanel createPropertyPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton backToHomeButton = new JButton("Back to Home");
        backToHomeButton.setActionCommand("home");
        backToHomeButton.addActionListener(this);
        panel.add(backToHomeButton);

        JButton addPropertyButton = new JButton("Add Property");
        addPropertyButton.addActionListener(e -> addProperty());
        panel.add(addPropertyButton);

        JButton deletePropertyButton = new JButton("Delete Property by Name");
        deletePropertyButton.addActionListener(e -> deleteProperty());
        panel.add(deletePropertyButton);

        JButton updatePropertyButton = new JButton("Update Property Details");
        updatePropertyButton.addActionListener(e -> updateProperty());
        panel.add(updatePropertyButton);

        JButton displayAboveInvestmentButton = new JButton("Display Properties Above Investment");
        displayAboveInvestmentButton.addActionListener(e -> displayPropertiesAboveInvestment());
        panel.add(displayAboveInvestmentButton);

        JButton checkPropertyExistsButton = new JButton("Check if Property Exists");
        checkPropertyExistsButton.addActionListener(e -> checkPropertyExists());
        panel.add(checkPropertyExistsButton);

        JButton getMostExpensiveButton = new JButton("Get Most Expensive Property");
        getMostExpensiveButton.addActionListener(e -> getMostExpensiveProperty());
        panel.add(getMostExpensiveButton);

        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> generatePropertyReport());
        panel.add(generateReportButton);

        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("finance".equals(command)) {
            cardLayout.show(mainPanel, "finance");
        } else if ("property".equals(command)) {
            cardLayout.show(mainPanel, "property");
        } else if ("currency".equals(command)) {
            new CurrencyConvertorGUI(this, outputArea).setVisible(true);
        } else if ("home".equals(command)) {
            cardLayout.show(mainPanel, "home");
        }
    }

    private void addOneTimeIncome() {
        JTextField typeField = new JTextField(15);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add One-Time Income",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String type = typeField.getText();
                double amount = Double.parseDouble(amountField.getText());
                financeManager.addOneTimeIncome(type, amount, currentDate);
                outputArea.append("Added one-time income: " + type + " - " + amount + " on " + currentDate.format(dateFormatter) + "\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addRecurringIncome() {
        JTextField typeField = new JTextField(15);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Recurring Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Recurring Income",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String type = typeField.getText();
                double amount = Double.parseDouble(amountField.getText());
                financeManager.addRecurringIncome(type, amount);
                outputArea.append("Added recurring income: " + type + " - " + amount + "\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void lendMoney() {
        JTextField nameField = new JTextField(15);
        JTextField amountField = new JTextField(10);
        JTextField rateField = new JTextField(5);
        JTextField monthsField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Borrower Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Annual Interest Rate (%):"));
        panel.add(rateField);
        panel.add(new JLabel("Duration (months):"));
        panel.add(monthsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Lend Money",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                double amount = Double.parseDouble(amountField.getText());
                double rate = Double.parseDouble(rateField.getText());
                int months = Integer.parseInt(monthsField.getText());
                financeManager.lend(name, amount, rate, months);
                outputArea.append("Lent " + amount + " to " + name + " at " + rate + "% for " + months + " months.\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addOneTimeExpenditure() {
        JTextField typeField = new JTextField(15);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Expenditure Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add One-Time Expenditure",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String type = typeField.getText();
                double amount = Double.parseDouble(amountField.getText());
                financeManager.addOneTimeExpenditure(type, amount, currentDate);
                outputArea.append("Added one-time expenditure: " + type + " - " + amount + " on " + currentDate.format(dateFormatter) + "\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addRecurringExpenditure() {
        JTextField typeField = new JTextField(15);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Recurring Expenditure Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Recurring Expenditure",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String type = typeField.getText();
                double amount = Double.parseDouble(amountField.getText());
                financeManager.addRecurringExpenditure(type, amount);
                outputArea.append("Added recurring expenditure: " + type + " - " + amount + "\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void borrowMoney() {
        JTextField lenderField = new JTextField(15);
        JTextField amountField = new JTextField(10);
        JTextField rateField = new JTextField(5);
        JTextField monthsField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Lender Name:"));
        panel.add(lenderField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Annual Interest Rate (%):"));
        panel.add(rateField);
        panel.add(new JLabel("Duration (months):"));
        panel.add(monthsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Borrow Money",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String lender = lenderField.getText();
                double amount = Double.parseDouble(amountField.getText());
                double rate = Double.parseDouble(rateField.getText());
                int months = Integer.parseInt(monthsField.getText());
                financeManager.borrow(lender, amount, rate, months);
                outputArea.append("Borrowed " + amount + " from " + lender + " at " + rate + "% for " + months + " months.\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addInvestmentPlan() {
        JTextField nameField = new JTextField(15);
        JTextField amountField = new JTextField(10);
        JTextField rateField = new JTextField(5);
        JTextField monthsField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Plan Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Monthly Investment:"));
        panel.add(amountField);
        panel.add(new JLabel("Expected Monthly Return Rate (%):"));
        panel.add(rateField);
        panel.add(new JLabel("Duration (months):"));
        panel.add(monthsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Investment Plan",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                double amount = Double.parseDouble(amountField.getText());
                double rate = Double.parseDouble(rateField.getText());
                int months = Integer.parseInt(monthsField.getText());
                financeManager.addInvestmentPlan(name, amount, rate, months);
                outputArea.append("Added investment plan: " + name + ", investing " + amount + " monthly for " + months + " months at " + rate + "% return.\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void monthlyUpdate() {
        double beforeUpdate = financeManager.getBalance();
        financeManager.monthlyUpdate(currentDate);
        double afterUpdate = financeManager.getBalance();
        double incomeOrChange = afterUpdate - beforeUpdate;

        outputArea.append("Monthly update performed for " + currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM")) + "\n");
        outputArea.append("Balance before: " + String.format("%.2f", beforeUpdate) + "\n");
        outputArea.append("Balance after: " + String.format("%.2f", afterUpdate) + "\n");
        outputArea.append("Change this month: " + String.format("%.2f", incomeOrChange) + "\n\n");
    }

    private void simulateMonths() {
        String monthsStr = JOptionPane.showInputDialog(this, "Enter number of months to simulate:", "Simulate Months", JOptionPane.QUESTION_MESSAGE);
        if (monthsStr != null && !monthsStr.isEmpty()) {
            try {
                int simMonths = Integer.parseInt(monthsStr);
                LocalDate futureDate = currentDate;
                double beforeSim = financeManager.getBalance();
                double afterSim = beforeSim;

                for (int i = 0; i < simMonths; i++) {
                    futureDate = futureDate.plusMonths(1);
                    financeManager.monthlyUpdate(futureDate);
                    afterSim = financeManager.getBalance();
                    outputArea.append("Simulated month: " + futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM")) + "\n");
                    outputArea.append("→ Balance: " + String.format("%.2f", afterSim) + "\n\n");
                }

                outputArea.append("Simulation complete for " + simMonths + " months.\n");
                outputArea.append("Starting Balance: " + String.format("%.2f", beforeSim) + "\n");
                outputArea.append("Ending Balance: " + String.format("%.2f", afterSim) + "\n");
                outputArea.append("Total Change: " + String.format("%.2f", afterSim - beforeSim) + "\n\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number of months.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showBalance() {
        outputArea.append("\nCurrent Balance: " + financeManager.getBalance() + "\n");
    }

    private void showInvestmentPlans() {
        outputArea.append("\n--- Investment Plans ---\n");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);
            
            financeManager.showInvestmentPlans();
            
            System.out.flush();
            System.setOut(old);
            
            outputArea.append(baos.toString());
        } catch (Exception e) {
            outputArea.append("Error displaying investment plans: " + e.getMessage() + "\n");
        }
        outputArea.append("-------------------------\n");
    }


    private void setCustomDate() {
        String newDateStr = JOptionPane.showInputDialog(this, "Enter new current month and year (yyyy-MM):", "Set Custom Date", JOptionPane.QUESTION_MESSAGE);
        if (newDateStr != null && !newDateStr.isEmpty()) {
            try {
                newDateStr += "-01";
                currentDate = LocalDate.parse(newDateStr, dateFormatter);
                outputArea.append("Current date set to: " + currentDate.format(dateFormatter) + "\n");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addProperty() {
        JTextField nameField = new JTextField(15);
        JTextField investmentField = new JTextField(10);
        JTextField locationField = new JTextField(15);
        JTextField typeField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Investment:"));
        panel.add(investmentField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Property",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                double investment = Double.parseDouble(investmentField.getText());
                String location = locationField.getText();
                String type = typeField.getText();
                businessManager.addProperty(name, investment, location, type);
                outputArea.append("Added property: " + name + " (Investment: " + investment + ", Location: " + location + ", Type: " + type + ")\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid investment amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProperty() {
        String nameToDelete = JOptionPane.showInputDialog(this, "Enter Name of Property to Delete:", "Delete Property", JOptionPane.QUESTION_MESSAGE);
        if (nameToDelete != null && !nameToDelete.isEmpty()) {
            if (businessManager.deletePropertyByName(nameToDelete)) {
                outputArea.append("Deleted property: " + nameToDelete + "\n");
            } else {
                JOptionPane.showMessageDialog(this, "Property not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateProperty() {
        JTextField oldNameField = new JTextField(15);
        JTextField newInvestmentField = new JTextField(10);
        JTextField newLocationField = new JTextField(15);
        JTextField newTypeField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name of Property to Update:"));
        panel.add(oldNameField);
        panel.add(new JLabel("New Investment:"));
        panel.add(newInvestmentField);
        panel.add(new JLabel("New Location:"));
        panel.add(newLocationField);
        panel.add(new JLabel("New Type:"));
        panel.add(newTypeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Property Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String oldName = oldNameField.getText();
                double newInvestment = Double.parseDouble(newInvestmentField.getText());
                String newLocation = newLocationField.getText();
                String newType = newTypeField.getText();
                businessManager.updatePropertyDetails(oldName, newInvestment, newLocation, newType);
                outputArea.append("Updated property: " + oldName + " to (Investment: " + newInvestment + ", Location: " + newLocation + ", Type: " + newType + ")\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid investment amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayPropertiesAboveInvestment() {
        String minInvestmentStr = JOptionPane.showInputDialog(this, "Enter Minimum Investment:", "Display Properties", JOptionPane.QUESTION_MESSAGE);
        if (minInvestmentStr != null && !minInvestmentStr.isEmpty()) {
            try {
                double minInvestment = Double.parseDouble(minInvestmentStr);
                outputArea.append("\n--- Properties Above Investment: " + minInvestment + " ---\n");
                businessManager.getProperties().stream()
                        .filter(property -> property.getInvestment() > minInvestment)
                        .forEach(property -> outputArea.append(property.toString() + "\n"));
                outputArea.append("---------------------------------------------\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid investment amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void checkPropertyExists() {
        String nameToCheck = JOptionPane.showInputDialog(this, "Enter Name of Property to Check:", "Check Property", JOptionPane.QUESTION_MESSAGE);
        if (nameToCheck != null && !nameToCheck.isEmpty()) {
            if (businessManager.propertyExists(nameToCheck)) {
                outputArea.append("Property '" + nameToCheck + "' exists.\n");
            } else {
                outputArea.append("Property '" + nameToCheck + "' does not exist.\n");
            }
        }
    }

    private void getMostExpensiveProperty() {
        BusinessGUI.Property mostExpensive = businessManager.getMostExpensiveInvestment();
        if (mostExpensive != null) {
            outputArea.append("\nMost Expensive Property: " + mostExpensive + "\n");
        } else {
            outputArea.append("No properties available.\n");
        }
    }

    private void generatePropertyReport() {
        outputArea.append("\n--- Property Report ---\n");
        businessManager.generateReportToTextArea(outputArea);
        outputArea.append("-----------------------\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}