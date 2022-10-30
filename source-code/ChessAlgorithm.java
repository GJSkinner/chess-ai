import java.util.ArrayList;

public class ChessAlgorithm {

    RuleBook ruleBook = new RuleBook();
    MoveGenerator moveGenerator = new MoveGenerator();

    private int curXPos = 100;
    private int curYPos = 100;
    private int oldXPos = 0;
    private int oldYPos = 0;

    private boolean isWhiteTurn = true;
    private static int[][] board;
    private ArrayList<Integer> highlights = new ArrayList<>();

    public ChessAlgorithm() {

        board = new int[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }

        configureBoard();

    }

    public void configureBoard() {

        for (int i = 0; i <= 6; i += 6) {

            int y = isWhiteTurn ? 7 : 0;

            for (int x = 0; x < 8; x++) {
                board[x][Math.abs(y - 1)] = (1 + i);
            }

            board[1][y] = (2 + i);
            board[6][y] = (2 + i);

            board[2][y] = (3 + i);
            board[5][y] = (3 + i);

            board[0][y] = (4 + i);
            board[7][y] = (4 + i);

            board[3][y] = (5 + i);
            board[4][y] = (6 + i);

            isWhiteTurn = false;

        }

        isWhiteTurn = true;

    }

    public void getAIMove() {

        isWhiteTurn = false;

        board = moveGenerator.minimaxTest(ruleBook, board);
        if (board[curXPos][curYPos] == 13) board[curXPos][curYPos] = 1;

        isWhiteTurn = true;

    }

    public boolean isCurrPlayersPiece(int curXPos, int curYPos) {

        int currPieces = isWhiteTurn ? 6 : 12;
        return board[curXPos][curYPos] > currPieces - 6 && board[curXPos][curYPos] <= currPieces;

    }

    public void selectSquare() {

        if (isCurrPlayersPiece(curXPos, curYPos)) {

            ruleBook.setBoard(board);
            ruleBook.findMoves(curXPos, curYPos, isWhiteTurn);
            highlights = ruleBook.highlightSquares(curXPos, curYPos);

            oldXPos = curXPos;
            oldYPos = curYPos;

        } else if (isHighlighted(curXPos, curYPos)) {

            movePiece();
            getAIMove();

        } else {highlights.clear();}

    }

    public boolean isHighlighted(int x, int y) {

        for (int i = 0; i < highlights.size(); i++) {
            if (highlights.get(i) == x && highlights.get(i + 1) == y) return true;
        }

        return false;

    }

    public void movePiece() {

        board[curXPos][curYPos] = board[oldXPos][oldYPos];
        board[oldXPos][oldYPos] = 0;

        if (board[curXPos][curYPos] == 4 || board[curXPos][curYPos] == 10) rookMoved();  // handles castling stuff
        if (board[curXPos][curYPos] == 6 || board[curXPos][curYPos] == 12) castleMoved();
        if (board[curXPos][curYPos] == 1 || board[curXPos][curYPos] == 7) pawnMoved(); // handles special pawn cases

    }

    public void pawnMoved() {

        if (isWhiteTurn && curYPos == 0) {  // promotion to queen
            board[curXPos][curYPos] = 5;
        } else if (!isWhiteTurn && curYPos == 7) {
            board[curXPos][curYPos] = 11;
        }

        if (Math.abs(oldYPos - curYPos) > 1) {  // sets en passant as possible
            board[curXPos][curYPos] = isWhiteTurn ? 13 : 14;
        }

        if (isWhiteTurn) {  // performs the en passant move
            if (board[curXPos][curYPos + 1] == 14) {
                board[curXPos][curYPos + 1] = 0;
            }
        } else {
            if (board[curXPos][curYPos - 1] == 13) {
                board[curXPos][curYPos - 1] = 0;
            }
        }

    }

    public void rookMoved() {

        if (oldXPos == 0 && oldYPos == 7) {
            ruleBook.setWCanCastleL(false);
        } else if (oldXPos == 0 && oldYPos == 0) {
            ruleBook.setBCanCastleL(false);
        }

        if (oldXPos == 7 && oldYPos == 7) {
            ruleBook.setWCanCastleR(false);
        } else if (oldXPos == 7 && oldYPos == 0) {
            ruleBook.setBCanCastleR(false);
        }

    }

    public void castleMoved() {

        if (isWhiteTurn) {
            ruleBook.setWCanCastleR(false);
            ruleBook.setWCanCastleL(false);
        } else {
            ruleBook.setBCanCastleR(false);
            ruleBook.setBCanCastleL(false);
        }

        int y = (isWhiteTurn) ? 7 : 0;
        int rook = (isWhiteTurn) ? 4 : 10;

        if (oldXPos - curXPos == 2) { // if left castle
            board[0][y] = 0;
            board[3][y] = rook;
            curXPos = 3;
            curYPos = y;
        } else if (oldXPos - curXPos == -2) { // if right castle
            board[7][y] = 0;
            board[5][y] = rook;
            curXPos = 5;
            curYPos = y;
        }

    }

    public void setCurYPos(int curYPos) {this.curYPos = curYPos;}
    public void setCurXPos(int curXPos) {this.curXPos = curXPos;}

    public static int[][] getBoard() {return board;}

}
