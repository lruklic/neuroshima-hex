package hr.nhex.game;

import hr.nhex.board.Board;
import hr.nhex.decks.Deck;
import hr.nhex.game.turn.Turn;
import hr.nhex.game.turn.TurnPhase;
import hr.nhex.model.AbstractTile;
import hr.nhex.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents instance of Neuroshima Hex game.
 *
 * @author Luka Ruklic
 *
 */

public class Game {

	/**
	 * List of players participating in current game.
	 */
	private List<Player> players = new ArrayList<>();
	/**
	 * Current phase of the game.
	 */
	private GamePhase gamePhase = GamePhase.HQ_SETUP;
	/**
	 * Instance of the current turn.
	 */
	private Turn currentTurn;
	/**
	 * Instance of board in current game.
	 */
	private Board board;
	/**
	 * Tile that is currently selected.
	 */
	private AbstractTile selectedTile;

	/**
	 * Constructor for game instance.
	 *
	 * @param board instance of board for current game
	 * @param players list of players that will play the game
	 */
	public Game(Board board, List<Player> players) {
		this.board = board;
		this.players = players;
		this.currentTurn = new Turn(players.get(0));
	}
	/**
	 * Method that starts the turn for the next player in line.
	 */
	public void nextPlayerTurn() {
		int currentPlayerIndex = players.indexOf(currentTurn.getCurrentPlayer());
		if (currentPlayerIndex != players.size()-1) {
			currentTurn.newPlayerTurn(players.get(currentPlayerIndex+1), gamePhase);
		} else {
			currentTurn.newPlayerTurn(players.get(0), gamePhase);
		}
	}
	/**
	 * Getter for current deck.
	 *
	 * @return current deck
	 */
	public Deck getCurrentPlayerDeck() {
		return getCurrentPlayer().getPlayerDeck();
	}

	public Player getCurrentPlayer() {
		return currentTurn.getCurrentPlayer();
	}

	public int getNumberOfPlayers() {
		return players.size();
	}

	public AbstractTile getSelectedTile() {
		return selectedTile;
	}

	public void setSelectedTile(AbstractTile selectedTile) {
		this.selectedTile = selectedTile;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Board getBoard() {
		return board;
	}

	public GamePhase getGamePhase() {
		return gamePhase;
	}

	public Turn getTurn() {
		return currentTurn;
	}

	public TurnPhase getTurnPhase() {
		return currentTurn.getTurnPhase();
	}

	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
	}

	public void setTurnPhase(TurnPhase turnPhase) {
		currentTurn.setTurnPhase(turnPhase);
	}

}
