//package the.david.randomdungeon.dungeon;
//
//import org.bukkit.Bukkit;
//import org.jetbrains.annotations.NotNull;
//import the.david.randomdungeon.RandomDungeon;
//import the.david.randomdungeon.dungeon.holder.*;
//import the.david.randomdungeon.enums.roomDoorDirections;
//
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static java.lang.Math.abs;
//
//public class oldDungeonRoomGenerator{
//	public oldDungeonRoomGenerator(RandomDungeon plugin, DungeonInstance dungeonInstance){
//		this.plugin = plugin;
//		this.dungeonInstance = dungeonInstance;
//		this.dungeon = dungeonInstance.getDungeon();
//		rooms = dungeon.getRooms();
//	}
//
//	private final DungeonInstance dungeonInstance;
//	private final Dungeon dungeon;
//	private final RandomDungeon plugin;
//	private final Random random = new Random();
//    private final Map<Vector2, RoomInstance> roomInstances = new HashMap<>();
//	private final Collection<Room> rooms;
//	private Vector2 positionNow;
//
//	public void generateRoomMap(Integer pathRoomAmount){
//		resetRoomMap();
//		while(true){
//			if(generatePath(pathRoomAmount)){
//				break;
//			}else{
//				resetRoomMap();
//			}
//		}
//		generateMaze();
//	}
//	public void resetRoomMap(){
//		roomInstances.clear();
//		Optional<Room> testingRoom;
//		while(true){
//			testingRoom = getRandomElement(rooms);
//			if(testingRoom.isPresent() && testingRoom.get().getDoorAmount() >= 1){
//				break;
//			}
//		}
//		roomInstances.put(new Vector2(0, 0), new RoomInstance(testingRoom.get()));
//		positionNow = new Vector2(0, 0);
//	}
//
//	public Boolean generatePath(Integer pathRoomAmount){
//		boolean succeedGenPath = true;
//		for(int i = 0; i < pathRoomAmount; i++){
//			Set<roomDoorDirections> checkedDirection = new HashSet<>();
//			RoomInstance checkingRoomInstanceHere = roomInstances.get(positionNow);
//			RoomInstance checkingRoomInstance = null;
//			roomDoorDirections checkingDirection;
//			while(true){
//				Bukkit.getLogger().info(checkedDirection.size() + " " + checkingRoomInstanceHere.getDoorAmount());
//				checkingDirection = randomDirection(checkingRoomInstanceHere, checkedDirection);
//				if(checkedDirection.size() >= checkingRoomInstanceHere.getDoorAmount()){
//					checkingDirection = null;
//					break;
//				}else if(checkCanGenerate(positionNow, roomInstances, checkingDirection)){
//					ArrayList<Room> newRoomList = new ArrayList<>();
//					rooms.forEach(v -> {
//						if(v.getDoorAmount() >= 2){
//							newRoomList.add(v);
//						}
//					});
//					Collections.shuffle(newRoomList);
//					Boolean canGenerate = null;
//					for(int j = 0; j < rooms.size(); j++){
//						if(checkingDirection.equals(roomDoorDirections.NORTH)){
//							if(newRoomList.get(j).getDoorSouth()){
//								checkingRoomInstance = new RoomInstance(newRoomList.get(j));
//								canGenerate = true;
//								break;
//							}
//						}else if(checkingDirection.equals(roomDoorDirections.SOUTH)){
//							if(newRoomList.get(j).getDoorNorth()){
//								checkingRoomInstance = new RoomInstance(newRoomList.get(j));
//								canGenerate = true;
//								break;
//							}
//						}else if(checkingDirection.equals(roomDoorDirections.EAST)){
//							if(newRoomList.get(j).getDoorWest()){
//								checkingRoomInstance = new RoomInstance(newRoomList.get(j));
//								canGenerate = true;
//								break;
//							}
//						}else if(checkingDirection.equals(roomDoorDirections.WEST)){
//							if(newRoomList.get(j).getDoorEast()){
//								checkingRoomInstance = new RoomInstance(newRoomList.get(j));
//								canGenerate = true;
//								break;
//							}
//						}
//					}
//					break;
//				}else{
//					checkedDirection.add(checkingDirection);
//				}
//			}
//			if(checkingDirection == null){
//				succeedGenPath = false;
//				Bukkit.getLogger().info("checkingDirection == null");
//				break;
//			}
//			switch(checkingDirection){
//				case EAST:
//					positionNow = new Vector2(positionNow.getX() + 1, positionNow.getY());
//					roomInstances.put(positionNow, checkingRoomInstance);
//					Bukkit.getLogger().info("east put " + positionNow.getX() + " " + positionNow.getY());
//					break;
//				case WEST:
//					positionNow = new Vector2(positionNow.getX() - 1, positionNow.getY());
//					roomInstances.put(positionNow, checkingRoomInstance);
//					Bukkit.getLogger().info("west put " + positionNow.getX() + " " + positionNow.getY());
//					break;
//				case SOUTH:
//					positionNow = new Vector2(positionNow.getX(), positionNow.getY() - 1);
//					roomInstances.put(positionNow, checkingRoomInstance);
//					Bukkit.getLogger().info("south put " + positionNow.getX() + " " + positionNow.getY());
//					break;
//				case NORTH:
//					positionNow = new Vector2(positionNow.getX(), positionNow.getY() + 1);
//					roomInstances.put(positionNow, checkingRoomInstance);
//					Bukkit.getLogger().info("north put " + positionNow.getX() + " " + positionNow.getY());
//					break;
//			}
//			Bukkit.getLogger().info(positionNow.getX() + " " + positionNow.getY() + " " + checkingRoomInstance.getRoomName());
//			printDungeonMap();
//		}
//		return succeedGenPath;
//	}
//	private final Integer mazeAddRoomAmount = 100;
//	public void generateMaze(){
//		positionNow = new Vector2(0, 0);
//		Map<Vector2, RoomInstance> mazeRoomInstances = new HashMap<>();
//		AtomicInteger addedRoomAmount = new AtomicInteger();
//		AtomicInteger totalAddedRoomAmount = new AtomicInteger();
//		addedRoomAmount.set(1);
//		AtomicInteger ranTimes = new AtomicInteger();
//		ranTimes.set(100);
//		while(addedRoomAmount.get() > 0 && ranTimes.get() > 0){
//			addedRoomAmount.set(0);
//			ranTimes.set(ranTimes.get() - 1);
//			roomInstances.forEach((k, v) ->{
//				if(checkMazeRoomCanGenerate(k, v)){
//					ArrayList<Room> newRoomList = new ArrayList<>();
//					RoomInstance checkingRoomInstance = null;
//					int maxDoorAmount = Math.round(4 - ((float) totalAddedRoomAmount.get() / (float) mazeAddRoomAmount) * 4);
//					rooms.forEach(v2 -> {
//						if(v2.getDoorAmount() >= 1 && v2.getDoorAmount() <= maxDoorAmount){
//							newRoomList.add(v2);
//						}
//					});
//					Collections.shuffle(newRoomList);
//					Boolean canGenerate = null;
//					for(int j = 0; j < rooms.size(); j++){
//						if(checkingDirection.equals(roomDoorDirections.NORTH)){
//							if(newRoomList.get(j).getDoorSouth()){
//								checkingRoomInstance = new RoomInstance(newRoomList.get(j));
//								canGenerate = true;
//								break;
//							}
//						}else if(checkingDirection.equals(roomDoorDirections.SOUTH)){
//							if(newRoomList.get(j).getDoorNorth()){
//								checkingRoomInstance = new RoomInstance(newRoomList.get(j));
//								canGenerate = true;
//								break;
//							}
//						}else if(checkingDirection.equals(roomDoorDirections.EAST)){
//							if(newRoomList.get(j).getDoorWest()){
//								checkingRoomInstance = new RoomInstance(newRoomList.get(j));
//								canGenerate = true;
//								break;
//							}
//						}else if(checkingDirection.equals(roomDoorDirections.WEST)){
//							if(newRoomList.get(j).getDoorEast()){
//								checkingRoomInstance = new RoomInstance(newRoomList.get(j));
//								canGenerate = true;
//								break;
//							}
//						}
//					}
//					if(canGenerate){
//						Vector2 addRoomPosition;
//						switch(checkingDirection){
//							case EAST:
//								addRoomPosition = new Vector2(k.getX() + 1, k.getY());
//								mazeRoomInstances.put(addRoomPosition, checkingRoomInstance);
//								Bukkit.getLogger().info("east put " + addRoomPosition.getX() + " " + addRoomPosition.getY());
//								break;
//							case WEST:
//								addRoomPosition = new Vector2(k.getX() - 1, k.getY());
//								mazeRoomInstances.put(addRoomPosition, checkingRoomInstance);
//								Bukkit.getLogger().info("west put " + addRoomPosition.getX() + " " + addRoomPosition.getY());
//								break;
//							case SOUTH:
//								addRoomPosition = new Vector2(k.getX(), k.getY() - 1);
//								mazeRoomInstances.put(addRoomPosition, checkingRoomInstance);
//								Bukkit.getLogger().info("south put " + addRoomPosition.getX() + " " + addRoomPosition.getY());
//								break;
//							case NORTH:
//								addRoomPosition = new Vector2(k.getX(), k.getY() + 1);
//								mazeRoomInstances.put(addRoomPosition, checkingRoomInstance);
//								Bukkit.getLogger().info("north put " + addRoomPosition.getX() + " " + addRoomPosition.getY());
//								break;
//						}
//						Bukkit.getLogger().info(k.getX() + " " + k.getY() + " " + checkingRoomInstance.getRoomName() + " " + addedRoomAmount.get());
//						addedRoomAmount.getAndIncrement();
//						totalAddedRoomAmount.getAndIncrement();
//					}else{
//						Bukkit.getLogger().info("canGenerate == null");
//					}
//				}
//			});
//			roomInstances.putAll(mazeRoomInstances);
//			printDungeonMap();
//		}
//	}
//
//	private void printDungeonMap(){
//		StringBuilder outOneLine = new StringBuilder();
//		outOneLine.append("  ");
//		for(int y = -20; y < 21; y++){
//			outOneLine.append(String.format("%2d", abs(y)));
//		}
//		Bukkit.getLogger().info(outOneLine.toString());
//		for(int x = -20; x < 21; x++){
//			outOneLine = new StringBuilder();
//			outOneLine.append(String.format("%2d", abs(x)));
//			for(int y = -20; y < 21; y++){
//				if(roomInstances.get(new Vector2(x, y)) == null){
//					outOneLine.append(String.format("%2s", " "));
//				}else{
//					outOneLine.append(String.format("%2s", getRoomShowSymbol(roomInstances.get(new Vector2(x, y)))));
//				}
//			}
//			Bukkit.getConsoleSender().sendMessage(outOneLine.toString());
////			Bukkit.getLogger().info(outOneLine.toString());
//		}
//	}
//
//	public roomDoorDirections checkingDirection;
//	public Boolean checkMazeRoomCanGenerate(Vector2 positionNow, RoomInstance roomInstance){
//		Set<roomDoorDirections> checkedDirection = new HashSet<>();
//		while(true){
//			if(checkedDirection.size() >= roomInstance.getDoorAmount()){
//				return false;
//			}
//			checkingDirection = randomDirection(roomInstance, checkedDirection);
//			if(checkCanGenerate(positionNow, roomInstances, checkingDirection)){
//				return true;
//			}
//			checkedDirection.add(checkingDirection);
//		}
//	}
//
//	public roomDoorDirections randomDirection(RoomInstance roomInstance, Set<roomDoorDirections> ignoreDirection){
//		Collection<roomDoorDirections> doorDirections = new ArrayList<>();
//		if(roomInstance.getDoorEast() && !ignoreDirection.contains(roomDoorDirections.EAST)){
//			doorDirections.add(roomDoorDirections.EAST);
//		}
//		if(roomInstance.getDoorWest() && !ignoreDirection.contains(roomDoorDirections.WEST)){
//			doorDirections.add(roomDoorDirections.WEST);
//		}
//		if(roomInstance.getDoorSouth() && !ignoreDirection.contains(roomDoorDirections.SOUTH)){
//			doorDirections.add(roomDoorDirections.SOUTH);
//		}
//		if(roomInstance.getDoorNorth() && !ignoreDirection.contains(roomDoorDirections.NORTH)){
//			doorDirections.add(roomDoorDirections.NORTH);
//		}
//		Optional<roomDoorDirections> result = getRandomElement(doorDirections);
//		return result.orElse(null);
//	}
//
//	public Boolean checkCanGenerate(Vector2 positionNow, Map<Vector2, RoomInstance> roomInstances, @NotNull roomDoorDirections direction){
//		switch(direction){
//			case EAST:
//				return roomInstances.get(new Vector2(positionNow.getX() + 1, positionNow.getY())) == null;
//			case WEST:
//				return roomInstances.get(new Vector2(positionNow.getX() - 1, positionNow.getY())) == null;
//			case SOUTH:
//				return roomInstances.get(new Vector2(positionNow.getX(), positionNow.getY() - 1)) == null;
//			case NORTH:
//				return roomInstances.get(new Vector2(positionNow.getX(), positionNow.getY() + 1)) == null;
//			default:
//				return null;
//		}
//	}
//
//	public <E> Optional<E> getRandomElement(Collection<E> e){
////        Bukkit.getLogger().info(String.valueOf(e.size()));
//		if(e.isEmpty()){
//			return Optional.empty();
//		}
//		return e.stream()
//				.skip(random.nextInt(e.size()))
//				.findFirst();
//	}
//
//	public String getRoomShowSymbol(RoomInstance roomInstance){
//		if(roomInstance.getDoorAmount() == 1){
//			if(roomInstance.getDoorNorth()){
//				return " §a╺§r";
//			}else if(roomInstance.getDoorEast()){
//				return " §a╻§r";
//			}else if(roomInstance.getDoorSouth()){
//				return " §a╸§r";
//			}else if(roomInstance.getDoorWest()){
//				return " §a╹§r";
//			}
//		}
//		if(roomInstance.getDoorAmount() == 2){
//			if(roomInstance.getDoorNorth() && roomInstance.getDoorEast()){
//				return "┏";
//			}else if(roomInstance.getDoorNorth() && roomInstance.getDoorSouth()){
//				return "━";
//			}else if(roomInstance.getDoorNorth() && roomInstance.getDoorWest()){
//				return "┗";
//			}else if(roomInstance.getDoorEast() && roomInstance.getDoorSouth()){
//				return "┓";
//			}else if(roomInstance.getDoorEast() && roomInstance.getDoorWest()){
//				return "┃";
//			}else if(roomInstance.getDoorSouth() && roomInstance.getDoorWest()){
//				return "┛";
//			}
//		}else if(roomInstance.getDoorAmount() == 3){
//			if(!roomInstance.getDoorNorth()){
//				return "┫";
//			}else if(!roomInstance.getDoorEast()){
//				return "┻";
//			}else if(!roomInstance.getDoorSouth()){
//				return "┣";
//			}else if(!roomInstance.getDoorWest()){
//				return "┳";
//			}
//		}else if(roomInstance.getDoorAmount() == 4){
//			return "╋";
//		}
//		return null;
//	}
//}