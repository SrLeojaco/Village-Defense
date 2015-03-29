package me.chasertw123.villagedefense.game.building;

import java.io.File;
import java.util.ArrayList;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.BuildingCreationException;
import me.chasertw123.villagedefense.game.villager.VillagerChurch;
import me.chasertw123.villagedefense.utils.SchematicUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuildingChurch extends Building {

    private ArrayList<Location> alters;

    public BuildingChurch(Location center) throws BuildingCreationException {
        super(BuildingType.CHURCH, center, new VillagerChurch(center), 1, new ItemStack(Material.ENCHANTED_BOOK), "This building allows you to recieve random buffs or may even hurt you. The higher level the building the better chance for high level buffs.");
    }

    @Override
    public void levelUp(Main plugin) {
        // Build new tier, kill villagers, spawn new villager, update menus/
        if (getTier() >= getMaxTier())
            return;

        setTier(getTier() + 1);

        // Generate structure and spawn villagers
        SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Church" + getTier() + ".schematic"), this, true);

    }

    @Override
    public void buildFirstTier(Main plugin) {
        // Generate structure and spawn villagers
        SchematicUtil.build(new File(plugin.getDataFolder() + File.separator + "schematics", "Church" + getTier() + ".schematic"), this, false);
    }

    @Override
    public int costToUpgrade(int tier) {
        return tier == 1 ? 0 : ((tier - 1) * 1500) - 500;
    }

    /**
     * @return the alters
     */
    public ArrayList<Location> getAlters() {
        return alters;
    }

    /**
     * @param alters the alters to set
     */
    public void setAlters(ArrayList<Location> alters) {
        this.alters = alters;
    }

}
