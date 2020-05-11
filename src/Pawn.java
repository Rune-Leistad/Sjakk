import javax.swing.ImageIcon;


public class Pawn extends Piece {
	
	public Pawn(boolean white) {
		super(white);
		
		if(whitePiece){
			image = new ImageIcon("G://Programmer/Chess/src/Icons/WhitePawn.png");
			value = 100;
			int[][] temp = {{-2, 0},{-1, -1},{-1, 1},{-1, 0}};
			moves = temp;
		}
		else {
			image = new ImageIcon("G://Programmer/Chess/src/Icons/BlackPawn.png");
			value = -100;
			int[][] temp = {{2, 0},{1, -1},{1, 1},{1, 0}};
			moves = temp;
			
		}
	}
	
	@Override
	public Object clone() {
		Pawn pawnClone = new Pawn(whitePiece);
		
		return pawnClone;
	}
}
