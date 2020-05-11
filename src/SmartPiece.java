
public class SmartPiece extends Piece {
	private int moves;
	public SmartPiece(BoardSquare startBoardSquare, String name) {
		super(startBoardSquare);
		moves = 0;
	}
	
	public int getMoves() {
		return moves;
	}
}
