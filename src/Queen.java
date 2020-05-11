import javax.swing.ImageIcon;


public class Queen extends Piece{

	public Queen(boolean white) {
		super(white);
		if(whitePiece) {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/WhiteQueen.png");
			value = 975;
		}
		else {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/BlackQueen.png");
			value = -975;
		}
		
		int[][] temp = {{0, 1},{0, -1},{1, 0},{-1, 0},{1, 1},{-1, -1},{1, -1},{-1, 1}};
		moves = temp;
	}
	
	@Override
	public Object clone() {
		Queen queenClone = new Queen(whitePiece);
		return queenClone;
	}
}
