package hr.nhex.game.turn;

import hr.nhex.board.controls.MovementControl;
import hr.nhex.game.GamePhase;
import hr.nhex.model.player.Player;

/**
 * Class that represents player's turn in Neuroshima Hex game.
 *
 * @author Luka Ruklic
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
	private TurnPhase turnPhase = TurnPhase.TILES_DRAWN;
	/**
	 * Tiles that already moved in the current turn.
	 */
	private MovementControl movementControl = new MovementControl();
	/**
	 * Constructor.
	 *
	 * @param firstPlayer player who has first turn
	 */
	public Turn(Player firstPlayer) {
		this.currentPlayer = firstPlayer;
	}

	/**
	 * Method that sets given player as current player and based on given game phase sets current turn phase.
	 *
	 * @param currentPlayer player whose turn it is
	 * @param gamePhase current game phase
	 */
	public void newPlayerTurn(Player currentPlayer, GamePhase gamePhase) {
		this.currentPlayer = currentPlayer;
		this.movementControl.clear();

		if (gamePhase == GamePhase.PLAYER_TURN) {
			this.turnPhase = TurnPhase.DISCARD_PHASE;
		} else {
			this.turnPhase = TurnPhase.TILES_DRAWN;
		}
	}

	/**
	 * Getter for turn phase.
	 *
	 * @return current turn phase
	 */
	public TurnPhase getTurnPhase() {
		return turnPhase;
	}

	/**
	 * Setter for turn phase.
	 *
	 * @param turnPhase new turn phase
	 */
	public void setTurnPhase(TurnPhase turnPhase) {
		this.turnPhase = turnPhase;
	}

	/**
	 * Getter for current player.
	 *
	 * @return current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Getter for movement control.
	 *
	 * @return instance of movement control
	 */
	public MovementControl getMovementControl() {
		return movementControl;
	}

}
