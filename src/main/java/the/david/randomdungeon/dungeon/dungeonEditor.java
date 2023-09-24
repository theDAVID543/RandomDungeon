package the.david.randomdungeon.dungeon;

import com.fastasyncworldedit.core.world.SimpleWorld;
import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import jdk.internal.net.http.common.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.cacheData.dungeonCacheData;
import the.david.randomdungeon.handler.config;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class dungeonEditor implements Listener {
    public dungeonEditor(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private final RandomDungeon plugin;
    private final Map<Player, Location> playerSelectionPos1 = new HashMap<>();
    private final Map<Player, Location> playerSelectionPos2 = new HashMap<>();
    private ItemStack getWandItem(){
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta itemMeta = wand.getItemMeta();
        itemMeta.displayName(
                Component.text("RandomDungeon Wand").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        );
        wand.setItemMeta(itemMeta);
        return wand;
    }
    public void editDungeon(Player player, String dungeonName){
        if(!dungeonCacheData.dungeons.contains(dungeonName)){
            return;
        }
        player.teleport(new Location(Bukkit.getWorld(dungeonCacheData.toWorldName(dungeonName)), 8, 1, 8).toCenterLocation());
        player.getInventory().addItem(getWandItem());
    }
    public void testCopy(Player player){
        if(!dungeonCacheData.dungeons.contains(dungeonCacheData.toShowName(player.getWorld().getName()))){
            return;
        }
        CuboidRegion region = new CuboidRegion(toBlockVector3(player, playerSelectionPos1), toBlockVector3(player, playerSelectionPos2));
        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
        com.sk89q.worldedit.world.World weWorld = new BukkitWorld(player.getWorld());
        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                weWorld, region, clipboard, region.getMinimumPoint()
        );
        // configure here
        forwardExtentCopy.setCopyingEntities(true);
        Operations.complete(forwardExtentCopy);

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(new BukkitWorld(player.getWorld()))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(player.getX(), player.getY(), player.getZ()))
                    // configure here
                    .build();
            Operations.complete(operation);
        }
    }
    public Boolean storeRoomData(Player player, String roomName){
        if(playerSelectionPos1.get(player) == null || playerSelectionPos2.get(player) == null || !dungeonCacheData.dungeons.contains(dungeonCacheData.toShowName(player.getWorld().getName()))){
            return false;
        }
        Location pos1 = playerSelectionPos1.get(player);
        Location pos2 = playerSelectionPos2.get(player);
        String dungeonShowName = dungeonCacheData.toShowName(player.getWorld().getName());
        dungeonCacheData.dungeonDataConfig.get(dungeonShowName).setLocation("Rooms." + roomName + ".pos1", pos1);
        dungeonCacheData.dungeonDataConfig.get(dungeonShowName).setLocation("Rooms." + roomName + ".pos2", pos2);
        dungeonCacheData.dungeonRoomsPos1.get(dungeonShowName).put(roomName, pos1);
        dungeonCacheData.dungeonRoomsPos2.get(dungeonShowName).put(roomName, pos2);
        return true;
    }

    private BlockVector3 toBlockVector3(Player player, Map<Player, Location> playerSelectionPos) {
        return new BlockVector3() {
            @Override
            public int getX() {
                return (int) playerSelectionPos.get(player).getX();
            }

            @Override
            public int getY() {
                return (int) playerSelectionPos.get(player).getY();
            }

            @Override
            public int getZ() {
                return (int) playerSelectionPos.get(player).getZ();
            }
        };
    }

    @EventHandler
    public void onClickOnBlock(PlayerInteractEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null || !e.getPlayer().getInventory().getItemInMainHand().getItemMeta().equals(getWandItem().getItemMeta()) || !dungeonCacheData.dungeons.contains(dungeonCacheData.toShowName(e.getPlayer().getWorld().getName()))){
            return;
        }
        if(e.getAction() == Action.LEFT_CLICK_BLOCK){
            e.getPlayer().sendMessage(
                    Component.text("Selected Pos1 X: " + e.getClickedBlock().getLocation().getBlockX() + " Y: " + e.getClickedBlock().getLocation().getBlockY()+ " Z: " + e.getClickedBlock().getLocation().getBlockZ())
            );
            playerSelectionPos1.put(e.getPlayer(), e.getClickedBlock().getLocation());
        }else if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            e.getPlayer().sendMessage(
                    Component.text("Selected Pos2 X: " + e.getClickedBlock().getLocation().getBlockX() + " Y: " + e.getClickedBlock().getLocation().getBlockY()+ " Z: " + e.getClickedBlock().getLocation().getBlockZ())
            );
            playerSelectionPos2.put(e.getPlayer(), e.getClickedBlock().getLocation());
        }
        e.setCancelled(true);
    }
}
