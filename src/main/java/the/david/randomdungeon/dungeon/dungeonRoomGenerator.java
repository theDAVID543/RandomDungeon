package the.david.randomdungeon.dungeon;

import org.jetbrains.annotations.NotNull;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.holder.*;

import java.util.*;

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
        Boolean succeedGenPath = true;
        for(int i = 0; i < pathRoomAmount; i++){
            Set<String> checkedDirection = new HashSet<>();
            RoomInstance checkingRoomInstance = new RoomInstance(getRandomElement(rooms).get());
            String checkingDirection = null;
            for(int j = 0; j < 4; j++){
                checkingDirection = randomDirection(checkingRoomInstance, checkedDirection);
                if(checkingDirection == null) {
                    break;
                }
                if(checkCanGenerate(positionNow, roomInstances, checkingDirection)) {
                    break;
                }
            }
            if(checkingDirection == null){
                succeedGenPath = false;
                break;
            }
        }
        if(!succeedGenPath){
            return;
        }
    }
    public String randomDirection(RoomInstance roomInstance, Set<String> ignoreDirection){
        Collection<String> doorDirections = new ArrayList<>();
        if(roomInstance.getDoorEast() && !ignoreDirection.contains("east")){
            doorDirections.add("east");
        }
        if(roomInstance.getDoorWest() && !ignoreDirection.contains("west")){
            doorDirections.add("west");
        }
        if(roomInstance.getDoorSouth() && !ignoreDirection.contains("south")){
            doorDirections.add("south");
        }
        if(roomInstance.getDoorNorth() && !ignoreDirection.contains("north")){
            doorDirections.add("north");
        }
        Optional<String> result = getRandomElement(doorDirections);
        return result.orElse(null);
    }
    public Boolean checkCanGenerate(Vector2 positionNow, Map<Vector2, RoomInstance> roomInstances, @NotNull String direction){
        switch (direction) {
            case "east":
                return roomInstances.get(new Vector2(positionNow.getX() + 1, positionNow.getY())) != null;
            case "west":
                return roomInstances.get(new Vector2(positionNow.getX() - 1, positionNow.getY())) != null;
            case "south":
                return roomInstances.get(new Vector2(positionNow.getX(), positionNow.getY() - 1)) != null;
            case "north":
                return roomInstances.get(new Vector2(positionNow.getX(), positionNow.getY() + 1)) != null;
            default:
                return null;
        }
    }
    public <E> Optional<E> getRandomElement(Collection<E> e) {
        return e.stream()
                .skip(random.nextInt(e.size()))
                .findFirst();
    }
}
