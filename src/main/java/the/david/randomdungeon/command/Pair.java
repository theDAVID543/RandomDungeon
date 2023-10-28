package the.david.randomdungeon.command;

public class Pair<T, U>{
	Pair(T Key, U Value){
		this.Key = Key;
		this.Value = Value;
	}
	public T Key;
	public U Value;
	public T getKey(){
		return Key;
	}
	public U getValue(){
		return Value;
	}
}
