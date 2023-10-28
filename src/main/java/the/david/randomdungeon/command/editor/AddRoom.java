package the.david.randomdungeon.command.editor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.command.SubCommand;

import java.util.Map;

public class AddRoom implements SubCommand{
	private final RandomDungeon plugin;

	public AddRoom(RandomDungeon plugin) {
		this.plugin = plugin;
	}

	@Override
	public void execute(Player player, Map<String, String> parsedArgs) {
		if (!plugin.dungeonEditor.addRoom(player, parsedArgs.get("roomName"))) {
			player.sendMessage(Component.text("無法新增Room").color(NamedTextColor.RED));
		}
	}
}
