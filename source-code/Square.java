public class Square {

    private boolean isSelected;
    private boolean isHighlighted;
    private int piece;
    private boolean isPassable;

    public void setPiece(int piece) {this.piece = piece;}

    public void setSelected(boolean selected) {this.isSelected = selected;}

    public void setHighlighted(boolean highlighted) {isHighlighted = highlighted;}

    public void setPassable(boolean passable) {isPassable = passable;}

    public int getPiece() {return piece;}

    public boolean getSelected() {return isSelected;}

    public boolean getHighlighted() {return isHighlighted;}

    public boolean getPassable() {return isPassable;}

}
