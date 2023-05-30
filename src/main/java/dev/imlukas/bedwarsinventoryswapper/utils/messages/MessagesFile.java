package dev.imlukas.bedwarsinventoryswapper.utils.messages;

import dev.imlukas.bedwarsinventoryswapper.InventorySwapperPlugin;
import dev.imlukas.bedwarsinventoryswapper.utils.storage.YMLBase;
import dev.imlukas.bedwarsinventoryswapper.utils.text.Placeholder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Collection;
import java.util.function.Function;

public class MessagesFile extends YMLBase {

    private final String prefix, arrow;
    protected boolean usePrefixConfig;
    private String msg;

    public MessagesFile(InventorySwapperPlugin plugin) {
        super(plugin, new File(plugin.getDataFolder(), "messages.yml"), true);
        prefix = getConfiguration().getString("messages.prefix");
        arrow = getConfiguration().getString("messages.arrow");
        usePrefixConfig = getConfiguration().getBoolean("messages.use-prefix");

    }

    public String setColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String setMessage(String name) {
        return setMessage(name, (s) -> s);
    }

    private String setMessage(String name, Function<String, String> action) {
        if (!getConfiguration().contains("messages." + name))
            return "";
        msg = getMessage(name);
        if (usePrefixConfig) {
            msg = prefix + " " + arrow + " " + getMessage(name);
        } else {
            msg = msg.replace("%prefix%", prefix);
        }
        msg = action.apply(msg);
        return setColor(msg);
    }

    public void sendStringMessage(CommandSender player, String msg) {
        player.sendMessage(setColor(msg));
    }

    public void sendMessage(CommandSender sender, String name) {
        sendMessage(sender, name, (s) -> s);
    }


    @SafeVarargs
    public final <T extends CommandSender> void sendMessage(T sender, String name, Placeholder<T>... placeholders) {
        sendMessage(sender, name, (text) -> {
            for (Placeholder<T> placeholder : placeholders) {
                text = placeholder.replace(text, sender);
            }

            return text;
        });
    }

    public final <T extends CommandSender> void sendMessage(T sender, String name, Collection<Placeholder<T>> placeholders) {
        sendMessage(sender, name, (text) -> {
            for (Placeholder<T> placeholder : placeholders) {
                text = placeholder.replace(text, sender);
            }

            return text;
        });
    }


    public void sendMessage(CommandSender sender, String name, Function<String, String> action) {
        if (getConfiguration().isList("messages." + name)) {
            for (String str : getConfiguration().getStringList("messages." + name)) {
                msg = str.replace("%prefix%", prefix);
                msg = action.apply(msg);
                sender.sendMessage(setColor(msg));
            }
            return;
        }

        msg = setMessage(name, action);
        sender.sendMessage(msg);
    }

    public String getMessage(String name) {
        return getConfiguration().getString("messages." + name);
    }

    public boolean togglePrefix() {
        return toggle("prefix", usePrefixConfig);
    }

    public boolean toggle(String type, boolean isEnabled) {
        if (isEnabled) {
            getConfiguration().set("messages.use-" + type, false);
        } else {
            getConfiguration().set("messages.use-" + type, true);
        }

        save();
        return !isEnabled;
    }
}

