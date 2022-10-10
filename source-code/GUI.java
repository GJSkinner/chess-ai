import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {

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
                    g.setColor(new Color(180, 180, 105));
                } else {
                    if ((rowCount + colCount) % 2 == 0) {
                        g.setColor(new Color(130, 180, 105));
                    } else {
                        g.setColor(new Color(65, 75, 55));
                    }
                }

                g.fillRect(rowCount * 100 + 100, colCount * 100 + 100, 100, 100);

                if (board[rowCount][colCount].getHighlighted()) {
                    g.setColor(new Color(187, 201, 179));
                    g.setFont(new Font(g.getFont().toString(), Font.PLAIN, 40));
                    g.drawString("\u2726", rowCount * 100 + 133, colCount * 100 + 163);
                }

                if (board[rowCount][colCount].getIsWhite() == 1) {
                    g.setColor(Color.white);
                } else if (board[rowCount][colCount].getIsWhite() == 0) {
                    g.setColor(Color.black);
                }

                g.setFont(new Font(g.getFont().toString(), Font.PLAIN, 80));

                if (board[rowCount][colCount].getPiece() == 1) {
                    g.drawString("\u2659", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 2) {
                    g.drawString("\u2658", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 3) {
                    g.drawString("\u2657", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 4) {
                    g.drawString("\u2656", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 5) {
                    g.drawString("\u2655", rowCount * 100 + 110, colCount * 100 + 180);
                } else if (board[rowCount][colCount].getPiece() == 6) {
                    g.drawString("\u2654", rowCount * 100 + 110, colCount * 100 + 180);
                }

            }
        }

        repaint();

    }

}
