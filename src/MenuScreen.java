import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuScreen extends JPanel{
    private JFrame window;
    private MazePanel mazePanel;
    private JPanel loadingPanel;


    public MenuScreen(JFrame window) {
        this.window = window;
        this.setLayout(new BorderLayout());

        createLoadingPanel();

        mazePanel = new MazePanel(loadingPanel);

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

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        mazeScrollPane.setBounds(0, 0, window.getWidth(), window.getHeight());
        layeredPane.add(mazeScrollPane, JLayeredPane.DEFAULT_LAYER);

        loadingPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        layeredPane.add(loadingPanel, JLayeredPane.PALETTE_LAYER);
        this.add(layeredPane, BorderLayout.CENTER);

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
        JSlider mazeSizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 16);
        mazeSizeSlider.setMajorTickSpacing(10);
        mazeSizeSlider.setPaintTicks(true);
        mazeSizeSlider.setPaintLabels(true);
        sliderPanel.add(new JLabel("Maze Size: "));
        sliderPanel.add(mazeSizeSlider);
        //sliderPanel.add(mazeSizePanel);

        JSlider scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 3, 3);
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
        String[] pathFinderOptions = {"Path Finder (Brute Force)", "Path Finder A*", "Path Finder DFS - Pre-Order"};
        JComboBox<String> pathFinderDropDown = new JComboBox<>(pathFinderOptions);
        pathFinderPanel.add(pathFinderDropDown);

        JButton runPathFinderButton = new JButton("Run Path Function");
        runPathFinderButton.addActionListener(e -> {
            String selectedOption = (String) pathFinderDropDown.getSelectedItem();
            if ("Path Finder (Brute Force)".equals(selectedOption)) {
                runPathFinder();
            } else if ("Path Finder A*".equals(selectedOption)) {
                runAStar();
            } else if ("Path Finder DFS - Pre-Order".equals(selectedOption)) {
                runDepthFirst();
            }

        });
        pathFinderPanel.add(runPathFinderButton);

        //JButton runAStarButton = new JButton("Run PathFinder (A*)");
        //runAStarButton.addActionListener(e -> runAStar());
        //pathFinderPanel.add(runAStarButton);

        buttonPanel.add(sliderPanel);
        buttonPanel.add(mazeButtonPanel);
        buttonPanel.add(pathFinderPanel);
        this.add(buttonPanel, BorderLayout.SOUTH);

        window.add(this);
        window.pack();
        window.setSize(new Dimension(mazePanel.getMazeWidth() + 20, mazePanel.getMazeHeight() + buttonPanel.getPreferredSize().height + 80)); // removed +40 width
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

    private void runDepthFirst() {
        int[][] mazeArray = mazePanel.getMaze();
        DFSTraversal traversal = new DFSTraversal(mazeArray);
        traversal.dfsPreOrder();
    }

    private void createLoadingPanel() {
        loadingPanel = new JPanel();
        loadingPanel.setLayout(new GridBagLayout());
        loadingPanel.setBackground(Color.BLACK);

        JLabel loadingLabel = new JLabel("Generating...");
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 24));

        loadingPanel.add(loadingLabel);
        loadingPanel.setVisible(false);
        this.add(loadingPanel, BorderLayout.CENTER);
    }
}
