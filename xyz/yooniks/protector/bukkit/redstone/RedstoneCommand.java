/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.redstone;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.redstone.RedstoneInventory;
import xyz.yooniks.protector.bukkit.redstone.RedstoneManager;

public class RedstoneCommand
implements CommandExecutor {
    private final RedstoneInventory inventory;
    private final RedstoneManager redstoneManager = RedstoneManager.getInstance();
    private final boolean enabled;

    public RedstoneCommand(RedstoneInventory a, boolean a2) {
        RedstoneCommand a3;
        a3.inventory = a;
        a3.enabled = a2;
    }

    public boolean onCommand(CommandSender a, Command a2, String a3, String[] a4) {
        RedstoneCommand a5;
        if (!(a instanceof Player)) {
            return true;
        }
        if (!a.hasPermission((String)"casualprotector.redstone")) {
            a.sendMessage((String)((Object)ChatColor.RED + "You don't have permission " + (Object)ChatColor.YELLOW + "casualprotector.redstone! =("));
            return true;
        }
        if (!a5.enabled) {
            a.sendMessage((String)((Object)ChatColor.RED + "Redstone feature in cprotector is disabled. Switch redstone.enabled in config to true and restart the server."));
            return true;
        }
        a5.inventory.open((Player)((Player)a));
        return true;
    }
}

