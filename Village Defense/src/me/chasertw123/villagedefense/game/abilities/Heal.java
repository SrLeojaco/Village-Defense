package me.chasertw123.villagedefense.game.abilities;

import me.chasertw123.villagedefense.exceptions.AbilityCreationException;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Heal extends Ability {

	public Heal() throws AbilityCreationException {
		super("Heal", 3, new int[] {1,2,3}, new int[] {1,2,3}, AbilityType.PRIMARY, new ItemStack(Material.DIRT));
	}

	@Override
	public void play(Object... args) {
		
		Player healer = (Player) args[0];
		
		for (Object  o : args) {
			Player healed = (Player) o;
			healed.setHealth(healed.getHealth() + (getTier() * 2));
		
			for (Player pl : Bukkit.getOnlinePlayers())
				pl.spigot().playEffect(healed.getLocation().clone().add(0, 1.8, 0), Effect.HEART, 0, 0, 0, 0, 0, 1, 5, 1);
		
			if (healer == healed)
				healer.sendMessage("You healed yourself");
			else
				healed.sendMessage(healer.getName() + " healed you");
		}
	}
	
}