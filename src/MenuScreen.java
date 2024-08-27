import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JPanel{
    private JFrame window;
    private MazePanel mazePanel;

    public MenuScreen(JFrame window) {
        System.out.println("MenuScreen Constructor");
        this.window = window;
        this.setLayout(new BorderLayout());

        mazePanel = new MazePanel();

        JScrollPane mazeScrollPane = new JScrollPane(mazePanel);
        this.add(mazeScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));

        JButton loadMazeButton = new JButton("Load a new maze");
        loadMazeButton.addActionListener(e -> mazePanel.loadNewMaze());
        buttonPanel.add(loadMazeButton);

        JButton runPathFinderButton = new JButton("Run PathFinder (Brute Force)");
        runPathFinderButton.addActionListener(e -> runPathFinder());
        buttonPanel.add(runPathFinderButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
        System.out.println("MenuScreen Constructor Complete");

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
}
