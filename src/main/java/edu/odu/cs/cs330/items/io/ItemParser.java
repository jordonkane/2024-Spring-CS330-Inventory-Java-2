package edu.odu.cs.cs330.items.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import edu.odu.cs.cs330.items.Item;
import edu.odu.cs.cs330.items.ItemFactory;

public class ItemParser
{
    private ItemParser()
    {
    }

    /**
     * Read an input stream and generate a collection of Items.
     *
     * @param ins source from which to read Items
     *
     * @return initialized list of Items
     *
     * @throws IOException if an input error occurs
     */
    public static List<Item> readItems(BufferedReader ins)
        throws IOException
    {
        return ins
            .lines()
            .map((String line) -> ItemFactory.parseItemLine(line))
            .filter(Objects::nonNull) // remove all nulls
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Read an input stream and generate a collection of Items.
     *
     * @param filename source from which to read Items
     *
     * @return initialized list of Items
     *
     * @throws IOException if an input error occurs
     */
    public static List<Item> readItemsFromFile(String filename)
        throws IOException
    {
        FileReader inFile = new FileReader(filename);
        BufferedReader buffer = new BufferedReader(inFile);

        List<Item> itemsToStore = ItemParser.readItems(buffer);
        buffer.close();

        return itemsToStore;
    }
}
