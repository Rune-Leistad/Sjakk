import java.util.ArrayList;


public class Board {
	private BoardSquare[][] board;
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	public final static int SIZE = 8;
	//private ArrayList<Piece> takenPieces;
	
	
	public Board(BoardSquare[][] b, ArrayList<Piece> wp, ArrayList<Piece> bp) {
		board = b;
		whitePieces = wp;
		blackPieces = bp;
		placePieces();
	}
	
	public BoardSquare[][] getBoard() {
		return board;
	}
	
	public int getWhiteTotalValue() {
		int wTot = 0;
		for(Piece p : whitePieces)
			wTot += p.getValue();
		return wTot;
	}
	
	public int getTotalValue(boolean white) {
		int bTot = 0;
		ArrayList<Piece> values;
		if(white)
			values = whitePieces;
		else
			values = blackPieces;
		
		for(Piece p : values) 
			bTot += p.getValue();
		
		return bTot;
	}
	
	public BoardSquare getSquare(BoardSquare bs) {
		int[] pos = bs.getPosition();
		return board[pos[0]][pos[1]];
	}
	
	public BoardSquare getSquare(int x, int y) {
		if(x < SIZE && x >= 0 && y < SIZE && y >= 0)
			return board[x][y];
		else 
			return null;
	}
	
	public Piece[] getPieces(boolean white) {
		Piece[] retArr;
		if(white) {
			retArr = new Piece[whitePieces.size()];
			retArr = whitePieces.toArray(retArr);
			return retArr;
		}
		else {
			retArr = new Piece[blackPieces.size()];
			retArr = blackPieces.toArray(retArr);
			return retArr;
		}
	}
	
	
	
	
	/*
	 * @piece Piece being moved
	 * @to Square piece is being moved to
	 */
	public void pieceMove(Piece piece, BoardSquare to) {
		to.setPiece(piece);
		piece.moved();
	}
	
	public void killPiece(Piece p, boolean white) {
		if(white) {
			whitePieces.remove(p);
		}
		else
			blackPieces.remove(p);
	}
	
	
	private void placePieces() {
		Piece[] pieces = blackPieces.toArray(new Piece[blackPieces.size()]);
		
		for(int i = 0; i < pieces.length; i++)
			pieces[i].getSquare().setPiece(pieces[i]);
		
		pieces = whitePieces.toArray(new Piece[whitePieces.size()]);
		for(int i = 0; i < pieces.length; i++) 
			pieces[i].getSquare().setPiece(pieces[i]);
	}
	
	public String toString() {
		String ret = "";
		for(int i = 0; i < SIZE; i++) {
			for(int y = 0; y < SIZE; y++) {
				ret += String.format("%25s", board[i][y]);
			}
			ret += "\n";
		}
		return ret;
	}
	
	@Override
	public Object clone() {
		BoardSquare[][] boardClone = new BoardSquare[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++) {
			for(int y = 0; y < SIZE; y++) {
				boardClone[i][y] = (BoardSquare)board[i][y].clone();
			}
		}
		ArrayList<Piece> wpClone = (ArrayList<Piece>)whitePieces.clone();
		ArrayList<Piece> bpClone = (ArrayList<Piece>)blackPieces.clone();
		Object clone = new Board(boardClone, wpClone, bpClone);
		return clone;
	}
}
