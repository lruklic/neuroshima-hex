package hr.nhex.game;

/**
 * Enumeration that identifies current game phase.
 *
 * @author Luka Ruklic
 *
 */

public enum GamePhase {
	/**
	 * Game has just begun.
	 */
	GAME_START,
	/**
	 * HQ are currently being set on the board.
	 */
	HQ_SETUP,
	/**
	 * First player turn. Only one tile is drawn.
	 */
	FIRST_PLAYER_TURN,
	/**
	 * Second player turn. Two tiles are drawn.
	 */
	SECOND_PLAYER_TURN,
	/**
	 * Common turn. Three tiles are drawn, one is discarded.
	 */
	PLAYER_TURN

}
