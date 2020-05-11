import javax.swing.ImageIcon;


public class Rook extends Piece {
	
	public Rook(boolean white) {
		super(white);
		if(whitePiece) {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/WhiteRook.png");
			value = 500;
		}
		else {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/BlackRook.png");
			value = -500;
		}
		int[][] temp = {{0, 1},{0, -1},{1, 0},{-1, 0}};
		moves = temp;
	}
	
	@Override
	public Object clone() {
		Rook rookClone = new Rook(whitePiece);
		return rookClone;
	}
}
