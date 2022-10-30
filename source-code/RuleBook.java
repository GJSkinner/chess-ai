import java.util.ArrayList;

public class RuleBook {

    private boolean wCanCastleL = true;
    private boolean wCanCastleR = true;
    private boolean bCanCastleL = true;
    private boolean bCanCastleR = true;

    private boolean whiteTurn;

    private int[][] board;
    private ArrayList<Integer> possibleMoves;

    public void findMoves(int x, int y, boolean isWhiteTurn) {

        whiteTurn = isWhiteTurn;
        possibleMoves = new ArrayList<>();

        if (board[x][y] == 1 || board[x][y] == 7) {
            findPawnMoves(x, y);
        } else if (board[x][y] == 2 || board[x][y] == 8) {
            findKnightMoves(x, y);
        } else if (board[x][y] == 3 || board[x][y] == 9) {
            findBishopMoves(x, y);
        } else if (board[x][y] == 4 || board[x][y] == 10) {
            findRookMoves(x, y);
        } else if (board[x][y] == 5 || board[x][y] == 11) {
            findBishopMoves(x, y);
            findRookMoves(x, y);
        } else if (board[x][y] == 6 || board[x][y] == 12) {
            findKingMoves(x, y);
        }

    }

    public ArrayList<Integer> highlightSquares(int curXPos, int curYPos) {

        ArrayList<Integer> possibleMoves = this.possibleMoves;
        ArrayList<Integer> highlightSquares = new ArrayList<>();

        for (int i = 0; i < possibleMoves.size(); i = i + 2) {

            int tempX = curXPos;
            int tempY = curYPos;
            int tempPiece = board[possibleMoves.get(i)][possibleMoves.get(i + 1)];
            int tempP2 = board[curXPos][curYPos];

            board[possibleMoves.get(i)][possibleMoves.get(i + 1)] = board[curXPos][curYPos];
            board[curXPos][curYPos] = 0;

            if (!canOpponentCheck()) {
                highlightSquares.add(possibleMoves.get(i));
                highlightSquares.add(possibleMoves.get(i + 1));
            }

            curXPos = tempX;
            curYPos = tempY;

            board[possibleMoves.get(i)][possibleMoves.get(i + 1)] = tempPiece;
            board[curXPos][curYPos] = tempP2;

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

        for (int i = 0; i < possibleMoves.size(); i = i + 2) {

            if (board[possibleMoves.get(i)][possibleMoves.get(i + 1)] == enemyKing) return true;

        }

        return false;

    }

    public boolean isNotCurrPlayersPiece(int curXPos, int curYPos) {

        int currPieces = whiteTurn ? 6 : 12;
        return board[curXPos][curYPos] > currPieces || board[curXPos][curYPos] <= currPieces - 6;

    }

    public int calculator(int i, int j, boolean isMinus) {

        if (whiteTurn ^ isMinus) return i + j;
        else return i - j;

    }

    public void findPawnMoves(int x, int y) {

        if (board[x][calculator(y, 1, true)] == 0) {
            possibleMoves.add(x); // move 1 forwards
            possibleMoves.add(calculator(y, 1, true));
        }
        if (whiteTurn && y == 6 || !whiteTurn && y == 1) {
            if (board[x][calculator(y, 2, true)] == 0 && board[x][calculator(y, 1, true)] == 0) {
                possibleMoves.add(x); // move 2 forwards
                possibleMoves.add(calculator(y, 2, true));
            }
        }
        if (whiteTurn && x < 7 || !whiteTurn && x > 0) {
            if (isNotCurrPlayersPiece(calculator(x, 1, false), calculator(y, 1, true))) {
                if (board[calculator(x, 1, false)][calculator(y, 1, true)] > 0) {
                    possibleMoves.add(calculator(x, 1, false)); // take on right
                    possibleMoves.add(calculator(y, 1, true));
                }
            }
        }
        if (whiteTurn && x > 0 || !whiteTurn && x < 7) {
            if (isNotCurrPlayersPiece(calculator(x, 1, true), calculator(y, 1, true))) {
                if (board[calculator(x, 1, true)][calculator(y, 1, true)] > 0) {
                    possibleMoves.add(calculator(x, 1, true)); // take on left
                    possibleMoves.add(calculator(y, 1, true));
                }
            }
        }
        if (whiteTurn && x < 7 || !whiteTurn && x > 0) {
            if (board[calculator(x, 1, false)][y] > 12 && isNotCurrPlayersPiece(calculator(x, 1, false), y)) {
                possibleMoves.add(calculator(x, 1, false));
                possibleMoves.add(calculator(y, 1, true));
            }
        }
        if (whiteTurn && x > 0 || !whiteTurn && x < 7) {
            if (board[calculator(x, 1, true)][y] > 12 && isNotCurrPlayersPiece(calculator(x, 1, true), y)) {
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

    public void findBishopMoves(int x, int y) {

        for (int i = 1; i > -2; i -= 2) {
            for (int j = 1; j > -2; j -= 2) {

                findSlidingMoves(x, y, i, j);

            }
        }

    }

    public void findRookMoves(int x, int y) {

        for (int i = 1; i > -2; i -= 1) {
            for (int j = 1; j > -2; j -= 1) {

                if (Math.abs(i) != Math.abs(j)) {

                    findSlidingMoves(x, y, i, j);

                }

            }

        }

    }

    public void findSlidingMoves(int x, int y, int i, int j) {

        int tempX = x;
        int tempY = y;

        for (int k = 0; k < board.length; k++) {

            try {

                if (isNotCurrPlayersPiece(tempX + i, tempY + j)) {

                    possibleMoves.add(tempX += i);
                    possibleMoves.add(tempY += j);

                }

                if (board[tempX][tempY] > 0) break;

            } catch (ArrayIndexOutOfBoundsException e) { break; }

        }

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
            if (wCanCastleL) {
                if (board[0][7] == 4 && board[1][7] == 0 && board[2][7] == 0 && board[3][7] == 0) {
                    possibleMoves.add(x - 2);
                    possibleMoves.add(y);
                }
            }
            if (wCanCastleR) {
                if (board[5][7] == 0 && board[6][7] == 0 && board[7][7] == 4) {
                    possibleMoves.add(x + 2);
                    possibleMoves.add(y);
                }
            }
        }

        if (!whiteTurn) {
            if (bCanCastleL) {
                if (board[0][0] == 10 && board[1][0] == 0 && board[2][0] == 0 && board[3][0] == 0) {
                    possibleMoves.add(x - 2);
                    possibleMoves.add(y);
                }
            }
            if (bCanCastleR) {
                if (board[5][0] == 0 && board[6][0] == 0 && board[7][0] == 10) {
                    possibleMoves.add(x + 2);
                    possibleMoves.add(y);
                }
            }
        }

    }

    public void setWCanCastleL(boolean wCanCastleL) {this.wCanCastleL = wCanCastleL;}
    public void setWCanCastleR(boolean wCanCastleR) {this.wCanCastleR = wCanCastleR;}
    public void setBCanCastleL(boolean bCanCastleL) {this.bCanCastleL = bCanCastleL;}
    public void setBCanCastleR(boolean bCanCastleR) {this.bCanCastleR = bCanCastleR;}
    public void setBoard(int[][] board) {this.board = board;}

    public ArrayList<Integer> getPossibleMoves() {return possibleMoves;}

}
