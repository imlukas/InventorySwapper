package dev.imlukas.bedwarsinventoryswapper.swap;

import dev.imlukas.bedwarsinventoryswapper.BedwarsInventorySwapper;
import dev.imlukas.bedwarsinventoryswapper.data.ParsedInventory;
import dev.imlukas.bedwarsinventoryswapper.manager.InventoryManager;
import dev.imlukas.bedwarsinventoryswapper.utils.messages.MessagesFile;
import dev.imlukas.bedwarsinventoryswapper.utils.text.Placeholder;
import org.bukkit.entity.Player;

import java.util.Collection;

public class InventorySwapper {

    private final MessagesFile messages;
    private final InventoryManager inventoryManager;

    public InventorySwapper(BedwarsInventorySwapper plugin) {
        this.messages = plugin.getMessages();
        this.inventoryManager = plugin.getInventoryManager();
    }

    /**
     * Swaps the inventory of the given player with a random one.
     * @param player the player
     */
    public void swap(Player player) {
        ParsedInventory randomInventory = inventoryManager.getRandomInventory(player.getUniqueId());

        if (randomInventory == null) {
            return;
        }

        randomInventory.applyTo(player);
        messages.sendMessage(player, "swap", new Placeholder<>("player", randomInventory.getPlayer().getDisplayName()));
    }

    public void swapAll(Collection<Player> players) {
        for (Player player : players) {
            swap(player);
        }
    }
}
