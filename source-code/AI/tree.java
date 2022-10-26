import java.util.ArrayList;

public class Tree {

    StaticValue staticValue = new StaticValue();

    private RuleBook ruleBook;
    private Square[][] board;
    private ArrayList<Node> nodes;
    private int depth = 2;
    private int branchingFactor = 1;
    private boolean isWhiteTurn;

    public void constructTree() {

        nodes = new ArrayList<>();
        nodes.add(constructNode(board, new Node(), new int[4]));
        int parentNodeIndex = 0;
        depth--;

        while (depth > 0) {
            while (branchingFactor > 0) {

                Node parentNode = nodes.get(parentNodeIndex);
                int branches = 0;

                for (int x = 0; x < board.length; x++) {                                                                      // for every piece on the board
                    for (int y = 0; y < board[0].length; y++) {

                        if (isCurrPlayersPiece(board, x, y)) {                                                                 // if that piece belongs to the current player

                            ruleBook.setBoard(board);                                                                         // find all the moves that piece can currently make
                            ruleBook.findMoves(x, y, isWhiteTurn);
                            ArrayList<Integer> highlights = ruleBook.highlightSquares(x, y);

                            for (int i = 0; i < highlights.size(); i += 2) {                                                  // simulate that move

                                int tempPiece = board[highlights.get(i)][highlights.get(i + 1)].getPiece();
                                board[highlights.get(i)][highlights.get(i + 1)].setPiece(board[x][y].getPiece());
                                board[x][y].setPiece(0);

                                int[] move = {x, y, highlights.get(i), highlights.get(i + 1)};
                                nodes.add(constructNode(board, parentNode, move));                                                  // and save the result in the form of a node
                                branches++;

                                board[x][y].setPiece(board[highlights.get(i)][highlights.get(i + 1)].getPiece());
                                board[highlights.get(i)][highlights.get(i + 1)].setPiece(tempPiece);

                            }

                        }

                    }
                }

                parentNode.setBranches(branches);
                parentNodeIndex++;
                branchingFactor--;

            }

            branchingFactor = nodes.get(parentNodeIndex).getBranches();
            isWhiteTurn = !isWhiteTurn;
            depth--;

        }

    }

    public Node constructNode(Square[][] board, Node parentNode, int[] move) {

        Node node = new Node();
        node.setDepth(depth);
        node.setBoardLayout(board);
        staticValue.master(board);
        node.setStaticValue(staticValue.getStaticValue());
        node.setParent(parentNode);
        node.setMove(move);
        return node;

    }

    public boolean isCurrPlayersPiece(Square[][] board, int curXPos, int curYPos) {

        int currPieces = isWhiteTurn ? 6 : 12;
        return board[curXPos][curYPos].getPiece() <= currPieces && board[curXPos][curYPos].getPiece() > currPieces - 6;

    }

    public void setRuleBook(RuleBook ruleBook) {this.ruleBook = ruleBook;}
    public void setBoard(Square[][] board) {this.board = board;}

    public ArrayList<Node> getNodes() {return nodes;}

}
