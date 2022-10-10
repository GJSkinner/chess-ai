import java.util.ArrayList;

public class RuleBook {

    public boolean wkingMoved = false;
    public boolean wlRookMoved = false;
    public boolean wrRookMoved = false;
    public boolean bkingMoved = false;
    public boolean blRookMoved = false;
    public boolean brRookMoved = false;

    boolean whiteTurn;

    Square[][] board;
    ArrayList<Integer> possibleMoves;

    public void findMoves(int x, int y, boolean isWhiteTurn) {

        whiteTurn = isWhiteTurn;
        board = ChessAlgorithm.getBoard();
        possibleMoves = new ArrayList<>();

        int white = 0;
        if (whiteTurn){
            white = 1;
        }

        if (x == 100 && y == 100) {

        } else if (board[x][y].getPiece() == 1) {
            findPawnMoves(x, y, white);
        } else if (board[x][y].getPiece() == 2) {
            findKnightMoves(x, y, white);
        } else if (board[x][y].getPiece() == 3) {
            findBishopMoves(x, y, white);
        } else if (board[x][y].getPiece() == 4) {
            findRookMoves(x, y, white);
        } else if (board[x][y].getPiece() == 5) {
            findBishopMoves(x, y, white);
            findRookMoves(x, y, white);
        } else if (board[x][y].getPiece() == 6) {
            findKingMoves(x, y, white);
        }

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

    public void findPawnMoves(int x, int y, int white) {

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
            if (board[calculator(x, 1, false)][calculator(y, 1, true)].getIsWhite() != white) {
                if (board[calculator(x, 1, false)][calculator(y, 1, true)].getPiece() > 0) {
                    possibleMoves.add(calculator(x, 1, false)); // take on right
                    possibleMoves.add(calculator(y, 1, true));
                }
            }
        }
        if (whiteTurn && x > 0 || !whiteTurn && x < 7) {
            if (board[calculator(x, 1, true)][calculator(y, 1, true)].getIsWhite() != white) {
                if (board[calculator(x, 1, true)][calculator(y, 1, true)].getPiece() > 0) {
                    possibleMoves.add(calculator(x, 1, true)); // take on left
                    possibleMoves.add(calculator(y, 1, true));
                }
            }
        }
        if (whiteTurn && x < 7 || !whiteTurn && x > 0) {
            if (board[calculator(x, 1, false)][y].getPassable() && board[calculator(x, 1, false)][y].getIsWhite() != white) {
                possibleMoves.add(calculator(x, 1, false));
                possibleMoves.add(calculator(y, 1, true));
            }
        }
        if (whiteTurn && x > 0 || !whiteTurn && x < 7) {
            if (board[calculator(x, 1, true)][y].getPassable() && board[calculator(x, 1, true)][y].getIsWhite() != white) {
                possibleMoves.add(calculator(x, 1, true));
                possibleMoves.add(calculator(y, 1, true));
            }
        }

    }

    public void findKnightMoves(int x, int y, int white) {

        for (int i = 2; i > -3; i--) {
            for (int j = 2; j > -3; j--) {

                if(Math.abs(i) == 2 ^ Math.abs(j) == 2) {
                    if (i != 0 && j != 0) {

                        try {
                            if (board[x + i][y + j].getIsWhite() != white) {
                                possibleMoves.add(x + i);
                                possibleMoves.add(y + j);
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {}

                    }
                }

            }
        }

    }

    public void findRookMoves(int x, int y, int white) {

        for (int i = 1; i < y + 1; i++) {  // up
            if (board[x][y - i].getPiece() == 0) {
                possibleMoves.add(x);
                possibleMoves.add(y - i);
            } else if (board[x][y - i].getIsWhite() != white) {
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
            } else if (board[x][y + i].getIsWhite() != white) {
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
            } else if (board[x + i][y].getIsWhite() != white) {
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
            } else if (board[x - i][y].getIsWhite() != white) {
                possibleMoves.add(x - i);
                possibleMoves.add(y);
                break;
            } else {
                break;
            }
        }

    }

    public void findBishopMoves(int x, int y, int white) {

        if ((board.length - x) < (board[0].length - y)) {
            for (int i = 1; i < board.length - x; i++) {
                if (diagonalDownRight(x, y, white, i)) break;
            }
        } else {
            for (int i = 1; i < board[0].length - y; i++) {
                if (diagonalDownRight(x, y, white, i)) break;
            }
        }

        if (y < (x + 1)) {
            for (int i = 1; i < y + 1; i++) {   // up/left
                if (board[x - i][y - i].getPiece() == 0) {
                    possibleMoves.add(x - i);
                    possibleMoves.add(y - i);
                } else if (board[x - i][y - i].getIsWhite() != white) {
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
                } else if (board[x - i][y - i].getIsWhite() != white) {
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
                if (diagonalUpRight(x, y, white, i)) break;
            }
        } else {
            for (int i = 1; i < board.length - x; i++) {
                if (diagonalUpRight(x, y, white, i)) break;
            }
        }

        if (x < (board[0].length - y)) {
            for (int i = 1; i < x + 1; i++) {
                if (diagonalDownLeft(x, y, white, i)) break;
            }
        } else {
            for (int i = 1; i < board[0].length - y; i++) {
                if (diagonalDownLeft(x, y, white, i)) break;
            }
        }

    }

    public boolean diagonalDownLeft(int x, int y, int white, int i) {

        if (board[x - i][y + i].getPiece() == 0) {
            possibleMoves.add(x - i);
            possibleMoves.add(y + i);
        } else if (board[x - i][y + i].getIsWhite() != white) {
            possibleMoves.add(x - i);
            possibleMoves.add(y + i);
            return true;
        } else {
            return true;
        }
        return false;

    }

    public boolean diagonalUpRight(int x, int y, int white, int i) {

        if (board[x + i][y - i].getPiece() == 0) {
            possibleMoves.add(x + i);
            possibleMoves.add(y - i);
        } else if (board[x + i][y - i].getIsWhite() != white) {
            possibleMoves.add(x + i);
            possibleMoves.add(y - i);
            return true;
        } else {
            return true;
        }
        return false;

    }

    public boolean diagonalDownRight(int x, int y, int white, int i) {

        if (board[x + i][y + i].getPiece() == 0) {
            possibleMoves.add(x + i);
            possibleMoves.add(y + i);
        } else if (board[x + i][y + i].getIsWhite() != white) {
            possibleMoves.add(x + i);
            possibleMoves.add(y + i);
            return true;
        } else {
            return true;
        }
        return false;

    }

    public void findKingMoves(int x, int y, int white) {

        for (int i = 1; i > -2; i--) {
            for (int j = 1; j > -2; j--) {
                if(!(i == 0 && j == 0)) {
                    try {
                        if (board[x + i][y + j].getIsWhite() != white) {
                            possibleMoves.add(x + i);
                            possibleMoves.add(y + j);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                }
            }
        }

        if (white == 1) {
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

        if (white == 0) {
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

    public ArrayList<Integer> getPossibleMoves() {return possibleMoves;}

}
