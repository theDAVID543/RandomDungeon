package the.david.randomdungeon.dungeon;

import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.holder.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class dungeonRoomGenerator {
    public dungeonRoomGenerator(RandomDungeon plugin){
        this.plugin = plugin;
    }
    public RandomDungeon plugin;
    public Map<Vector2, RoomInstance> roomInstances = new HashMap<>();
    public void generateRooms(DungeonInstance dungeonInstance){
        Dungeon dungeon = dungeonInstance.getDungeon();
        Collection<Room> rooms =  dungeon.getRooms();

    }
}
