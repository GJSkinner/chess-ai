public class Square {

    private boolean isSelected;
    private boolean isHighlighted;
    private int piece;
    private int isWhite;
    private boolean isPassable;

    public Square() {
        isSelected = false;
        isHighlighted = false;
        piece = 0;
        isWhite = 2;
        isPassable = false;
    }

    public void setPiece(int piece) {this.piece = piece;}

    public void setSelected(boolean selected) {this.isSelected = selected;}

    public void setHighlighted(boolean highlighted) {isHighlighted = highlighted;}

    public void setIsWhite(int isWhite) {this.isWhite = isWhite;}

    public void setPassable(boolean passable) {isPassable = passable;}

    public int getPiece() {return piece;}

    public boolean getSelected() {return isSelected;}

    public boolean getHighlighted() {return isHighlighted;}

    public int getIsWhite() {return isWhite;}

    public boolean getPassable() {return isPassable;}
}
