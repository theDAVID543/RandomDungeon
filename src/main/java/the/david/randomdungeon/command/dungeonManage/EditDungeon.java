package the.david.randomdungeon.command.dungeonManage;

import org.bukkit.entity.Player;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.command.SubCommand;

import java.util.Map;

public class EditDungeon implements SubCommand{
	private final RandomDungeon plugin;
	public EditDungeon(RandomDungeon plugin) {
		this.plugin = plugin;
	}
	@Override
	public void execute(Player player, Map<String, String> parsedArgs){
		plugin.dungeonEditor.editDungeon(player, parsedArgs.get("dungeonName"));
	}
}
