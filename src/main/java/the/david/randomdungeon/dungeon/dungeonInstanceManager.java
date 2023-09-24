package the.david.randomdungeon.dungeon;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.codehaus.plexus.util.FileUtils;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.cacheData.dungeonCacheData;
import the.david.randomdungeon.dungeon.cacheData.instanceCacheData;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class dungeonInstanceManager {
    public dungeonInstanceManager(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;
    public void playDungeon(Player player, String dungeonName){
        if(!dungeonCacheData.dungeons.contains(dungeonName)){
            player.sendMessage(
                    Component.text("無此Dungeon").color(NamedTextColor.RED)
            );
            return;
        }
        if(instanceCacheData.playerInDungeon.containsKey(player)){
            player.sendMessage(
                    Component.text("已在Dungeon內").color(NamedTextColor.RED)
            );
            return;
        }
        instanceCacheData.playerOriginLocation.put(player, player.getLocation());

        instanceCacheData.dungeonInstanceAmount.putIfAbsent(dungeonName, 0);
        String instanceName = plugin.instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonInstances/" + dungeonName + "_" + instanceCacheData.dungeonInstanceAmount.get(dungeonName);
        instanceCacheData.dungeonInstanceAmount.put(dungeonName, instanceCacheData.dungeonInstanceAmount.get(dungeonName) + 1);
        Bukkit.getWorld(dungeonCacheData.toWorldName(dungeonName)).save();
        File dungeonFile = Bukkit.getWorld(dungeonCacheData.toWorldName(dungeonName)).getWorldFolder();
        plugin.dungeonInstanceWorldHandler.copyWorld(dungeonFile, new File(instanceName));
        WorldCreator worldCreator = new WorldCreator(instanceName);
        World world = worldCreator.createWorld();
        player.teleport(new Location(world, 8, 1, 8).toCenterLocation());
        instanceCacheData.dungeonInstancePlayerSet.putIfAbsent(instanceName, new HashSet<>());
        instanceCacheData.dungeonInstancePlayerSet.get(instanceName).add(player);
        instanceCacheData.dungeonInstances.putIfAbsent(dungeonName, new HashSet<>());
        instanceCacheData.dungeonInstances.get(dungeonName).add(instanceName);
        instanceCacheData.playerInDungeon.put(player, instanceName);
    }
    public void leaveDungeon(Player player){
        if(!instanceCacheData.playerInDungeon.containsKey(player)){
            return;
        }
        if(!instanceCacheData.playerOriginLocation.containsKey(player)){
            return;
        }
        World world = player.getWorld();
        instanceCacheData.dungeonInstancePlayerSet.get(world.getName()).remove(player);
        player.teleport(instanceCacheData.playerOriginLocation.get(player));
        deleteEmptyInstance(world.getName());
        instanceCacheData.playerInDungeon.remove(player);
    }
    public Boolean deleteEmptyInstance(String instanceName){
        if(instanceCacheData.dungeonInstancePlayerSet.get(instanceName).isEmpty()){
            return deleteInstanceWorld(instanceName);
        }
        return false;
    }
    public Boolean deleteInstanceWorld(String instanceName){
        World world = Bukkit.getWorld(instanceName);
        if(world == null){
            return false;
        }
        File worldFile = world.getWorldFolder();
        Bukkit.unloadWorld(world, true);
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
//                    FileUtils.deleteDirectory(worldFile);
                    FileUtils.forceDelete(worldFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskLaterAsynchronously(plugin.instance, 100L);
        return true;
    }
    public void deleteInstances(){
        instanceCacheData.dungeonInstances.forEach((k, v) -> v.forEach(this::deleteInstanceWorld));
    }

}
