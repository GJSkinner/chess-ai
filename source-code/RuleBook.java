import java.util.ArrayList;

public class RuleBook {

    private boolean wkingMoved = false;
    private boolean wlRookMoved = false;
    private boolean wrRookMoved = false;
    private boolean bkingMoved = false;
    private boolean blRookMoved = false;
    private boolean brRookMoved = false;

    private boolean whiteTurn;

    private Square[][] board;
    private ArrayList<Integer> possibleMoves;

    public void findMoves(int x, int y, boolean isWhiteTurn) {

        whiteTurn = isWhiteTurn;
        possibleMoves = new ArrayList<>();

        if (board[x][y].getPiece() == 1 || board[x][y].getPiece() == 7) {
            findPawnMoves(x, y);
        } else if (board[x][y].getPiece() == 2 || board[x][y].getPiece() == 8) {
            findKnightMoves(x, y);
        } else if (board[x][y].getPiece() == 3 || board[x][y].getPiece() == 9) {
            findBishopMoves(x, y);
        } else if (board[x][y].getPiece() == 4 || board[x][y].getPiece() == 10) {
            findRookMoves(x, y);
        } else if (board[x][y].getPiece() == 5 || board[x][y].getPiece() == 11) {
            findBishopMoves(x, y);
            findRookMoves(x, y);
        } else if (board[x][y].getPiece() == 6 || board[x][y].getPiece() == 12) {
            findKingMoves(x, y);
        }

    }

    public ArrayList<Integer> highlightSquares(int curXPos, int curYPos) {

        ArrayList<Integer> possibleMoves = this.possibleMoves;
        ArrayList<Integer> highlightSquares = new ArrayList<>();

        for (int i = 0; i < possibleMoves.size(); i = i + 2) {

            int tempX = curXPos;
            int tempY = curYPos;
            int tempPiece = board[possibleMoves.get(i)][possibleMoves.get(i + 1)].getPiece();
            int tempP2 = board[curXPos][curYPos].getPiece();

            board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setPiece(board[curXPos][curYPos].getPiece());
            board[curXPos][curYPos].setPiece(0);

            if (!canOpponentCheck()) {
                board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setHighlighted(true);
                highlightSquares.add(possibleMoves.get(i));
                highlightSquares.add(possibleMoves.get(i + 1));
            }

            curXPos = tempX;
            curYPos = tempY;

            board[possibleMoves.get(i)][possibleMoves.get(i + 1)].setPiece(tempPiece);
            board[curXPos][curYPos].setPiece(tempP2);

        }

        return highlightSquares;

    }

    public boolean canOpponentCheck() {

        for (int curX = 0; curX < board.length; curX++) {
            for (int curY = 0; curY < board[0].length; curY++) {

                if (isNotCurrPlayersPiece(curX, curY)) {

                    whiteTurn = !whiteTurn;
                    if (checkChecker(curX, curY)) {

                        whiteTurn = !whiteTurn;
                        return true;

                    }
                    whiteTurn = !whiteTurn;

                }
            }
        }

        return false;

    }

    public boolean checkChecker(int x, int y) {

        int enemyKing = (whiteTurn) ? 12 : 6;
        findMoves(x, y, whiteTurn);
        ArrayList<Integer> possibleMoves = this.possibleMoves;

        for (int i = 0; i < possibleMoves.size(); i = i + 2) {

            if (board[possibleMoves.get(i)][possibleMoves.get(i + 1)].getPiece() == enemyKing) return true;

        }

        return false;

    }

    public boolean isNotCurrPlayersPiece(int curXPos, int curYPos) {

        int currPieces = whiteTurn ? 6 : 12;
        return board[curXPos][curYPos].getPiece() > currPieces || board[curXPos][curYPos].getPiece() <= currPieces - 6;

    }

    public int calculator(int i, int j, boolean isMinus) {

        int answer;
        if (whiteTurn) {
            answer = i - j;
            if (!isMinus) {
                answer = i + j;
            }
        } else {
            answer = i + j;
            if (!isMinus) {
                answer = i - j;
            }
        }
        return answer;

    }

    public void findPawnMoves(int x, int y) {

        if (board[x][calculator(y, 1, true)].getPiece() == 0) {
            possibleMoves.add(x); // move 1 forwards
            possibleMoves.add(calculator(y, 1, true));
        }
        if (whiteTurn && y == 6 || !whiteTurn && y == 1) {
            if (board[x][calculator(y, 2, true)].getPiece() == 0 && board[x][calculator(y, 1, true)].getPiece() == 0) {
                possibleMoves.add(x); // move 2 forwards
                possibleMoves.add(calculator(y, 2, true));
            }
        }
        if (whiteTurn && x < 7 || !whiteTurn && x > 0) {
            if (isNotCurrPlayersPiece(calculator(x, 1, false), calculator(y, 1, true))) {
                if (board[calculator(x, 1, false)][calculator(y, 1, true)].getPiece() > 0) {
                    possibleMoves.add(calculator(x, 1, false)); // take on right
                    possibleMoves.add(calculator(y, 1, true));
                }
            }
        }
        if (whiteTurn && x > 0 || !whiteTurn && x < 7) {
            if (isNotCurrPlayersPiece(calculator(x, 1, true), calculator(y, 1, true))) {
                if (board[calculator(x, 1, true)][calculator(y, 1, true)].getPiece() > 0) {
                    possibleMoves.add(calculator(x, 1, true)); // take on left
                    possibleMoves.add(calculator(y, 1, true));
                }
            }
        }
        if (whiteTurn && x < 7 || !whiteTurn && x > 0) {
            if (board[calculator(x, 1, false)][y].getPassable() && isNotCurrPlayersPiece(calculator(x, 1, false), y)) {
                possibleMoves.add(calculator(x, 1, false));
                possibleMoves.add(calculator(y, 1, true));
            }
        }
        if (whiteTurn && x > 0 || !whiteTurn && x < 7) {
            if (board[calculator(x, 1, true)][y].getPassable() && isNotCurrPlayersPiece(calculator(x, 1, true), y)) {
                possibleMoves.add(calculator(x, 1, true));
                possibleMoves.add(calculator(y, 1, true));
            }
        }

    }

    public void findKnightMoves(int x, int y) {

        for (int i = 2; i > -3; i--) {
            for (int j = 2; j > -3; j--) {

                if(Math.abs(i) == 2 ^ Math.abs(j) == 2) {
                    if (i != 0 && j != 0) {

                        try {
                            if (isNotCurrPlayersPiece(x + i, y + j)) {
                                possibleMoves.add(x + i);
                                possibleMoves.add(y + j);
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {}

                    }
                }

            }
        }

    }

    public void findRookMoves(int x, int y) {

        for (int i = 1; i < y + 1; i++) {  // up
            if (board[x][y - i].getPiece() == 0) {
                possibleMoves.add(x);
                possibleMoves.add(y - i);
            } else if (isNotCurrPlayersPiece(x, y - i)) {
                possibleMoves.add(x);
                possibleMoves.add(y - i);
                break;
            } else {
                break;
            }
        }
        for (int i = 1; i < board[0].length - y; i++) {   // down
            if (board[x][y + i].getPiece() == 0) {
                possibleMoves.add(x);
                possibleMoves.add(y + i);
            } else if (isNotCurrPlayersPiece(x, y + i)) {
                possibleMoves.add(x);
                possibleMoves.add(y + i);
                break;
            } else {
                break;
            }
        }
        for (int i = 1; i < board.length - x; i++) {   // right
            if (board[x + i][y].getPiece() == 0) {
                possibleMoves.add(x + i);
                possibleMoves.add(y);
            } else if (isNotCurrPlayersPiece(x + i, y)) {
                possibleMoves.add(x + i);
                possibleMoves.add(y);
                break;
            } else {
                break;
            }
        }
        for (int i = 1; i < x + 1; i++) {   //left
            if (board[x - i][y].getPiece() == 0) {
                possibleMoves.add(x - i);
                possibleMoves.add(y);
            } else if (isNotCurrPlayersPiece(x - i, y)) {
                possibleMoves.add(x - i);
                possibleMoves.add(y);
                break;
            } else {
                break;
            }
        }

    }

    public void findBishopMoves(int x, int y) {

        if ((board.length - x) < (board[0].length - y)) {
            for (int i = 1; i < board.length - x; i++) {
                if (diagonalDownRight(x, y, i)) break;
            }
        } else {
            for (int i = 1; i < board[0].length - y; i++) {
                if (diagonalDownRight(x, y, i)) break;
            }
        }

        if (y < (x + 1)) {
            for (int i = 1; i < y + 1; i++) {   // up/left
                if (board[x - i][y - i].getPiece() == 0) {
                    possibleMoves.add(x - i);
                    possibleMoves.add(y - i);
                } else if (isNotCurrPlayersPiece(x - i, y - i)) {
                    possibleMoves.add(x - i);
                    possibleMoves.add(y - i);
                    break;
                } else {
                    break;
                }
            }
        } else {
            for (int i = 1; i < x + 1; i++) {
                if (board[x - i][y - i].getPiece() == 0) {
                    possibleMoves.add(x - i);
                    possibleMoves.add(y - i);
                } else if (isNotCurrPlayersPiece(x - i, y - i)) {
                    possibleMoves.add(x - i);
                    possibleMoves.add(y - i);
                    break;
                } else {
                    break;
                }
            }
        }

        if (y < (board.length - x)) {
            for (int i = 1; i < y + 1; i++) {
                if (diagonalUpRight(x, y, i)) break;
            }
        } else {
            for (int i = 1; i < board.length - x; i++) {
                if (diagonalUpRight(x, y, i)) break;
            }
        }

        if (x < (board[0].length - y)) {
            for (int i = 1; i < x + 1; i++) {
                if (diagonalDownLeft(x, y, i)) break;
            }
        } else {
            for (int i = 1; i < board[0].length - y; i++) {
                if (diagonalDownLeft(x, y, i)) break;
            }
        }

    }

    public boolean diagonalDownLeft(int x, int y, int i) {

        if (board[x - i][y + i].getPiece() == 0) {
            possibleMoves.add(x - i);
            possibleMoves.add(y + i);
        } else if (isNotCurrPlayersPiece(x - i, y + i)) {
            possibleMoves.add(x - i);
            possibleMoves.add(y + i);
            return true;
        } else {
            return true;
        }
        return false;

    }

    public boolean diagonalUpRight(int x, int y, int i) {

        if (board[x + i][y - i].getPiece() == 0) {
            possibleMoves.add(x + i);
            possibleMoves.add(y - i);
        } else if (isNotCurrPlayersPiece(x + i, y - i)) {
            possibleMoves.add(x + i);
            possibleMoves.add(y - i);
            return true;
        } else {
            return true;
        }
        return false;

    }

    public boolean diagonalDownRight(int x, int y, int i) {

        if (board[x + i][y + i].getPiece() == 0) {
            possibleMoves.add(x + i);
            possibleMoves.add(y + i);
        } else if (isNotCurrPlayersPiece(x + i, y + i)) {
            possibleMoves.add(x + i);
            possibleMoves.add(y + i);
            return true;
        } else {
            return true;
        }
        return false;

    }

    public void findKingMoves(int x, int y) {

        for (int i = 1; i > -2; i--) {
            for (int j = 1; j > -2; j--) {
                if(!(i == 0 && j == 0)) {
                    try {
                        if (isNotCurrPlayersPiece(x + i, y + j)) {
                            possibleMoves.add(x + i);
                            possibleMoves.add(y + j);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                }
            }
        }

        if (whiteTurn) {
            if (!wkingMoved) {
                if (!wlRookMoved) {
                    if (board[1][7].getPiece() == 0 && board[2][7].getPiece() == 0 && board[3][7].getPiece() == 0) {
                        possibleMoves.add(x - 2);
                        possibleMoves.add(y);
                    }
                }
                if (!wrRookMoved) {
                    if (board[5][7].getPiece() == 0 && board[6][7].getPiece() == 0) {
                        possibleMoves.add(x + 2);
                        possibleMoves.add(y);
                    }
                }
            }
        }

        if (!whiteTurn) {
            if (!bkingMoved) {
                if (!blRookMoved) {
                    if (board[1][0].getPiece() == 0 && board[2][0].getPiece() == 0 && board[3][0].getPiece() == 0) {
                        possibleMoves.add(x - 2);
                        possibleMoves.add(y);
                    }
                }
                if (!brRookMoved) {
                    if (board[5][0].getPiece() == 0 && board[6][0].getPiece() == 0) {
                        possibleMoves.add(x + 2);
                        possibleMoves.add(y);
                    }
                }
            }
        }

    }

    public void setBkingMoved(boolean bkingMoved) {this.bkingMoved = bkingMoved;}
    public void setBlRookMoved(boolean blRookMoved) {this.blRookMoved = blRookMoved;}
    public void setBrRookMoved(boolean brRookMoved) {this.brRookMoved = brRookMoved;}
    public void setWkingMoved(boolean wkingMoved) {this.wkingMoved = wkingMoved;}
    public void setWlRookMoved(boolean wlRookMoved) {this.wlRookMoved = wlRookMoved;}
    public void setWrRookMoved(boolean wrRookMoved) {this.wrRookMoved = wrRookMoved;}
    public void setBoard(Square[][] board) {this.board = board;}

    public ArrayList<Integer> getPossibleMoves() {return possibleMoves;}

}
