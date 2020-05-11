import javax.swing.ImageIcon;


public class Bishop extends Piece {

	public Bishop(boolean white) {
		super(white);
		if(whitePiece) {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/WhiteBishop.png");
			value = 325;
		}
		else {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/BlackBishop.png");
			value = -325;
		}
		
		int[][] temp = {{1, 1},{-1, -1},{1, -1},{-1, 1}};
		moves = temp;
	}
	
	@Override
	public Object clone() {
		Bishop bishopClone = new Bishop(whitePiece);
		return bishopClone;
	}

}
