package de.Lavahayn.Main;

import java.io.Serializable;

import org.bukkit.inventory.ItemStack;

public class Recipe implements Serializable {
	public ItemStack upperLeft;
	public ItemStack upperMiddle;
	public ItemStack upperRight;
	public ItemStack middleLeft;
	public ItemStack middleMiddle;
	public ItemStack middleRight;
	public ItemStack downerLeft;
	public ItemStack downerMiddle;
	public ItemStack downerRight;
	public ItemStack result;
	private static final long serialVersionUID = 1788778415055879865L;
}
