package dev.imlukas.bedwarsinventoryswapper.data.settings;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents the plugin settings.
 */
public class Settings {

    private final World worldToSwap;
    public Settings(FileConfiguration config) {
        this.worldToSwap = Bukkit.getWorld(config.getString("world"));
    }

    public World getWorldToSwap() {
        return worldToSwap;
    }
}
