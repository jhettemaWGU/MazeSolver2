import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JPanel{
    private JFrame window;
    private MazePanel mazePanel;


    public MenuScreen(JFrame window) {
        this.window = window;
        this.setLayout(new BorderLayout());

        mazePanel = new MazePanel();

        JScrollPane mazeScrollPane = new JScrollPane(mazePanel);
        this.add(mazeScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 100));

        JButton loadMazeButton = new JButton("Load a new maze");
        loadMazeButton.addActionListener(e -> mazePanel.loadNewMaze());
        buttonPanel.add(loadMazeButton);

        JButton resetMazeButton = new JButton("Reset the maze");
        resetMazeButton.addActionListener(e -> mazePanel.resetMaze());
        buttonPanel.add(resetMazeButton);

        JButton runPathFinderButton = new JButton("Run PathFinder (Brute Force)");
        runPathFinderButton.addActionListener(e -> runPathFinder());
        buttonPanel.add(runPathFinderButton);

        JButton runAStarButton = new JButton("Run PathFinder (AStar)");
        runAStarButton.addActionListener(e -> runAStar());
        buttonPanel.add(runAStarButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        window.add(this);
        window.pack();
        window.setSize(new Dimension(mazePanel.getMazeWidth() + 40, mazePanel.getMazeHeight() + buttonPanel.getPreferredSize().height + 80));
    }

    private void runPathFinder() {
        int[][] mazeArray = mazePanel.getMaze();
        MazePath pathFinder = new MazePath(mazeArray);
        Position start = pathFinder.getStartPosition(mazeArray);
        pathFinder.setStartPosition(start);
        pathFinder.findPath();
        mazePanel.repaint();
    }

    private void runAStar() {
        int[][] mazeArray = mazePanel.getMaze();
        AStar aStarPath = new AStar(mazeArray, mazePanel);
        aStarPath.findPathAStar();
    }
}
