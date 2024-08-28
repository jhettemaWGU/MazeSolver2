import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {

    private MazeGenerator generator;
    private int[][] mazeArray;

    final int originalTileSize = 16;
    final int scale = 3;
    private int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = maxScreenCol * tileSize;
    final int screenHeight = maxScreenRow * tileSize;

    public MazePanel() {
        System.out.println("MazePanel Constructor Start");
        this.tileSize = tileSize;
        generator = new MazeGenerator(maxScreenRow, maxScreenCol);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.mazeArray = generator.generateMaze();
    }

    public int getMazeWidth() { return mazeArray[0].length * tileSize; }
    public int getMazeHeight() { return mazeArray.length * tileSize; }

    public int[][] getMaze() { return mazeArray; }

    public void setMaze(int[][] newMazeArray) {
        this.mazeArray = newMazeArray;
        repaint();
    }

    private void drawMaze(Graphics g) {
        for (int row = 0; row < mazeArray.length; row++) {
            for (int col = 0; col < mazeArray[row].length; col++) {
                if (mazeArray[row][col] == 1) {
                    g.setColor(Color.WHITE);
                } else if (mazeArray[row][col] == 3) {
                    g.setColor(Color.GREEN);
                } else if (mazeArray[row][col] == 2) {
                    g.setColor(Color.RED);
                } else if (mazeArray[row][col] == 4) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }

    public void loadNewMaze() {
        mazeArray = generator.generateMaze();
        this.setMaze(mazeArray);
    }

    public void resetMaze() {
        for (int row = 0; row < mazeArray.length; row++) {
            for (int col = 0; col < mazeArray[row].length; col++) {
                if (mazeArray[row][col] == 4) {
                    mazeArray[row][col] = 1;
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze(g);
        //drawPath(g);
    }
}
