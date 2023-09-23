package the.david.randomdungeon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import the.david.randomdungeon.command.commands;
import the.david.randomdungeon.dungeon.data.instanceData;
import the.david.randomdungeon.dungeon.dungeonInstanceManager;
import the.david.randomdungeon.dungeon.dungeonEditor;
import the.david.randomdungeon.handler.config;
import the.david.randomdungeon.dungeon.dungeonManager;
import the.david.randomdungeon.dungeon.data.dungeonData;
import the.david.randomdungeon.handler.dungeonInstanceWorldHandler;

public final class RandomDungeon extends JavaPlugin {
    public JavaPlugin instance;
    public config dungeonsConfig;
    public dungeonManager dungeonManager;
    public dungeonInstanceManager dungeonInstanceManager;
    public dungeonInstanceWorldHandler dungeonInstanceWorldHandler;
    public dungeonEditor dungeonEditor;
    public dungeonData dungeonData;
    public instanceData instanceData;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getPluginCommand("randomdungeon").setExecutor(new commands(this));
        dungeonsConfig = new config(this, "data/dungeons.yml");
        dungeonsConfig.createCustomConfig();
        dungeonManager = new dungeonManager(this);
        dungeonManager.init();
        dungeonInstanceManager = new dungeonInstanceManager(this);
        dungeonInstanceWorldHandler = new dungeonInstanceWorldHandler(this);
        dungeonEditor = new dungeonEditor(this);
        dungeonData = new dungeonData(this);
        instanceData = new instanceData(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dungeonInstanceManager.deleteInstances();
    }
}
