package de.Lavahayn.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public class baseClass extends JavaPlugin {

	List<Recipe> m_Recipes = new ArrayList<Recipe>();
	RecipeManager m_RecipeManager;
	EventListeners m_EventListeners;
	private DatabaseAccess m_DatabaseAccess = new DatabaseAccess(this);
	
	public List<Recipe> getRecipes() {
		return m_Recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		m_Recipes = recipes;
	}

	public DatabaseAccess getM_DatabaseAccess() {
		return m_DatabaseAccess;
	}

	public void setM_DatabaseAccess(DatabaseAccess m_DatabaseAccess) {
		this.m_DatabaseAccess = m_DatabaseAccess;
	}

	@Override
	public void onEnable() {
		m_EventListeners = new EventListeners(this);
		m_RecipeManager = new RecipeManager(this);
		CreateAllData();
		Cook();
		m_DatabaseAccess.MaintainceDatabase();
	}

	@Override
	public void onDisable() {
		m_RecipeManager.SerializeRecipes();
	}

	private void Cook() {
		this.getServer().resetRecipes();
		m_RecipeManager.DeserializeRecipes();
	}

	private void CreateAllData() {
		SerializeConfigFile();
	}

	private void SerializeConfigFile() {
		File configFile;
		try {
			configFile = new File(getDataFolder() + File.separator + "config.xml");
			if (!configFile.exists()) {
				// TODO: Create Config file
			}
		} catch (Exception e) {
			Log(Level.WARNING, e.getMessage());
			Log(Level.FINEST, e.toString());
		}
	}

	public void Log(Level level, String msg) {
		Log(level, msg, null);
	}

	public void Log(Level level, String msg, Throwable thrown) {
		this.getServer().getLogger().log(level, msg, thrown);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		boolean blnReturn = false;
		String strCommandName = cmd.getName();
		if (strCommandName.equalsIgnoreCase("h") || strCommandName.equalsIgnoreCase("hochzeit")) {
			if (args.length == 0) {
				blnReturn = true;
			}
			sender.sendMessage("A executed");
			blnReturn = true;
		} else if (strCommandName.equalsIgnoreCase("addnewrecipe")) {

			blnReturn = true;
			if (sender instanceof Player) {
				m_RecipeManager.AddNewRecipe(sender);
			} else {
				NotifyOnlyPlayers(sender);
			}
		} else if (cmd.getName().equalsIgnoreCase("wipeallrecipes")) {
			blnReturn = true;
			m_RecipeManager.WipeAllRecipes();
		} else if (cmd.getName().equalsIgnoreCase("removerecipeat")) {
			blnReturn = true;
			try {
				getServer().resetRecipes();
				m_Recipes.remove(Integer.parseInt(args[0]));
				for (Recipe recipe : m_Recipes) {
					getServer().addRecipe(recipe);
				}
			} catch (NumberFormatException e) {
				sender.sendMessage(e.getMessage());
			}
		} else if (cmd.getName().equalsIgnoreCase("listserverrecipes")) {
			blnReturn = true;

			if (m_Recipes.size() == 0) {
				sender.sendMessage("Es gibt aktuell keine eigenen Rezepte");
			} else {
				sender.sendMessage("Verfügbare Rezepte:");
				for (int i = 0; i < m_Recipes.size(); i++) {
					sender.sendMessage("[" + i + "] " + m_Recipes.get(i).getResult().toString());
				}
			}

		}
		return blnReturn;
	}

	private void NotifyOnlyPlayers(CommandSender sender) {
		sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden");

	}


}
