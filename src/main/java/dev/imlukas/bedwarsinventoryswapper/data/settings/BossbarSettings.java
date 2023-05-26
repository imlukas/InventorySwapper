package dev.imlukas.bedwarsinventoryswapper.data.settings;

import dev.imlukas.bedwarsinventoryswapper.utils.text.TextUtils;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class BossbarSettings {

    private final String text;
    private final BarColor color;

    public BossbarSettings(ConfigurationSection barSection) {
        text = TextUtils.color(barSection.getString("text"));
        color = BarColor.valueOf(barSection.getString("color"));
    }

    public BarColor getColor() {
        return color;
    }

    public String getText() {
        return text;
    }
}
