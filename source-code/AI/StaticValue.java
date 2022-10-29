import java.util.ArrayList;

public class StaticValue {

    RuleBook ruleBook = new RuleBook();

    public int getStaticValue(int[][] board) {

        int blackValue = computeStaticValue(board, false);
        int whiteValue = computeStaticValue(board, true);

        return whiteValue - blackValue;

    }

    public int computeStaticValue(int[][] board, boolean isWhiteTurn) {

        int squaresControlled = 0;
        int totalPieceValue = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {

                if (isCurrPlayersPiece(board, isWhiteTurn, x, y)) {

                    totalPieceValue += getPieceValue(board, x, y);
                    squaresControlled += getSquaresControlled(board, isWhiteTurn, x, y);

                }

            }
        }

        return (int) ((totalPieceValue * 0.8) + (squaresControlled * 0.2));

    }

    public boolean isCurrPlayersPiece(int[][] board, boolean isWhiteTurn, int curXPos, int curYPos) {

        int currPieces = isWhiteTurn ? 6 : 12;
        return board[curXPos][curYPos] <= currPieces && board[curXPos][curYPos] > currPieces - 6;

    }

    public int getSquaresControlled(int[][] board, boolean isWhiteTurn, int x, int y) {

        ruleBook.setBoard(board);
        ruleBook.findMoves(x, y, isWhiteTurn);
        ArrayList<Integer> possibleMoves = ruleBook.getPossibleMoves();
        return (possibleMoves.size() / 2);

    }

    public int getPieceValue(int[][] board, int x, int y) {

        return switch (board[x][y]) {
            case 1, 7 -> 1;
            case 2, 3, 8, 9 -> 3;
            case 4, 10 -> 5;
            case 5, 11 -> 9;
            default -> 0;
        };

    }

}
