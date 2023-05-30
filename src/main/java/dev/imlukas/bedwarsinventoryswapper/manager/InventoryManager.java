package dev.imlukas.bedwarsinventoryswapper.manager;

import dev.imlukas.bedwarsinventoryswapper.data.ParsedInventory;
import dev.imlukas.bedwarsinventoryswapper.utils.ListUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages player inventories.
 */
public class InventoryManager {

    private final Map<UUID, ParsedInventory> parsedInventories = new HashMap<>();

    /**
     * Parses and adds a player's inventory into the map.
     *
     * @param player the player
     * @return the parsed inventory
     */
    public ParsedInventory parseInventory(Player player) {
        PlayerInventory inventory = player.getInventory();

        ItemStack[] items = inventory.getContents();
        ItemStack[] armor = new ItemStack[4];

        for (int index = 0; index < armor.length; index++) {
            armor[index] = inventory.getArmorContents()[index];
        }

        ParsedInventory parsedInventory = new ParsedInventory(player.getUniqueId(), items, armor);
        parsedInventories.put(player.getUniqueId(), parsedInventory);
        return parsedInventory;
    }

    /**
     * Parses and adds multiple player inventories into the map.
     *
     * @param players the players
     * @return the parsed inventories
     */
    public List<ParsedInventory> parseAll(Collection<Player> players) {
        for (Player player : players) {
            parseInventory(player);
        }

        return new ArrayList<>(parsedInventories.values());
    }

    /**
     * Removes a parsed inventory from the map.
     *
     * @param playerId the player's UUID
     */
    public void removeParsedInventory(UUID playerId) {
        parsedInventories.remove(playerId);
    }

    /**
     * Gets a parsed inventory from the map.
     *
     * @param playerId the player's UUID
     * @return the parsed inventory
     */
    public ParsedInventory getParsedInventory(UUID playerId) {
        return parsedInventories.get(playerId);
    }

    /**
     * Gets a random inventory that's not from the player with the given UUID.
     *
     * @param toExclude the UUID to exclude
     * @return the random inventory
     */
    public ParsedInventory getRandomInventory(UUID toExclude) {
        List<ParsedInventory> inventories = new ArrayList<>(parsedInventories.values());
        int offset = ThreadLocalRandom.current().nextInt(1, inventories.size());

        if (inventories.size() == 1) {
            return inventories.get(0);
        }

        ParsedInventory randomInventory = ListUtils.getRandom(inventories);

        if (randomInventory == null) {
            return null;
        }

        UUID inventoryPlayerId = randomInventory.getPlayerId();

        if (inventoryPlayerId.equals(toExclude)) {
            return getRandomInventory(toExclude);
        }

        removeParsedInventory(inventoryPlayerId);
        return randomInventory;
    }

    /**
     * Gets all parsed inventories.
     *
     * @return the parsed inventories
     */
    public Map<UUID, ParsedInventory> getParsedInventories() {
        return parsedInventories;
    }
}
