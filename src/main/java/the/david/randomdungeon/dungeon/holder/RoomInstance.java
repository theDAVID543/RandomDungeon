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

	public final Room room;

	public boolean collidesWith(RoomInstance other) {
		return !(x + width + 1 <= other.x || x >= other.x + other.width + 1 ||
				y + height + 1 <= other.y || y >= other.y + other.height + 1);
	}

	public GridNode getCenter() {
		return new GridNode(x + width / 2, y + height / 2);
	}
}
