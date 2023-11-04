package the.david.randomdungeon.dungeon.utils;

import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;

public class DungeonEditChecks{
	public DungeonEditChecks(RandomDungeon plugin){
		this.plugin = plugin;
	}
	private final RandomDungeon plugin;
	public Boolean playerIsEditingDungeon(Player player){
		return plugin.dungeonManager.getDungeonNames().contains(plugin.dungeonManager.toShowName(player.getWorld().getName()));
	}
}
