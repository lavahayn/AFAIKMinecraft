package de.Lavahayn.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.print.attribute.standard.Chromaticity;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import net.md_5.bungee.api.ChatColor;

public class RecipeManager {
	baseClass m_Server;

	public RecipeManager(baseClass plugin) {
		m_Server = plugin;
	}

	public void SerializeRecipes() {
		try {
			FileOutputStream fileOut = new FileOutputStream(m_Server.getDataFolder() + File.separator + "recipes.bin");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(m_Server.recipes);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			m_Server.Log(Level.WARNING, e.getMessage());
			m_Server.Log(Level.INFO, "Is the plugin maybe starting the first time?");
		}
	}

	@SuppressWarnings("deprecation")
	public void AddNewRecipe(CommandSender sender) {
		try {
			PlayerInventory inventory = ((Player) sender).getInventory();
			ItemStack secondHandItem = inventory.getItemInOffHand();
			if (secondHandItem.getType() != Material.AIR) {
				if (m_Server.recipes == null) {
					m_Server.recipes = new ArrayList<Recipe>();
				}
				m_Server.Log(Level.WARNING, "Created Array List");
				ShapedRecipe craftingRecipe = new ShapedRecipe(inventory.getItemInOffHand());
				
				m_Server.Log(Level.WARNING, "Instanciated Shaped Recipe");
				craftingRecipe.shape("ABC","DEF","GHI");
				for (int i = 0; i < 9; i++) {
					m_Server.Log(Level.WARNING, i + " time");
					ItemStack currentItem = inventory.getItem(i);
					Material currentMaterial = null;
					
					if (currentItem == null) {
						currentMaterial = Material.AIR;
					} else {
						currentMaterial = Material.getMaterial(currentItem.getTypeId());
					}
					craftingRecipe.setIngredient((char)(i +65), currentMaterial);

				}
				m_Server.recipes.add(craftingRecipe);

				m_Server.getServer().addRecipe(craftingRecipe);
				sender.sendMessage("Ein neues Craftingrezept wurde angelegt");

			} else if (secondHandItem.getType() == Material.AIR) {
				sender.sendMessage(
						"Du musst ein Item in der Off-Hand halten, dass aus dem Crafting Rezept bestehen soll."
								+ "Das Craftingrezept besteht aus den Items in deiner Itemleiste, wobei der erste Slot dem Feld "
								+ "oben links entspricht und der neunte Slot dem feld unten rechts."
								+ "Um einen Slot im Craftingrezept leer zu lassen musst du den entsprechenden Slot in deiner "
								+ "Itemleiste freilassen.");
			} else {
				sender.sendMessage("Das Craftingrezept muss aus neun eingehenden Items und einem herauskommenden Item,"
						+ " das du in der Hand hältst, bestehen");
			}
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Während der Ausführung des Befehls ist ein Fehler aufgetreten");
			m_Server.Log(Level.WARNING, e.getMessage());
			m_Server.Log(Level.WARNING, e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public void DeserializeRecipes() {
		try {
			FileInputStream fileIn = new FileInputStream(m_Server.getDataFolder() + File.separator + "recipes.bin");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object deserializedList = in.readObject();
			m_Server.recipes = (List<Recipe>) deserializedList;
			in.close();
			fileIn.close();
		} catch (Exception e) {
			m_Server.Log(Level.WARNING, e.getMessage());
		}
		for (Recipe recipe : m_Server.recipes) {
			m_Server.getServer().addRecipe(recipe);
		}
	}

	public void WipeAllRecipes() {
		m_Server.recipes.clear();
		m_Server.getServer().resetRecipes();
	}
}
