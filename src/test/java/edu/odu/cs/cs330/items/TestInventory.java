package edu.odu.cs.cs330.items;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.hamcrest.core.IsNull;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.Scanner;


/**
 * 1 - Does this piece of code perform the operations
 *     it was designed to perform?
 *
 * 2 - Does this piece of code do something it was not
 *     designed to perform?
 *
 * 1 Test per mutator
 *
 * This is technically an Integration Test.
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestInventory
{
    final Inventory EMPTY_INVENTORY = new Inventory();

    Item[] TEST_ITEMS;

    @BeforeEach
    public void setUp()
    {
        Armour boots = new Armour();
        boots.read(
            new Scanner("Boots Diamond 100 10 FeatherFalling 4 lightning")
        );

        Consumable tomato = new Consumable();
        tomato.read(
            new Scanner("Tomato Hunger-10 2")
        );

        Tool shovel = new Tool();
        shovel.read(
            new Scanner(
                "Shovel Gold 20 3 Unbreaking 2"
            )
        );

        TEST_ITEMS = new Item[] {
            boots,
            tomato,
            shovel
        };
    }

    @Test
    public void testDefaultConstructor()
    {
        assertThat(EMPTY_INVENTORY.utilizedSlots(), equalTo(0));
        assertThat(EMPTY_INVENTORY.emptySlots(), equalTo(10));
        assertThat(EMPTY_INVENTORY.totalSlots(), equalTo(10));
        assertFalse(EMPTY_INVENTORY.isFull());
    }

    @Test
    public void testConstructorSizeN()
    {
        Inventory invWith8Slots = new Inventory(8);

        assertThat(invWith8Slots.utilizedSlots(), equalTo(0));
        assertThat(invWith8Slots.emptySlots(), equalTo(8));
        assertThat(invWith8Slots.totalSlots(), equalTo(8));
        assertFalse(invWith8Slots.isFull());
    }


    /**
     * Add ItemStacks to an Inventory without filling the Inventory or attempting
     * to add duplicate Items
     */
    @Test
    public void testAddItemStackNoCheck()
    {
        List<ItemStack> stacksToAdd = Arrays.asList(
            new ItemStack(TEST_ITEMS[0]),
            new ItemStack(TEST_ITEMS[1]),
            new ItemStack(TEST_ITEMS[2])
        );

        Inventory aBag = new Inventory(4);

        aBag.addItems(stacksToAdd.get(0));
        aBag.addItems(stacksToAdd.get(1));
        aBag.addItems(stacksToAdd.get(2));

        assertFalse(aBag.isFull());
        assertThat(aBag.utilizedSlots(), equalTo(3));
        assertThat(aBag.emptySlots(), equalTo(1));
        assertThat(aBag.totalSlots(), equalTo(4));

        // Retrieve each of the items and check that they were added
        Iterator<ItemStack> it = aBag.iterator();

        assertThat(it.next(), equalTo(stacksToAdd.get(0)));
        assertThat(it.next(), equalTo(stacksToAdd.get(1)));
        assertThat(it.next(), equalTo(stacksToAdd.get(2)));

        // Check that there are no more ItemStacks to retrieve
        assertFalse(it.hasNext());
    }

    /**
     * Add ItemStacks to an Inventory without filling the Inventory, but attempting
     * to add duplicate Items
     */
    @Test
    public void testAddItemWithDuplicateItems()
    {
        List<ItemStack> stacksToAdd = Arrays.asList(
            new ItemStack(TEST_ITEMS[0]),
            new ItemStack(TEST_ITEMS[1]),
            new ItemStack(TEST_ITEMS[1])
        );

        Inventory aBag = new Inventory(4);

        for (ItemStack stack : stacksToAdd) {
            aBag.addItems(stack);
        }

        assertFalse(aBag.isFull());
        assertThat(aBag.utilizedSlots(), equalTo(2));
        assertThat(aBag.emptySlots(), equalTo(2));
        assertThat(aBag.totalSlots(), equalTo(4));

        // Retrieve each of the items and check that they were added
        Iterator<ItemStack> it = aBag.iterator();

        assertThat(it.next(), equalTo(stacksToAdd.get(0)));

        // Expect the merged stack to be returned.
        ItemStack mergedStack = new ItemStack(TEST_ITEMS[1]);
        mergedStack.addItems(1);

        final ItemStack retrieved = it.next();

        assertThat(retrieved, equalTo(mergedStack));
        assertThat(retrieved.size(), equalTo(2));

        // Check that there are no more ItemStacks to retrieve
        assertThat(it.hasNext(), is(false));
    }

    /**
     * Add ItemStacks to an Inventory and fill it.
     * Then try to add one more ItemStack that is stackable.
     */
    @Test
    public void testAddItemAfterFullWithNonStackable()
    {
        List<ItemStack> stacksToAdd = Arrays.asList(
            new ItemStack(TEST_ITEMS[0]),
            new ItemStack(TEST_ITEMS[1]),
            new ItemStack(TEST_ITEMS[2])
        );

        Inventory aBag = new Inventory(2);

        aBag.addItems(stacksToAdd.get(0));
        aBag.addItems(stacksToAdd.get(1));

        assertThat(aBag.addItems(stacksToAdd.get(2)), is(false));

        assertThat(aBag.isFull(), is(true));
        assertThat(aBag.utilizedSlots(), equalTo(2));
        assertThat(aBag.emptySlots(), equalTo(0));
        assertThat(aBag.totalSlots(), equalTo(2));

        // Retrieve each of the items and check that they were added
        Iterator<ItemStack> it = aBag.iterator();

        assertThat(it.next(), equalTo(stacksToAdd.get(0)));
        assertThat(it.next(), equalTo(stacksToAdd.get(1)));

        // Check that there are no more ItemStacks to retrieve
        assertThat(it.hasNext(), is(false));
    }

    /**
     * Add ItemStacks to an Inventory and fill it.
     * Then try to add one more ItemStack that is **not** stackable.
     */
    @Test
    public void testAddItemAfterFullWithStackable()
    {
        List<ItemStack> stacksToAdd = Arrays.asList(
            new ItemStack(TEST_ITEMS[0]),
            new ItemStack(TEST_ITEMS[1])
        );

        Inventory aBag = new Inventory(2);

        aBag.addItems(stacksToAdd.get(0));
        aBag.addItems(stacksToAdd.get(1));
        aBag.addItems(stacksToAdd.get(0));

        assertThat(aBag.addItems(stacksToAdd.get(1)), is(true));

        assertThat(aBag.isFull(), is(true));
        assertThat(aBag.utilizedSlots(), equalTo(2));
        assertThat(aBag.emptySlots(), equalTo(0));
        assertThat(aBag.totalSlots(), equalTo(2));

        // Retrieve each of the items and check that they were added
        Iterator<ItemStack> it = aBag.iterator();

        assertThat(it.next(), equalTo(stacksToAdd.get(0)));
        assertThat(it.next(), equalTo(stacksToAdd.get(1)));

        // Check that there are no more ItemStacks to retrieve
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void testToString()
    {
        List<ItemStack> stacksToAdd = Arrays.asList(
            new ItemStack(TEST_ITEMS[0]),
            new ItemStack(TEST_ITEMS[1]),
            new ItemStack(TEST_ITEMS[2])
        );

        Inventory aBag = new Inventory(4);
        for (ItemStack stack : stacksToAdd) {
            aBag.addItems(stack);
        }

        List<String> itemsAsStrings = stacksToAdd.stream()
            .map(ItemStack::toString)
            .collect(java.util.stream.Collectors.toList());

        String aBagAsStr = aBag.toString();
        assertThat(aBagAsStr, stringContainsInOrder(Arrays.asList("75%", "of", "4", "slots")));
        assertThat(aBagAsStr, stringContainsInOrder(itemsAsStrings));
    }
}

