package de.Lavahayn.Main;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class EventListeners implements Listener {

	baseClass m_Server;

	public EventListeners(baseClass plugin) {
		m_Server = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	void onPlayerJoin(PlayerJoinEvent args) {
		args.setJoinMessage(null);
		m_Server.getServer().broadcastMessage("Willkommen " + args.getPlayer().getDisplayName() + " auf dem Server!");
		Player p = args.getPlayer();
		if (p.isOp()) {
			p.setGlowing(false);
		}
		p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);

	}

	@EventHandler
	void onPlayerDisconnect(PlayerQuitEvent args) {
		args.setQuitMessage(null);
	}

	@EventHandler
	void onAsyncPlayerChatEvent(AsyncPlayerChatEvent args) {
		args.setCancelled(true);
		m_Server.getServer().broadcastMessage("[" + args.getPlayer().getDisplayName() + "] " + args.getMessage());
	}

	@EventHandler
	void onServerListPing(ServerListPingEvent args) {
		args.setMotd(ChatColor.BLUE + "Willkommen bei AFAIK! Das ist nur eine Spielwiese für Senpai Thonny");
		try {
			args.setServerIcon(Bukkit.loadServerIcon(new File("server-icon.png")));
		} catch (Exception e) {
			m_Server.Log(Level.WARNING, e.getMessage());
		}
	}

	@EventHandler
	void onPlayerAttack(EntityDamageEvent args) {
		if (args instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) args;
			if (event.getDamager() instanceof Player) {
				Player player = (Player) event.getDamager();
				if (event.getEntity() instanceof Player) {
					player.removePotionEffect(PotionEffectType.INVISIBILITY);
					player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1));
				}

			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getItem() == null) {
			return;
		}

		Material type = event.getItem().getType();
		if(type.equals(Material.POTION)){
			event.setCancelled(true);
		}
	}
	

	@EventHandler
	public void onPotionSplash(PotionSplashEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onLingeringPotionSplash(LingeringPotionSplashEvent event){
		event.setCancelled(true);
	}
	

}
