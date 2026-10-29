package inventory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Inventory<T> {
    private static final String FILE_NAME = "inventory.dat";
    private List<T> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(items);
            System.out.println("Inventory saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved inventory found. Starting with an empty inventory.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object data = in.readObject();
            if (data instanceof List<?>) {
                items = (List<T>) data;
                System.out.println("Inventory loaded from " + FILE_NAME);
            } else {
                System.out.println("Saved file format is invalid.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    // Add an item to the inventory
    public void addItem(T item) {
        try {
            items.add(item);
            System.out.println("Item added successfully: " + item);
        } catch (Exception e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    // Remove an item from the inventory
    public void removeItem(T item) {
        try {
            if (!items.contains(item)) {
                throw new IllegalArgumentException("Item not found in inventory.");
            }
            items.remove(item);
            System.out.println("Item removed successfully: " + item);
        } catch (Exception e) {
            System.out.println("Error removing item: " + e.getMessage());
        }
    }

    // View all items in the inventory
    public void viewItems() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory items:");
            for (T item : items) {
                System.out.println(item);
            }
        }
    }

    // Getter method for items
    public List<T> getItems() {
        return items;
    }
}