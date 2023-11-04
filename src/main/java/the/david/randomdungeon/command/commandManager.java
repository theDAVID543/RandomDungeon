package the.david.randomdungeon.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.command.dungeonManage.CreateDungeon;
import the.david.randomdungeon.command.dungeonManage.EditDungeon;
import the.david.randomdungeon.command.editor.AddDoor;
import the.david.randomdungeon.command.editor.AddRoom;
import the.david.randomdungeon.command.editor.RemoveRoom;
import the.david.randomdungeon.command.editor.SetGridSize;
import the.david.randomdungeon.command.player.LeaveDungeon;
import the.david.randomdungeon.command.player.PlayDungeon;
import the.david.randomdungeon.utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class commandManager implements CommandExecutor{
	public commandManager(RandomDungeon plugin){
		this.plugin = plugin;
		subCommands.put("leave", new LeaveDungeon(plugin));
		subCommands.put("create {dungeonName}", new CreateDungeon(plugin));
		subCommands.put("edit {dungeonName}", new EditDungeon(plugin));
		subCommands.put("play {dungeonName}", new PlayDungeon(plugin));
		subCommands.put("editor room add {roomName}", new AddRoom(plugin));
		subCommands.put("editor room remove {roomName}", new RemoveRoom(plugin));
		subCommands.put("editor room doors add {roomName} {x} {y}", new AddDoor(plugin));
		subCommands.put("editor room doors remove {roomName} {x} {y}", new AddDoor(plugin));
		subCommands.put("editor gridsize {gridSize}", new SetGridSize(plugin));
	}

	private final RandomDungeon plugin;
	private final Map<String, SubCommand> subCommands = new HashMap<>();


	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player) sender;


		Pair<SubCommand, Map<String, String>> matchedData = matchCommand(args);
		if (matchedData != null) {
			matchedData.getKey().execute(player, matchedData.getValue());
		} else {
			// 提示玩家該指令不存在或其他默認行為
			player.sendMessage(
					Component.text("該指令不存在").color(NamedTextColor.RED));
		}



//		if(args.length == 1){
//			if(args[0].equals("leave")){
//				plugin.dungeonInstanceManager.leaveDungeon(player, true);
//			}
//		}else if(args.length == 2){
//			switch (args[0]) {
//				case "create":
//					World world = plugin.dungeonManager.createDungeon(args[1]);
//					if (world != null) {
//						// player.teleport(new Location(world, 8, 1, 8).toCenterLocation());
//						player.sendMessage(Component.text("created Dungeon").color(NamedTextColor.RED));
//					} else {
//						player.sendMessage(Component.text("無法創建Dungeon").color(NamedTextColor.RED));
//					}
//					break;
//				case "edit":
//					plugin.dungeonEditor.editDungeon(player, args[1]);
//					break;
//				case "play":
//					plugin.dungeonInstanceManager.playDungeon(player, args[1]);
//					break;
//			}
//		}else if(args.length == 3){
//
//		}else if(args.length == 4){
//			if(Objects.equals(args[0], "editor")){
//				if(Objects.equals(args[1], "room")){
//					if(Objects.equals(args[2], "add")){
//						if(!plugin.dungeonEditor.addRoom(player, args[3])){
//							player.sendMessage(
//									Component.text("無法新增Room").color(NamedTextColor.RED)
//							);
//						}
//					}
//					if(Objects.equals(args[2], "remove")){
//						if(!plugin.dungeonEditor.removeRoom(player, args[3])){
//							player.sendMessage(
//									Component.text("無法刪除Room").color(NamedTextColor.RED)
//							);
//						}
//					}
//				}
//			}
//		}else if(args.length == 5){
//
//		}else if(args.length == 7){
//			if(Objects.equals(args[0], "editor")){
//				if(Objects.equals(args[1], "room")){
//					if(Objects.equals(args[2], "doors")){
//						if(Objects.equals(args[3], "add")){
//							if(!plugin.dungeonEditor.addDoorPosition(player, args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]))){
//								player.sendMessage(
//										Component.text("無法新增door").color(NamedTextColor.RED)
//								);
//							}
//						}
//						if(Objects.equals(args[3], "remove")){
//							if(!plugin.dungeonEditor.removeDoorPosition(player, args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]))){
//								player.sendMessage(
//										Component.text("無法刪除door").color(NamedTextColor.RED)
//								);
//							}
//						}
//					}
//				}
//			}
//		}
		return false;
	}

	private Pair<SubCommand, Map<String, String>> matchCommand(String[] args) {
		for (Map.Entry<String, SubCommand> entry : subCommands.entrySet()) {
			String[] commandParts = entry.getKey().split(" ");
			if (commandParts.length != args.length) {
				continue;
			}

			Map<String, String> parsedArgs = new HashMap<>();
			boolean matches = true;
			for (int i = 0; i < commandParts.length; i++) {
				if (commandParts[i].startsWith("{") && commandParts[i].endsWith("}")) {
					String paramName = commandParts[i].substring(1, commandParts[i].length() - 1);
					parsedArgs.put(paramName, args[i]);
				} else if (!commandParts[i].equals(args[i])) {
					matches = false;
					break;
				}
			}

			if (matches) {
				return new Pair<>(entry.getValue(), parsedArgs);
			}
		}
		return null;
	}
}
