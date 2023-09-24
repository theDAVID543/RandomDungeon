package the.david.randomdungeon.dungeon.cacheData;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.handler.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static the.david.randomdungeon.RandomDungeon.dungeonFolder;

public class dungeonCacheData {
    public dungeonCacheData(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;
    public static Set<String> dungeons = new HashSet<>();
    public static final Map<String, config> dungeonDataConfig = new HashMap<>();
    public static final Map<String, Map<String, Location>> dungeonRoomsPos1 = new HashMap<>();
    public static final Map<String, Map<String, Location>> dungeonRoomsPos2 = new HashMap<>();

    public static String toWorldName(String showName){
        return dungeonFolder + showName;
    }
    public static String toShowName(String fullName){
        return fullName.replaceAll(dungeonFolder, "");
    }
}
