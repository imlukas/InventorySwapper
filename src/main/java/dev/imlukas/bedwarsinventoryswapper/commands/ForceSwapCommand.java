package dev.imlukas.bedwarsinventoryswapper.commands;

import dev.imlukas.bedwarsinventoryswapper.InventorySwapperPlugin;
import dev.imlukas.bedwarsinventoryswapper.data.settings.Settings;
import dev.imlukas.bedwarsinventoryswapper.manager.InventoryManager;
import dev.imlukas.bedwarsinventoryswapper.swap.InventorySwapper;
import dev.imlukas.bedwarsinventoryswapper.swap.SwapTimer;
import dev.imlukas.bedwarsinventoryswapper.utils.command.SimpleCommand;
import dev.imlukas.bedwarsinventoryswapper.utils.messages.MessagesFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class ForceSwapCommand implements SimpleCommand {
    private final MessagesFile messages;
    private final Settings pluginSettings;
    private final SwapTimer swapTimer;
    private final InventorySwapper inventorySwapper;
    private final InventoryManager inventoryManager;

    public ForceSwapCommand(InventorySwapperPlugin plugin) {
        this.messages = plugin.getMessages();
        this.pluginSettings = plugin.getPluginSettings();
        this.swapTimer = plugin.getSwapTimer();
        this.inventorySwapper = plugin.getInventorySwapper();
        this.inventoryManager = plugin.getInventoryManager();
    }

    @Override
    public String getIdentifier() {
        return "swapper.forceswap";
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        List<Player> players = pluginSettings.getWorldToSwap().getPlayers();

        inventoryManager.parseAll(players);
        inventorySwapper.swapAll(players);
        swapTimer.getScheduler().cancel();

        messages.sendMessage(sender, "force-swap");
    }
}
