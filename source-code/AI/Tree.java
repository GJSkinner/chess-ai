import java.util.ArrayList;
import java.util.Arrays;

public class Tree {

    StaticValue staticValue = new StaticValue();

    private RuleBook ruleBook;
    private int[][] bestBoard;

    public int minimax(int[][] board, int depth, int alpha, int beta, boolean isWhiteTurn) {

        if (depth == 0) return staticValue.getStaticValue(board);

        ArrayList<int[][]> children = getChildren(board, isWhiteTurn);

        if (isWhiteTurn) {

            int maxEval = -1000;

            for (int[][] child : children) {

                int evalBoard = minimax(child, depth - 1, alpha, beta, false);
                maxEval =  Math.max(maxEval, evalBoard);
                alpha = Math.max(alpha, evalBoard);
                if (beta <= alpha) break;

            }

            return maxEval;

        } else {

            int minEval = 1000;
            int[][] minEvalBoard = new int[8][8];

            for (int[][] child : children) {

                int evalBoard = minimax(child, depth - 1, alpha, beta, true);

                if (evalBoard < minEval) {
                    minEval = evalBoard;
                    minEvalBoard = child.clone();
                }

                beta = Math.min(beta, evalBoard);
                if (beta <= alpha) break;

            }

            bestBoard = minEvalBoard;
            return minEval;

        }

    }

    public ArrayList<int[][]> getChildren(int[][] board, boolean isWhiteTurn) {

        ArrayList<int[][]> children = new ArrayList<>();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {

                if (isCurrPlayersPiece(board, isWhiteTurn, x, y)) {

                    ruleBook.setBoard(board);
                    ruleBook.findMoves(x, y, isWhiteTurn);
                    ArrayList<Integer> highlights = ruleBook.highlightSquares(x, y);

                    for (int i = 0; i < highlights.size(); i += 2) {

                        int[][] newBoard = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);

                        newBoard[highlights.get(i)][highlights.get(i + 1)] = newBoard[x][y];
                        newBoard[x][y] = 0;

                        children.add(newBoard);

                    }

                }

            }
        }

        return children;

    }

    public boolean isCurrPlayersPiece(int[][] board, boolean isWhiteTurn, int curXPos, int curYPos) {

        int currPieces = isWhiteTurn ? 6 : 12;
        return board[curXPos][curYPos] <= currPieces && board[curXPos][curYPos] > currPieces - 6;

    }

    public void setRuleBook(RuleBook ruleBook) {this.ruleBook = ruleBook;}

    public int[][] getBestBoard() {return bestBoard;}

}
