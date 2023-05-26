package dev.imlukas.bedwarsinventoryswapper.listener;

import dev.imlukas.bedwarsinventoryswapper.InventorySwapperPlugin;
import dev.imlukas.bedwarsinventoryswapper.swap.SwapTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final SwapTimer swapTimer;

    public ConnectionListener(InventorySwapperPlugin plugin) {
        this.swapTimer = plugin.getSwapTimer();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        swapTimer.getTimerBossbar().display(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        swapTimer.getTimerBossbar().display(event.getPlayer().getUniqueId());
    }
}
