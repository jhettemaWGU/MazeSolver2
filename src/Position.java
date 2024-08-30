import java.util.Objects;

public class Position {
    public int x;
    public int y;
    public int gCost;
    public int hCost;
    public Position parent;

    public Position(int y, int x) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())  return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getGCost() {
        return gCost;
    }

    public int calculateGCost(Position start, Position end) {
        int distance = Math.abs(this.x - start.x) + Math.abs(this.y - start.y);
        return distance;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public int getFCost() {
        return gCost + hCost;
    }

    public void print() {System.out.println("Position: (" + this.x + ", " + this.y + ")");}
}
