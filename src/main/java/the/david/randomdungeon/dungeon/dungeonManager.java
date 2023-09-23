package the.david.randomdungeon.dungeon;

import org.bukkit.*;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.data.dungeonData;

import java.util.HashSet;
import java.util.Set;

public class dungeonManager {
    public dungeonManager(RandomDungeon plugin){
        this.plugin = plugin;
    }
    RandomDungeon plugin;

    public void init(){
        dungeonData.dungeons.addAll(plugin.dungeonsConfig.getKeys(null));
    }
    public World createDungeon(String name){
        if(dungeonData.dungeons.contains(name)){
            Bukkit.getLogger().info("該Dungeon已存在");
            return null;
        }
//        WorldCreator worldCreator = new WorldCreator(name);
        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        worldCreator.generatorSettings("{\"layers\": [], \"biome\":\"plains\"}");
        World world = worldCreator.createWorld();
        Location location = new Location(world, 8, 0, 8);
        location.getBlock().setType(Material.STONE);
        plugin.dungeonsConfig.setObject(name + ".active", false);
        dungeonData.dungeons.add(name);
        return world;
    }
}
