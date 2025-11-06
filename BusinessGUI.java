package Project;

import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.List;

public class BusinessGUI {

    private List<Property> properties = new ArrayList<>();
    
    public static class Property {
        private String name;
        private double investment;
        private String location;
        private String type;

        public Property(String name, double investment, String location, String type) {
            this.name = name;
            this.investment = investment;
            this.location = location;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public double getInvestment() {
            return investment;
        }

        public String getLocation() {
          return location;
        }

        public String getType() {
          return type;
        }

        public void setInvestment(double investment) {
            this.investment = investment;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Investment: " + investment + ", Location: " + location + ", Type: " + type;
        }
    }

    public void addProperty(String name, double investment, String location, String type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be null or empty.");
        }
        if (investment <= 0) {
            throw new IllegalArgumentException("Investment must be greater than zero.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty");
        }
        for (Property property : properties) {
            if (property.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Property with this name already exists.");
            }
        }
        properties.add(new Property(name, investment, location, type));
    }

    public boolean propertyExists(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false; 
        }
        for (Property property : properties) {
            if (property.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean deletePropertyByName(String name) {
       if (name == null || name.trim().isEmpty()) {
            return false; 
        }
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getName().equalsIgnoreCase(name)) {
                properties.remove(i);
                return true;
            }
        }
        return false;
    }

    public void updatePropertyDetails(String name, double investment, String location, String type) {
        if (name == null || name.trim().isEmpty()) {
             throw new IllegalArgumentException("Property name cannot be null or empty.");
        }
        if (investment <= 0) {
            throw new IllegalArgumentException("Investment must be greater than zero.");
        }
         if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty");
        }

        for (Property property : properties) {
            if (property.getName().equalsIgnoreCase(name)) {
                property.setInvestment(investment);
                property.setLocation(location);
                property.setType(type);
                return;
            }
        }
        throw new IllegalArgumentException("Property not found: " + name);
    }
    

    public List<Property> getProperties() {
        return properties;
    }

    public void displayByMinInvestment(double min) {
        if (min < 0) {
            throw new IllegalArgumentException("Minimum investment cannot be negative.");
        }
        for (Property property : properties) {
            if (property.getInvestment() >= min) {
                System.out.println(property); 
            }
        }
    }

    public double calculateTotalInvestment() {
        double total = 0;
        for (Property property : properties) {
            total += property.getInvestment();
        }
        return total;
    }

    public double averageInvestment() {
        return properties.isEmpty() ? 0 : calculateTotalInvestment() / properties.size();
    }

    public Property getMostExpensiveInvestment() {
        if (properties.isEmpty()) {
            return null;
        }
        Property max = properties.get(0);
        for (Property property : properties) {
            if (property.getInvestment() > max.getInvestment()) {
                max = property;
            }
        }
        return max;
    }

    public void generateReport() {
        System.out.println("--- PROPERTY REPORT ---");
        System.out.println("Total Properties: " + properties.size());
        System.out.println("Total Investment: " + calculateTotalInvestment());
        System.out.println("Average Investment: " + averageInvestment());
        Property max = getMostExpensiveInvestment();
        if (max != null) {
            System.out.println("Most Expensive Property: " + max);
        } else {
            System.out.println("No properties added.");
        }
    }

    public void generateReportToTextArea(JTextArea textArea) {
        textArea.append("--- PROPERTY REPORT ---\n");
        textArea.append("Total Properties: " + properties.size() + "\n");
        textArea.append("Total Investment: " + calculateTotalInvestment() + "\n");
        textArea.append("Average Investment: " + averageInvestment() + "\n");
        Property max = getMostExpensiveInvestment();
        if (max != null) {
            textArea.append("Most Expensive Property: " + max + "\n");
        } else {
            textArea.append("No properties added.\n");
        }
        textArea.append("--- End of Report --- \n");
    }
}

