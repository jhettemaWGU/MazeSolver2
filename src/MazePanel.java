import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {

    private MazeGenerator generator;
    private int[][] mazeArray;
    private JPanel loadingPanel;

    final int originalTileSize = 16;
    private int scale = 3;
    private int tileSize = originalTileSize * scale;
    private int maxScreenCol = 16;
    private int maxScreenRow = 12;
    private int screenWidth = maxScreenCol * tileSize;
    private int screenHeight = maxScreenRow * tileSize;

    public MazePanel(JPanel loadingPanel) {
        this.loadingPanel = loadingPanel;
        this.tileSize = tileSize;
        generator = new MazeGenerator(maxScreenRow, maxScreenCol);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.mazeArray = generator.generateMaze();
    }

    private void updatePreferredSize() {
        int screenWidth = maxScreenCol * tileSize;
        int screenHeight = maxScreenRow * tileSize;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        revalidate();
    }

    public int getMazeWidth() { return mazeArray[0].length * tileSize; }
    public int getMazeHeight() { return mazeArray.length * tileSize; }

    public int[][] getMaze() { return mazeArray; }

    public void setMaze(int[][] newMazeArray) {
        this.mazeArray = newMazeArray;
        repaint();
    }

    public void setMaxScreenCol(int maxScreenCol) {
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenCol * 3 / 4;
        this.screenWidth = maxScreenCol * tileSize;
        this.screenHeight = maxScreenRow * tileSize;
        generator = new MazeGenerator(maxScreenRow, maxScreenCol);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        loadNewMaze();
    }

    public void setScale (int scale) {
        this.scale = scale;
        this.tileSize = originalTileSize * scale;
        this.screenWidth = maxScreenCol * tileSize;
        this.screenHeight = maxScreenRow * tileSize;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        revalidate();
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
                } else if (mazeArray[row][col] == 5){
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }

    public void loadNewMaze() {
        loadingPanel.setVisible(true);
        this.setVisible(false);
        revalidate();
        repaint();

        SwingWorker<int[][], Void> worker = new SwingWorker<>() {
            @Override
            protected int[][] doInBackground() throws Exception {
                return generator.generateMaze();
            }

            @Override
            protected void done() {
                try {
                    setMaze(get());

                    loadingPanel.setVisible(false);
                    MazePanel.this.setVisible(true);
                    revalidate();
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public void resetMaze() {
        for (int row = 0; row < mazeArray.length; row++) {
            for (int col = 0; col < mazeArray[row].length; col++) {
                if (mazeArray[row][col] == 4 || mazeArray[row][col] == 5) {
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
