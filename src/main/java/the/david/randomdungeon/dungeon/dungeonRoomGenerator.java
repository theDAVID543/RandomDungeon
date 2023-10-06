package the.david.randomdungeon.dungeon;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.dungeon.holder.*;

import java.util.*;

import static java.lang.Math.abs;

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
        for (int i = 0; i < pathRoomAmount; i++) {
            Set<String> checkedDirection = new HashSet<>();
            RoomInstance checkingRoomInstanceHere = roomInstances.get(positionNow);
            RoomInstance checkingRoomInstance = null;
            String checkingDirection = null;
            while (true) {
                Bukkit.getLogger().info(checkedDirection.size() + " " + checkingRoomInstanceHere.getDoorAmount());
                checkingDirection = randomDirection(checkingRoomInstanceHere, checkedDirection);
                if (checkingDirection == null) {
                    break;
                }
                if(checkedDirection.size() >= checkingRoomInstanceHere.getDoorAmount()) {
                    checkingDirection = null;
                    break;
                }else if (checkCanGenerate(positionNow, roomInstances, checkingDirection)) {
                    ArrayList<Room> newRoomList = new ArrayList<>(rooms);
                    Collections.shuffle(newRoomList);
                    Boolean canGenerate = null;
                    for (int k = 0; k < rooms.size(); k++) {
                        if (checkingDirection.equals("north")) {
                            if (newRoomList.get(k).getDoorSouth()) {
                                checkingRoomInstance = new RoomInstance(newRoomList.get(k));
                                canGenerate = true;
                                break;
                            }
                        } else if (checkingDirection.equals("south")) {
                            if (newRoomList.get(k).getDoorNorth()) {
                                checkingRoomInstance = new RoomInstance(newRoomList.get(k));
                                canGenerate = true;
                                break;
                            }
                        } else if (checkingDirection.equals("east")) {
                            if (newRoomList.get(k).getDoorWest()) {
                                checkingRoomInstance = new RoomInstance(newRoomList.get(k));
                                canGenerate = true;
                                break;
                            }
                        } else if (checkingDirection.equals("west")) {
                            if (newRoomList.get(k).getDoorEast()) {
                                checkingRoomInstance = new RoomInstance(newRoomList.get(k));
                                canGenerate = true;
                                break;
                            }
                        }
                    }
                    break;
                } else {
                    checkedDirection.add(checkingDirection);
                }
            }
            if (checkingDirection == null) {
                succeedGenPath = false;
                Bukkit.getLogger().info("checkingDirection == null");
                break;
            }
            switch (checkingDirection) {
                case "east":
                    positionNow = new Vector2(positionNow.getX() + 1, positionNow.getY());
                    roomInstances.put(positionNow, checkingRoomInstance);
                    Bukkit.getLogger().info("east put " + positionNow.getX() + " " + positionNow.getY());
                    break;
                case "west":
                    positionNow = new Vector2(positionNow.getX() - 1, positionNow.getY());
                    roomInstances.put(positionNow, checkingRoomInstance);
                    Bukkit.getLogger().info("west put " + positionNow.getX() + " " + positionNow.getY());
                    break;
                case "south":
                    positionNow = new Vector2(positionNow.getX(), positionNow.getY() - 1);
                    roomInstances.put(positionNow, checkingRoomInstance);
                    Bukkit.getLogger().info("south put " + positionNow.getX() + " " + positionNow.getY());
                    break;
                case "north":
                    positionNow = new Vector2(positionNow.getX(), positionNow.getY() + 1);
                    roomInstances.put(positionNow, checkingRoomInstance);
                    Bukkit.getLogger().info("north put " + positionNow.getX() + " " + positionNow.getY());
                    break;
            }
            Bukkit.getLogger().info(positionNow.getX() + " " + positionNow.getY() + " " + checkingRoomInstance.getRoomName());
            StringBuilder outOneLine = new StringBuilder();
            outOneLine.append("  ");
            for (int y = -20; y < 21; y++) {
                outOneLine.append(String.format("%2d", abs(y)));
            }
            Bukkit.getLogger().info(outOneLine.toString());
            for (int x = -20; x < 21; x++) {
                outOneLine = new StringBuilder();
                outOneLine.append(String.format("%2d", abs(x)));
                for (int y = -20; y < 21; y++) {
                    if (roomInstances.get(new Vector2(x, y)) == null) {
                        outOneLine.append(String.format("%2s", "╳"));
                    } else {
                        outOneLine.append(String.format("%2s", getRoomShowSymbol(roomInstances.get(new Vector2(x, y)))));
                    }
                }
                Bukkit.getLogger().info(outOneLine.toString());
            }
        }
//        if(!succeedGenPath){
//            return;
//        }
//        for(int i = -15; i < 15; i++){
//            StringBuilder outOneLine = new StringBuilder();
//            for(int j = -15; j < 15; j++){
//                if(roomInstances.get(new Vector2(i,j)) == null){
//                    outOneLine.append("╳");
//                }else{
//                    outOneLine.append(getRoomShowSymbol(roomInstances.get(new Vector2(i,j))));
//                }
//            }
//            Bukkit.getLogger().info(outOneLine.toString());
//        }
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
                return roomInstances.get(new Vector2(positionNow.getX() + 1, positionNow.getY())) == null;
            case "west":
                return roomInstances.get(new Vector2(positionNow.getX() - 1, positionNow.getY())) == null;
            case "south":
                return roomInstances.get(new Vector2(positionNow.getX(), positionNow.getY() - 1)) == null;
            case "north":
                return roomInstances.get(new Vector2(positionNow.getX(), positionNow.getY() + 1)) == null;
            default:
                return null;
        }
    }
    public <E> Optional<E> getRandomElement(Collection<E> e) {
//        Bukkit.getLogger().info(String.valueOf(e.size()));
        return e.stream()
                .skip(random.nextInt(e.size()))
                .findFirst();
    }
    public String getRoomShowSymbol(RoomInstance roomInstance){
        if(roomInstance.getDoorAmount() == 2){
            if(roomInstance.getDoorNorth() && roomInstance.getDoorEast()){
                return "┏";
            }else if(roomInstance.getDoorNorth() && roomInstance.getDoorSouth()){
                return "━";
            }else if(roomInstance.getDoorNorth() && roomInstance.getDoorWest()){
                return "┗";
            }else if(roomInstance.getDoorEast() && roomInstance.getDoorSouth()){
                return "┓";
            }else if(roomInstance.getDoorEast() && roomInstance.getDoorWest()){
                return "┃";
            }else if(roomInstance.getDoorSouth() && roomInstance.getDoorWest()){
                return "┛";
            }
        }else if(roomInstance.getDoorAmount() == 3){
            if(!roomInstance.getDoorNorth()){
                return "┫";
            }else if(!roomInstance.getDoorEast()){
                return "┻";
            }else if(!roomInstance.getDoorSouth()){
                return "┣";
            }else if(!roomInstance.getDoorWest()){
                return "┳";
            }
        }else if(roomInstance.getDoorAmount() == 4){
            return "╋";
        }
        return null;
    }
}