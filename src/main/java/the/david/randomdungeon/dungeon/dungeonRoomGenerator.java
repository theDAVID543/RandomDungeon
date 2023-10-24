package the.david.randomdungeon.dungeon;

import the.david.randomdungeon.dungeon.holder.Edge;
import the.david.randomdungeon.dungeon.holder.Graph;
import the.david.randomdungeon.dungeon.holder.GridNode;
import the.david.randomdungeon.dungeon.holder.RoomInstance;

import java.util.*;

public class dungeonRoomGenerator {
	private static final int MAX_ROOM_SIZE = 5;
	private static final int MAX_ATTEMPTS = 10;
	public static int QUERY_RANGE = 50;
	private static final int MAX_ROOMS = 50;

	private final List<RoomInstance> roomInstances = new ArrayList<>();

	private boolean roomCollides(RoomInstance RoomInstance) {
		for (RoomInstance existingRoomInstance : roomInstances) {
			if (RoomInstance.collidesWith(existingRoomInstance)) {
				return true;
			}
		}
		return false;
	}

	public void generateRooms() {
		Random rand = new Random();

		// Start with a smaller initial room centered.
		RoomInstance initialRoomInstance = new RoomInstance(-2, -2, 4, 4);
		roomInstances.add(initialRoomInstance);

		List<RoomInstance> activeRoomInstances = new ArrayList<>();
		activeRoomInstances.add(initialRoomInstance);

		while (!activeRoomInstances.isEmpty() && roomInstances.size() < MAX_ROOMS) {
			RoomInstance current = activeRoomInstances.get(rand.nextInt(activeRoomInstances.size()));

			boolean roomAdded = false;
			for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
				int newWidth = rand.nextInt(MAX_ROOM_SIZE) + 1;
				int newHeight = rand.nextInt(MAX_ROOM_SIZE) + 1;

				// Determine side
				int side = rand.nextInt(4);
				int newX = side == 0 ? current.x + rand.nextInt(current.width) :
						side == 1 ? current.x + current.width + 1 :
								side == 2 ? current.x + rand.nextInt(current.width) - newWidth :
										current.x - newWidth - 1;

				int newY = side == 0 ? current.y - newHeight - 1 :
						side == 1 ? current.y + rand.nextInt(current.height) :
								side == 2 ? current.y + current.height + 1 :
										current.y + rand.nextInt(current.height) - newHeight;

				RoomInstance newRoomInstance = new RoomInstance(newX, newY, newWidth, newHeight);
				if (!roomCollides(newRoomInstance)) {
					roomInstances.add(newRoomInstance);
					activeRoomInstances.add(newRoomInstance);
					roomAdded = true;
					break;
				}
			}

			if (!roomAdded) {
				activeRoomInstances.remove(current);
			}
		}
	}
	private void getMinQueryRange(){
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		for(RoomInstance RoomInstance : roomInstances){
			if(RoomInstance.x < minX){
				minX = RoomInstance.x;
			}
			if(RoomInstance.y < minY){
				minY = RoomInstance.y;
			}
			if(RoomInstance.x + RoomInstance.width > maxX){
				maxX = RoomInstance.x + RoomInstance.width;
			}
			if(RoomInstance.y + RoomInstance.height > maxY){
				maxY = RoomInstance.y + RoomInstance.height;
			}
		}
		System.out.println("MinX: " + minX + " MinY: " + minY + " MaxX: " + maxX + " MaxY: " + maxY);
		QUERY_RANGE = Math.max(Math.max(Math.abs(minX), Math.abs(minY)), Math.max(Math.abs(maxX), Math.abs(maxY)));
	}

	public void displayGrid() {
		for (int i = -QUERY_RANGE; i < QUERY_RANGE; i++) {
			for (int j = -QUERY_RANGE; j < QUERY_RANGE; j++) {
				boolean occupied = false;
				for (RoomInstance RoomInstance : roomInstances) {
					if (j >= RoomInstance.x && j < RoomInstance.x + RoomInstance.width && i >= RoomInstance.y && i < RoomInstance.y + RoomInstance.height) {
						occupied = true;
						break;
					}
				}
				System.out.print(occupied ? " # " : " . ");
			}
			System.out.println();
		}
	}
	public static Set<GridNode> path = new HashSet<>();

	public static void main(String[] args) {
		for(int e = 0; e < 100; e++){
			dungeonRoomGenerator generator = new dungeonRoomGenerator();
			generator.generateRooms();
			generator.displayGrid();
			path = new HashSet<>();
			Graph graph = new Graph(generator.roomInstances);
			List<Edge> mstEdges = graph.primsMST();
			mstEdges.forEach(v -> {
				System.out.println("Edge from: (" + v.source.x + ", " + v.source.y + ") to (" + v.destination.x + ", " + v.destination.y + ")");
				AStar aStar = new AStar(generator.roomInstances, new GridNode(v.source.x-1, v.source.y), new GridNode(v.destination.x-1, v.destination.y));
				Set<GridNode> temp = aStar.findPath();
				if(temp != null){
					path.addAll(aStar.findPath());
				}else {
					System.out.println("No path found");
				}
			});
			Map<GridNode, String> finalResult = new HashMap<>();

//			for (int i = -QUERY_RANGE; i < QUERY_RANGE; i++) {
//				for (int j = -QUERY_RANGE; j < QUERY_RANGE; j++) {
//					boolean occupied = false;
//					for (Room room : generator.rooms) {
//						if (j >= room.x && j < room.x + room.width && i >= room.y && i < room.y + room.height) {
//							occupied = true;
//							break;
//						}
//					}
//					finalResult.put(new GridNode(j, i), occupied ? "███" : "   ");
//				}
//			}
			generator.roomInstances.forEach(room -> {
				for(int y = room.y; y < room.y + room.height; y++) {
					for (int x = room.x; x < room.x + room.width; x++) {
						finalResult.put(new GridNode(x,y), "███");
					}
				}
			});
			path.forEach(v -> {
				finalResult.put(v, "━╋━");
			});
//			for(int y = -QUERY_RANGE; y < QUERY_RANGE; y++){
//				for(int x = -QUERY_RANGE; x < QUERY_RANGE; x++){
//					if(path.contains(new GridNode(x, y))){
//						finalResult.put(new GridNode(x, y), "━╋━");
//					}
//				}
//			}
			for(int y = -QUERY_RANGE; y < QUERY_RANGE; y++){
				for(int x = -QUERY_RANGE; x < QUERY_RANGE; x++){
					if(finalResult.get(new GridNode(x, y)) == null){
						System.out.print("   ");
					}else {
						System.out.print(finalResult.get(new GridNode(x, y)));
					}
				}
				System.out.println();
			}
		}
	}
}

