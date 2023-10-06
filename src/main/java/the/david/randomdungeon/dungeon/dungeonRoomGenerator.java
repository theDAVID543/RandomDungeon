package the.david.randomdungeon.dungeon;

import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.holder.*;

import java.util.*;
import java.util.stream.Stream;

public class dungeonRoomGenerator {
    public dungeonRoomGenerator(RandomDungeon plugin){
        this.plugin = plugin;
    }
    public RandomDungeon plugin;
    public Random random = new Random();
    public void generateRoomMap(DungeonInstance dungeonInstance, Integer pathRoomAmount){
        Dungeon dungeon = dungeonInstance.getDungeon();
        Collection<Room> rooms =  dungeon.getRooms();
        Map<Vector2, RoomInstance> roomInstances = new HashMap<>();
        RoomInstance firstRoomInstance = new RoomInstance(getRandomElement(rooms).get());
        roomInstances.put(new Vector2(0,0), firstRoomInstance);
        Vector2 positionNow = new Vector2(0,0);
        for(int i = 0; i < pathRoomAmount; i++){
            roomInstances.get(positionNow);
        }
    }
    public String randomDirection(RoomInstance roomInstance){
        Collection<String> doorDirections = new ArrayList<>();
        if(roomInstance.getDoorEast()){
            doorDirections.add("east");
        }
        if(roomInstance.getDoorWest()){
            doorDirections.add("west");
        }
        if(roomInstance.getDoorSouth()){
            doorDirections.add("south");
        }
        if(roomInstance.getDoorNorth()){
            doorDirections.add("north");
        }
        Optional<String> result = getRandomElement(doorDirections);
        return result.orElse(null);
    }
    public Boolean checkCanGenerate(Vector2 positionNow, Map<Vector2, RoomInstance> roomInstances, String direction){
        if(direction.equals("east")){
            if(roomInstances.get(new Vector2(positionNow.getX() + 1, positionNow.getY())) != null){
                return true;
            }else{
                return false;
            }
        }else if(direction.equals("west")){
            if(roomInstances.get(new Vector2(positionNow.getX() - 1, positionNow.getY())) != null){
                return true;
            }else{
                return false;
            }
        }else if(direction.equals("south")){
            if(roomInstances.get(new Vector2(positionNow.getX(), positionNow.getY() - 1)) != null){
                return true;
            }else{
                return false;
            }
        }else if(direction.equals("north")){
            if(roomInstances.get(new Vector2(positionNow.getX(), positionNow.getY() + 1)) != null){
                return true;
            }else{
                return false;
            }
        }else{
            return null;
        }
    }
    public <E> Optional<E> getRandomElement(Collection<E> e) {
        return e.stream()
                .skip(random.nextInt(e.size()))
                .findFirst();
    }
}
