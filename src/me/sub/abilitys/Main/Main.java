package me.sub.abilitys.Main;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.sub.abilitys.Commands.AbilityCommand;
import me.sub.abilitys.Events.UseAbility;
import me.sub.abilitys.Files.Abilities;
import me.sub.abilitys.Files.Players;

public class Main extends JavaPlugin {
	
	public HashMap<Player, Double> firework_cooldown = new HashMap<Player, Double>();
	public HashMap<Player, Double> cooldown = new HashMap<Player, Double>();
	public HashMap<Player, Double> beserk_cooldown = new HashMap<Player, Double>();
	public HashMap<Player, Double> bard_cooldown = new HashMap<Player, Double>();
	private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }
    
	@Override
	public void onEnable() {
		files();
		events();
		commands();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	private void commands() {
		getCommand("ability").setExecutor(new AbilityCommand());
	}
	
	private void events() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new UseAbility(), this);
	}
	
	private void files() {
		Abilities.setup();
		
		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("INCREASE_DAMAGE"); list3.add("DAMAGE_RESISTANCE");
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("INCREASE_DAMAGE"); list2.add("DAMAGE_RESISTANCE");
		ArrayList<String> list = new ArrayList<String>();
		list.add("beserk"); list.add("firework");
		list.add("switcher"); list.add("portbard");
		Abilities.get().addDefault("Abilities.Cooldown", 10);
		Abilities.get().addDefault("Abilities.FullList", list);
		Abilities.get().addDefault("Abilities.beserk.Item", "INK_SACK");
		Abilities.get().addDefault("Abilities.beserk.Byte", (byte) 1);
		Abilities.get().addDefault("Abilities.beserk.Cooldown", 30);
		Abilities.get().addDefault("Abilities.beserk.Name", "&bBeserk &7(Right Click)");
		Abilities.get().addDefault("Abilities.beserk.Effects", list2);
		Abilities.get().addDefault("Abilities.beserk.Identity", "BESERK");
		Abilities.get().addDefault("Abilities.beserk.displayName", "Beserk");
		
		Abilities.get().addDefault("Abilities.firework.Item", "FIREWORK");
		Abilities.get().addDefault("Abilities.firework.Byte", (byte) 0);
		Abilities.get().addDefault("Abilities.firework.Cooldown", 15);
		Abilities.get().addDefault("Abilities.firework.Name", "&eFirework &7(Right Click)");
		Abilities.get().addDefault("Abilities.firework.Identity", "FIREWORK");
		Abilities.get().addDefault("Abilities.firework.displayName", "Firework");
		
		Abilities.get().addDefault("Abilities.portbard.Item", "MONSTER_EGG");
		Abilities.get().addDefault("Abilities.portbard.Byte", (byte) 61);
		Abilities.get().addDefault("Abilities.portbard.Cooldown", 120);
		Abilities.get().addDefault("Abilities.portbard.Name", "&dPortable Bard &7(Right Click)");
		Abilities.get().addDefault("Abilities.portbard.Identity", "PORTBARD");
		Abilities.get().addDefault("Abilities.portbard.displayName", "Portable Bard");
		
		for (String s : list2) {
			Abilities.get().addDefault("Abilities.beserk.Effects." + list2.indexOf(s) + ".Effect", s);
			Abilities.get().addDefault("Abilities.beserk.Effects." + list2.indexOf(s) + ".Time", 10);
			Abilities.get().addDefault("Abilities.beserk.Effects." + list2.indexOf(s) + ".Power", 1);
		}
		
		
		for (String s : list3) {
			Abilities.get().addDefault("Abilities.portbard.Effects." + list2.indexOf(s) + ".Effect", s);
			Abilities.get().addDefault("Abilities.portbard.Effects." + list2.indexOf(s) + ".Time", 5);
			Abilities.get().addDefault("Abilities.portbard.Effects." + list2.indexOf(s) + ".Power", 1);
		}
		Abilities.get().options().copyDefaults(true);
		Abilities.save();
		
		
		Players.setup();
		Players.save();
	}

}
