package dev.imlukas.bedwarsinventoryswapper.swap;

import dev.imlukas.bedwarsinventoryswapper.InventorySwapperPlugin;
import dev.imlukas.bedwarsinventoryswapper.data.ParsedInventory;
import dev.imlukas.bedwarsinventoryswapper.manager.InventoryManager;
import dev.imlukas.bedwarsinventoryswapper.utils.messages.MessagesFile;
import dev.imlukas.bedwarsinventoryswapper.utils.text.Placeholder;
import dev.imlukas.bedwarsinventoryswapper.utils.text.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InventorySwapper {

    private final MessagesFile messages;
    private final InventoryManager inventoryManager;

    public InventorySwapper(InventorySwapperPlugin plugin) {
        this.messages = plugin.getMessages();
        this.inventoryManager = plugin.getInventoryManager();
    }

    /**
     * Swaps the inventory of the given player with a random one.
     *
     * @param target the player
     */
    public void swap(Player target, ParsedInventory targetInventory) {
        targetInventory.applyTo(target);
        messages.sendMessage(target, "swap", List.of(
                new Placeholder<>("player", targetInventory.getPlayer().getDisplayName()),
                new Placeholder<>("armor", getArmorString(targetInventory.getArmor())))
        );
    }

    /**
     * Swaps all inventories of the given players with a random one.
     *
     * @param playersCollection the players
     */
    public void swapAll(Collection<Player> playersCollection) {
        List<Player> players = List.copyOf(playersCollection);

        int size = players.size();

        if (size <= 1) {
            return;
        }

        /* With this implementation we avoid possible errors with odd player numbers.
        Similar to the Fisher-Yates shuffle algorithm. */
        int offset = ThreadLocalRandom.current().nextInt(1, size);
        for (int i = 0; i < size; i++) {
            int targetIndex = (i + offset) % size;

            Player sourcePlayer = players.get(i);
            Player targetPlayer = players.get(targetIndex);
            ParsedInventory targetInventory = inventoryManager.getParsedInventory(targetPlayer.getUniqueId());

            swap(sourcePlayer, targetInventory);
        }
    }

    /**
     * Returns a string representation of the given armor.
     * @param armor the armor
     * @return the string representation
     */
    public String getArmorString(ItemStack[] armor) {
        StringBuilder armorString = new StringBuilder("[ ");

        if (armor.length == 0) {
            armorString.append("None :(");
            return armorString.toString();
        }

        for (int i = 0; i < armor.length; i++) {
            ItemStack item = armor[i];

            if (item == null) {
                armorString.append("empty");
            } else {
                String itemType = TextUtils.capitalizeAll(item.getType().name());
                armorString.append(itemType);
            }

            if (i != armor.length - 1) {
                armorString.append(" | ");
            }
        }

        armorString.append(" ]");
        return armorString.toString();
    }
}
