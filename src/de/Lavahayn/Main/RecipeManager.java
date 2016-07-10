package de.Lavahayn.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;

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
