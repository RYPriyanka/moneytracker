package Project;
import java.util.Scanner;

public class Business {

    static class Property {
        String name;
        double investment;
        String location;
        String type;

        Property(String name, double investment, String location, String type) {
            this.name = name;
            this.investment = investment;
            this.location = location;
            this.type = type;
        }

        public String toString() {
            return "Name: " + name + ", Investment: " + investment + ", Location: " + location + ", Type: " + type;
        }
    }

    static class Node {
        Property data;
        Node prev, next;

        Node(Property data) {
            this.data = data;
        }
    }

    Node head = null, tail = null;
    int size = 0;

    void addProperty(String name, double investment, String location, String type) {
        Property p = new Property(name, investment, location, type);
        Node newNode = new Node(p);
        if (head == null) {
            head = tail = newNode;
            newNode.next = newNode.prev = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
            tail = newNode;
        }
        size++;
    }

    boolean propertyExists(String name) {
        if (head == null) return false;
        Node current = head;
        do {
            if (current.data.name.equalsIgnoreCase(name)) return true;
            current = current.next;
        } while (current != head);
        return false;
    }

    boolean deletePropertyByName(String name) {
        if (head == null) return false;
        Node current = head;
        do {
            if (current.data.name.equalsIgnoreCase(name)) {
                if (current == head && current == tail) {
                    head = tail = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    if (current == head) head = current.next;
                    if (current == tail) tail = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        } while (current != head);
        return false;
    }

    void updatePropertyDetails(String name, double investment, String location, String type) {
        if (head == null) {
            System.out.println("Property not found.");
            return;
        }
        Node current = head;
        do {
            if (current.data.name.equalsIgnoreCase(name)) {
                current.data.investment = investment;
                current.data.location = location;
                current.data.type = type;
                System.out.println("Property details updated.");
                return;
            }
            current = current.next;
        } while (current != head);
        System.out.println("Property not found.");
    }

    void displayByMinInvestment(double min) {
        if (head == null) return;
        Node current = head;
        do {
            if (current.data.investment >= min)
                System.out.println(current.data);
            current = current.next;
        } while (current != head);
    }

    double calculateTotalInvestment() {
        double total = 0;
        if (head == null) return 0;
        Node current = head;
        do {
            total += current.data.investment;
            current = current.next;
        } while (current != head);
        return total;
    }

    double averageInvestment() {
        return size == 0 ? 0 : calculateTotalInvestment() / size;
    }

    Property getMostExpensiveInvestment() {
        if (head == null) return null;
        Node current = head;
        Property max = current.data;
        do {
            if (current.data.investment > max.investment)
                max = current.data;
            current = current.next;
        } while (current != head);
        return max;
    }

    void generateReport() {
        System.out.println("--- PROPERTY REPORT ---");
        System.out.println("Total Properties: " + size);
        System.out.println("Total Investment: " + calculateTotalInvestment());
        System.out.println("Average Investment: " + averageInvestment());
        Property max = getMostExpensiveInvestment();
        if (max != null)
            System.out.println("Most Expensive Property: " + max);
        else
            System.out.println("No properties added.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Business biz = new Business();
        int choice;

        do {
            System.out.println("\n--- Property Management Menu ---");
            System.out.println("1. Add Property");
            System.out.println("2. Delete Property by Name");
            System.out.println("3. Update Property Details");
            System.out.println("4. Display Properties Above Investment");
            System.out.println("5. Check if Property Exists");
            System.out.println("6. Get Most Expensive Property");
            System.out.println("7. Generate Report");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Investment: ");
                    double inv = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter Location: ");
                    String loc = sc.nextLine();
                    System.out.print("Enter Type: ");
                    String type = sc.nextLine();
                    biz.addProperty(name, inv, loc, type);
                    break;
                case 2:
                    System.out.print("Enter Name to Delete: ");
                    String delName = sc.nextLine();
                    if (biz.deletePropertyByName(delName))
                        System.out.println("Deleted Successfully.");
                    else
                        System.out.println("Property not found.");
                    break;
                case 3:
                    System.out.print("Enter Name to Update: ");
                    String updName = sc.nextLine();
                    System.out.print("Enter New Investment: ");
                    double newInv = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter New Location: ");
                    String newLoc = sc.nextLine();
                    System.out.print("Enter New Type: ");
                    String newType = sc.nextLine();
                    biz.updatePropertyDetails(updName, newInv, newLoc, newType);
                    break;
                case 4:
                    System.out.print("Enter Minimum Investment: ");
                    double minInv = sc.nextDouble();
                    biz.displayByMinInvestment(minInv);
                    break;
                case 5:
                    System.out.print("Enter Name to Check: ");
                    String checkName = sc.nextLine();
                    if (biz.propertyExists(checkName))
                        System.out.println("Property exists.");
                    else
                        System.out.println("Property does not exist.");
                    break;
                case 6:
                    Property max = biz.getMostExpensiveInvestment();
                    if (max != null)
                        System.out.println("Most Expensive: " + max);
                    else
                        System.out.println("No properties available.");
                    break;
                case 7:
                    biz.generateReport();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 8);

        sc.close();
    }
}
