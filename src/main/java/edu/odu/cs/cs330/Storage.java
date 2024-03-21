package edu.odu.cs.cs330;

import java.io.IOException;
import java.util.List;

import edu.odu.cs.cs330.items.Item;
import edu.odu.cs.cs330.items.ItemFactory;
import edu.odu.cs.cs330.items.ItemStack;
import edu.odu.cs.cs330.items.Inventory;
import edu.odu.cs.cs330.items.io.ItemParser;

/**
 * This is the Storage Driver. It contains the main function, supporting
 * functions, and all argument parsing.
 */
public class Storage {
    /**
     * Parse the inventory size from the command line arguments. If no size was
     * provided or the provided size is not valid use
     * {@link edu.odu.cs.cs330.items.Inventory#DEFAULT_SIZE}
     *
     * @param args command line arguments
     */
    public static int getInventorySize(String[] args)
    {
        int size = 0;

        try {
            size = Integer.parseInt(args[1]);
        }
        catch (IndexOutOfBoundsException | NumberFormatException e) {
            size = Inventory.DEFAULT_SIZE;
        }

        if (size < 1) {
            size = Inventory.DEFAULT_SIZE;
        }

        return size;
    }

    /**
     * This is the Item Storage Assignment in Java.
     *
     * @param argv user supplied item filename and (optional) inventory size.
     */
    public static void main(String[] argv)
    {
        if (argv.length < 1) {
            System.err.println("Usage: java -jar Storage.jar items-file");
            System.exit(1);
        }

        int invSize = getInventorySize(argv);

        List<Item> itemsToStore = null;
        try {
            itemsToStore = ItemParser.readItemsFromFile(argv[0]);
        }
        catch (IOException e) {
            System.err.printf("Error: %s could not be opened or read%n", argv[0]);
            System.exit(3);
        }

        Inventory inv = createInventory(itemsToStore, invSize);

        System.out.println("Player Storage Summary:");
        System.out.println(inv);
    }

    /**
     * Read an input stream and generate an Inventory.
     *
     * @param itemsToStore collection of items to place into an inventory
     * @param size desired number of Inventory slots
     *
     * @return initialized Inventory
     */
    public static Inventory createInventory(Iterable<Item> itemsToStore, int size)
    {
        Inventory inventory = new Inventory(size);

        System.out.println("Processing Log:");

        for (Item item : itemsToStore) {
            final boolean success = inventory.addItem(item);
            final char statusLetter = (success ? 'S' : 'D');

            System.out.printf(" (%s) %s%n", statusLetter, item.getName());
        }
        System.out.println();

        return inventory;
    }
}
