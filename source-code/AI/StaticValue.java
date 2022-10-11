import java.util.ArrayList;

public class StaticValue {

    RuleBook ruleBook = new RuleBook();
    boolean isWhiteTurn;

    int totalPieceValue;
    int squaresControlled;
    int staticValue;

    public void computeStaticValue(int white, Square[][] board) {

        isWhiteTurn = white == 1;
        totalPieceValue = 0;
        squaresControlled = 0;
        staticValue = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {

                if (board[x][y].getIsWhite() == white) {

                    getPieceValue(board, x, y);
                    getSquaresControlled(x, y);

                }

            }
        }

        staticValue = (int) ((totalPieceValue * 0.6) + (squaresControlled * 0.4));

    }

    public void getSquaresControlled(int x, int y) {

        ArrayList<Integer> possibleMoves;
        ruleBook.findMoves(x, y, isWhiteTurn);
        possibleMoves = ruleBook.getPossibleMoves();
        squaresControlled = squaresControlled + (possibleMoves.size() / 2);
        possibleMoves.clear();

    }

    public void getPieceValue(Square[][] board, int x, int y) {

        switch (board[x][y].getPiece()) {
            case 1 -> totalPieceValue = totalPieceValue + 1;
            case 2, 3 -> totalPieceValue = totalPieceValue + 3;
            case 4 -> totalPieceValue = totalPieceValue + 5;
            case 5 -> totalPieceValue = totalPieceValue + 9;
        }

    }

    public int getStaticValue() {
        return staticValue;
    }
}
