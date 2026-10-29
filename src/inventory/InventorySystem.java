package inventory;

import java.util.Scanner;

public class InventorySystem {
    public static void main(String[] args) {
        Inventory<Item> inventory = new Inventory<>();
        inventory.loadFromFile();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nInventory System");
            System.out.println("1. Add item");
            System.out.println("2. Remove item");
            System.out.println("3. View items");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    // Add an item
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter item quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter item price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();  // consume the newline

                    Item newItem = new Item(name, quantity, price);
                    inventory.addItem(newItem);
                    inventory.saveToFile();
                    break;

                case 2:
                    // Remove an item
                    System.out.print("Enter item name to remove: ");
                    String itemNameToRemove = scanner.nextLine();
                    Item itemToRemove = null;

                    // Search for the item using the getter method for items
                    for (Item item : inventory.getItems()) {
                        if (item.getName().equalsIgnoreCase(itemNameToRemove)) {
                            itemToRemove = item;
                            break;
                        }
                    }

                    if (itemToRemove != null) {
                        inventory.removeItem(itemToRemove);
                        inventory.saveToFile();
                    } else {
                        System.out.println("Item not found in inventory.");
                    }
                    break;

                case 3:
                    // View items
                    inventory.viewItems();
                    break;

                case 4:
                    // Exit
                    inventory.saveToFile();
                    exit = true;
                    System.out.println("Exiting Inventory System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}