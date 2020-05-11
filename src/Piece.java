import java.util.ArrayList;

import javax.swing.Icon;


public abstract class Piece implements Cloneable {
	protected ArrayList<BoardSquare> controlling;
	protected int[][] moves;
	public static final int 
			PAWN = 100,
			KNIGHT = 320,
			BISHOP = 325,
			ROOK = 500,
			QUEEN = 975,
			KING = 32767;
	protected int value; // value of the piece
	protected boolean whitePiece;
	protected Icon image;
	protected boolean firstMove;
	
	public Piece(boolean white) {
		whitePiece = white;
		controlling = new ArrayList<BoardSquare>();
		firstMove = true;
	}
	
	protected void setControl(ArrayList<BoardSquare> contList) {
		controlling = contList;
	}
	
	public ArrayList<BoardSquare> getControl() {
		return controlling;
	}
	
	public boolean hasMove(BoardSquare check) {
		if(controlling == null)
			return false;
		return controlling.contains(check);
	}
	
	public boolean hasMoved() {
		return !firstMove;
	}
	
	@Override
	abstract public Object clone();
	
	public boolean isWhite() {
		return whitePiece;
	}
	
	public Icon getIcon() {
		return image;
	}
	
	/*
	 * Returns 0 if it has same value, positive if .this has greater value than compare
	 * and negative if compare is worth more than .this.value
	 */
	public int compareTo(Piece compare) {
		return Math.abs(this.getValue()) - Math.abs(compare.getValue());
	}
	
	public int[][] getMoves() {
		return moves;
	}
	
	public int getValue() {
		return value;
	}
	
	public void moved() {
		firstMove = false;
	}
}
