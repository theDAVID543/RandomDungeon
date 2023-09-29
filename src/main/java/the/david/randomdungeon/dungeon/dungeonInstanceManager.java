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

        RDPlayer rdPlayer = playerManager.addRDPlayer(player);
        rdPlayer.setOriginLocation(player.getLocation());
        rdPlayer.setPlayingDungeon(dungeon);
        rdPlayer.setPlayingDungeonInstance(dungeonInstance);
        player.teleport(new Location(dungeonInstance.getWorld(), 8, 1, 8).toCenterLocation());
    }
    public void leaveDungeon(Player player, Boolean deleteEmpty){
        RDPlayer rdPlayer = playerManager.getRDPlayer(player);
        if(rdPlayer == null){
            return;
        }
        if(rdPlayer.getOriginLocation() == null){
            return;
        }
        DungeonInstance dungeonInstance = rdPlayer.getPlayingDungeonInstance();
        dungeonInstance.getPlayers().remove(player);
        rdPlayer.setPlayingDungeonInstance(null);
        player.teleport(rdPlayer.getOriginLocation());
        rdPlayer.setOriginLocation(null);
        if(deleteEmpty){
            deleteEmptyInstance(dungeonInstance);
        }
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
        if(plugin.dungeonManager.getDungeons().isEmpty()){
            return;
        }
        plugin.dungeonManager.getDungeons().forEach(v -> {
            if(!v.getInstances().isEmpty()){
                v.getInstances().forEach(j ->{
                    evacuatePlayers(j);
                    World world = j.getWorld();
                    if(world != null){
                        File worldFile = world.getWorldFolder();
                        Bukkit.unloadWorld(world, false);
                        try {
                            FileUtils.forceDelete(worldFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }
    public void evacuatePlayers(DungeonInstance dungeonInstance){
        if(dungeonInstance == null){
            return;
        }
        dungeonInstance.getPlayers().forEach(v ->{
            leaveDungeon(v, false);
        });
    }

}
