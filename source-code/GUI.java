import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {

    private int selectedX = -1;
    private int selectedY = -1;

    public GUI() {

        setOpaque(true);
        setBackground(new Color(40, 40, 40));

    }

    @Override
    public void paintComponent(Graphics gg) {

        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int[][] board = ChessAlgorithm.getBoard();

        for (int rowCount = 0; rowCount < board.length; rowCount++) {
            for (int colCount = 0; colCount < board[0].length; colCount++) {

                g.setColor(Color.black);
                g.setStroke(new BasicStroke(0));
                g.drawRect(rowCount * 100 + 100, colCount * 100 + 100, 100, 100);

                if (rowCount == selectedX && colCount == selectedY) {
                    g.setColor(new Color(210, 210, 100));
                } else {
                    if ((rowCount + colCount) % 2 == 0) {
                        g.setColor(new Color(200, 230, 210));
                    } else {
                        g.setColor(new Color(130, 180, 105));
                    }
                }

                g.fillRect(rowCount * 100 + 100, colCount * 100 + 100, 100, 100);

                boolean black = board[rowCount][colCount] > 6;

                g.setFont(new Font(g.getFont().toString(), Font.PLAIN, 80));
                g.setColor(Color.black);

                if (board[rowCount][colCount] == 1 || board[rowCount][colCount] == 7) {
                    g.drawString((black) ? "\u265F" : "\u2659", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount] == 2 || board[rowCount][colCount] == 8) {
                    g.drawString((black) ? "\u265E" : "\u2658", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount] == 3 || board[rowCount][colCount] == 9) {
                    g.drawString((black) ? "\u265D" : "\u2657", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount] == 4 || board[rowCount][colCount] == 10) {
                    g.drawString((black) ? "\u265C" : "\u2656", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount] == 5 || board[rowCount][colCount] == 11) {
                    g.drawString((black) ? "\u265B" : "\u2655", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount] == 6 || board[rowCount][colCount] == 12) {
                    g.drawString((black) ? "\u265A" : "\u2654", rowCount * 100 + 110, colCount * 100 + 180);
                }

            }
        }

        repaint();

    }

    public void setSelectedX(int selectedX) {this.selectedX = selectedX;}
    public void setSelectedY(int selectedY) {this.selectedY = selectedY;}

}
