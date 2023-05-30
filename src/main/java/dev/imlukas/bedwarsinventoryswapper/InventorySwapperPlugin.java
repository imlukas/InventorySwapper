package dev.imlukas.bedwarsinventoryswapper;

import dev.imlukas.bedwarsinventoryswapper.commands.ForceSwapCommand;
import dev.imlukas.bedwarsinventoryswapper.data.settings.Settings;
import dev.imlukas.bedwarsinventoryswapper.manager.InventoryManager;
import dev.imlukas.bedwarsinventoryswapper.swap.InventorySwapper;
import dev.imlukas.bedwarsinventoryswapper.swap.SwapTimer;
import dev.imlukas.bedwarsinventoryswapper.utils.command.impl.CommandManager;
import dev.imlukas.bedwarsinventoryswapper.utils.messages.MessagesFile;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class InventorySwapperPlugin extends JavaPlugin {

    private MessagesFile messages;
    private CommandManager commandManager;

    private Settings pluginSettings;
    private InventoryManager inventoryManager;
    private InventorySwapper inventorySwapper;
    private SwapTimer swapTimer;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        messages = new MessagesFile(this);
        commandManager = new CommandManager(this);

        pluginSettings = new Settings(getConfig());
        inventoryManager = new InventoryManager();
        inventorySwapper = new InventorySwapper(this);
        swapTimer = new SwapTimer(this);

        commandManager.register(new ForceSwapCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MessagesFile getMessages() {
        return messages;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Settings getPluginSettings() {
        return pluginSettings;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public InventorySwapper getInventorySwapper() {
        return inventorySwapper;
    }

    public SwapTimer getSwapTimer() {
        return swapTimer;
    }
}
