package Project;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class Income {
    protected IncomeNode incomeHead = null;
    protected RecurringIncomeNode recurringIncomeHead = null;
    protected LoanNode loanHead = null;
    protected DecimalFormat money = new DecimalFormat("##,##,##0.00");

    protected double balance = 0;

    class IncomeNode {
        String type;
        double amount;
        String monthYear;
        IncomeNode next;

        IncomeNode(String type, double amount, String monthYear) {
            this.type = type;
            this.amount = amount;
            this.monthYear = monthYear;
            this.next = null;
        }
    }

    class RecurringIncomeNode {
        String type;
        double amount;
        RecurringIncomeNode next;

        RecurringIncomeNode(String type, double amount) {
            this.type = type;
            this.amount = amount;
            this.next = null;
        }
    }

    class LoanNode {
        String name;
        double principal;
        double rate;
        int monthsLeft;
        int totalMonths;
        LoanNode next;

        LoanNode(String name, double principal, double rate, int months) {
            this.name = name;
            this.principal = principal;
            this.rate = rate;
            this.monthsLeft = months;
            this.totalMonths = months;
            this.next = null;
        }

        double getMonthlyInterest() {
            return (principal * rate) / 100.0 / 12.0;
        }

        boolean isActive() {
            return monthsLeft > 0;
        }

        void reduceMonth() {
            if (monthsLeft > 0) monthsLeft--;
        }
    }

    public void addOneTimeIncome(String type, double amount, LocalDate date) {
        String monthYear = date.format(DateTimeFormatter.ofPattern("MMMM-yyyy"));
        IncomeNode newIncome = new IncomeNode(type, amount, monthYear);
        newIncome.next = incomeHead;
        incomeHead = newIncome;
        balance += amount;
        System.out.println("One-time income of " + money.format(amount) + " for " + monthYear + " added.");
    }

    public void addRecurringIncome(String type, double amount) {
        RecurringIncomeNode newRecurring = new RecurringIncomeNode(type, amount);
        newRecurring.next = recurringIncomeHead;
        recurringIncomeHead = newRecurring;
        balance += amount;
        System.out.println("Recurring income of " + money.format(amount) + " added.");
    }

    public void lend(String to, double amount, double rate, int months) {
        if (amount > balance) {
            System.out.println("Not enough balance to lend!");
            return;
        }

        LoanNode newLoan = new LoanNode(to, amount, rate, months);
        newLoan.next = loanHead;
        loanHead = newLoan;
        balance -= amount;
        System.out.println("Lent " + money.format(amount) + " to " + to + " at " + rate + "% for " + months + " months");
    }

    public void monthlyUpdate(LocalDate date) {
        double totalIncome = 0;
        double totalExpense = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-yyyy");
        String monthYear = date.format(formatter);
        System.out.println("\n-- Monthly Update for " + monthYear + " --");

        RecurringIncomeNode rec = recurringIncomeHead;
        while (rec != null) {
            System.out.println("• Recurring Income: " + rec.type + " + " + money.format(rec.amount));
            balance += rec.amount;
            totalIncome += rec.amount;
            rec = rec.next;
        }

        IncomeNode income = incomeHead;
        while (income != null) {
            if (income.monthYear.equals(monthYear)) {
                System.out.println("• One-Time Income: " + income.type + " + " + money.format(income.amount));
                totalIncome += income.amount;
            }
            income = income.next;
        }

        LoanNode loan = loanHead;
        while (loan != null) {
            if (loan.isActive()) {
                double interest = loan.getMonthlyInterest();
                System.out.println("• Loan Interest from " + loan.name + ": +" + money.format(interest));
                balance += interest;
                totalIncome += interest;
                loan.reduceMonth();

                if (loan.monthsLeft == 0) {
                    System.out.println("• Loan to " + loan.name + " term completed.");
                }
            }
            loan = loan.next;
        }

        System.out.println("→ Total Income this month: " + money.format(totalIncome));
        System.out.println("→ Total Expenses this month: " + money.format(totalExpense));
        System.out.println("→ New Balance: " + money.format(balance));
    }

    public void showBalance() {
        System.out.println("Current Balance: " + money.format(balance));
    }

    public double getBalance() {
        return balance;
    }

    public void deductFromBalance(double amount) {
        balance -= amount;
    }
}