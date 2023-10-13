package the.david.randomdungeon.dungeon.holder;


public class RoomInstance{
	public RoomInstance(Room room){
		this.room = room;
	}

	private final Room room;

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
}
