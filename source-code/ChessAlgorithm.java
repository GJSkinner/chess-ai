import java.util.ArrayList;

public class ChessAlgorithm {

    RuleBook ruleBook = new RuleBook();
    //StaticValue staticValue = new StaticValue();

    public int curXPos = 100;
    public int curYPos = 100;
    public int oldXPos = 0;
    public int oldYPos = 0;

    public int turnsTaken = 0;
    public int turnsSince = 0;

    public boolean isWhiteTurn = true;

    public boolean kingChecked = false;
    public boolean escapable = true;
    public boolean checkMate =  false;
    ArrayList<Integer> checkSavingMoves;

    public static Square[][] board;

    public ChessAlgorithm() {

        board = new Square[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Square();
            }
        }

        configureBoard();

    }

    public void configureBoard() {

        for (int x = 0; x < board.length; x++) {       // sets all squares on board to zero
            for (int y = 0; y < board[0].length; y++) {
                board[x][y].setPiece(0);
            }
        }

        int y;       // sets values to that of starting board

        for (int i = 0; i < 2; i++) {

            if (isWhiteTurn) {
                y = 7;
                for (int q = 0; q < 8; q++) {
                    board[q][y].setIsWhite(1);
                    board[q][Math.abs(y - 1)].setIsWhite(1);
                }
            } else {
                y = 0;
                for (int q = 0; q < 8; q++) {
                    board[q][y].setIsWhite(0);
                    board[q][Math.abs(y - 1)].setIsWhite(0);
                }
            }

            for (int x = 0; x < 8; x++) {       // sets pieces onto the board
                board[x][Math.abs(y - 1)].setPiece(1);
            }

            board[1][y].setPiece(2);
            board[6][y].setPiece(2);

            board[2][y].setPiece(3);
            board[5][y].setPiece(3);

            board[0][y].setPiece(4);
            board[7][y].setPiece(4);

            board[3][y].setPiece(5);
            board[4][y].setPiece(6);

            isWhiteTurn = false;

        }

        isWhiteTurn = true;

    }

    public void selectSquare() {

        int white = 0;
        if (isWhiteTurn){
            white = 1;
        }

        board[oldXPos][oldYPos].setSelected(false);

        if (board[curXPos][curYPos].getIsWhite() == white) {
            ruleBook.findMoves(100, 100, isWhiteTurn); // to reset possible moves
            unhighlightSquares();
            board[curXPos][curYPos].setSelected(true);
            if (!kingChecked) {
                ruleBook.findMoves(curXPos, curYPos, isWhiteTurn);
            } else {
                checkMoves(white);
            }
            highlightSquares(white);
            oldXPos = curXPos;
            oldYPos = curYPos;
        } else if (board[curXPos][curYPos].getHighlighted() && board[curXPos][curYPos].getPiece() == 0) {
            movePiece(white);
        } else if (board[curXPos][curYPos].getHighlighted() && board[curXPos][curYPos].getIsWhite() != white && board[curXPos][curYPos].getPiece() > 0) {
            movePiece(white);
            takePiece();
        }

    }

    public void movePiece(int colour) {

        turnsSince++;

        board[curXPos][curYPos].setPiece(board[oldXPos][oldYPos].getPiece());
        board[curXPos][curYPos].setIsWhite(colour);

        castling(colour);  // handles castling stuff

        if (board[curXPos][curYPos].getPiece() == 1) {  // handles special pawn cases
            pawnFeatures(colour);
        }

        board[oldXPos][oldYPos].setPiece(0);
        board[oldXPos][oldYPos].setIsWhite(2);
        unhighlightSquares();

        if (turnsSince == 1) {  // prevents en passant after first turn
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[0].length; y++) {
                    board[x][y].setPassable(false);
                }
            }
        }

        kingChecked = false;
        checkChecker(colour);

        //staticValue.computeStaticValue(colour);
        //System.out.println("static value: " + staticValue.getStaticValue());

        isWhiteTurn = colour != 1;
        turnsTaken++;

    }

    public void pawnFeatures(int colour) {

        if (isWhiteTurn && curYPos == 0 || !isWhiteTurn && curYPos == 7) {  // promotion to queen
            board[curXPos][curYPos].setPiece(5);
        }
        if (Math.abs(oldYPos - curYPos) > 1) {  // sets en passant as possible
            board[curXPos][curYPos].setPassable(true);
            turnsSince = 0;
        }
        if (colour == 1) {  // performs the en passant move
            if (board[curXPos][curYPos + 1].getPassable() && turnsSince == 1 && board[curXPos][curYPos + 1].getIsWhite() != colour) {
                board[curXPos][curYPos + 1].setPiece(0);
                board[curXPos][curYPos + 1].setIsWhite(2);
                board[curXPos][curYPos + 1].setPassable(false);
            }
        }
        if (colour == 0) {
            if (board[curXPos][curYPos - 1].getPassable() && turnsSince == 1 && board[curXPos][curYPos - 1].getIsWhite() != colour) {
                board[curXPos][curYPos - 1].setPiece(0);
                board[curXPos][curYPos - 1].setIsWhite(2);
                board[curXPos][curYPos - 1].setPassable(false);
            }
        }

    }

    public void castling(int colour) {

        if (board[oldXPos][oldYPos].getPiece() == 6 && colour == 1) {
            ruleBook.setWkingMoved(true);
        } else if (board[oldXPos][oldYPos].getPiece() == 6 && colour == 0) {
            ruleBook.setBkingMoved(true);
        }
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
        if (board[curXPos][curYPos].getPiece() == 6) {

            int y = 0;
            if (isWhiteTurn) {
                y = 7;
            }

            if (oldXPos - curXPos == 2) { // if left castle
                board[0][y].setPiece(0);
                board[0][y].setIsWhite(2);
                board[3][y].setPiece(4);
                board[3][y].setIsWhite(colour);
                curXPos = 3;
                curYPos = y;
            } else if (oldXPos - curXPos == -2) { // if right castle
                board[7][y].setPiece(0);
                board[7][y].setIsWhite(2);
                board[5][y].setPiece(4);
                board[5][y].setIsWhite(colour);
                curXPos = 5;
                curYPos = y;
            }

        }

    }

    public void takePiece() {
        System.out.println("TOOKEN");
    }

    public void highlightSquares(int white) {

        ArrayList<Integer> possibleMoves = new ArrayList<>();
        ArrayList<Integer> noGoMoves = new ArrayList<>();

        if (!kingChecked) {
            possibleMoves = ruleBook.getPossibleMoves();
            for (int i = 0; i < possibleMoves.size(); i = i + 2) {

                int tempX = curXPos;
                int tempY = curYPos;
                int tempPiece = board[possibleMoves.get(i)][possibleMoves.get(i + 1)].getPiece();
                int tempP2 = board[curXPos][curYPos].getPiece();
                int tempColour = board[possibleMoves.get(i)][possibleMoves.get(i + 1)].getIsWhite();
                int tempC2 = board[curXPos][curYPos].getIsWhite();

                if (board[curXPos][curYPos].getPiece() == 6) {
                    if (Math.abs(possibleMoves.get(i) - curXPos) > 1) {

                        int off = (curXPos - possibleMoves.get(i)) / 2;

                        board[curXPos - off][curYPos].setPiece(6);
                        board[curXPos - off][curYPos].setIsWhite(tempC2);

                        canOpponentCheck(white);
                        curXPos = tempX;
                        curYPos = tempY;

                        board[possibleMoves.get(i)][curYPos].setPiece(6);
                        board[possibleMoves.get(i)][curYPos].setIsWhite(tempC2);

                        canOpponentCheck(white);
                        curXPos = tempX;
                        curYPos = tempY;

                        if (!escapable) {
                            noGoMoves.add(possibleMoves.get(i));
                            noGoMoves.add(possibleMoves.get(i + 1));
                            noGoMoves.add(curXPos - off);
                            noGoMoves.add(curYPos);
                        }

                        board[curXPos - off][curYPos].setPiece(0);
                        board[curXPos - off][curYPos].setIsWhite(2);
                        board[possibleMoves.get(i)][curYPos].setPiece(0);
                        board[possibleMoves.get(i)][curYPos].setIsWhite(2);

                        escapable = true;
                        kingChecked = false;

                    }
                }

                board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setPiece(board[curXPos][curYPos].getPiece());
                board[curXPos][curYPos].setPiece(0);
                board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setIsWhite(board[curXPos][curYPos].getIsWhite());
                board[curXPos][curYPos].setIsWhite(2);

                canOpponentCheck(white);

                if (!escapable) {
                    noGoMoves.add(possibleMoves.get(i));
                    noGoMoves.add(possibleMoves.get(i + 1));
                }

                escapable = true;
                kingChecked = false;

                curXPos = tempX;
                curYPos = tempY;

                board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setPiece(tempPiece);
                board[curXPos][curYPos].setPiece(tempP2);
                board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setIsWhite(tempColour);
                board[curXPos][curYPos].setIsWhite(tempC2);

            }

        } else {

            ruleBook.findMoves(curXPos, curYPos, isWhiteTurn); // finds check saving moves
            ArrayList<Integer> bossibleMoves = ruleBook.getPossibleMoves();
            for (int i = 0; i < checkSavingMoves.size(); i = i + 3) {
                if (checkSavingMoves.get(i) == board[curXPos][curYPos].getPiece()) {
                    if (board[curXPos][curYPos].getPiece() == 6) {
                        if (Math.abs(checkSavingMoves.get(i + 1) - curXPos) > 1) {
                            noGoMoves.add(checkSavingMoves.get(i + 1));
                            noGoMoves.add(checkSavingMoves.get(i + 2));
                        }
                    }
                    for (int j = 0; j < bossibleMoves.size(); j = j + 2) {
                        if (checkSavingMoves.get(i + 1).equals(bossibleMoves.get(j)) && checkSavingMoves.get(i + 2).equals(bossibleMoves.get(j + 1))) {
                            possibleMoves.add(checkSavingMoves.get(i + 1));
                            possibleMoves.add(checkSavingMoves.get(i + 2));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < possibleMoves.size(); i = i + 2) {
            boolean ok = true;
            if (noGoMoves.size() > 0) {
                for (int j = 0; j < noGoMoves.size(); j = j + 2) {
                    if (possibleMoves.get(i).equals(noGoMoves.get(j)) && possibleMoves.get(i + 1).equals(noGoMoves.get(j + 1))) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setHighlighted(true);
                }
            } else {
                board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setHighlighted(true);
            }
        }

    }

    public void unhighlightSquares() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setHighlighted(false);
            }
        }

    }

    public void canOpponentCheck(int white) {

        for (int curX = 0; curX < board.length; curX++) {
            for (int curY = 0; curY < board[0].length; curY++) {

                if (board[curX][curY].getIsWhite() < 2 && board[curX][curY].getIsWhite() != white) {

                    curXPos = curX;
                    curYPos = curY;

                    if (white == 0) {
                        isWhiteTurn = true;
                        checkChecker(1);
                        isWhiteTurn = false;
                    } else {
                        isWhiteTurn = false;
                        checkChecker(0);
                        isWhiteTurn = true;
                    }
                }
            }
        }

    }

    public void checkChecker(int white) {

        kingChecked = false;

        ruleBook.findMoves(curXPos, curYPos, isWhiteTurn);
        ArrayList<Integer> possibleMoves = ruleBook.getPossibleMoves();

        for (int i = 0; i < possibleMoves.size(); i = i + 2) {
            if (board[possibleMoves.get(i)][possibleMoves.get(i + 1)].getPiece() == 6) {
                if (board[possibleMoves.get(i)][possibleMoves.get(i + 1)].getIsWhite() != white) {
                    kingChecked = true;
                    escapable = false;
                }
            }
        }

    }

    public void checkMoves(int white) {

        ArrayList<Integer> possibleMoves;
        ArrayList<Integer> allPossibleMoves = new ArrayList<>();
        checkSavingMoves = new ArrayList<>();
        ArrayList<Integer> piece = new ArrayList<>();
        ArrayList<Integer> piecePos = new ArrayList<>();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {

                if (board[x][y].getIsWhite() == white) {

                    ruleBook.findMoves(x, y, isWhiteTurn);

                    possibleMoves = ruleBook.getPossibleMoves();

                    allPossibleMoves.addAll(possibleMoves);

                    if (possibleMoves.size() > 0) {
                        piece.add(board[x][y].getPiece());
                        piece.add(possibleMoves.size() / 2);
                        piecePos.add(x);
                        piecePos.add(y);
                    }

                    possibleMoves.clear();

                }

            }
        }

        int tempX = curXPos;
        int tempY = curYPos;

        int offset = 0;

        for (int i = 0; i < piece.size(); i = i + 2) {
            for (int j = 0; j < piece.get(i + 1); j++) {

                if (j > 0) {
                    offset = offset + 2;
                }

                int x = allPossibleMoves.get(i + offset);
                int y = allPossibleMoves.get(i + offset + 1);

                int tempPieceOG =  board[piecePos.get(i)][piecePos.get(i + 1)].getPiece();
                int tempColourOG =  board[piecePos.get(i)][piecePos.get(i + 1)].getIsWhite();
                int tempPiece = board[x][y].getPiece();
                int tempColour = board[x][y].getIsWhite();

                board[piecePos.get(i)][piecePos.get(i + 1)].setPiece(0);
                board[piecePos.get(i)][piecePos.get(i + 1)].setIsWhite(2);


                board[x][y].setPiece(piece.get(i));
                board[x][y].setIsWhite(white);

                canOpponentCheck(white);

                if (escapable) {
                    checkSavingMoves.add(piece.get(i));
                    checkSavingMoves.add(x);
                    checkSavingMoves.add(y);
                }

                escapable = true;

                board[piecePos.get(i)][piecePos.get(i + 1)].setPiece(tempPieceOG);
                board[piecePos.get(i)][piecePos.get(i + 1)].setIsWhite(tempColourOG);
                board[x][y].setPiece(tempPiece);
                board[x][y].setIsWhite(tempColour);

            }

        }

        kingChecked = true;
        curXPos = tempX;
        curYPos = tempY;

        if (checkSavingMoves.size() == 0) {
            checkMate = true;
            System.out.println("CHECKMATE!");
        }

    }

    public void setCurYPos(int curYPos) {this.curYPos = curYPos;}

    public void setCurXPos(int curXPos) {this.curXPos = curXPos;}

    public static Square[][] getBoard() {return board;}

}
