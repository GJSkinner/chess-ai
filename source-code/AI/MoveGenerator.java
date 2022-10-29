import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MoveGenerator {

    private RuleBook ruleBook;
    public int[][] board;

    public int oldXPos;
    public int oldYPos;
    public int curXPos;
    public int curYPos;

    public int[][] minimaxTest(RuleBook ruleBook, int[][] board) {

        Tree tree = new Tree();
        tree.setRuleBook(ruleBook);
        tree.minimax(board, 3, -1000, 1000, false);
        return tree.getBestBoard();

    }

    public void selectRandomPiece(RuleBook ruleBook, int[][] board) {

        this.ruleBook = ruleBook;
        this.board = board;

        ArrayList<ArrayList<Integer>> pieces = new ArrayList<>();

        for (int x = 0; x < board.length; x++) {       // sets all squares on board to zero
            for (int y = 0; y < board[0].length; y++) {

                if (board[x][y] > 6) {

                    ArrayList<Integer> piece = new ArrayList<>();
                    piece.add(x);
                    piece.add(y);
                    pieces.add(piece);

                }

            }
        }

        int randomNum = ThreadLocalRandom.current().nextInt(0, pieces.size());
        selectRandomMove(pieces.get(randomNum).get(0), pieces.get(randomNum).get(1));

    }

    public void selectRandomMove(int x, int y) {

        ruleBook.setBoard(board);
        ruleBook.findMoves(x, y, false);
        ArrayList<Integer> highlights = ruleBook.highlightSquares(x, y);

        if (highlights.size() > 0) {

            int randomNum = ThreadLocalRandom.current().nextInt(0, highlights.size());
            if (randomNum % 2 != 0) randomNum--;

            oldXPos = x;
            oldYPos = y;
            curXPos = highlights.get(randomNum);
            curYPos = highlights.get(randomNum + 1);

        } else {

            selectRandomPiece(ruleBook, board);
        }

    }

    public int getCurXPos() {return curXPos;}
    public int getCurYPos() {return curYPos;}
    public int getOldXPos() {return oldXPos;}
    public int getOldYPos() {return oldYPos;}

}
