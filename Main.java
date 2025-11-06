package Project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Expenditure financeManager = new Expenditure();
        Business businessManager = new Business(); 
        CurrencyExchange currencyConverter = new CurrencyExchange(); 
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("Welcome! Today's date: " + currentDate + "\n");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Finance Management");
            System.out.println("2. Property Management (Business)");
            System.out.println("3. Currency Exchange");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    int financeChoice;
                    do {
                        System.out.println("\n====== Finance Manager Menu ======");
                        System.out.println("1. Add One-Time Income");
                        System.out.println("2. Add Recurring Income");
                        System.out.println("3. Lend Money");
                        System.out.println("4. Add One-Time Expenditure");
                        System.out.println("5. Add Recurring Expenditure");
                        System.out.println("6. Borrow Money");
                        System.out.println("7. Add Investment Plan");
                        System.out.println("8. Monthly Update");
                        System.out.println("9. Simulate Months Ahead");
                        System.out.println("10. Show Balance");
                        System.out.println("11. Show Investment Plans");
                        System.out.println("12. Set Custom Date");
                        System.out.println("13. Back to Main Menu");
                        System.out.print("Choose an option: ");
                        financeChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (financeChoice) {
                            case 1:
                                System.out.print("Enter type: ");
                                String type = scanner.nextLine();
                                System.out.print("Enter amount: ");
                                double amount = scanner.nextDouble();
                                scanner.nextLine();
                                financeManager.addOneTimeIncome(type, amount, currentDate);
                                break;
                            case 2:
                                System.out.print("Enter recurring type: ");
                                String rType = scanner.nextLine();
                                System.out.print("Enter amount: ");
                                double rAmount = scanner.nextDouble();
                                scanner.nextLine();
                                financeManager.addRecurringIncome(rType, rAmount);
                                break;
                            case 3:
                                System.out.print("Enter name of borrower: ");
                                String name = scanner.nextLine();
                                System.out.print("Enter amount: ");
                                double lAmount = scanner.nextDouble();
                                System.out.print("Enter annual interest rate (%): ");
                                double rate = scanner.nextDouble();
                                System.out.print("Enter duration (months): ");
                                int months = scanner.nextInt();
                                scanner.nextLine();
                                financeManager.lend(name, lAmount, rate, months);
                                break;
                            case 4:
                                System.out.print("Enter expenditure type: ");
                                String expType = scanner.nextLine();
                                System.out.print("Enter amount: ");
                                double expAmount = scanner.nextDouble();
                                scanner.nextLine();
                                financeManager.addOneTimeExpenditure(expType, expAmount, currentDate);
                                break;
                            case 5:
                                System.out.print("Enter recurring expenditure type: ");
                                String recExpType = scanner.nextLine();
                                System.out.print("Enter amount: ");
                                double recExpAmount = scanner.nextDouble();
                                scanner.nextLine();
                                financeManager.addRecurringExpenditure(recExpType, recExpAmount);
                                break;
                            case 6:
                                System.out.print("Enter name of lender: ");
                                String lender = scanner.nextLine();
                                System.out.print("Enter amount: ");
                                double bAmount = scanner.nextDouble();
                                System.out.print("Enter annual interest rate (%): ");
                                double bRate = scanner.nextDouble();
                                System.out.print("Enter duration (months): ");
                                int bMonths = scanner.nextInt();
                                scanner.nextLine();
                                financeManager.borrow(lender, bAmount, bRate, bMonths);
                                break;
                            case 7:
                                System.out.print("Enter investment plan name: ");
                                String planName = scanner.nextLine();
                                System.out.print("Enter monthly investment amount: ");
                                double invAmount = scanner.nextDouble();
                                System.out.print("Enter expected monthly return rate (%): ");
                                double invRate = scanner.nextDouble();
                                System.out.print("Enter duration (months): ");
                                int invMonths = scanner.nextInt();
                                scanner.nextLine();
                                financeManager.addInvestmentPlan(planName, invAmount, invRate, invMonths);
                                break;
                            case 8:
                                financeManager.monthlyUpdate(currentDate);
                                break;
                            case 9:
                                System.out.print("Enter number of months to simulate: ");
                                int simMonths = scanner.nextInt();
                                scanner.nextLine();
                                financeManager.simulateMonths(currentDate, simMonths);
                                break;
                            case 10:
                                financeManager.showBalance();
                                break;
                            case 11:
                                financeManager.showInvestmentPlans();
                                break;
                            case 12:
                                System.out.print("Enter new current month and year (numeric format e.g. 2025-04): ");
                                String newDateStr = scanner.nextLine();
                                newDateStr += "-01";
                                currentDate = LocalDate.parse(newDateStr, dateFormatter);
                                System.out.println("Current date set to: " + currentDate);
                                break;
                            case 13:
                                System.out.println("Back to Main Menu.");
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                    } while (financeChoice != 13);
                    break;

                case 2:
                    int propertyChoice;
                    do {
                        System.out.println("\n--- Property Management Menu ---");
                        System.out.println("1. Add Property");
                        System.out.println("2. Delete Property by Name");
                        System.out.println("3. Update Property Details");
                        System.out.println("4. Display Properties Above Investment");
                        System.out.println("5. Check if Property Exists");
                        System.out.println("6. Get Most Expensive Property");
                        System.out.println("7. Generate Report");
                        System.out.println("8. Back to Main Menu");
                        System.out.print("Enter your choice: ");
                        propertyChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (propertyChoice) {
                            case 1:
                                System.out.print("Enter Name: ");
                                String name = scanner.nextLine();
                                System.out.print("Enter Investment: ");
                                double inv = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.print("Enter Location: ");
                                String loc = scanner.nextLine();
                                System.out.print("Enter Type: ");
                                String type = scanner.nextLine();
                                businessManager.addProperty(name, inv, loc, type); // Call Business method
                                break;
                            case 2:
                                System.out.print("Enter Name to Delete: ");
                                String delName = scanner.nextLine();
                                if (businessManager.deletePropertyByName(delName)) // Call Business method
                                    System.out.println("Deleted Successfully.");
                                else
                                    System.out.println("Property not found.");
                                break;
                            case 3:
                                System.out.print("Enter Name to Update: ");
                                String updName = scanner.nextLine();
                                System.out.print("Enter New Investment: ");
                                double newInv = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.print("Enter New Location: ");
                                String newLoc = scanner.nextLine();
                                System.out.print("Enter New Type: ");
                                String newType = scanner.nextLine();
                                businessManager.updatePropertyDetails(updName, newInv, newLoc, newType); // Call Business method
                                break;
                            case 4:
                                System.out.print("Enter Minimum Investment: ");
                                double minInv = scanner.nextDouble();
                                businessManager.displayByMinInvestment(minInv); // Call Business method
                                break;
                            case 5:
                                System.out.print("Enter Name to Check: ");
                                String checkName = scanner.nextLine();
                                if (businessManager.propertyExists(checkName)) // Call Business method
                                    System.out.println("Property exists.");
                                else
                                    System.out.println("Property does not exist.");
                                break;
                            case 6:
                                Project.Business.Property max = businessManager.getMostExpensiveInvestment(); // Call Business method
                                if (max != null)
                                    System.out.println("Most Expensive: " + max);
                                else
                                    System.out.println("No properties available.");
                                break;
                            case 7:
                                businessManager.generateReport(); // Call Business method
                                break;
                            case 8:
                                System.out.println("Back to Main Menu.");
                                break;
                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                    } while (propertyChoice != 8);
                    break;

                case 3:
                    CurrencyExchange.main(null); 
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}