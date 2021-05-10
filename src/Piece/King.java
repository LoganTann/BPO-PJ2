package Piece;

import Chessboard.vect2D;
import Chessboard.Chessboard;

public class King extends Piece{
	private static final int ALLOWED_NBR_OF_STEPS = 1;

	public King(Boolean isWhite) {
		super("R", isWhite);
	}

	public void play(Chessboard chessboard, vect2D originCoord, vect2D newCoord, Boolean isWhite) throws BadMoveException {
		Piece target = chessboard.getPiece(newCoord.y, newCoord.x);
		if (isWhite != this.isWhite) {
			throw new BadMoveException("Pion adverse");
		}

		if (target.isWhite == this.isWhite && !(target instanceof EmptyPiece))
			throw new BadMoveException("Le roi ne peut pas être cannibale...");
		if (!isValidMove(originCoord, newCoord)) {
			throw new BadMoveException("Mouvement impossible");
		}
		chessboard.setPiece(originCoord.y, originCoord.x, new EmptyPiece());
		chessboard.setPiece(newCoord.y, newCoord.x, this);
	}

	/**
	 * @See Piece.Piece.clone()
	 */
	public King clone() {
		return new King(this.isWhite);
	}

	/**
	 * voir la doc de Piece.isValidMove.
	 * Le roi se déplace d'une case autour de lui
	 * @param currentPos le point source
	 * @param target le point destination
	 * @return true si le déplacement entre currentPos et target est un déplacement d'exactement 1 case
	 */
	@Override
	public boolean isValidMove(vect2D currentPos, vect2D target) {
		return isMovingExactlyWithOneStep(currentPos, target);
	}

	/**
	 * Vérifie si le déplacement entre deux points est un déplacement en diagonale
	 * @param currentPos la coordonée absolue (sous forme de vecteur) du point source
	 * @param target la coordonée absolue (sous forme de vecteur) du point de destination
	 * @return true si le déplacement entre currentPos et target est un déplacement d'exactement 1 case
	 */
	public static boolean isMovingExactlyWithOneStep(vect2D currentPos, vect2D target) {
		vect2D relative_move = isValidMove_computeTranslation(currentPos, target);
		if (vect2D.isEqual(relative_move, vect2D.INVALID_VECT)) {
			return false; // n'a pas pu calculer ou préconditions non résolues
		}
		relative_move.x = Math.abs(relative_move.x);
		relative_move.y = Math.abs(relative_move.y);
		return relative_move.x <= ALLOWED_NBR_OF_STEPS
			&& relative_move.y <= ALLOWED_NBR_OF_STEPS;
	}
}
