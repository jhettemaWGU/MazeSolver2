import java.awt.*;
import java.util.*;

public class AStar extends MazePath {

    private Position start;
    private Position end;
    private Map<Point, Position> allPositions = new HashMap<>();
    private MazePanel mazePanel;


    public AStar(int[][] mazeArray, MazePanel mazePanel) {
        super(mazeArray);
        this.end = getEndPosition(mazeArray);
        this.mazePanel = mazePanel;
    }

    private Position getPosition(int y, int x) {
        Point point = new Point(x, y);
        if (!allPositions.containsKey(point)) {
            allPositions.put(point, new Position(y, x));
        }
        return allPositions.get(point);
    }

    public void findPathAStar() {
        this.start = this.getStartPosition(mazeArray);
        end = this.getEndPosition(mazeArray);
        ArrayList<Position> open = new ArrayList<>();
        HashSet<Position> closed = new HashSet<>();

        open.add(start);
        System.out.println("Start: " + start.x + ", " + start.y);
        System.out.println("End: " + end.x + ", " + end.y);

        while(!open.isEmpty()) {
            Position current = open.get(0);
            for (int i = 1; i < open.size(); i++) {
                if (open.get(i).getFCost() < current.getFCost() || open.get(i).getFCost() == current.getFCost() && open.get(i).getHCost() < current.getHCost()) {
                    current = open.get(i);
                }
            }

            open.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                System.out.println("End Position reached.");
                Position finalEnd = allPositions.get(new Point(current.x, current.y));
                retracePath(start, finalEnd);
                return;
            }

            for (Position neighbor : getNeighbors(current)) {

                if (closed.contains(neighbor)) {
                    continue;
                }

                int newCostToNeighbor = current.calculateGCost(start, end) + getDistance(current, neighbor);
                if (newCostToNeighbor < neighbor.getGCost() || !open.contains(neighbor)) {
                    neighbor.gCost = newCostToNeighbor;
                    neighbor.hCost = getDistance(neighbor, end);
                    neighbor.parent = current;

                    allPositions.put(new Point(neighbor.x, neighbor.y), neighbor);
                    if (!open.contains(neighbor)) {
                        open.add(neighbor);
                    }
                }
            }
        }
    }

    public Position getStartPosition(int[][] mazeArray) {
        for (int i = 0; i < mazeArray.length; i++) {
            if (mazeArray[i][0] == 3) {
                return new Position(i, 0);
            }
        }
        System.out.println("You attempted to generate a maze without a start position.");
        return null;
    }

    private Position getEndPosition(int[][] mazeArray) {
        for (int i = 0; i < mazeArray.length; i++) {
            if (mazeArray[i][mazeArray[0].length - 1] == 2) {
                return new Position(i, mazeArray[0].length - 1);
            }
        }
        return null;
    }

    public int getDistance(Position a, Position b) {
        int distanceX = Math.abs(a.x - b.x);
        int distanceY = Math.abs(a.y - b.y);

        return distanceX + distanceY;
    }

    public int calculateToEnd(Position current) {
        int distance = Math.abs(current.x - end.x) + Math.abs(current.y - end.y);
        return distance;
    }

    public void retracePath(Position start, Position end) {
        System.out.println("Running retracePath...");
        LinkedList<Position> path = new LinkedList<>();
        Position current = end;

        while (!current.equals(start)) {
            if (current == null) {
                System.out.println("Error: Found a null parent, terminating retrace.");
                break;
            }

            path.add(current);
            if (!current.equals(start) && !current.equals(end)) {
                mazeArray[current.y][current.x] = 4;
            }
            current = current.parent;
        }

        if (current.equals(start)) {
            path.add(start);
        }
        reverseList(path);
        mazePanel.repaint();
    }

    public ArrayList<Position> getNeighbors(Position current) {
        ArrayList<Position> neighbors = new ArrayList<>();

        if (current.x - 1 >= 0 && current.x - 1 < mazeArray[0].length && mazeArray[current.y][current.x - 1] != 0) {
            Position leftNeighbor = new Position(current.y, current.x - 1);
            neighbors.add(leftNeighbor);
            if (!leftNeighbor.equals(end) && !leftNeighbor.equals(start)) {
                mazeArray[current.y][current.x - 1] = 5;
            }
        }
        if (current.x + 1 >= 0 && current.x + 1 < mazeArray[0].length && mazeArray[current.y][current.x + 1] != 0) {
            Position rightNeighbor = new Position(current.y, current.x + 1);
            neighbors.add(rightNeighbor);
            if (!rightNeighbor.equals(end) && !rightNeighbor.equals(start)) {
                mazeArray[current.y][current.x + 1] = 5;
            }
        }
        if (current.y + 1 >= 0 && current.y + 1 < mazeArray.length && mazeArray[current.y + 1][current.x] != 0) {
            Position bottomNeighbor = new Position(current.y + 1, current.x);
            neighbors.add(bottomNeighbor);
            if (!bottomNeighbor.equals(end) && !bottomNeighbor.equals(start)) {
                mazeArray[current.y + 1][current.x] = 5;
            }
        }
        if (current.y - 1 >= 0 && current.y - 1 < mazeArray.length && mazeArray[current.y - 1][current.x] != 0) {
            Position topNeighbor = new Position(current.y - 1, current.x);
            neighbors.add(topNeighbor);
            if (!topNeighbor.equals(end) && !topNeighbor.equals(start)) {
                mazeArray[current.y - 1][current.x] = 5;
            }
        }
        return neighbors;
    }

    public void printAllPositions() {
        for (Map.Entry<Point, Position> entry : allPositions.entrySet()) {
            Point key = entry.getKey();
            Position value = entry.getValue();
            System.out.println("Key (Point): (" + key.x + ", " + key.y + ")");
            System.out.println("Value (Position): (" + value.x + ", " + value.y + "), Parent: " +
                    (value.parent != null ? "(" + value.parent.x + ", " + value.parent.y + ")" : "null"));
            System.out.println("GCost: " + value.gCost + ", HCost: " + value.hCost);
            System.out.println();
        }
    }
}