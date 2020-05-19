package com.cemgokmen.wildsex;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import com.cemgokmen.wildsex.api.WildAnimal;
import com.google.common.collect.Sets;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class WildSex extends JavaPlugin {

	private static final Set<String> VERSIONS_USING_NMS = Sets.newHashSet("v1_10_R1", "v1_11_R1", "v1_12_R1");

    private final Set<UUID> lastMateAnimals = new HashSet<>();

    private WildAnimal wildAnimalHandler;
    private BukkitTask wildSexTask;

    private long startTime;
    private int interval;
    private boolean mateMode;
    private double chance;
    private double maxAnimalsPerBlock;
    private double maxAnimalsCheckRadius;
    private int maxMateDistance;

    @Override
    public void onEnable() {
        String packageName = this.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        if (VERSIONS_USING_NMS.contains(version)) {
            try {
                final Class<?> clazz = Class.forName("com.cemgokmen.wildsex.wildanimal." + version + ".WildAnimalHandler");
                // Check if we have a WildAnimalHandler class at that location.
                if (WildAnimal.class.isAssignableFrom(clazz)) { // Make sure it actually implements WildAnimal
                    this.wildAnimalHandler = (WildAnimal) clazz.getConstructor().newInstance(); // Set our handler
                }
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
                this.getLogger().severe("This CraftBukkit version is not supported.");
                this.getLogger().info("Check for updates at http://dev.bukkit.org/bukkit-plugins/wildsex/");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
        } else {
        	this.wildAnimalHandler = new WildAnimalHandlerSafe();
        }

        // Start the actual loading part here.
        this.saveDefaultConfig();

        FileConfiguration config = getConfig();
        this.interval = config.getInt("interval") * 20 * 60;
        this.mateMode = config.getBoolean("mateMode");
        this.chance = config.getDouble("chance");
        this.maxAnimalsPerBlock = config.getDouble("maxAnimalsPerBlock");
        this.maxAnimalsCheckRadius = config.getDouble("maxAnimalsCheckRadius");
        this.maxMateDistance = config.getInt("maxMateDistance");

        if (config.getBoolean("autoUpdate")) {
            Updater updater = new Updater(this, 85038, this.getFile(), Updater.UpdateType.DEFAULT, true);
            getLogger().info("Running automatic updater.");
            Updater.UpdateResult result = updater.getResult();
            switch(result)
            {
                case SUCCESS:
                    getLogger().log(Level.INFO, "Success: The updater found an update, and has readied it to be loaded the next time the server restarts/reloads");
                    break;
                case NO_UPDATE:
                    getLogger().log(Level.INFO, "No Update: The updater did not find an update, and nothing was downloaded.");
                    break;
                case DISABLED:
                    getLogger().log(Level.INFO, "Won't Update: The updater was disabled in its configuration file.");
                    break;
                case FAIL_DOWNLOAD:
                    getLogger().log(Level.INFO, "Download Failed: The updater found an update, but was unable to download it.");
                    break;
                case FAIL_DBO:
                    getLogger().log(Level.INFO, "dev.bukkit.org Failed: For some reason, the updater was unable to contact DBO to download the file.");
                    break;
                case FAIL_NOVERSION:
                    getLogger().log(Level.INFO, "No version found: When running the version check, the file on DBO did not contain the a version in the format 'vVersion' such as 'v1.0'.");
                    break;
                case FAIL_BADID:
                    getLogger().log(Level.INFO, "Bad id: The id provided by the plugin running the updater was invalid and doesn't exist on DBO.");
                    break;
                case FAIL_APIKEY:
                    getLogger().log(Level.INFO, "Bad API key: The user provided an invalid API key for the updater to use.");
                    break;
                case UPDATE_AVAILABLE:
                    getLogger().log(Level.INFO, "There was an update found, but because you had the UpdateType set to NO_DOWNLOAD, it was not downloaded.");
            }
        }

        if (config.getBoolean("removeXP")) {
            Bukkit.getPluginManager().registerEvents(new WildSexTaskListener(this), this);
        }

        this.wildSexTask = new WildSexTask(this).runTaskTimer(this, 0L, interval);
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onDisable() {
        if (wildSexTask != null) {
            this.wildSexTask.cancel();
        }

        if (lastMateAnimals != null) {
            this.clearMatedAnimals();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        long numSeconds = (this.interval / 20) - (((System.currentTimeMillis() - this.startTime) % (this.interval * 50)) / 1000);
        sender.sendMessage(numSeconds + " seconds left until next wild sex.");
        return true;
    }

    public void clearMatedAnimals() {
        this.lastMateAnimals.clear();
    }

    public void addMatedAnimal(Entity e) {
        if (e == null) return;
        this.lastMateAnimals.add(e.getUniqueId());
    }

    public void removeMatedAnimal(Entity e) {
        if (e == null) return;
        this.lastMateAnimals.remove(e.getUniqueId());
    }

    public boolean isMatedAnimal(Entity e) {
        return e != null && lastMateAnimals.contains(e.getUniqueId());
    }

    public WildAnimal getWildAnimalHandler() {
        return wildAnimalHandler;
    }

    public boolean getMateMode() {
        return mateMode;
    }

    public double getChance() {
        return chance;
    }

    public double getMaxAnimalsPerBlock() {
        return maxAnimalsPerBlock;
    }

    public double getMaxAnimalsCheckRadius() {
        return maxAnimalsCheckRadius;
    }

    public int getMaxMateDistance() {
        return maxMateDistance;
    }
}
