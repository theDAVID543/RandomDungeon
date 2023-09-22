package the.david.randomdungeon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import the.david.randomdungeon.command.commands;
import the.david.randomdungeon.dungeon.dungeonPlay;
import the.david.randomdungeon.dungeon.dungeonEditor;
import the.david.randomdungeon.handler.config;
import the.david.randomdungeon.dungeon.dungeonManager;
import the.david.randomdungeon.handler.dungeonInstance;

public final class RandomDungeon extends JavaPlugin {
    public JavaPlugin instance;
    public config dungeonsConfig;
    public dungeonManager dungeonManager;
    public dungeonPlay dungeonPlay;
    public dungeonInstance dungeonInstance;
    public dungeonEditor dungeonEditor;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getPluginCommand("randomdungeon").setExecutor(new commands(this));
        dungeonsConfig = new config(this, "data/dungeons.yml");
        dungeonsConfig.createCustomConfig();
        dungeonManager = new dungeonManager(this);
        dungeonManager.init();
        dungeonPlay = new dungeonPlay(this);
        dungeonInstance = new dungeonInstance(this);
        dungeonEditor = new dungeonEditor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dungeonPlay.deleteInstances();
    }
}
