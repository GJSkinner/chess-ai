public class ChessAlgorithm {

    RuleBook ruleBook = new RuleBook();
    StaticValue staticValue = new StaticValue();
    MoveGenerator moveGenerator = new MoveGenerator();

    private int curXPos = 100;
    private int curYPos = 100;
    private int oldXPos = 0;
    private int oldYPos = 0;

    private int turnsSince = 0;
    private boolean isWhiteTurn = true;
    private static Square[][] board;

    public ChessAlgorithm() {

        board = new Square[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Square();
                board[i][j].setPiece(0);
            }
        }

        configureBoard();

    }

    public void configureBoard() {

        for (int i = 0; i <= 6; i += 6) {

            int y = isWhiteTurn ? 7 : 0;

            for (int x = 0; x < 8; x++) {
                board[x][Math.abs(y - 1)].setPiece(1 + i);
            }

            board[1][y].setPiece(2 + i);
            board[6][y].setPiece(2 + i);

            board[2][y].setPiece(3 + i);
            board[5][y].setPiece(3 + i);

            board[0][y].setPiece(4 + i);
            board[7][y].setPiece(4 + i);

            board[3][y].setPiece(5 + i);
            board[4][y].setPiece(6 + i);

            isWhiteTurn = false;

        }

        isWhiteTurn = true;

    }

    public void getAIMove() {

        isWhiteTurn = false;

        board[oldXPos][oldYPos].setSelected(false);
        unhighlightSquares();

        moveGenerator.selectRandomPiece(ruleBook, board);

        oldXPos = moveGenerator.getOldXPos();
        oldYPos = moveGenerator.getOldYPos();
        curXPos = moveGenerator.getCurXPos();
        curYPos = moveGenerator.getCurYPos();

        movePiece();

        isWhiteTurn = true;

    }

    public boolean isNotCurrPlayersPiece(int curXPos, int curYPos) {

        int currPieces = isWhiteTurn ? 6 : 12;
        return board[curXPos][curYPos].getPiece() > currPieces || board[curXPos][curYPos].getPiece() <= currPieces - 6;

    }

    public void selectSquare() {

        board[oldXPos][oldYPos].setSelected(false);

        if (!isNotCurrPlayersPiece(curXPos, curYPos)) {

            unhighlightSquares();
            board[curXPos][curYPos].setSelected(true);

            ruleBook.setBoard(board);
            ruleBook.findMoves(curXPos, curYPos, isWhiteTurn);
            ruleBook.highlightSquares(curXPos, curYPos);

            oldXPos = curXPos;
            oldYPos = curYPos;

        } else if (board[curXPos][curYPos].getHighlighted()) {
            movePiece();
            getAIMove();
        } else {
            unhighlightSquares();
        }

    }

    public void movePiece() {

        turnsSince++;

        board[curXPos][curYPos].setPiece(board[oldXPos][oldYPos].getPiece());
        board[oldXPos][oldYPos].setPiece(0);
        unhighlightSquares();

        if (board[curXPos][curYPos].getPiece() == 4 || board[curXPos][curYPos].getPiece() == 10) rookMoved();
        if (board[curXPos][curYPos].getPiece() == 6 || board[curXPos][curYPos].getPiece() == 12) castling();  // handles castling stuff
        if (board[curXPos][curYPos].getPiece() == 1 || board[curXPos][curYPos].getPiece() == 7) pawnFeatures(); // handles special pawn cases

        if (turnsSince == 1) {  // prevents en passant after first turn
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[0].length; y++) {
                    board[x][y].setPassable(false);
                }
            }
        }

        staticValue.setWhiteTurn(isWhiteTurn);
        staticValue.computeStaticValue(board);
        System.out.println("static value: " + staticValue.getStaticValue());

    }

    public void pawnFeatures() {

        if (isWhiteTurn && curYPos == 0) {  // promotion to queen
            board[curXPos][curYPos].setPiece(5);
        } else if (!isWhiteTurn && curYPos == 7) {
            board[curXPos][curYPos].setPiece(11);
        }

        if (Math.abs(oldYPos - curYPos) > 1) {  // sets en passant as possible
            board[curXPos][curYPos].setPassable(true);
            turnsSince = 0;
        }

        if (isWhiteTurn) {  // performs the en passant move
            if (board[curXPos][curYPos + 1].getPassable() && turnsSince == 1 && isNotCurrPlayersPiece(curXPos, curYPos + 1)) {
                board[curXPos][curYPos + 1].setPiece(0);
                board[curXPos][curYPos + 1].setPassable(false);
            }
        } else {
            if (board[curXPos][curYPos - 1].getPassable() && turnsSince == 1 && isNotCurrPlayersPiece(curXPos, curYPos - 1)) {
                board[curXPos][curYPos - 1].setPiece(0);
                board[curXPos][curYPos - 1].setPassable(false);
            }
        }

    }

    public void rookMoved() {

        if (oldXPos == 0 && oldYPos == 7) {
            ruleBook.setWlRookMoved(true);
        } else if (oldXPos == 0 && oldYPos == 0) {
            ruleBook.setBlRookMoved(true);
        }

        if (oldXPos == 7 && oldYPos == 7) {
            ruleBook.setWrRookMoved(true);
        } else if (oldXPos == 7 && oldYPos == 0) {
            ruleBook.setBrRookMoved(true);
        }

    }

    public void castling() {

        if (isWhiteTurn) ruleBook.setWkingMoved(true);
        else ruleBook.setBkingMoved(true);

        int y = (isWhiteTurn) ? 7 : 0;
        int rook = (isWhiteTurn) ? 4 : 10;

        if (oldXPos - curXPos == 2) { // if left castle
            board[0][y].setPiece(0);
            board[3][y].setPiece(rook);
            curXPos = 3;
            curYPos = y;
        } else if (oldXPos - curXPos == -2) { // if right castle
            board[7][y].setPiece(0);
            board[5][y].setPiece(rook);
            curXPos = 5;
            curYPos = y;
        }

    }

    public void unhighlightSquares() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setHighlighted(false);
            }
        }

    }

    public void setCurYPos(int curYPos) {this.curYPos = curYPos;}
    public void setCurXPos(int curXPos) {this.curXPos = curXPos;}

    public static Square[][] getBoard() {return board;}

}
