import java.util.ArrayList;

public class StaticValue {

    RuleBook ruleBook = new RuleBook();

    private boolean isWhiteTurn;
    private int whiteValue;
    private int blackValue;
    private int staticValue;

    public void computeStaticValue(Square[][] board) {

        int squaresControlled = 0;
        int totalPieceValue = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {

                if (isCurrPlayersPiece(board, x, y)) {

                    totalPieceValue += getPieceValue(board, x, y);
                    squaresControlled += getSquaresControlled(board, x, y);

                }

            }
        }

        System.out.println(totalPieceValue);

        if (isWhiteTurn) whiteValue = (int) ((totalPieceValue * 0.8) + (squaresControlled * 0.2));
        else blackValue = (int) ((totalPieceValue * 0.8) + (squaresControlled * 0.2));

        staticValue = whiteValue - blackValue;

    }

    public boolean isCurrPlayersPiece(Square[][] board, int curXPos, int curYPos) {

        int currPieces = isWhiteTurn ? 6 : 12;
        return board[curXPos][curYPos].getPiece() <= currPieces && board[curXPos][curYPos].getPiece() > currPieces - 6;

    }

    public int getSquaresControlled(Square[][] board, int x, int y) {

        ruleBook.setBoard(board);
        ruleBook.findMoves(x, y, isWhiteTurn);
        ArrayList<Integer> possibleMoves = ruleBook.getPossibleMoves();
        return (possibleMoves.size() / 2);

    }

    public int getPieceValue(Square[][] board, int x, int y) {

        return switch (board[x][y].getPiece()) {
            case 1, 7 -> 1;
            case 2, 3, 8, 9 -> 3;
            case 4, 10 -> 5;
            case 5, 11 -> 9;
            default -> 0;
        };

    }

    public int getStaticValue() {
        return staticValue;
    }

    public void setWhiteTurn(boolean whiteTurn) {isWhiteTurn = whiteTurn;}

}
