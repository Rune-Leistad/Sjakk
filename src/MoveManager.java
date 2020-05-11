import java.util.ArrayList;


public class MoveManager {
	
	private boolean whiteNext, blackInCheck, whiteInCheck;
	private BoardSquare markedSquare;
	public final int X = 0;
	public final int Y = 1;
	private Piece enPassant;
	
	public MoveManager(Board b) {
		whiteNext = true;
		blackInCheck = false;
		whiteInCheck = false;
		markedSquare = null;
		enPassant = null; // less than 0 means not available
		initMoves(b);
	}
	
	public void initMoves(Board b) {
		setPieceMoves(b, true, false);
	}
	
	private void move(Board b, BoardSquare from, BoardSquare to) {
		Piece movingPiece = from.getPiece();
		Piece takenPiece = to.getPiece();
		int[] fp = from.getPosition();
		int[] tp = to.getPosition();
		if(takenPiece != null) {
			b.killPiece(takenPiece, takenPiece.isWhite());
		}
		else if(movingPiece instanceof Pawn && !movingPiece.hasMoved()) {
			
			if(Math.abs(fp[X] - tp[X]) == 2)
				enPassant = (Pawn)movingPiece;
			else
				enPassant = null;
		}
		else if(enPassant != null && movingPiece instanceof Pawn && tookEnPassant(from, to)) {
			b.killPiece(enPassant, enPassant.isWhite());
			enPassant = null;
			
		}
		else if(movingPiece instanceof King) { // checking castling
			if(Math.abs(fp[Y] - tp[Y]) == 2) {
				if(fp[Y] - tp[Y] < 0) {
					b.pieceMove(b.getSquare(fp[X], 7).getPiece(), b.getSquare(tp[X], tp[Y]-1));
				}
				else {
					b.pieceMove(b.getSquare(fp[X], 0).getPiece(), b.getSquare(tp[X], tp[Y]+1));
				}
			}
		}
		else
			enPassant = null;
			
		
		b.pieceMove(from.getPiece(), to);
		whiteNext = !whiteNext;
		movingPiece.moved();
	}
	
	private boolean tookEnPassant(BoardSquare from, BoardSquare to) {		
		if(to.getPiece() == null) {
			int[] fp = from.getPosition();
			int[] tp = to.getPosition();
			if(fp[Y] - tp[Y] != 0)
				return true;
		}
		return false;
	}
	
	public void legalMove(Board b, BoardSquare square) {
		
		if(markedSquare == null){
			if(square.getPiece() != null && whiteNext == square.getPiece().isWhite()) {
				markedSquare = square;
				Piece piece = square.getPiece();
				if(piece != null) {
					ArrayList<BoardSquare> controlling = piece.getControl();
					for(BoardSquare bs: controlling)
						bs.markSquare();
				}
			}
		}
			
		else {
			
			Piece p = markedSquare.getPiece();
			ArrayList<BoardSquare> controlling = p.getControl();
			for(BoardSquare bs: controlling)
				bs.unmarkSquare();
			
			if(p.hasMove(square)) {
				move(b, markedSquare, square);
				setPieceMoves(b, whiteNext, false);
				setPieceMoves(b, !whiteNext, false);
				markedSquare = null;
			}
			else
				markedSquare = null;
			
			

		}
		// Piece has that move in its legal moves -> perform move
	}
	
	private boolean sameTeam(BoardSquare from, BoardSquare to) {
		return from.getPiece().isWhite() == to.getPiece().isWhite();
		
	}
	
	private void setPieceMoves(Board b, boolean white, boolean verifying) {
		Piece[] pieces = b.getPieces(white);
		int num = 0;
		for(int i = 0; i < pieces.length; i++) {
			int pv = Math.abs(pieces[i].getValue());
			if(pv == Piece.PAWN)
				num += findPawnMoves(b, pieces[i], verifying);
			else 
				num += findRBQKKMoves(b, pieces[i], verifying);
		}
		
		// if num == 0 checkmate
	}
	
	private BoardSquare enPassant(Board b, Piece pawn) {
		if(enPassant == null)
			return null; // if null enpassant is unavailable
		else if(pawn.isWhite() == enPassant.isWhite())
			return null;
		
		int[] pp = pawn.getSquare().getPosition();
		int[] epp = enPassant.getSquare().getPosition();
		if(pp[X] == epp[X]) {
			if(pp[Y] == epp[Y] - 1) {
				int[][] moves = pawn.getMoves();
				return b.getSquare(pp[X] + moves[2][X], pp[Y] + moves[2][Y]);
			}
			if(pp[Y] == epp[Y] + 1) {
				int[][] moves = pawn.getMoves();
				return b.getSquare(pp[X] + moves[1][X], pp[Y] + moves[1][Y]);
			}
		}
			
		return null;
	}
	
	private int findPawnMoves(Board b, Piece p, boolean verifying) {
		Pawn pawn = (Pawn)p;
		int[] pos = pawn.getSquare().getPosition();
		int[][] moves = pawn.getMoves();
		ArrayList<BoardSquare> al = new ArrayList<BoardSquare>();
		
		BoardSquare possibleMove = enPassant(b, p);
		if(possibleMove != null) {
			if(!verifying) { // Preventing eternal loop
				boolean confirmedMove = verifyMove(b, pawn, possibleMove);
				if(confirmedMove)
					al.add(possibleMove);
			}
			else {
				al.add(possibleMove);
			}
		}
			
		possibleMove = b.getSquare(pos[X] + moves[3][X], pos[Y] + moves[3][Y]);
		if(possibleMove.getPiece() == null) { // push the pawn
			if(!verifying) { // Preventing eternal loop
				boolean confirmedMove = verifyMove(b, pawn, possibleMove);
				if(confirmedMove)
					al.add(possibleMove);
			}
			else {
				al.add(possibleMove);
			}
		}
		
		possibleMove = b.getSquare(pos[X] + moves[2][X], pos[Y] + moves[2][Y]);
		if(possibleMove != null && possibleMove.getPiece() != null) {
			if(!sameTeam(possibleMove, p.getSquare())){
				if(!verifying) { // Preventing eternal loop
					boolean confirmedMove = verifyMove(b, pawn, possibleMove);
					if(confirmedMove)
						al.add(possibleMove);
				}
				else {
					al.add(possibleMove);
				}
			}
		}
		
		possibleMove = b.getSquare(pos[X] + moves[1][X], pos[Y] + moves[1][Y]);
		if(possibleMove != null && possibleMove.getPiece() != null) {
			if(!sameTeam(possibleMove, p.getSquare())) {
				if(!verifying) { // Preventing eternal loop
					boolean confirmedMove = verifyMove(b, pawn, possibleMove);
					if(confirmedMove)
						al.add(possibleMove);// not occupied by the same team
				}
				else {
					al.add(possibleMove);
				} 
			}
		}
		
		possibleMove = b.getSquare(pos[X] + moves[0][X], pos[Y] + moves[0][Y]);
		if(!pawn.hasMoved()) {
			BoardSquare first = b.getSquare(pos[X] + moves[3][X], pos[Y] + moves[3][Y]);
			if(al.contains(first)) {
				if(possibleMove.getPiece() == null) {
					if(!verifying) { // Preventing eternal loop
						boolean confirmedMove = verifyMove(b, pawn, possibleMove);
						if(confirmedMove)
							al.add(possibleMove);// not occupied by the same team
					}
					else {
						al.add(possibleMove);
					}
				}
			}
		}
		
		p.setControl(al);
		return al.size();
	}
	
	private int findRBQKKMoves(Board b, Piece p, boolean verifying) {
		int[][] moves = p.getMoves();
		int[] pos = p.getSquare().getPosition();
		ArrayList<BoardSquare> al = new ArrayList<BoardSquare>();
		for(int i = 0; i < moves.length; i++) {
			boolean moreMoves = true;
			int counter = 1;
			while(moreMoves){
				BoardSquare possibleMove = b.getSquare(pos[X] + (moves[i][X] * counter), pos[Y] + (moves[i][Y] * counter));
				if(possibleMove != null ) { // Is a square on the board
					if(possibleMove.getPiece() != null) {  // square has a piece on it
						if(!sameTeam(p.getSquare(), possibleMove)) {
							if(!verifying) { // Preventing eternal loop
								boolean confirmedMove = verifyMove(b, p, possibleMove);
								if(confirmedMove)
									al.add(possibleMove);// not occupied by the same team
							}
							else {
								al.add(possibleMove);
							}
							
						}
						
						moreMoves = false;
					}
					else {
						if(!verifying) { // Preventing eternal loop
							boolean confirmedMove = verifyMove(b, p, possibleMove);
							if(confirmedMove)
								al.add(possibleMove);// not occupied by the same team
						}
						else {
							al.add(possibleMove);
						}
					}
				}
				else
					moreMoves = false;
				
				if(Math.abs(p.getValue()) == Piece.KING || Math.abs(p.getValue()) == Piece.KNIGHT )
					moreMoves = false;
				counter++;
			}
		}
		
		if(Math.abs(p.getValue()) == Piece.KING) {
			BoardSquare kingSide = allowCastling(b, p, true);
			BoardSquare queenSide = allowCastling(b, p, false);
			if(kingSide != null)
				al.add(kingSide);
			if(queenSide != null)
				al.add(queenSide);
		}
			
		
		p.setControl(al);
		return al.size();
	}
	
	private boolean opponentControllingSquare(Board b, BoardSquare bs, boolean currentPlayer) {
		Piece[] opponent = b.getPieces(!currentPlayer);
		for(Piece p : opponent) {
			if(p.hasMove(bs))
				return true;
		}
		
		return false;
	}

	
	private boolean pinned(Board b, Piece p) {
		
		return blackInCheck;
	}
	
	private boolean verifyMove(Board b, Piece p, BoardSquare bs) {
		if(kingInCheckAfter(b,p.getSquare(), bs))
			return false;
		return true;
	}
	
	private boolean inCheck(Board b, boolean white) {
		
		
		
		Piece[] pieces = b.getPieces(white);
		for(int i = 0; i < pieces.length; i++) {
			if(pieces[i] instanceof King) {
				return opponentControllingSquare(b, pieces[i].getSquare(), white);
			}
		}
		return false;
	}
	
	private boolean kingInCheckAfter(Board b, BoardSquare from, BoardSquare to) {
		Board clone = (Board)b.clone();
		Piece fromPiece = clone.getSquare(from).getPiece();
		move(clone, clone.getSquare(from), clone.getSquare(to));
		setPieceMoves(clone, !fromPiece.isWhite(), true);
		Piece[] whites = clone.getPieces(fromPiece.isWhite());
		for(int i = 0; i < whites.length; i++) {
			if(whites[i] instanceof King)
				return opponentControllingSquare(clone, whites[i].getSquare(), whites[i].isWhite());
		}
		
		return false;
	}
	
	private BoardSquare allowCastling(Board b, Piece p, boolean kingSide) {
		if(p.hasMoved())
			return null;
		
		int[] pos = p.getSquare().getPosition();
		if(kingSide) {
			
			for(int i = pos[Y] + 1; i < Board.SIZE - 1; i++) {
				BoardSquare next = b.getSquare(pos[X], i);
				if(next.getPiece() != null || opponentControllingSquare(b, next, p.isWhite()))
					return null;
			}
			
			
			BoardSquare rook = b.getSquare(pos[X], 7);
			if(rook.getPiece() != null && rook.getPiece() instanceof Rook && rook.getPiece().isWhite() == p.isWhite())
				return b.getSquare(pos[X], pos[Y] + 2);
		}
		else { // queen side castling
			for(int i = pos[Y] - 1; i > 0; i--) {
				BoardSquare next = b.getSquare(pos[X], i);
				if(next.getPiece() != null || opponentControllingSquare(b, next, p.isWhite()))
					return null;
			}
			
			BoardSquare rook = b.getSquare(pos[X], 0);
			if(rook.getPiece() != null && rook.getPiece() instanceof Rook && rook.getPiece().isWhite() == p.isWhite())
				return b.getSquare(pos[X], pos[Y] -2);
		}
		
		return null;
	}
}
