import java.util.LinkedList;
import java.util.Stack;

public class DFSTraversal extends MazePath {

    private static final int[] rowDirection = {-1, 1, 0, 0};
    private static final int[] colDirection = {0, 0, -1, 1};
    private Position start;
    private Position end;
    private LinkedList<Position> path;

    public DFSTraversal (int[][] mazeArray) {
        super(mazeArray);
        start = this.getStartPosition(mazeArray);
        end = this.getEndPosition(mazeArray);
    }

    public boolean dfsPreOrder() {
        Stack<Position> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Position current = stack.pop();

            if (mazeArray[current.y][current.x] == 2) {
                return true;
            }

            mazeArray[current.y][current.x] = 4;

            for (int i = 0; i < 4; i++) {
                int newRow = current.y + rowDirection[i];
                int newCol = current.x + colDirection[i];

                if (isValidMove(mazeArray, newRow, newCol)) {
                    Position nextPosition = new Position(newRow, newCol);
                    nextPosition.parent = current;
                    stack.push(nextPosition);
                }
            }
        }

        return false;
    }

    private boolean isValidMove(int[][] mazeArray, int row, int col) {
        int numRows = mazeArray.length;
        int numCols = mazeArray[0].length;
        return row >= 0 && row < numRows && col >= 0 && col < numCols && (mazeArray[row][col] == 1 || mazeArray[row][col] == 2);
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
}
