package me.chasertw123.villagedefense.utils;

import java.io.File;

import me.chasertw123.villagedefense.exceptions.BuildingSpawnException;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.building.BuildingChurch;
import me.chasertw123.villagedefense.game.building.BuildingType;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;

@SuppressWarnings("deprecation")
public class SchematicUtil {

    public static void build(File file, Building b, boolean display) throws BuildingSpawnException {

        boolean placedVillager = false;

        try {

            EditSession es = new EditSession(new BukkitWorld(b.getCenter().getWorld()), 999999999);
            CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
            Vector v = new Vector(b.getCenter().getBlockX(), b.getCenter().getBlockY(), b.getCenter().getBlockZ());

            cc.setOrigin(v);
            cc.paste(es, v, false);

            if (display)
                b.getCenter().getWorld().playSound(b.getCenter(), Sound.ANVIL_USE, 1F, 1F);

            for (int x = 0; x < cc.getWidth(); x++)
                for (int y = 0; y < cc.getHeight(); y++)
                    for (int z = 0; z < cc.getLength(); z++) {

                        Block bl = b.getCenter().getWorld().getBlockAt(v.getBlockX() + x, v.getBlockY() + y, v.getBlockZ() + z);

                        if (display && bl.getType() == Material.AIR)
                            bl.getLocation().getWorld().spigot().playEffect(bl.getLocation(), Effect.FLYING_GLYPH);

                        if (b.getType() == BuildingType.CHURCH && bl.getType() == Material.ENCHANTMENT_TABLE)
                            ((BuildingChurch) b).getAlters().add(bl.getLocation());

                        if (bl.getType() == Material.SPONGE && !placedVillager) {

                            bl.setType(Material.AIR);

                            b.getVillager().setLoc(new Location(b.getCenter().getWorld(), x + v.getBlockX(), y + v.getBlockY(), v.getBlockZ() + z).add(0.5, 0, 0.5));
                            b.getVillager().spawnVillager();

                            placedVillager = true;
                        }
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!placedVillager)
            throw new BuildingSpawnException("Failed to find villager spawn location!");
    }
}
