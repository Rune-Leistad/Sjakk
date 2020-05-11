import javax.swing.ImageIcon;


public class Knight extends Piece {

	public Knight(boolean white) {
		super(white);
		if(whitePiece) {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/WhiteKnight.png");
			value = 320;
		}
		else {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/BlackKnight.png");
			value = -320;
		}
		int[][] tempMoves = {{-1, -2}, {1, -2}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
		moves = tempMoves;
	}

	@Override
	public Object clone() {
		Knight knightClone = new Knight(whitePiece);
		return knightClone;
	}
}
