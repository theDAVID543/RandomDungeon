package the.david.randomdungeon.dungeon.holder;

import java.util.Objects;

public class Vector3{
	public final int x, y, z;
	private final int hashCode;

	public Vector3(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.hashCode = Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(o == null || getClass() != o.getClass()){
			return false;
		}
		Vector3 that = (Vector3) o;
		return x == that.x && y == that.y && z == that.z;
	}

	@Override
	public int hashCode(){
		return this.hashCode;
	}
}
