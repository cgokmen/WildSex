package com.cemgokmen.WildSex;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WildSex extends JavaPlugin {
	private int wildSexTask;
	private long startTime;
	private int interval;
	private boolean mateMode;
	private double chance;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.reloadConfig();
		
		this.interval = this.getConfig().getInt("interval") * 20 * 60;
		this.mateMode = this.getConfig().getBoolean("matemode");
		this.chance = this.getConfig().getDouble("chance");
		
		this.wildSexTask = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new WildSexTask(this, this.chance, this.mateMode), 0L, this.interval);
		this.startTime = System.currentTimeMillis();
		getLogger().info("WildSex v" + this.getDescription().getVersion() + " by Funstein successfully activated!");
		String mateModeString = (this.mateMode) ? "active" : "inactive";
		getLogger().info("Mate mode: " + mateModeString + ", interval: " + (interval / 1200) + " minutes, chance: " + String.format("%.2f", this.chance) + ".");
	}
	
	@Override
	public void onDisable() {
		this.getServer().getScheduler().cancelTask(wildSexTask);
		getLogger().info("WildSex v" + this.getDescription().getVersion() + " by Funstein successfully deactivated!");
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
