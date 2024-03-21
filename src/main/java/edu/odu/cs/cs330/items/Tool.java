package edu.odu.cs.cs330.items;

import java.util.Scanner;

/**
 * This class represents one tool--as found in most video games. This includes
 * pickaxes and shovels.
 *
 * Tools may not be stacked. All Constructors must initialize Item::stackable
 * to false.
 */
public class Tool extends Item {
    /**
     * Durability decreases each time a tool is used.
     */
    protected int durability;

    /**
     * Base operation (e.g., harvest/mine) speed.
     */
    protected int speed;

    /**
     * Material out of which a tool is composed.
     */
    protected String material;

    /**
     * An enchantment (e.g., efficiency, fortune or unbreaking).
     */
    protected String modifier;

    /**
     * Enchantment level, usually in the range 1 to 5.
     */
    protected int modifierLevel;

    /**
     * Default to an unstackable tool with an empty name, zero durability, zero
     * speed, an empty material name, no modifier, and a modifier level of 1.
     */
    public Tool()
    {
        super("");

        this.durability    = 0;
        this.speed         = 0;
        this.material      = "";
        this.modifier      = "";
        this.modifierLevel = 1;
    }

    /**
     * Duplicate a Tool.
     *
     * @param src Tool to duplicate
     */
    public Tool(Tool src)
    {

    }

    /**
     * Retrieve tool durability.
     *
     * @return current durability
     */
    public int getDurability()
    {
        return this.durability;
    }

    /**
     * Change tool durability.
     *
     * @param dur new durability
     */
    public void setDurability(int dur)
    {
        this.durability = dur;
    }

    /**
     * Retrieve tool speed.
     *
     * @return how quickly a tool operates
     */
    public int getSpeed()
    {
        return this.speed;
    }

    /**
     * Set tool speed.
     *
     * @param spd new speed
     */
    public void setSpeed(int spd)
    {
        this.speed = spd;
    }

    /**
     * Retrieve tool Material.
     *
     * @return material out of which this tool is constructed
     */
    public String getMaterial()
    {
        return this.material;
    }

    /**
     * Set tool Material.
     *
     * @param mat replacement material
     */
    public void setMaterial(String mat)
    {
        this.material = mat;
    }

    /**
     * Retrieve tool modifier.
     *
     * @return current modifier
     */
    public String getModifier()
    {
        return this.modifier;
    }

    /**
     * Replace tool modifier.
     *
     * @param mod replacement modifier
     */
    public void setModifier(String mod)
    {
        this.modifier = mod;
    }

    /**
     * Retrieve tool modifier level.
     *
     * @return current modifier level
     */
    public int getModifierLevel()
    {
        return this.modifierLevel;
    }

    /**
     * Retrieve tool modifier level.
     *
     * @param level new buff/debuff level
     */
    public void setModifierLevel(int level)
    {
        this.modifierLevel = level;
    }

    @Override
    public boolean isStackable()
    {
        return false;
    }

    /**
     * Read tool attributes.
     */
    @Override
    public void read(Scanner snr)
    {
        // Complete this method
    }

    /**
     * Clone--i.e., copy--this Tool.
     */
    @Override
    public Item clone()
    {
        return new Tool(this);
    }

    /**
     * Check for logical equivalence--based on name, material, and modifier.
     *
     * @param rhs object for which a comparison is desired
     */
    @Override
    public boolean equals(Object rhs)
    {
        if (!(rhs instanceof Tool)) {
            return false;
        }

        Tool rhsItem = (Tool) rhs;

        // Replace the next line
        return false;
    }

    /**
     * Generate a hash code by adding the name, material, and modifier hash
     * codes.
     */
    @Override
    public int hashCode()
    {
        return this.name.hashCode()
             + this.material.hashCode()
             + this.modifier.hashCode();
    }

    /**
     * *Print* a Tool.
     */
    @Override
    public String toString()
    {
        return "";
    }
}
