package the.david.randomdungeon.dungeon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.data.dungeonData;

public class dungeonEditor {
    public dungeonEditor(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;
    public void editDungeon(Player player, String dungeonName){
        if(!dungeonData.dungeons.contains(dungeonName)){
            return;
        }
        player.teleport(new Location(Bukkit.getWorld(dungeonName), 8, 1, 8).toCenterLocation());
    }
}
