package inventory;

import java.io.File;

public class InventoryPersistenceTest {
    public static void main(String[] args) {
        File file = new File("inventory.dat");
            if (file.exists()) {
                file.delete();
        }

        Inventory<Item> inventory = new Inventory<>();
            inventory.addItem(new Item("Mouse", 3, 45.50));
            inventory.addItem(new Item("Teclado", 2, 99.99));
            inventory.saveToFile();

        Inventory<Item> loaded = new Inventory<>();
            loaded.loadFromFile();

        if (loaded.getItems().size() != 2) {
            throw new RuntimeException("Expected 2 items after loading from file.");
        }

        System.out.println("Persistence test passed.");
    }
}
