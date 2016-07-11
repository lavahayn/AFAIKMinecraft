package de.Lavahayn.Main;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class baseClass extends JavaPlugin {

	List<Recipe> recipes;
	RecipeManager recipeManager;
	EventListeners eventListeners;

	@Override
	public void onEnable() {
		eventListeners = new EventListeners(this);
		recipeManager = new RecipeManager(this);
		CreateAllData();
		Cook();
	}

	@Override
	public void onDisable() {
		recipeManager.SerializeRecipes();
	}

	private void Cook() {
		this.getServer().clearRecipes();
		recipeManager.DeserializeRecipes();
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
				recipeManager.AddNewRecipe(sender);
			} else {
				NotifyOnlyPlayers(sender);
			}
		}
		return blnReturn;
	}

	private void NotifyOnlyPlayers(CommandSender sender) {
		sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden");
		
	}

}
