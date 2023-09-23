package the.david.randomdungeon.dungeon.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class instanceData {
    public instanceData(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;

    public static Map<String, Integer> dungeonInstanceAmount = new HashMap<>();
    public static Map<String, Set<Player>> dungeonInstancePlayerSet = new HashMap<>();
    public static Map<String, Set<String>> dungeonInstances = new HashMap<>();
    public static Map<Player, String> playerInDungeon = new HashMap<>();
    public static Map<Player, Location> playerOriginLocation = new HashMap<>();
}
