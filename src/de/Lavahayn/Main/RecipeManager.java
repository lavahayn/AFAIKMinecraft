package de.Lavahayn.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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

	public void AddNewRecipe(CommandSender sender) {
		try {
			PlayerInventory inventory = ((Player) sender).getInventory();
			ItemStack secondHandItem = ((Player) sender).getInventory().getItemInOffHand();
			if (secondHandItem.getType() != Material.AIR) {
				Recipe craftingRecipe = new Recipe();
				Field[] fields = craftingRecipe.getClass().getFields();
				for (int i = 0; i < 9; i++) {
					try {
						fields[i].set(craftingRecipe, inventory.getItem(i));
					} catch (Exception e) {
						m_Server.Log(Level.WARNING, e.getMessage());
					}
				}
				craftingRecipe.result = inventory.getItemInOffHand();
				boolean blnContinue = true;
				if (m_Server.recipes == null) {
					m_Server.recipes = new ArrayList<Recipe>();
				}
				m_Server.Log(Level.WARNING, "" + m_Server.recipes.toArray().length);
				for (int i = 0; i < m_Server.recipes.size(); i++) {
					Recipe recipe = (Recipe) m_Server.recipes.get(i);
					if (recipe.upperLeft == craftingRecipe.upperLeft && recipe.upperMiddle == craftingRecipe.upperMiddle
							&& recipe.upperRight == craftingRecipe.upperRight
							&& recipe.middleLeft == craftingRecipe.middleLeft
							&& recipe.middleMiddle == craftingRecipe.middleMiddle
							&& recipe.middleRight == craftingRecipe.middleRight
							&& recipe.downerLeft == craftingRecipe.downerLeft
							&& recipe.downerMiddle == craftingRecipe.downerMiddle
							&& recipe.downerRight == craftingRecipe.downerRight) {
						blnContinue = false;
						continue;
					}
				}
				if (blnContinue) {
					m_Server.recipes.add(craftingRecipe);
					sender.sendMessage("Ein neues Craftingrezept wurde angelegt");
				} else {
					sender.sendMessage("Es existiert bereits ein Rezept, dass aus den angegeben Items besteht");
				}
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
	}
}
