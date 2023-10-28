package the.david.randomdungeon.command.editor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.command.SubCommand;

import java.util.Map;

public class RemoveDoor implements SubCommand{
	private final RandomDungeon plugin;

	public RemoveDoor(RandomDungeon plugin) {
		this.plugin = plugin;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs) {
		if(!plugin.dungeonEditor.removeDoorPosition(player, parsedArgs.get("roomName"), Integer.parseInt(parsedArgs.get("x")), Integer.parseInt(parsedArgs.get("y")))){
			player.sendMessage(
					Component.text("無法刪除door").color(NamedTextColor.RED)
			);
		}
	}
}
