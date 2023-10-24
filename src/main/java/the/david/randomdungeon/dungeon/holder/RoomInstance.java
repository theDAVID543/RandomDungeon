package the.david.randomdungeon.dungeon.holder;


public class RoomInstance{
	public RoomInstance(Room room, int x, int y, int width, int height){
		this.room = room;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public int x, y, width, height;

	final Room room;

	public Boolean getDoorEast(){
		return room.getDoorEast();
	}

	public Boolean getDoorWest(){
		return room.getDoorWest();
	}

	public Boolean getDoorNorth(){
		return room.getDoorNorth();
	}

	public Boolean getDoorSouth(){
		return room.getDoorSouth();
	}

	public Integer getDoorAmount(){
		return room.getDoorAmount();
	}

	public String getRoomName(){
		return room.getRoomName();
	}

	public Boolean getCanRotate(){
		return room.getCanRotate();
	}
	public boolean collidesWith(RoomInstance other) {
		return !(x + width + 1 <= other.x || x >= other.x + other.width + 1 ||
				y + height + 1 <= other.y || y >= other.y + other.height + 1);
	}

	public GridNode getCenter() {
		return new GridNode(x + width / 2, y + height / 2);
	}
}
