package the.david.randomdungeon.dungeon.holder;


import org.bukkit.Location;
import the.david.randomdungeon.utils.Pair;
import the.david.randomdungeon.utils.Vector2;
import the.david.randomdungeon.utils.Vector3;

import java.util.HashMap;
import java.util.Map;

public class Room{
	public Room(String roomName, Location pos1, Location pos2, Dungeon dungeon){
		this.roomName = roomName;
		this.dungeon = dungeon;
		configPath = "Rooms." + roomName;
		absolutePosition = new Vector3(
				Math.min(pos1.getBlockX(), pos2.getBlockX()),
				Math.min(pos1.getBlockY(), pos2.getBlockY()),
				Math.min(pos1.getBlockZ(), pos2.getBlockZ())
		);
		diagonalPosition = toRelativePosition(new Vector3(
				Math.max(pos1.getBlockX(), pos2.getBlockX()),
				Math.max(pos1.getBlockY(), pos2.getBlockY()),
				Math.max(pos1.getBlockZ(), pos2.getBlockZ()))
		);
	}

	public void setDefaultConfigs(){
		dungeon.getConfig().setVector3(configPath + ".absolutePosition", absolutePosition);
		dungeon.getConfig().setVector3(configPath + ".diagonalPosition", diagonalPosition);
		dungeon.getConfig().setObject(configPath + ".canBeStartRoom", false);
//		dungeon.getConfig().setLocation(configPath + ".Pos1", pos1);
//		dungeon.getConfig().setLocation(configPath + ".Pos2", pos2);
//		dungeon.getConfig().setObject(configPath + ".doorEast", false);
//		dungeon.getConfig().setObject(configPath + ".doorWest", false);
//		dungeon.getConfig().setObject(configPath + ".doorNorth", false);
//		dungeon.getConfig().setObject(configPath + ".doorSouth", false);
	}

	public void loadSettings(){
		if(!dungeon.getConfig().hasKey(configPath)){
			return;
		}
		absolutePosition = dungeon.getConfig().getVector3(configPath + ".absolutePosition");
		diagonalPosition = dungeon.getConfig().getVector3(configPath + ".diagonalPosition");
		canBeStartRoom = dungeon.getConfig().getBoolean(configPath + ".canBeStartRoom");
		if(dungeon.getConfig().hasKey(configPath + ".doors")){
			doorPositions = dungeon.getConfig().getDoors(configPath + ".doors");
		}
//		pos1 = dungeon.getConfig().getLocation(configPath + ".Pos1");
//		pos2 = dungeon.getConfig().getLocation(configPath + ".Pos2");
	}
	public Vector3 absolutePosition;
	public Vector3 diagonalPosition;
	private final Dungeon dungeon;
	public final String roomName;
	public Boolean canRotate;
	private final String configPath;
	private boolean canBeStartRoom = false;
	public Map<Vector2, Pair<Vector3, Vector3>> doorPositions = new HashMap<>();
	public void addDoor(Vector2 gridPos, Location pos1, Location pos2){
		Vector3 doorPos1 = new Vector3(
				Math.min(pos1.getBlockX(), pos2.getBlockX()),
				Math.min(pos1.getBlockY(), pos2.getBlockY()),
				Math.min(pos1.getBlockZ(), pos2.getBlockZ())
		);
		Vector3 doorPos2 = new Vector3(
				Math.max(pos1.getBlockX(), pos2.getBlockX()),
				Math.max(pos1.getBlockY(), pos2.getBlockY()),
				Math.max(pos1.getBlockZ(), pos2.getBlockZ())
		);
		doorPositions.put(gridPos, new Pair<>(doorPos1, doorPos2));
		dungeon.getConfig().setDoor(configPath + ".doors", gridPos, doorPos1, doorPos2);
	}

	public Vector3 toRelativePosition(Vector3 location){
		return new Vector3(location.x - absolutePosition.x, location.y - absolutePosition.y, location.z - absolutePosition.z);
	}
	public void setCanBeStartRoom(boolean canBeStartRoom){
		this.canBeStartRoom = canBeStartRoom;
		dungeon.getConfig().setObject(configPath + ".canBeStartRoom", canBeStartRoom);
	}
	public boolean getCanBeStartRoom(){
		return canBeStartRoom;
	}
	public void removeDoorPosition(int x,int y){
		doorPositions.remove(new Vector2(x,y));
	}

	public void remove(){
//        dungeon.getConfig().setObject(configPath, null);
		dungeon.getConfig().removeKey(configPath);
	}
}
