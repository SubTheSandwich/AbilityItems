package me.sub.abilitys.Utils;

import org.bukkit.entity.Player;

public class Inventory {
	public static boolean isFull(Player p) {
		if (p.getInventory().firstEmpty() == -1) {
			return true;
		} else {
			return false;
		}
	}
}
