package the.david.randomdungeon;

import org.bukkit.plugin.java.JavaPlugin;

public final class RandomDungeon extends JavaPlugin {
    public JavaPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
