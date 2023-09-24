package the.david.randomdungeon.dungeon;

import org.bukkit.*;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.cacheData.dungeonCacheData;
import the.david.randomdungeon.handler.config;

import java.util.HashMap;

import static the.david.randomdungeon.RandomDungeon.dungeonFolder;

public class dungeonManager {
    public dungeonManager(RandomDungeon plugin){
        this.plugin = plugin;
    }
    RandomDungeon plugin;

    public void init(){
        dungeonCacheData.dungeons.addAll(plugin.dungeonsConfig.getKeys(null));
        dungeonCacheData.dungeons.forEach(dungeonName ->{
            dungeonCacheData.dungeonDataConfig.put(dungeonName, new config(plugin, "/data/dungeons/" + dungeonName + ".yml"));
            config dungeonConfig = dungeonCacheData.dungeonDataConfig.get(dungeonName);
            dungeonConfig.createCustomConfig();
            dungeonCacheData.dungeonRoomsPos1.putIfAbsent(dungeonName, new HashMap<>());
            dungeonCacheData.dungeonRoomsPos2.putIfAbsent(dungeonName, new HashMap<>());
            if(dungeonConfig.getKeys("Rooms") != null){
                dungeonConfig.getKeys("Rooms").forEach(roomName ->{
                    Location pos1 = dungeonConfig.getLocation("Rooms." + roomName + ".pos1");
                    Location pos2 = dungeonConfig.getLocation("Rooms." + roomName + ".pos2");
                    dungeonCacheData.dungeonRoomsPos1.get(dungeonName).put(roomName, pos1);
                    dungeonCacheData.dungeonRoomsPos2.get(dungeonName).put(roomName, pos2);
                    Bukkit.getLogger().info(roomName);
                });
            }
            Bukkit.getLogger().info(dungeonCacheData.dungeonRoomsPos1.toString());
        });
    }
    public World createDungeon(String showName){
        if(dungeonCacheData.dungeons.contains(showName)){
            Bukkit.getLogger().info("該Dungeon已存在");
            return null;
        }
        WorldCreator worldCreator = new WorldCreator(dungeonFolder + showName);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        worldCreator.generatorSettings("{\"layers\": [], \"biome\":\"plains\"}");
        World world = worldCreator.createWorld();
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        Location location = new Location(world, 8, 0, 8);
        location.getBlock().setType(Material.STONE);
        plugin.dungeonsConfig.setObject(showName + ".active", false);
        dungeonCacheData.dungeons.add(showName);
        dungeonCacheData.dungeonDataConfig.put(showName, new config(plugin, "/data/dungeons/" + showName + ".yml"));
        dungeonCacheData.dungeonDataConfig.get(showName).createCustomConfig();
        return world;
    }
}
