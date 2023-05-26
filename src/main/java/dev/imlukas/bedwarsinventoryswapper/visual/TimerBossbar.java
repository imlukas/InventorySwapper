package dev.imlukas.bedwarsinventoryswapper.visual;

import dev.imlukas.bedwarsinventoryswapper.InventorySwapperPlugin;
import dev.imlukas.bedwarsinventoryswapper.data.settings.BossbarSettings;
import dev.imlukas.bedwarsinventoryswapper.manager.InventoryManager;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.builders.ScheduleBuilder;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TimerBossbar {

    private final BossbarSettings bossbarSettings;
    private final BossBar bossBar;
    private int totalTicks;
    private int elapsedTicks = 0;

    public TimerBossbar(InventorySwapperPlugin plugin) {
        InventoryManager inventoryManager = plugin.getInventoryManager();
        this.bossbarSettings = plugin.getPluginSettings().getBossbarSettings();
        this.bossBar = create();

        for (UUID playerId : inventoryManager.getParsedInventories().keySet()) {
            display(playerId);
        }

        new ScheduleBuilder(plugin).every(1).ticks().run(this::tick).sync().start();
    }

    /**
     * This method displays the bossbar to a player.
     * @return the bossbar
     */
    public BossBar create() {
        int remainingSeconds = (totalTicks - elapsedTicks) / 20;
        String text = bossbarSettings.getText().replace("%time%", remainingSeconds + " Seconds");
        BarColor color = bossbarSettings.getColor();
        BossBar bossBar = Bukkit.createBossBar(text, color, BarStyle.SOLID);
        bossBar.setProgress(1);
        bossBar.setVisible(true);

        return bossBar;
    }

    /**
     * This method is called every tick to update the bossbar.
     */
    public void tick() {
        if (elapsedTicks >= totalTicks) {
            return;
        }

        float progress = (float) elapsedTicks++ / totalTicks;
        int remainingSeconds = (totalTicks - elapsedTicks) / 20;

        bossBar.setProgress(1 - progress);
        bossBar.setTitle(bossbarSettings.getText().replace("%time%", remainingSeconds + " Seconds"));
    }

    /**
     * Displays the bossbar to a player.
     * @param playerId the player's UUID
     */
    public void display(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);

        if (player == null) {
            return;
        }

        bossBar.addPlayer(player);
    }

    /**
     * Unused method, would be used in a game context.
     */
    public void dispose() {
        bossBar.removeAll();
        bossBar.setVisible(false);
    }

    /**
     * Resets the timer.
     * @param newTotalTicks the new total ticks
     */
    public void reset(int newTotalTicks) {
        this.elapsedTicks = 0;
        this.totalTicks = newTotalTicks;
    }

    /**
     * Gets the total ticks.
     * @param totalTicks the total ticks
     */
    public void setTotalTicks(int totalTicks) {
        this.totalTicks = totalTicks;
    }
}
