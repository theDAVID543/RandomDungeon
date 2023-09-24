package the.david.randomdungeon.dungeon.holder;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import the.david.randomdungeon.handler.dungeonInstanceWorldHandler;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static the.david.randomdungeon.RandomDungeon.plugin;


public class DungeonInstance {
    public DungeonInstance(String dungeonShowName, Integer instanceNumber){
        this.dungeonShowName = dungeonShowName;
        this.instanceNumber = instanceNumber;
        instanceName = plugin.instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonInstances/" + dungeonShowName + "_" + instanceNumber;
        File dungeonFile = plugin.dungeonManager.getDungeonByName(dungeonShowName).getWorld().getWorldFolder();
//        dungeonInstanceWorldHandler.copyWorld(dungeonFile, new File(instanceName));
        dungeonInstanceWorldHandler.copyDirectory(dungeonFile, new File(instanceName));
        WorldCreator worldCreator = new WorldCreator(instanceName);
        world = worldCreator.createWorld();
    }
    private final String dungeonShowName;
    private final Set<Player> players = new HashSet<>();
    private Integer instanceNumber;
    private String instanceName;
    private World world;
    public World getWorld(){
        return world;
    }
    public Set<Player> getPlayers(){
        return players;
    }
    public void addPlayer(Player player){
        players.add(player);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }
}
