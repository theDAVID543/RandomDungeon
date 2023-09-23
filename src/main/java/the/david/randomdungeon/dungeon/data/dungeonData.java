package the.david.randomdungeon.dungeon.data;

import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;

import java.util.HashSet;
import java.util.Set;

public class dungeonData {
    public dungeonData(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;
    public static Set<String> dungeons = new HashSet<>();

    public static Set<Player> PlayersInDungeon = new HashSet<>();
}
