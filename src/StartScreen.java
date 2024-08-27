import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartScreen extends JPanel {
    private JFrame window;

    public StartScreen(JFrame window) {
        this.window = window;
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked on StartScreen");
                showMenuScreen();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Maze Solver", 250, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Click to Start", 300, 400);
    }

    private void showMenuScreen() {
        System.out.println("Running showMenuScreen");
        try {
            window.getContentPane().removeAll();
            window.getContentPane().add(new MenuScreen(window));
            window.revalidate();
            window.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
