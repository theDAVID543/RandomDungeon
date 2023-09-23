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
import the.david.randomdungeon.dungeon.data.dungeonData;
import the.david.randomdungeon.dungeon.data.instanceData;

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
    public void playDungeon(Player player, String dungeonName){
        if(!dungeonData.dungeons.contains(dungeonName)){
            player.sendMessage(
                    Component.text("無此Dungeon").color(NamedTextColor.RED)
            );
            return;
        }
        if(instanceData.playerInDungeon.containsKey(player)){
            player.sendMessage(
                    Component.text("已在Dungeon內").color(NamedTextColor.RED)
            );
            return;
        }
        instanceData.dungeonInstanceAmount.putIfAbsent(dungeonName, 0);
        String instanceName = dungeonName + "_" + instanceData.dungeonInstanceAmount.get(dungeonName);
        instanceData.dungeonInstanceAmount.put(dungeonName, instanceData.dungeonInstanceAmount.get(dungeonName) + 1);
        Bukkit.getWorld(dungeonName).save();
        File dungeonFile = Bukkit.getWorld(dungeonName).getWorldFolder();
        plugin.dungeonInstanceWorldHandler.copyWorld(dungeonFile, new File(plugin.instance.getDataFolder().getPath() + "/DungeonInstances", instanceName));
        WorldCreator worldCreator = new WorldCreator(plugin.instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonInstances/" + instanceName);
        World world = worldCreator.createWorld();
        player.teleport(new Location(world, 8, 1, 8).toCenterLocation());
        instanceData.dungeonInstancePlayerSet.putIfAbsent(instanceName, new HashSet<>());
        instanceData.dungeonInstancePlayerSet.get(instanceName).add(player);
        instanceData.dungeonInstances.putIfAbsent(dungeonName, new HashSet<>());
        instanceData.dungeonInstances.get(dungeonName).add(plugin.instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonInstances/" + instanceName);
        instanceData.playerInDungeon.put(player, plugin.instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonInstances/" + instanceName);
    }
    public void leaveDungeon(Player player){
        if(!instanceData.playerInDungeon.containsKey(player)){
            return;
        }

    }
    public void deleteInstances(){
        instanceData.dungeonInstances.forEach((k, v) ->{
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
