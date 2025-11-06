package Project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Expenditure extends Income {
    private ExpenditureNode expenditureHead = null;
    private RecurringExpenditureNode recurringExpenditureHead = null;
    private BorrowNode borrowHead = null;
    private InvestmentPlanNode investmentPlanHead = null;

    class ExpenditureNode {
        String type;
        double amount;
        String monthYear;
        ExpenditureNode next;

        ExpenditureNode(String type, double amount, String monthYear) {
            this.type = type;
            this.amount = amount;
            this.monthYear = monthYear;
            this.next = null;
        }
    }

    class RecurringExpenditureNode {
        String type;
        double amount;
        RecurringExpenditureNode next;

        RecurringExpenditureNode(String type, double amount) {
            this.type = type;
            this.amount = amount;
            this.next = null;
        }
    }

    class BorrowNode {
        String from;
        double principal;
        double rate;
        int monthsLeft;
        BorrowNode next;

        BorrowNode(String from, double principal, double rate, int months) {
            this.from = from;
            this.principal = principal;
            this.rate = rate;
            this.monthsLeft = months;
            this.next = null;
        }

        double getMonthlyInterest() {
            return (principal * rate) / 100.0 / 12.0;
        }

        boolean isActive() {
            return monthsLeft > 0;
        }

        void reduceMonth() {
            monthsLeft--;
        }
    }

    class InvestmentPlanNode {
        String name;
        double monthlyInvestment;
        double monthlyRate;
        int months;
        int monthsLeft;
        double totalInvested = 0;
        double currentValue = 0;
        InvestmentPlanNode next;

        InvestmentPlanNode(String name, double monthlyInvestment, double monthlyRate, int months) {
            this.name = name;
            this.monthlyInvestment = monthlyInvestment;
            this.monthlyRate = monthlyRate;
            this.months = months;
            this.monthsLeft = months;
        }

        boolean isActive() {
            return monthsLeft > 0;
        }

        void processMonth() {
            if (isActive()) {
                totalInvested += monthlyInvestment;
                currentValue += monthlyInvestment;
                double growth = (currentValue * monthlyRate) / 100.0;
                currentValue += growth;
                monthsLeft--;
            }
        }
    }

    public void addOneTimeExpenditure(String type, double amount, LocalDate date) {
        String monthYear = date.format(DateTimeFormatter.ofPattern("MMMM-yyyy"));
        ExpenditureNode newNode = new ExpenditureNode(type, amount, monthYear);
        newNode.next = expenditureHead;
        expenditureHead = newNode;
        deductFromBalance(amount);
        System.out.println("One-time expenditure of " + money.format(amount) + " for " + monthYear + " added.");
    }

    public void addRecurringExpenditure(String type, double amount) {
        RecurringExpenditureNode newNode = new RecurringExpenditureNode(type, amount);
        newNode.next = recurringExpenditureHead;
        recurringExpenditureHead = newNode;
        deductFromBalance(amount);
        System.out.println("Recurring expenditure of " + money.format(amount) + " added.");
    }

    public void borrow(String from, double amount, double rate, int months) {
        BorrowNode newBorrow = new BorrowNode(from, amount, rate, months);
        newBorrow.next = borrowHead;
        borrowHead = newBorrow;
        balance += amount;
        System.out.println("Borrowed " + money.format(amount) + " from " + from + " at " + rate + "% for " + months + " months");
    }

    public void addInvestmentPlan(String name, double monthlyInvestment, double monthlyRate, int months) {
        InvestmentPlanNode newPlan = new InvestmentPlanNode(name, monthlyInvestment, monthlyRate, months);
        newPlan.next = investmentPlanHead;
        investmentPlanHead = newPlan;
        System.out.println("Investment Plan added: " + name + " - " + money.format(monthlyInvestment) + "/month");
    }

    public void showInvestmentPlans() {
        System.out.println("\n--- Investment Plan Summary (Current Status) ---");
        if (investmentPlanHead == null) {
            System.out.println("No active investment plans");
            return;
        }

        InvestmentPlanNode current = investmentPlanHead;
        while (current != null) {
            System.out.println("\n• Plan: " + current.name);
            System.out.println("  Monthly Investment: " + money.format(current.monthlyInvestment));
            System.out.println("  Expected Return Rate: " + current.monthlyRate + "% per month");
            System.out.println("  Time Remaining: " + current.monthsLeft + " of " + current.months + " months");
            System.out.println("  Current Value: " + money.format(current.currentValue));
            System.out.println("  Growth: " + money.format(current.currentValue - current.totalInvested));
            current = current.next;
        }
    }

    public void showInvestmentPlansFullDetails() {
        System.out.println("\n--- Investment Plan Full Details ---");
        if (investmentPlanHead == null) {
            System.out.println("No investment plans added yet.");
            return;
        }

        InvestmentPlanNode current = investmentPlanHead;
        while (current != null) {
            System.out.println("\n• Plan: " + current.name);
            System.out.println("  Monthly Investment: " + money.format(current.monthlyInvestment));
            System.out.println("  Expected Return Rate: " + current.monthlyRate + "% per month");
            System.out.println("  Total Duration: " + current.months + " months");
            System.out.println("  Months Remaining: " + current.monthsLeft + " months");
            System.out.println("  Total Invested: " + money.format(current.totalInvested));
            System.out.println("  Current Value: " + money.format(current.currentValue));
            System.out.println("  Growth: " + money.format(current.currentValue - current.totalInvested));
            current = current.next;
        }
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

        RecurringExpenditureNode recExp = recurringExpenditureHead;
        while (recExp != null) {
            System.out.println("• Recurring Expenditure: " + recExp.type + " - " + money.format(recExp.amount));
            balance -= recExp.amount;
            totalExpense += recExp.amount;
            recExp = recExp.next;
        }

        ExpenditureNode exp = expenditureHead;
        while (exp != null) {
            if (exp.monthYear.equals(monthYear)) {
                System.out.println("• One-Time Expenditure: " + exp.type + " - " + money.format(exp.amount));
                balance -= exp.amount;
                totalExpense += exp.amount;
            }
            exp = exp.next;
        }

        BorrowNode borrow = borrowHead;
        while (borrow != null) {
            if (borrow.isActive()) {
                double interest = borrow.getMonthlyInterest();
                System.out.println("• Loan Interest to " + borrow.from + ": -" + money.format(interest));
                balance -= interest;
                totalExpense += interest;
                borrow.reduceMonth();

                if (borrow.monthsLeft == 0) {
                    System.out.println("• Loan from " + borrow.from + " term completed.");
                }
            }
            borrow = borrow.next;
        }

        InvestmentPlanNode invest = investmentPlanHead;
        while (invest != null) {
            if (invest.isActive()) {
                System.out.println("• Investment: " + invest.name + " - " + money.format(invest.monthlyInvestment));
                balance -= invest.monthlyInvestment;
                totalExpense += invest.monthlyInvestment;
                invest.processMonth();
            }
            invest = invest.next;
        }

        System.out.println("→ Total Income this month: " + money.format(totalIncome));
        System.out.println("→ Total Expenses this month: " + money.format(totalExpense));
        System.out.println("→ New Balance: " + money.format(balance));
    }

    public void simulateMonths(LocalDate startDate, int months) {
        System.out.println("\n=== Simulating " + months + " months ===");
        double startingBalance = balance;
        double projectedBalance = balance;
        Expenditure tempFinanceManager = new Expenditure(); 

        RecurringIncomeNode currentRecIncome = recurringIncomeHead;
        while (currentRecIncome != null) {
            tempFinanceManager.addRecurringIncome(currentRecIncome.type, currentRecIncome.amount);
            currentRecIncome = currentRecIncome.next;
        }
        RecurringExpenditureNode currentRecExp = recurringExpenditureHead;
        while (currentRecExp != null) {
            tempFinanceManager.addRecurringExpenditure(currentRecExp.type, currentRecExp.amount);
            currentRecExp = currentRecExp.next;
        }
        BorrowNode currentBorrow = borrowHead;
        while (currentBorrow != null) {
            tempFinanceManager.borrow(currentBorrow.from, currentBorrow.principal, currentBorrow.rate, currentBorrow.monthsLeft);
            currentBorrow = currentBorrow.next;
        }
        InvestmentPlanNode currentInvest = investmentPlanHead;
        while (currentInvest != null) {
            tempFinanceManager.addInvestmentPlan(currentInvest.name, currentInvest.monthlyInvestment, currentInvest.monthlyRate, currentInvest.months);
            InvestmentPlanNode tempInvest = tempFinanceManager.investmentPlanHead;
            while (tempInvest != null && !tempInvest.name.equals(currentInvest.name)) {
                tempInvest = tempInvest.next;
            }
            if (tempInvest != null) {
                tempInvest.monthsLeft = currentInvest.monthsLeft;
                tempInvest.totalInvested = currentInvest.totalInvested;
                tempInvest.currentValue = currentInvest.currentValue;
            }
            currentInvest = currentInvest.next;
        }
        tempFinanceManager.balance = startingBalance;

        for (int i = 0; i < months; i++) {
            LocalDate currentMonth = startDate.plusMonths(i);
            System.out.println("\nMonth " + (i + 1) + ": " + currentMonth.format(DateTimeFormatter.ofPattern("MMMM")));
            tempFinanceManager.monthlyUpdate(currentMonth);
            projectedBalance = tempFinanceManager.balance;
            System.out.println("Projected Balance: " + money.format(projectedBalance));
        }

        System.out.println("\nStarting Balance: " + money.format(startingBalance));
        System.out.println("Projected Balance after " + months + " months: " + money.format(projectedBalance));
        System.out.println("Projected Change: " + money.format(projectedBalance - startingBalance));
    }
}