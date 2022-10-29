import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    GUI gui;
    ChessAlgorithm chessAlgorithm = new ChessAlgorithm();

    MouseListener(GUI gui) {

        this.gui = gui;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int squareWidth = 100;

        int xPos = 0;
        int yPos = 0;

        for (int i = 1; (squareWidth * i) + 106 < e.getX(); i++) {
            xPos = i;
        }

        for (int i = 1; (squareWidth * i) + 130 < e.getY(); i++) {
            yPos = i;
        }

        if (e.getX() < 106 || e.getX() > 906) {
            xPos = 100;
        }

        if (e.getY() < 130 || e.getY() > 930) {
            yPos = 100;
        }

        chessAlgorithm.setCurXPos(xPos);
        chessAlgorithm.setCurYPos(yPos);
        gui.setSelectedX(xPos);
        gui.setSelectedY(yPos);

        chessAlgorithm.selectSquare();

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
