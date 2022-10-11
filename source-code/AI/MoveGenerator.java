import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MoveGenerator {

    RuleBook ruleBook = new RuleBook();

    public Square[][] board;

    public int oldXPos;
    public int oldYPos;
    public int curXPos;
    public int curYPos;

    public void selectRandomPiece(Square[][] board) {

        this.board = board;

        List<List<Integer>> pieces = new ArrayList<>();

        for (int x = 0; x < board.length; x++) {       // sets all squares on board to zero
            for (int y = 0; y < board[0].length; y++) {

                if (board[x][y].getIsWhite() == 0) {

                    List<Integer> piece = new ArrayList<>();
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

        ArrayList<Integer> possibleMoves;

        ruleBook.findMoves(x, y, false);
        possibleMoves = ruleBook.getPossibleMoves();

        if (possibleMoves.size() == 0) selectRandomPiece(board);
        else {

            int randomNum = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
            if (randomNum % 2 != 0) randomNum--;

            oldXPos = x;
            oldYPos = y;
            curXPos = possibleMoves.get(randomNum);
            curYPos = possibleMoves.get(randomNum + 1);

        }

    }

    public int getCurXPos() {return curXPos;}
    public int getCurYPos() {return curYPos;}
    public int getOldXPos() {return oldXPos;}
    public int getOldYPos() {return oldYPos;}

}
