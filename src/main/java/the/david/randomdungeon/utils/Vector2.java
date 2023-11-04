package the.david.randomdungeon.utils;

import java.util.Objects;

public class Vector2{
	public final int x, z;
	private final int hashCode;

	public Vector2(int x, int z){
		this.x = x;
		this.z = z;
		this.hashCode = Objects.hash(x, z);
	}

	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(o == null || getClass() != o.getClass()){
			return false;
		}
		Vector2 that = (Vector2) o;
		return x == that.x && z == that.z;
	}

	@Override
	public int hashCode(){
		return this.hashCode;
	}
}
