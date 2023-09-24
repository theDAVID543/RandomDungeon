package the.david.randomdungeon.dungeon.holder;


import org.bukkit.Location;

public class Room {
    public Room(String roomName, Location pos1, Location pos2){
        this.roomName = roomName;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }
    private String roomName;
    private Location pos1;
    private Location pos2;

    public void setPos1(Location location){
        pos1 = location;
    }
    public void setPos2(Location location){
        pos2 = location;
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
}
