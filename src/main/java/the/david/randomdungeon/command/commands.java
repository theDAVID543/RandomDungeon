package the.david.randomdungeon.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import the.david.randomdungeon.RandomDungeon;

import java.util.Objects;

public class commands implements CommandExecutor {
    public commands(RandomDungeon plugin){
        this.plugin = plugin;
    }
    private RandomDungeon plugin;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(args.length == 1){
            if(args[0].equals("leave")){
                plugin.dungeonInstanceManager.leaveDungeon(player);
            }
        }else if(args.length == 2){
            if(Objects.equals(args[0], "create")){
                World world = plugin.dungeonManager.createDungeon(args[1]);
                if(world!=null){
                    player.teleport(new Location(world, 8, 1, 8).toCenterLocation());
                }else{
                    player.sendMessage(
                            Component.text("無法創建Dungeon").color(NamedTextColor.RED)
                    );
                }
            }
            if(Objects.equals(args[0], "edit")){
                plugin.dungeonEditor.editDungeon(player, args[1]);
            }
            if(Objects.equals(args[0], "play")){
                plugin.dungeonInstanceManager.playDungeon(player, args[1]);
            }
        }else if(args.length == 3){
            if(Objects.equals(args[0], "editor")){
                if(Objects.equals(args[1], "addroom")){
                    if(!plugin.dungeonEditor.addRoom(player, args[2])){
                        player.sendMessage(
                                Component.text("無法新增Room").color(NamedTextColor.RED)
                        );
                    }
                }
                if(Objects.equals(args[1], "removeeroom")){
                    if(!plugin.dungeonEditor.removeRoom(player, args[2])){
                        player.sendMessage(
                                Component.text("無法刪除Room").color(NamedTextColor.RED)
                        );
                    }
                }
            }
        }
        return false;
    }
}
