package the.david.randomdungeon.dungeon.holder;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RDPlayer{
	public RDPlayer(Player player){
		this.player = player;
	}

	private final Player player;
	private Location originLocation;
	private Dungeon playingDungeon;
	private DungeonInstance playingDungeonInstance;

	public void setOriginLocation(Location location){
		originLocation = location;
	}

	public Location getOriginLocation(){
		return originLocation;
	}

	public void setPlayingDungeon(Dungeon dungeon){
		playingDungeon = dungeon;
	}

	public Dungeon getPlayingDungeon(){
		return playingDungeon;
	}

	public void setPlayingDungeonInstance(DungeonInstance dungeonInstance){
		playingDungeonInstance = dungeonInstance;
	}

	public DungeonInstance getPlayingDungeonInstance(){
		return playingDungeonInstance;
	}
}
