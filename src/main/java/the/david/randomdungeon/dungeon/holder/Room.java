package the.david.randomdungeon.dungeon.holder;


import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class Room{
	public Room(String roomName, Location pos1, Location pos2, Dungeon dungeon){
		this.roomName = roomName;
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.dungeon = dungeon;
		configPath = "Rooms." + roomName;
		x1 = Math.min(pos1.getBlockX(), pos2.getBlockX());
		y1 = Math.min(pos1.getBlockY(), pos2.getBlockY());
		z1 = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
		x2 = Math.max(pos1.getBlockX(), pos2.getBlockX());
		y2 = Math.max(pos1.getBlockY(), pos2.getBlockY());
		z2 = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
	}

	public void setDefaultConfigs(){
		dungeon.getConfig().setLocation(configPath + ".Pos1", pos1);
		dungeon.getConfig().setLocation(configPath + ".Pos2", pos2);
		dungeon.getConfig().setObject(configPath + ".doorEast", false);
		dungeon.getConfig().setObject(configPath + ".doorWest", false);
		dungeon.getConfig().setObject(configPath + ".doorNorth", false);
		dungeon.getConfig().setObject(configPath + ".doorSouth", false);
	}

	public void loadSettings(){
		if(!dungeon.getConfig().hasKey(configPath)){
			return;
		}
		pos1 = dungeon.getConfig().getLocation(configPath + ".Pos1");
		pos2 = dungeon.getConfig().getLocation(configPath + ".Pos2");
	}

	public int x1, y1, z1, x2, y2, z2;
	private final Dungeon dungeon;
	public final String roomName;
	public Location pos1, pos2;
	public Boolean canRotate;
	private final String configPath;
	public Set<Vector2> doorPositions = new HashSet<>();

	public void addDoorPosition(int x,int y){
		doorPositions.add(new Vector2(x,y));
	}
	public void addDoorPosition(Vector2 vector2){
		doorPositions.add(vector2);
	}
	public void removeDoorPosition(int x,int y){
		doorPositions.remove(new Vector2(x,y));
	}

	public void remove(){
//        dungeon.getConfig().setObject(configPath, null);
		dungeon.getConfig().removeKey(configPath);
	}
}
