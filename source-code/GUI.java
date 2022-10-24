import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {

    public GUI() {

        setOpaque(true);
        setBackground(new Color(40, 40, 40));

    }

    @Override
    public void paintComponent(Graphics gg) {

        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Square[][] board = ChessAlgorithm.getBoard();

        for (int rowCount = 0; rowCount < board.length; rowCount++) {
            for (int colCount = 0; colCount < board[0].length; colCount++) {

                g.setColor(Color.black);
                g.setStroke(new BasicStroke(0));
                g.drawRect(rowCount * 100 + 100, colCount * 100 + 100, 100, 100);

                if (board[rowCount][colCount].getSelected()) {
                    g.setColor(new Color(210, 210, 100));
                } else {
                    if ((rowCount + colCount) % 2 == 0) {
                        g.setColor(new Color(200, 230, 210));
                    } else {
                        g.setColor(new Color(130, 180, 105));
                    }
                }

                g.fillRect(rowCount * 100 + 100, colCount * 100 + 100, 100, 100);

                boolean black = board[rowCount][colCount].getPiece() > 6;

                g.setFont(new Font(g.getFont().toString(), Font.PLAIN, 80));
                g.setColor(Color.black);

                if (board[rowCount][colCount].getPiece() == 1 || board[rowCount][colCount].getPiece() == 7) {
                    g.drawString((black) ? "\u265F" : "\u2659", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 2 || board[rowCount][colCount].getPiece() == 8) {
                    g.drawString((black) ? "\u265E" : "\u2658", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 3 || board[rowCount][colCount].getPiece() == 9) {
                    g.drawString((black) ? "\u265D" : "\u2657", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 4 || board[rowCount][colCount].getPiece() == 10) {
                    g.drawString((black) ? "\u265C" : "\u2656", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 5 || board[rowCount][colCount].getPiece() == 11) {
                    g.drawString((black) ? "\u265B" : "\u2655", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 6 || board[rowCount][colCount].getPiece() == 12) {
                    g.drawString((black) ? "\u265A" : "\u2654", rowCount * 100 + 110, colCount * 100 + 180);
                }

                if (board[rowCount][colCount].getHighlighted()) {
                    g.setColor(Color.white);
                    g.setFont(new Font(g.getFont().toString(), Font.PLAIN, 40));
                    g.drawString("\u2726", rowCount * 100 + 133, colCount * 100 + 163);
                }

            }
        }

        repaint();

    }

}
