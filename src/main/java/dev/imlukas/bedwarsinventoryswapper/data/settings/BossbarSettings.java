package dev.imlukas.bedwarsinventoryswapper.data.settings;

import dev.imlukas.bedwarsinventoryswapper.utils.text.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.inventivetalent.bossbar.BossBarAPI;

public class BossbarSettings {

    private final String text;
    private final BossBarAPI.Color color;

    public BossbarSettings(ConfigurationSection barSection) {
        text = TextUtils.color(barSection.getString("text"));
        color = BossBarAPI.Color.valueOf(barSection.getString("color"));
    }

    public BossBarAPI.Color getColor() {
        return color;
    }

    public String getText() {
        return text;
    }
}
