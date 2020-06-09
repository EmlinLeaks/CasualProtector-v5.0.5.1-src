/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit.command;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitTask;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MemoryHelper;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistUpdater;

public class CasualProtectorCommand
implements CommandExecutor {
    private final Plugin plugin;
    private final WhitelistManager whitelistManager;

    public boolean onCommand(CommandSender a, Command a2, String a3, String[] a4) {
        CasualProtectorCommand a5;
        if (a4.length > 0 && a4[0].equalsIgnoreCase((String)"tps")) {
            a.sendMessage((String)((Object)ChatColor.BLUE + "CasualProtector, tps: " + (Object)ChatColor.GREEN + MemoryHelper.getTPS((int)0)));
            return true;
        }
        if (!a.hasPermission((String)"casualprotector.command")) {
            a.sendMessage((String)((Object)ChatColor.BLUE + "CasualProtector " + a5.plugin.getDescription().getVersion()));
            a.sendMessage((String)((Object)ChatColor.BLUE + "An author and contact: " + (Object)ChatColor.GREEN + "yooniks" + (Object)ChatColor.BLUE + ", discord: " + (Object)ChatColor.GREEN + "yooniks#2411"));
            a.sendMessage((String)((Object)ChatColor.BLUE + "Buy the resource on: https://www.spigotmc.org/resources/%E2%AD%90-casualprotector-the-most-powerful-anticrash-and-antibot-optimize-fps-and-tps-%E2%AD%90.59866/"));
            a.sendMessage((String)new StringBuilder().insert((int)0, (String)"\n").append((Object)ChatColor.RED).append((String)MessagesConfig.COMMAND$NO_PERMISSION).toString());
            return true;
        }
        if (a4.length < 1) {
            a.sendMessage((String)((Object)ChatColor.GREEN + "Correct usage: /casualprotector reload\n/casualprotector bypass [nickname]"));
            return true;
        }
        if (a4[0].equalsIgnoreCase((String)"reload")) {
            MessagesConfig.reloadConfig();
            a5.plugin.reloadConfig();
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)a5.plugin, (Runnable)new WhitelistUpdater((WhitelistManager)ProtectorSpigot.getInstance().getWhitelistManager(), (File)new File((File)a5.plugin.getDataFolder(), (String)"bypass.txt")));
            a.sendMessage((String)((Object)ChatColor.GREEN + "Reloaded configs! Remember - you need restart to save new messages in listeners etc! Please do not use /reload, it's not even recommended by spigot!"));
            return true;
        }
        if (!a4[0].equalsIgnoreCase((String)"bypass")) {
            a.sendMessage((String)((Object)ChatColor.GREEN + "Correct usage: /casualprotector reload"));
            return true;
        }
        if (a4.length < 2) {
            a.sendMessage((String)((Object)ChatColor.RED + "Correct usage: /casualprotector bypass [nick]"));
            return true;
        }
        a5.whitelistManager.addWhitelist((String)a4[1]);
        a.sendMessage((String)((Object)ChatColor.GREEN + "Added " + a4[1] + " to bypass list!"));
        return true;
    }

    public CasualProtectorCommand(Plugin a, WhitelistManager a2) {
        CasualProtectorCommand a3;
        a3.plugin = a;
        a3.whitelistManager = a2;
    }
}

