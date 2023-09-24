package the.david.randomdungeon.dungeon;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.codehaus.plexus.util.FileUtils;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.holder.Dungeon;
import the.david.randomdungeon.dungeon.holder.DungeonInstance;
import the.david.randomdungeon.dungeon.holder.RDPlayer;

import java.io.File;
import java.io.IOException;

public class dungeonInstanceManager {
    public dungeonInstanceManager(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;
    public void playDungeon(Player player, String dungeonName){
        if(!plugin.dungeonManager.getDungeonNames().contains(dungeonName)){
            player.sendMessage(
                    Component.text("無此Dungeon").color(NamedTextColor.RED)
            );
            return;
        }
        Dungeon dungeon = plugin.dungeonManager.getDungeonByName(dungeonName);
        DungeonInstance dungeonInstance = dungeon.createInstance();
        dungeonInstance.addPlayer(player);

        RDPlayer rdPlayer = playerManager.addRDPlayer(player);
        rdPlayer.setOriginLocation(player.getLocation());
        rdPlayer.setPlayingDungeon(dungeon);
        rdPlayer.setPlayingDungeonInstance(dungeonInstance);
        player.teleport(new Location(dungeonInstance.getWorld(), 8, 1, 8).toCenterLocation());

//        instancesHandler.playerOriginLocation.put(player, player.getLocation());
//
//        instancesHandler.dungeonInstanceAmount.putIfAbsent(dungeonName, 0);
//        String instanceName = plugin.instance.getDataFolder().getPath().replaceAll("\\\\", "/") + "/DungeonInstances/" + dungeonName + "_" + instancesHandler.dungeonInstanceAmount.get(dungeonName);
//        instancesHandler.dungeonInstanceAmount.put(dungeonName, instancesHandler.dungeonInstanceAmount.get(dungeonName) + 1);
//        plugin.dungeonManager.getDungeonByName(dungeonName).getWorld().save();
//        File dungeonFile = plugin.dungeonManager.getDungeonByName(dungeonName).getWorld().getWorldFolder();
//        plugin.dungeonInstanceWorldHandler.copyWorld(dungeonFile, new File(instanceName));
//        WorldCreator worldCreator = new WorldCreator(instanceName);
//        World world = worldCreator.createWorld();
//        player.teleport(new Location(world, 8, 1, 8).toCenterLocation());
//        instancesHandler.dungeonInstancePlayerSet.putIfAbsent(instanceName, new HashSet<>());
//        instancesHandler.dungeonInstancePlayerSet.get(instanceName).add(player);
//        instancesHandler.dungeonInstances.putIfAbsent(dungeonName, new HashSet<>());
//        instancesHandler.dungeonInstances.get(dungeonName).add(instanceName);
//        instancesHandler.playerInDungeon.put(player, instanceName);
    }
    public void leaveDungeon(Player player){
        RDPlayer rdPlayer = playerManager.getRDPlayer(player);
        if(rdPlayer.getOriginLocation() == null){
            return;
        }
        DungeonInstance dungeonInstance = rdPlayer.getPlayingDungeonInstance();
        dungeonInstance.getPlayers().remove(player);
        rdPlayer.setPlayingDungeonInstance(null);
        player.teleport(rdPlayer.getOriginLocation());
        rdPlayer.setOriginLocation(null);
        deleteEmptyInstance(dungeonInstance);
    }
    public void deleteEmptyInstance(DungeonInstance dungeonInstance){
        if(dungeonInstance.getPlayers().isEmpty()){
            deleteInstanceWorld(dungeonInstance);
        }
    }
    public Boolean deleteInstanceWorld(DungeonInstance dungeonInstance){
        World world = dungeonInstance.getWorld();
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
        }.runTaskLater(plugin.instance, 100L);
        return true;
    }
    public void deleteInstances(){
        plugin.dungeonManager.getDungeons().forEach(v -> v.getInstances().forEach(this::deleteInstanceWorld));
    }

}
