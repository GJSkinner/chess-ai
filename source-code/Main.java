import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        GUI gui = new GUI();

        JFrame jFrame = new JFrame();

        MouseListener mouseListener = new MouseListener(gui);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Chess");
        jFrame.setPreferredSize(new Dimension(1000, 1000));
        jFrame.add(gui, BorderLayout.CENTER);
        jFrame.addMouseListener(mouseListener);
        jFrame.pack();
        jFrame.setVisible(true);

    }

}
