import javax.swing.ImageIcon;


public class King extends Piece {

	public King(boolean white) {
		super(white);
		if(whitePiece) {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/WhiteKing.png");
			value = 32767;
		}
		else {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/BlackKing.png");
			value = -32767;
		}
		
		int[][] temp = {{0, 1},{0, -1},{1, 0},{-1, 0},{1, 1},{-1, -1},{1, -1},{-1, 1}};
		moves = temp;
	}

	@Override
	public Object clone() {
		King kingClone = new King(whitePiece);
		return kingClone;
	}
}
