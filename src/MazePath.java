import java.util.LinkedList;

public class MazePath {
    public int[][] mazeArray;
    public LinkedList<Position> path;
    public Position start;


    public MazePath(int[][] mazeArray) {
        this.mazeArray = mazeArray;
    }

    public boolean isInBounds(int y, int x) {
        return y >= 0 && y < mazeArray.length && x >= 0 && x < mazeArray[0].length;
    }

    public Position getStartPosition(int[][] maze) {
        for (int i = 0; i < mazeArray.length; i++) {
            if (maze[i][0] == 3) {
                return new Position(i, 0);
            }
        }
        System.out.println("You attempted to generate a maze without a start position.");
        return null;
    }

    public void setStartPosition(Position position) {
        this.start = position;
    }

    private boolean moveTo(int y, int x) {
        if (isInBounds(y, x)) {
            Position curr = new Position(y, x);
            if (mazeArray[y][x] == 2) {
                path.push(curr);
                return true;
            } else if (mazeArray[y][x] == 1) {
                path.push(curr);
                return true;
            }
        }
        return false;
    }

    public boolean findPath() {
        path = new LinkedList<Position>();
        if (start == null) {
            return false;
        }
        path.push(start);

        while (!path.isEmpty()) {
            Position curr = path.peek();
            int y = curr.y;
            int x = curr.x;
            if (curr != start) {
                mazeArray[y][x] = 4;
            }

            boolean moved = false;

            if (moveTo(y - 1, x)) {
                moved = true;
            }
            if (moveTo(y + 1, x)) {
                moved = true;
            }
            if (moveTo(y, x - 1)) {
                moved = true;
            }
            if (moveTo(y, x + 1)) {
                moved = true;
            }
            if (!moved) {
                path.pop();
            }

            if (!path.isEmpty() && mazeArray[path.peek().y][path.peek().x] == 2) {
                return true;
            }
            for (Position pos : path) {
                System.out.print("path: " + pos.y + ", " + pos.x + "  ");
            }
            System.out.println();
        }
        System.out.println("No path found.");
        return false;
    }

    public void reverseList(LinkedList<Position> path) {
        int i = path.size();
        int pos = 0;
        while(i-- > 1) {
            Position n = path.removeLast();
            path.add(pos++, n);
        }
    }
}
