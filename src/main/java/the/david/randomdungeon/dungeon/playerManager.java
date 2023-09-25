package the.david.randomdungeon.dungeon;

import org.bukkit.entity.Player;
import the.david.randomdungeon.dungeon.holder.RDPlayer;

import java.util.HashMap;
import java.util.Map;

public class playerManager {
    private static final Map<Player, RDPlayer> rdPlayers = new HashMap<>();
    public static RDPlayer getRDPlayer(Player player){
        return rdPlayers.get(player);
    }
    public static RDPlayer addRDPlayer(Player player){
        RDPlayer rdPlayer = new RDPlayer(player);
        rdPlayers.put(player, rdPlayer);
        return rdPlayer;
    }
}
