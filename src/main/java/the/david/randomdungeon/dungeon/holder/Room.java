package the.david.randomdungeon.dungeon.holder;


import org.bukkit.Location;

public class Room {
    public Room(String roomName, Location pos1, Location pos2, Dungeon dungeon){
        this.roomName = roomName;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.dungeon = dungeon;
        configPath = "Rooms." + roomName;
    }
    public void setDefaultConfigs(){
        dungeon.getConfig().setLocation( configPath + ".Pos1", pos1);
        dungeon.getConfig().setLocation( configPath + ".Pos2", pos2);
        dungeon.getConfig().setObject(configPath + ".doorEast", false);
        dungeon.getConfig().setObject(configPath + ".doorWest", false);
        dungeon.getConfig().setObject(configPath + ".doorNorth", false);
        dungeon.getConfig().setObject(configPath + ".doorSouth", false);
    }
    public Boolean loadSettings(){
        if(!dungeon.getConfig().hasKey(configPath)){
            return false;
        }
        pos1 = dungeon.getConfig().getLocation(configPath + ".Pos1");
        pos2 = dungeon.getConfig().getLocation(configPath + ".Pos2");
        doorEast = dungeon.getConfig().getBoolean(configPath + ".doorEast");
        doorWest = dungeon.getConfig().getBoolean(configPath + ".doorWest");
        doorNorth = dungeon.getConfig().getBoolean(configPath + ".doorNorth");
        doorSouth = dungeon.getConfig().getBoolean(configPath + ".doorSouth");
        return true;
    }
    private final Dungeon dungeon;
    private final String roomName;
    private Location pos1;
    private Location pos2;
    private Boolean doorEast = false;
    private Boolean doorWest = false;
    private Boolean doorNorth = false;
    private Boolean doorSouth = false;
    private Boolean canRotate;
    private final String configPath;

    public void setDoorDirection(String direction){
        if(direction.toUpperCase().contains("E")){
            doorEast = true;
        }
        if(direction.toUpperCase().contains("W")){
            doorWest = true;
        }
        if(direction.toUpperCase().contains("N")){
            doorNorth = true;
        }
        if(direction.toUpperCase().contains("S")){
            doorSouth = true;
        }
    }
    public Boolean getDoorEast(){
        return doorEast;
    }
    public Boolean getDoorWest(){
        return doorWest;
    }
    public Boolean getDoorNorth(){
        return doorNorth;
    }
    public Boolean getDoorSouth(){
        return doorSouth;
    }
    public Boolean getCanRotate(){
        return canRotate;
    }
    public Location getPos1(){
        return pos1;
    }
    public Location getPos2(){
        return pos2;
    }
    public String getRoomName(){
        return roomName;
    }
    public void remove(){
//        dungeon.getConfig().setObject(configPath, null);
        dungeon.getConfig().removeKey(configPath);
    }
}
