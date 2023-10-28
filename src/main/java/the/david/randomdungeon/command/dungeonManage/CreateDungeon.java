package the.david.randomdungeon.command.dungeonManage;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.command.SubCommand;

import java.util.Map;

public class CreateDungeon implements SubCommand{
	private final RandomDungeon plugin;
	public CreateDungeon(RandomDungeon plugin) {
		this.plugin = plugin;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		World world = plugin.dungeonManager.createDungeon(parsedArgs.get("dungeonName"));
		if (world != null) {
			// player.teleport(new Location(world, 8, 1, 8).toCenterLocation());
			player.sendMessage(Component.text("created Dungeon").color(NamedTextColor.RED));
		} else {
			player.sendMessage(Component.text("無法創建Dungeon").color(NamedTextColor.RED));
		}
	}
}
