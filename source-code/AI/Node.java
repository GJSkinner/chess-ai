public class Node {

    private Square[][] boardLayout;
    private int staticValue;
    private int depth;
    private int branches;
    private Node parent;
    private int[] move;

    public void setBoardLayout(Square[][] boardLayout) {this.boardLayout = boardLayout;}
    public void setStaticValue(int value) {this.staticValue = value;}
    public void setDepth(int depth) {this.depth = depth;}
    public void setBranches(int branches) {this.branches = branches;}
    public void setParent(Node parent) {this.parent = parent;}
    public void setMove(int[] move) {this.move = move;}

    public Square[][] getBoardLayout() {return boardLayout;}
    public int getStaticValue() {return staticValue;}
    public int getDepth() {return depth;}
    public int getBranches() {return branches;}
    public Node getParent() {return parent;}
    public int[] getMove() {return move;}

}
