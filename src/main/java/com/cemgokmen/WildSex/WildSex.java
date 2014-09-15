package com.cemgokmen.WildSex;

import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class WildSex extends JavaPlugin {

    private int wildSexTask;
    private long startTime;
    private int interval;
    private boolean mateMode;
    private double chance;
    private WildSexTaskListener listener;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        this.interval = this.getConfig().getInt("interval") * 20 * 60;
        this.mateMode = this.getConfig().getBoolean("matemode");
        this.chance = this.getConfig().getDouble("chance");
        this.listener = new WildSexTaskListener(this);
        getServer().getPluginManager().registerEvents(this.listener, this);

        this.wildSexTask = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new WildSexTask(this, this.chance, this.mateMode, this.listener), 0L, this.interval);
        this.startTime = System.currentTimeMillis();

        getLogger().log(Level.INFO, "WildSex v{0} by Funstein successfully activated!", this.getDescription().getVersion());
        String mateModeString = (this.mateMode) ? "active" : "inactive";
        getLogger().log(Level.INFO, "Mate mode: {0}, interval: {1} minutes, chance: {2}.", new Object[]{mateModeString, this.interval / 1200, String.format("%.2f", this.chance)});
        
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
            getLogger().log(Level.INFO, "WildSex connected to mcstats.org successfully!");
        } catch (IOException e) {
            getLogger().log(Level.INFO, "WildSex could not connect to mcstats.org.");
        }
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTask(this.wildSexTask);
        HandlerList.unregisterAll(this);
        getLogger().log(Level.INFO, "WildSex v{0} by Funstein successfully deactivated!", this.getDescription().getVersion());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            long numSeconds = (this.interval / 20) - (((System.currentTimeMillis() - this.startTime) % (this.interval * 50)) / 1000);
            player.sendMessage(numSeconds + " seconds left until next wild sex.");
            return true;
        } else {
            sender.sendMessage("You must be a player!");
            return false;
        }
    }
}
