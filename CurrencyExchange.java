package Project;

import java.util.Scanner;

public class CurrencyExchange {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double[] rates = {
                0.012,  // US Dollar (USD)
                0.011,  // European Euro (EUR)
                1.63,   // Japanese Yen (JPY)
                0.0096, // British Pound Sterling (GBP)
                0.016,  // Canadian Dollar (CAD)
                0.011,  // Swiss Franc (CHF)
                0.22,   // South African Rand (ZAR)
                0.019,  // New Zealand Dollar (NZD)
                0.0096, // Gibraltar Pound (GIP)
                0.083   // Chinese Yuan (CNY)
            };


        String[] currencies = {
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

        while (true) {
            System.out.println("Select the source currency:");
            for (int i = 0; i < currencies.length; i++) {
                System.out.printf("%d. %s%n", i + 1, currencies[i]);
            }
            int sourceIndex = scanner.nextInt() - 1;

            System.out.println("Select the target currency:");
            for (int i = 0; i < currencies.length; i++) {
                System.out.printf("%d. %s%n", i + 1, currencies[i]);
            }
            int targetIndex = scanner.nextInt() - 1;

            System.out.print("Enter the amount in " + currencies[sourceIndex] + ": ");
            double amount = scanner.nextDouble();

            double convertedAmount = amount * (rates[targetIndex] / rates[sourceIndex]);

            System.out.printf("%.2f %s is equal to %.2f %s%n",amount, currencies[sourceIndex],convertedAmount, currencies[targetIndex]);

            System.out.print("Do you want to perform another conversion? (yes/no): ");
            scanner.nextLine(); 
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("no")) {
                System.out.println("Thank you for using the currency converter!");
                break;
            }
        }

        scanner.close();
    }
}