package me.sub.abilitys.Events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.sub.abilitys.Files.Abilities;
import me.sub.abilitys.Main.Main;
import me.sub.abilitys.Utils.C;

public class UseAbility implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		try {
			if (!p.getItemInHand().getType().equals(null) && !p.getItemInHand().getType().equals(Material.AIR)) {
				if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
					for (String s : Abilities.get().getStringList("Abilities.FullList")) {
						if (Abilities.get().getString("Abilities." + s + ".Item").equals(p.getItemInHand().getType().toString())) {
							if (C.chat(Abilities.get().getString("Abilities." + s + ".Name")).equals(C.chat(p.getInventory().getItemInHand().getItemMeta().getDisplayName()))) {
								if (!Main.getInstance().cooldown.containsKey(p)) {
									if (Abilities.get().getString("Abilities." + s + ".Identity").equals("FIREWORK")) {
										if (!Main.getInstance().firework_cooldown.containsKey(p)) {
											Main.getInstance().firework_cooldown.put(p, (double) Abilities.get().getInt("Abilities." + s + ".Cooldown"));
											Main.getInstance().cooldown.put(p, Abilities.get().getDouble("Abilities.Cooldown"));
											e.setCancelled(true);
											p.sendMessage(C.chat("&e&oWoooosh"));
											p.setVelocity(p.getLocation().getDirection().multiply(2).add(new Vector(0,2,0)));
											Damageable player = (Damageable) p;
											if (p.getItemInHand().getAmount() > 1) {
												p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
											} else {
	
												p.getInventory().setItemInHand(new ItemStack(Material.AIR));
											}
											
											p.setFallDistance(0);
											
											new BukkitRunnable() {
												@SuppressWarnings("deprecation")
												@Override
												public void run() {
													if (!p.isOnGround()) {
														p.setHealth(player.getHealth());
														p.setFallDistance(0);
													} else {
														p.setHealth(player.getHealth());
														p.setFallDistance(0);
														cancel();
													}
												}
											}.runTaskTimer(Main.getInstance(), 0, 1);
											
											new BukkitRunnable() {
												@Override
												public void run() {
													int timeLeft = (int) Math.round(Main.getInstance().cooldown.get(p));
													
													int newTime = timeLeft - 1;
													if (newTime > 0) {
														Main.getInstance().cooldown.remove(p, Main.getInstance().cooldown.get(p));
														Main.getInstance().cooldown.put(p, (double) newTime);
													} else {
														p.sendMessage(C.chat("&eYour ability item cooldown has &aexpired&e."));
														Main.getInstance().cooldown.remove(p, Main.getInstance().cooldown.get(p));
														cancel();
													}
												}
											}.runTaskTimer(Main.getInstance(), 0, 20);
											
											new BukkitRunnable() {
												@Override
												public void run() {
													int timeLeft = (int) Math.round(Main.getInstance().firework_cooldown.get(p));
													
													int newTime = timeLeft - 1;
													if (newTime > 0) {
														Main.getInstance().firework_cooldown.remove(p, Main.getInstance().firework_cooldown.get(p));
														Main.getInstance().firework_cooldown.put(p, (double) newTime);
													} else {
														p.sendMessage(C.chat("&eYour " + Abilities.get().getString("Abilities." + s + ".displayName") + " &ecooldown has &aexpired&e."));
														Main.getInstance().firework_cooldown.remove(p, Main.getInstance().firework_cooldown.get(p));
														cancel();
													}
												}
											}.runTaskTimer(Main.getInstance(), 0, 20);
											
										} else {
											int timeLeft = (int) Math.round(Main.getInstance().firework_cooldown.get(p));
											p.sendMessage(C.chat("&cYou have a cooldown of " + timeLeft + "&c seconds on this item."));
										}
									} else if (Abilities.get().getString("Abilities." + s + ".Identity").equals("BESERK")) {
										if (!Main.getInstance().beserk_cooldown.containsKey(p)) {
											if (p.getItemInHand().getAmount() > 1) {
												p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
											} else {
	
												p.getInventory().setItemInHand(new ItemStack(Material.AIR));
											}
											Main.getInstance().beserk_cooldown.put(p, (double) Abilities.get().getInt("Abilities." + s + ".Cooldown"));
											Main.getInstance().cooldown.put(p, Abilities.get().getDouble("Abilities.Cooldown"));
											e.setCancelled(true);
											ArrayList<String> paths = new ArrayList<String>();
											for (int i = 0; i < 30; i++) {
												if (Abilities.get().isConfigurationSection("Abilities." + s + ".Effects." + String.valueOf(i))) {
													paths.add(String.valueOf(i));
												}
											}
											
											for (String value : paths) {
												PotionEffect potion = new PotionEffect(PotionEffectType.getByName(Abilities.get().getString("Abilities." + s + ".Effects." + value + ".Effect")), Abilities.get().getInt("Abilities." + s + ".Effects." + value + ".Time") * 20, Abilities.get().getInt("Abilities." + s + ".Effects." + value + ".Power"));
												p.addPotionEffect(potion);
											}
											
											p.sendMessage(C.chat("&eYou have gone &cBESERK&e."));
											
											new BukkitRunnable() {
												@Override
												public void run() {
													int timeLeft = (int) Math.round(Main.getInstance().cooldown.get(p));
													
													int newTime = timeLeft - 1;
													if (newTime > 0) {
														Main.getInstance().cooldown.remove(p, Main.getInstance().cooldown.get(p));
														Main.getInstance().cooldown.put(p, (double) newTime);
													} else {
														p.sendMessage(C.chat("&eYour ability item cooldown has &aexpired&e."));
														Main.getInstance().cooldown.remove(p, Main.getInstance().cooldown.get(p));
														cancel();
													}
												}
											}.runTaskTimer(Main.getInstance(), 0, 20);
											
											new BukkitRunnable() {
												@Override
												public void run() {
													int timeLeft = (int) Math.round(Main.getInstance().beserk_cooldown.get(p));
													
													int newTime = timeLeft - 1;
													if (newTime > 0) {
														Main.getInstance().beserk_cooldown.remove(p, Main.getInstance().beserk_cooldown.get(p));
														Main.getInstance().beserk_cooldown.put(p, (double) newTime);
													} else {
														p.sendMessage(C.chat("&eYour " + Abilities.get().getString("Abilities." + s + ".displayName") + " &ecooldown has &aexpired&e."));
														Main.getInstance().beserk_cooldown.remove(p, Main.getInstance().beserk_cooldown.get(p));
														cancel();
													}
												}
											}.runTaskTimer(Main.getInstance(), 0, 20);
										} else {
											int timeLeft = (int) Math.round(Main.getInstance().beserk_cooldown.get(p));
											p.sendMessage(C.chat("&cYou have a cooldown of " + timeLeft + "&c seconds on this item."));
										}
									}
								} else {
									int timeLeft = (int) Math.round(Main.getInstance().cooldown.get(p));
									p.sendMessage(C.chat("&cYou have a cooldown of " + timeLeft + "&c seconds on ability items."));
								}
							}
						}
					}
				}
			}
		} catch (Exception d) {}
	}

}
