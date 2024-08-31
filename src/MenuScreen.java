import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuScreen extends JPanel{
    private JFrame window;
    private MazePanel mazePanel;


    public MenuScreen(JFrame window) {
        this.window = window;
        this.setLayout(new BorderLayout());

        mazePanel = new MazePanel();

        JScrollPane mazeScrollPane = new JScrollPane(mazePanel);
        mazeScrollPane.getViewport().setBackground(Color.BLACK);
        mazeScrollPane.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mazePanel.revalidate();
            }
        });
        mazeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mazeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(mazeScrollPane, BorderLayout.CENTER);

        JPanel warningPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        warningPanel.setBackground(Color.BLACK);
        JLabel warningLabel = new JLabel("Warning: Large mazes may take some time to generate!");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        warningLabel.setForeground(Color.RED);
        warningPanel.add(warningLabel);

        mazeScrollPane.setColumnHeaderView(warningPanel);
        //JPanel mazeSizePanel = new JPanel(new BorderLayout());
        //JLabel mazeSizeLabel = new JLabel("Maze Size: ");
        //mazeSizePanel.add(mazeSizeLabel, BorderLayout.WEST);

        JPanel sliderPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JSlider mazeSizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 25);
        mazeSizeSlider.setMajorTickSpacing(10);
        mazeSizeSlider.setPaintTicks(true);
        mazeSizeSlider.setPaintLabels(true);
        sliderPanel.add(new JLabel("Maze Size: "));
        sliderPanel.add(mazeSizeSlider);
        //sliderPanel.add(mazeSizePanel);

        JSlider scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 3, 2);
        scaleSlider.setMajorTickSpacing(1);
        scaleSlider.setPaintTicks(true);
        scaleSlider.setPaintLabels(true);
        sliderPanel.add(new JLabel("Scale: "));
        sliderPanel.add(scaleSlider);

        mazeSizeSlider.addChangeListener(e -> {
            if (!mazeSizeSlider.getValueIsAdjusting()) {
                int newMaxCol = mazeSizeSlider.getValue();
                mazePanel.setMaxScreenCol(newMaxCol);
                window.revalidate();
                window.repaint();
            }

        });

        scaleSlider.addChangeListener(e -> {
            int newScale = scaleSlider.getValue();
            mazePanel.setScale(newScale);
            //window.pack();
        });

        JPanel mazeButtonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton loadMazeButton = new JButton("Load a New Maze");
        loadMazeButton.addActionListener(e -> mazePanel.loadNewMaze());
        mazeButtonPanel.add(loadMazeButton);

        JButton resetMazeButton = new JButton("Reset the maze");
        resetMazeButton.addActionListener(e -> mazePanel.resetMaze());
        mazeButtonPanel.add(resetMazeButton);

        JPanel pathFinderPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton runPathFinderButton = new JButton("Run Pathfinder (Brute Force)");
        runPathFinderButton.addActionListener(e -> runPathFinder());
        pathFinderPanel.add(runPathFinderButton);

        JButton runAStarButton = new JButton("Run PathFinder (A*)");
        runAStarButton.addActionListener(e -> runAStar());
        pathFinderPanel.add(runAStarButton);

        buttonPanel.add(sliderPanel);
        buttonPanel.add(mazeButtonPanel);
        buttonPanel.add(pathFinderPanel);
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
