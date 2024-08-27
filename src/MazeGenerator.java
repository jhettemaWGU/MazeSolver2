import java.util.Arrays;
import java.util.Random;

public class MazeGenerator {

    private int[][] mazeArray;
    private int rows;
    private int cols;
    private Random rand;

    public MazeGenerator(int rows, int cols) {
        System.out.println("MazeGenerator Constructor start: rows=" + rows + ", cols=" + cols);
        this.rows = rows;
        this.cols = cols;
        this.mazeArray = new int[rows][cols];
        this.rand = new Random();
        System.out.println("MazeGenerator Constructor end");

    }

    public int[][] generateMaze() {
        System.out.println("generateMaze() start");
        do {
            mazeArray = new int[rows][cols];
            System.out.println("New MazeArray. rows: " + rows + ", cols: " + cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    mazeArray[i][j] = rand.nextInt(2);
                }
            }
            for (int i = 0; i < rows; i++) {
                System.out.println(Arrays.toString(mazeArray[i]));
            }

            int startRow = rand.nextInt(rows);
            mazeArray[startRow][0] = 3; // Starting square value
            int endRow = rand.nextInt(rows);
            mazeArray[endRow][cols - 1] = 2; // Ending square value

            for (int i = 0; i < rows; i++) {
                System.out.println(Arrays.toString(mazeArray[i]));
            }

        } while (!isValidMaze());
        for (int i = 0; i < rows; i++) {
            System.out.println(Arrays.toString(mazeArray[i]));
        }
        System.out.println("generateMaze() end");
        return mazeArray;
    }

    private boolean isValidMaze() {
        System.out.println("isValidMaze() start");
        int[][] mazeArrayCopy = copyMazeArray(mazeArray);
        MazePath pathFinder = new MazePath(mazeArrayCopy);
        Position start = pathFinder.getStartPosition(mazeArrayCopy);
        pathFinder.setStartPosition(start);
        System.out.println("start: " + start);
        return pathFinder.findPath();
    }

    private int[][] copyMazeArray(int[][] mazeArray) {
        int[][] mazeArrayCopy = new int[mazeArray.length][mazeArray[0].length];
        for (int i = 0; i < mazeArray.length; i++) {
            System.arraycopy(mazeArray[i], 0, mazeArrayCopy[i], 0, mazeArray[i].length);
        }
        return mazeArrayCopy;
    }

    /*private void loadNewMaze() {
        MazeGenerator generator = new MazeGenerator(max)
        int[][] newMazeArray = .generateMaze();
        mazePanel.setMaze();
    }*/
    public void printArray() {
        for (int i = 0; i < rows; i++) {
            System.out.println(Arrays.toString(mazeArray[i]));
        }
    }
}
