import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            window.setResizable(false);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setTitle("Maze Solver");

            StartScreen startScreen = new StartScreen(window);
            window.add(startScreen);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
