package the.david.randomdungeon.dungeon.holder;

import java.util.Objects;

public class Vector2 {
    private final int x;
    private final int y;
    private int hashCode;
    public Vector2(int x, int y){
        this.x= x;
        this.y = y;
        this.hashCode = Objects.hash(x,y);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Vector2 that = (Vector2) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
