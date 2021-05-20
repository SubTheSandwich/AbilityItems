package me.sub.abilitys.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.sub.abilitys.Files.Abilities;
import me.sub.abilitys.Utils.C;
import me.sub.abilitys.Utils.Inventory;

public class AbilityCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("abilitys.command.ability")) {
				if (args.length == 0) {
					p.sendMessage(C.chat("&cUsage: /ability give <player> <item> <amount>"));
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("give")) {
						p.sendMessage(C.chat("&cUsage: /ability give <player> <item> <amount>"));
					}
				} else if (args.length == 2) {
					p.sendMessage(C.chat("&cUsage: /ability give <player> <item> <amount>"));
				} else if (args.length == 3) {
					p.sendMessage(C.chat("&cUsage: /ability give <player> <item> <amount>"));
				} else if (args.length == 4) {
					if (args[0].equalsIgnoreCase("give")) {
						Player arg = Bukkit.getPlayer(args[1]);
						if (arg != null) {

							boolean contains = Abilities.get().getStringList("Abilities.FullList").contains(args[2].toLowerCase());
							if (contains) {
								Integer amount = Integer.parseInt(args[3]);
								if (amount != null) {
									ItemStack item = new ItemStack(Material.getMaterial(Abilities.get().getString("Abilities." + args[2].toLowerCase() + ".Item")), amount, (byte) Abilities.get().getInt("Abilities." + args[2].toLowerCase() + ".Byte"));
									ItemMeta meta = item.getItemMeta();
									
									meta.setDisplayName(C.chat(Abilities.get().getString("Abilities." + args[2].toLowerCase() + ".Name")));
									item.setItemMeta(meta);
									
									if (!Inventory.isFull(arg)) {
										p.getInventory().addItem(item);
										arg.sendMessage(C.chat("&a" + amount + "&ax &b" + args[2].toLowerCase() + " &eability item has been added to your inventory."));
										p.sendMessage(C.chat("&eYou have added &a" + amount + "&ax &b" + args[2].toLowerCase() + " &eability item to &d " + arg.getName() + "&e's inventory."));
									} else {
										arg.getWorld().dropItem(arg.getLocation(), item);
										arg.sendMessage(C.chat("&eSince your inventory is full, your &a" + amount + "&ax &b" + args[2].toLowerCase() + " &eability item has been dropped on the ground."));
										p.sendMessage(C.chat("&eYou have added &a" + amount + "&ax &b" + args[2].toLowerCase() + " &eability item to &d " + arg.getName() + "&e's inventory."));
									}
								} 
							} else {
								p.sendMessage(C.chat("&cUsage: /ability give <player> <item> <amount>"));
							}
						} else {
							p.sendMessage(C.chat("&cUsage: /ability give <player> <item> <amount>"));
						}
					} else {
						p.sendMessage(C.chat("&cUsage: /ability give <player> <item> <amount>"));
					}
				}
			} else {
				p.sendMessage(C.chat("&cNo permission."));
			}
		} else {
			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("give")) {
					Player arg = Bukkit.getPlayer(args[1]);
					if (arg != null) {
						boolean contains = Abilities.get().getStringList("Abilities.FullList").stream().anyMatch(args[2]::equalsIgnoreCase);
						if (contains) {
							Integer amount = Integer.parseInt(args[3]);
							if (amount != null) {
								
								ItemStack item = new ItemStack(Material.getMaterial(Abilities.get().getString("Abilities." + args[2].toLowerCase() + ".Item")), amount, (byte) Abilities.get().getInt("Abilities." + args[2].toLowerCase() + ".Byte"));
								ItemMeta meta = item.getItemMeta();
								
								meta.setDisplayName(C.chat(Abilities.get().getString("Abilities." + args[2].toLowerCase() + ".Name")));
								item.setItemMeta(meta);
								
								if (!Inventory.isFull(arg)) {
									arg.sendMessage(C.chat("&a" + amount + "&ax &b" + args[2].toLowerCase() + " &eability item has been added to your inventory."));
								} else {
									arg.getWorld().dropItem(arg.getLocation(), item);
									arg.sendMessage(C.chat("&eSince your inventory is full, your &a" + amount + "&ax &b" + args[2].toLowerCase() + " &eability item has been dropped on the ground."));
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

}
