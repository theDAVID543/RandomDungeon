package the.david.randomdungeon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import the.david.randomdungeon.command.commands;
import the.david.randomdungeon.handler.config;

public final class RandomDungeon extends JavaPlugin {
    public JavaPlugin instance;
    public config dungeonsConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getPluginCommand("randomdungeon").setExecutor(new commands(this));
        dungeonsConfig = new config(this, "data/dungeons.yml");
        dungeonsConfig.createCustomConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
