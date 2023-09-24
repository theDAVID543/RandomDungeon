package the.david.randomdungeon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import the.david.randomdungeon.command.commands;
import the.david.randomdungeon.dungeon.cacheData.instanceCacheData;
import the.david.randomdungeon.dungeon.dungeonInstanceManager;
import the.david.randomdungeon.dungeon.dungeonEditor;
import the.david.randomdungeon.handler.config;
import the.david.randomdungeon.dungeon.dungeonManager;
import the.david.randomdungeon.dungeon.cacheData.dungeonCacheData;
import the.david.randomdungeon.handler.dungeonInstanceWorldHandler;

public final class RandomDungeon extends JavaPlugin {
    public JavaPlugin instance;
    public config dungeonsConfig;
    public dungeonManager dungeonManager;
    public dungeonInstanceManager dungeonInstanceManager;
    public dungeonInstanceWorldHandler dungeonInstanceWorldHandler;
    public dungeonEditor dungeonEditor;
    public dungeonCacheData dungeonCacheData;
    public instanceCacheData instanceCacheData;
    public static String dungeonFolder;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        dungeonFolder = instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonMaps/";
        Bukkit.getPluginCommand("randomdungeon").setExecutor(new commands(this));
        dungeonsConfig = new config(this, "data/dungeons.yml");
        dungeonsConfig.createCustomConfig();
        dungeonManager = new dungeonManager(this);
        dungeonManager.init();
        dungeonInstanceManager = new dungeonInstanceManager(this);
        dungeonInstanceWorldHandler = new dungeonInstanceWorldHandler(this);
        dungeonEditor = new dungeonEditor(this);
        dungeonCacheData = new dungeonCacheData(this);
        instanceCacheData = new instanceCacheData(this);
        Bukkit.getPluginManager().registerEvents(dungeonEditor, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dungeonInstanceManager.deleteInstances();
    }
}
