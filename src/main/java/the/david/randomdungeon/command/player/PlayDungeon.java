package the.david.randomdungeon.command.player;

import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.command.SubCommand;

import java.util.Map;

public class PlayDungeon implements SubCommand{
	private final RandomDungeon plugin;
	public PlayDungeon(RandomDungeon plugin) {
		this.plugin = plugin;
	}
	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		plugin.dungeonInstanceManager.playDungeon(player, parsedArgs.get("dungeonName"));
	}
}
