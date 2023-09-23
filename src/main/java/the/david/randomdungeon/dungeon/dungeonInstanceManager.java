package the.david.randomdungeon.dungeon;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.codehaus.plexus.util.FileUtils;
import the.david.randomdungeon.RandomDungeon;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class dungeonInstanceManager {
    public dungeonInstanceManager(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;
    public Set<Player> playingPlayers = new HashSet<>();
    public Map<String, Integer> dungeonInstanceAmount = new HashMap<>();
    public Map<String, Set<Player>> dungeonInstancePlayerSet = new HashMap<>();
    public Map<String, Set<String>> dungeonInstances = new HashMap<>();
    public void playDungeon(Player player, String dungeonName){
        if(!plugin.dungeonManager.dungeons.contains(dungeonName)){
            player.sendMessage(
                    Component.text("無此Dungeon").color(NamedTextColor.RED)
            );
            return;
        }
        if(playingPlayers.contains(player)){
            player.sendMessage(
                    Component.text("已在Dungeon內").color(NamedTextColor.RED)
            );
            return;
        }
        dungeonInstanceAmount.putIfAbsent(dungeonName, 0);
        String instanceName = dungeonName + "_" + dungeonInstanceAmount.get(dungeonName);
        dungeonInstanceAmount.put(dungeonName, dungeonInstanceAmount.get(dungeonName) + 1);
        Bukkit.getWorld(dungeonName).save();
        File dungeonFile = Bukkit.getWorld(dungeonName).getWorldFolder();
        plugin.dungeonInstanceWorldHandler.copyWorld(dungeonFile, new File(plugin.instance.getDataFolder().getPath() + "/DungeonInstances", instanceName));
        WorldCreator worldCreator = new WorldCreator(plugin.instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonInstances/" + instanceName);
        World world = worldCreator.createWorld();
        player.teleport(new Location(world, 8, 1, 8).toCenterLocation());
        dungeonInstancePlayerSet.putIfAbsent(instanceName, new HashSet<>());
        dungeonInstancePlayerSet.get(instanceName).add(player);
        dungeonInstances.putIfAbsent(dungeonName, new HashSet<>());
        dungeonInstances.get(dungeonName).add(plugin.instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonInstances/" + instanceName);
    }
    public void deleteInstances(){
        dungeonInstances.forEach((k,v) ->{
            v.forEach(w ->{
                World world = Bukkit.getWorld(w);
                File worldFile = world.getWorldFolder();
                Bukkit.unloadWorld(w, false);
                try {
                    FileUtils.deleteDirectory(worldFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

}
