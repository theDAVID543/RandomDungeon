package the.david.randomdungeon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import the.david.randomdungeon.RandomDungeon;

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

        return true;
    }
}
