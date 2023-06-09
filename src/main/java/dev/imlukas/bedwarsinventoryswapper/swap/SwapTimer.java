package dev.imlukas.bedwarsinventoryswapper.swap;

import dev.imlukas.bedwarsinventoryswapper.InventorySwapperPlugin;
import dev.imlukas.bedwarsinventoryswapper.data.settings.Settings;
import dev.imlukas.bedwarsinventoryswapper.manager.InventoryManager;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.ScheduledTask;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.builders.ScheduleBuilder;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwapTimer {

    private final InventorySwapperPlugin plugin;
    private final Settings pluginSettings;
    private final InventoryManager inventoryManager;
    private final InventorySwapper inventorySwapper;

    private ScheduledTask scheduler;

    public SwapTimer(InventorySwapperPlugin plugin) {
        this.plugin = plugin;
        this.pluginSettings = plugin.getPluginSettings();
        this.inventoryManager = plugin.getInventoryManager();
        this.inventorySwapper = plugin.getInventorySwapper();
        setup();
    }

    /**
     * This method setups the timer to swap inventories.
     * In a traditional implementation this would only start whenever the game started, or after a defined time.
     * Since there is no game logic in this plugin, it will just start whenever the plugin is enabled.
     */
    public void setup() {
        int randomSecond = ThreadLocalRandom.current().nextInt(45, 90);

        scheduler = new ScheduleBuilder(plugin).in(randomSecond).seconds().run(() -> {
            List<Player> players = pluginSettings.getWorldToSwap().getPlayers();

            inventoryManager.parseAll(players);
            inventorySwapper.swapAll(players);

            this.setup();
        }).sync().start().onCancel(this::setup);
    }

    /**
     * Gets the scheduler for this timer.
     *
     * @return
     */
    public ScheduledTask getScheduler() {
        return scheduler;
    }

}
