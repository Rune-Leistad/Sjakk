import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;


public class BoardSquare extends JLabel implements Cloneable {

	private static final long serialVersionUID = -5678968104259069948L;
	private Piece pieceOnSquare;
	private int x,y;
	private Color normalBackgroundColor;
	
	public BoardSquare(int xpos, int ypos) {
		pieceOnSquare = null;
		x = xpos;
		y = ypos;
	}
	
	public void markSquare() {
		this.setBorder(BorderFactory.createLineBorder(new Color(0,200,0),3));
		this.setBackground(Color.GRAY.brighter());
	}
	
	public void setBG(Color bgColor) {
		setBackground(bgColor);
		normalBackgroundColor = bgColor;
	}
	
	public int[] getPosition() {
		int[] pos = new int[2];
		pos[0] = x;
		pos[1] = y;
		return pos;
	}
	
	public boolean equals(BoardSquare bs) {
		if(bs.x == this.x && bs.y == this.y)
			return true;
		else
			return false;
	}
	
	public void unmarkSquare() {
		this.setBackground(normalBackgroundColor);
		this.setBorder(null);
	}
	
	public boolean hasPiece() {
		return pieceOnSquare != null;
	}
	
	public Piece getPiece() {
		return pieceOnSquare;
	}
	
	public void setPiece(Piece newPiece) {
		pieceOnSquare = newPiece;
		if(newPiece != null)
			this.setIcon(newPiece.getIcon());
		else
			this.setIcon(null);
	}
	
	public String toString() {
		
		return x + ", " + y + ": " + pieceOnSquare;
	}
	
	@Override
	public Object clone() {
		BoardSquare clone = new BoardSquare(x, y);
		if(pieceOnSquare != null){
			Piece pieceClone = (Piece)pieceOnSquare.clone();
			clone.setPiece(pieceClone);
		}
		
		return clone;
	}
}
