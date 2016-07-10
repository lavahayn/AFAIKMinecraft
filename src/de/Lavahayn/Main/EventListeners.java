package de.Lavahayn.Main;
import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import net.md_5.bungee.api.ChatColor;

public class EventListeners implements Listener {
	
	baseClass m_Server;
	
	public EventListeners(baseClass plugin){
		m_Server = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	

	@EventHandler(priority = EventPriority.HIGHEST)
	void onPlayerJoin(PlayerJoinEvent args){
		args.setJoinMessage(null);
		m_Server.getServer().broadcastMessage("Willkommen " + args.getPlayer().getDisplayName() + " auf dem Server!");
	}
	
	@EventHandler
	void onPlayerDisconnect(PlayerQuitEvent args){
		args.setQuitMessage(null);
	}
	
	@EventHandler
	void onAsyncPlayerChatEvent(AsyncPlayerChatEvent args){
		args.setCancelled(true);
		m_Server.getServer().broadcastMessage("[" + args.getPlayer().getDisplayName() + "] " + args.getMessage());
	}
	
	@EventHandler
	void onServerListPing(ServerListPingEvent args){
		args.setMotd(ChatColor.BLUE + "Willkommen bei AFAIK! Das ist nur eine Spielwiese für Senpai Thonny");
		try {
			args.setServerIcon(Bukkit.loadServerIcon(new File("server-icon.png")));
		} catch (Exception e) {
			m_Server.Log(Level.WARNING, e.getMessage());
		}
	}
}
