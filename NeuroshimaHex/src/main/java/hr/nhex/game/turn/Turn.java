package hr.nhex.game.turn;

import hr.nhex.board.controls.MovementControl;
import hr.nhex.game.GamePhase;
import hr.nhex.model.player.Player;

/**
 * Class that represents player's turn in Neuroshima Hex game.
 *
 * @author Luka Ruklić
 *
 */

public class Turn {

	/**
	 * Current player whose turn it is.
	 */
	private Player currentPlayer;
	/**
	 * Current phase of the turn.
	 */
	private TurnPhase turnPhase;
	/**
	 * Tiles that already moved in the current turn.
	 */
	private MovementControl movementControl = new MovementControl();

	public Turn(Player firstPlayer) {
		this.currentPlayer = firstPlayer;
	}

	public TurnPhase getTurnPhase() {
		return turnPhase;
	}

	public void setTurnPhase(TurnPhase turnPhase) {
		this.turnPhase = turnPhase;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void newPlayerTurn(Player currentPlayer, GamePhase gamePhase) {
		this.currentPlayer = currentPlayer;
		this.movementControl.clear();

		if (gamePhase == GamePhase.PLAYER_TURN) {
			this.turnPhase = TurnPhase.DISCARD_PHASE;
		} else {
			this.turnPhase = TurnPhase.TILES_DRAWN;
		}
	}

	public MovementControl getMovementControl() {
		return movementControl;
	}

}
