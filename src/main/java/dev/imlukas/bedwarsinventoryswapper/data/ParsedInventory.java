package dev.imlukas.bedwarsinventoryswapper.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Represents a parsed inventory.
 */
public class ParsedInventory {

    private final UUID playerId;
    private final ItemStack[] items;

    public ParsedInventory(UUID playerId, ItemStack[] inventory) {
        this.playerId = playerId;
        this.items = inventory;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void applyTo(Player player) {
        player.getInventory().setContents(getItems());
    }
}
