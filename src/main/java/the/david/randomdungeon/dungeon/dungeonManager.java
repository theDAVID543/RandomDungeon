package the.david.randomdungeon.dungeon;

import org.bukkit.*;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.holder.Dungeon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static the.david.randomdungeon.RandomDungeon.dungeonFolder;

public class dungeonManager {
    public dungeonManager(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;
    private static final Map<String, Dungeon> dungeons = new HashMap<>();

    public void init(){
        plugin.dungeonsConfig.getKeys(null).forEach(plugin.dungeonManager::addDungeonWithName);
    }
    public World createDungeon(String showName){
        if(getDungeonNames().contains(showName)){
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
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Location location = new Location(world, 8, 0, 8);
        location.getBlock().setType(Material.STONE);
        plugin.dungeonsConfig.setObject(showName + ".active", false);
        addDungeonWithName(showName);
//        dungeonsHandler.dungeons.add(showName);
//        dungeonsHandler.dungeonDataConfig.put(showName, new config(plugin, "/data/dungeons/" + showName + ".yml"));
//        dungeonsHandler.dungeonDataConfig.get(showName).createCustomConfig();
        return world;
    }
    public Set<String> getDungeonNames(){
        return dungeons.keySet();
    }
    public Collection<Dungeon> getDungeons(){
        return dungeons.values();
    }
    public Dungeon getDungeonByName(String dungeonName){
        return dungeons.get(dungeonName);
    }
    public void addDungeon(Dungeon dungeon){
        dungeons.put(dungeon.getShowName(), dungeon);
    }
    public void addDungeonWithName(String dungeonName){
        Dungeon dungeon = new Dungeon(dungeonName);
        addDungeon(dungeon);
    }
    public String toShowName(String fullName){
        return fullName.replaceAll(dungeonFolder, "");
    }
}
