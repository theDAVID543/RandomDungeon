package the.david.randomdungeon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import the.david.randomdungeon.command.commands;
import the.david.randomdungeon.dungeon.dungeonEditor;
import the.david.randomdungeon.dungeon.dungeonInstanceManager;
import the.david.randomdungeon.dungeon.dungeonManager;
import the.david.randomdungeon.dungeon.dungeonRoomGenerator;
import the.david.randomdungeon.handler.config;
import the.david.randomdungeon.handler.dungeonInstanceWorldHandler;

public final class RandomDungeon extends JavaPlugin {
    public JavaPlugin instance;
    public config dungeonsConfig;
    public dungeonManager dungeonManager;
    public dungeonInstanceManager dungeonInstanceManager;
    public dungeonInstanceWorldHandler dungeonInstanceWorldHandler;
    public dungeonEditor dungeonEditor;
    public static String dungeonFolder;
    public static RandomDungeon plugin;
    public dungeonRoomGenerator dungeonRoomGenerator;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        instance = this;
        dungeonFolder = instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonMaps/";
        Bukkit.getPluginCommand("randomdungeon").setExecutor(new commands(this));
        dungeonsConfig = new config(this, "data/dungeons.yml");
        dungeonsConfig.createCustomConfig();
        dungeonManager = new dungeonManager(this);
        dungeonManager.init();
        dungeonInstanceManager = new dungeonInstanceManager(this);
        Bukkit.getPluginManager().registerEvents(dungeonInstanceManager, instance);
        dungeonEditor = new dungeonEditor(this);
        Bukkit.getPluginManager().registerEvents(dungeonEditor, instance);
        dungeonRoomGenerator = new dungeonRoomGenerator(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dungeonInstanceManager.deleteInstances();
    }
}
