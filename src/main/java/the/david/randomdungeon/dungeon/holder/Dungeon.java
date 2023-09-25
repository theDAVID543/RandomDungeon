package the.david.randomdungeon.dungeon.holder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import the.david.randomdungeon.handler.config;

import java.util.*;

import static the.david.randomdungeon.RandomDungeon.dungeonFolder;
import static the.david.randomdungeon.RandomDungeon.plugin;

public class Dungeon {
    public Dungeon(String dungeonShowName){
        showName = dungeonShowName;
        dungeonDataConfig = new config(plugin, "/data/dungeons/" + dungeonShowName + ".yml");
        dungeonDataConfig.createCustomConfig();
        WorldCreator worldCreator = new WorldCreator(dungeonFolder + showName);
        dungeonWorld = worldCreator.createWorld();
        if(dungeonDataConfig.getKeys("Rooms") != null){
            dungeonDataConfig.getKeys("Rooms").forEach(v ->{
                Room room = new Room(v, dungeonDataConfig.getLocation("Rooms." + v + ".Pos1"), dungeonDataConfig.getLocation("Rooms." + v + ".Pos2"), this);
                addRoom(room);
            });
        }
    }
    private World dungeonWorld;
    private final String showName;
    private final config dungeonDataConfig;
    private final Map<String, Room> rooms = new HashMap<>();
    private final Set<DungeonInstance> instances = new HashSet<>();
    private Integer instanceAmount = 0;
    public DungeonInstance createInstance(){
        Bukkit.unloadWorld(getWorld(), true);
        DungeonInstance instance = new DungeonInstance(getShowName(), instanceAmount);
        instanceAmount += 1;
        instances.add(instance);
        return instance;
    }
    public Set<DungeonInstance> getInstances(){
        return instances;
    }
    public config getConfig(){
        return dungeonDataConfig;
    }
    public Collection<Room> getRooms(){
        return rooms.values();
    }
    public Room getRoomByName(String roomName){
        return rooms.get(roomName);
    }
    public String getWorldName(){
        return dungeonFolder + showName;
    }
    public World getWorld(){
        return dungeonWorld;
    }
    public String getShowName(){
        return showName;
    }
    public void addRoom(Room room){
        rooms.put(room.getRoomName(), room);
    }
    public void addRoomWithName(String roomName, Location pos1, Location pos2){
        Room room = new Room(roomName, pos1, pos2, this);
        addRoom(room);
        getConfig().setLocation( "Rooms." + roomName + ".Pos1", pos1);
        getConfig().setLocation( "Rooms." + roomName + ".Pos2", pos2);
    }
    public void loadWorld(){
        dungeonWorld = new WorldCreator(getWorldName()).createWorld();
    }
    public void removeRoom(String roomName){
        Room room = rooms.get(roomName);
        room.remove();
        rooms.remove(roomName);
    }
}
