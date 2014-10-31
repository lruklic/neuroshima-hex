package hr.nhex.game;

import hr.nhex.board.Board;
import hr.nhex.decks.GameDeck;
import hr.nhex.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents instance of Neuroshima Hex game.
 * 
 * @author Luka Rukliæ
 *
 */

public class Game {

	/**
	 * List of players participating in current game.
	 */
	private List<Player> players = new ArrayList<>();
	/**
	 * Player whose turn currently is.
	 */
	private Player currentPlayer;
	/**
	 * Decks containing tiles of player currently in the game.
	 */
	private List<GameDeck> decks = new ArrayList<>();

	private GamePhase gamePhase;

	private TurnPhase turnPhase = TurnPhase.DISCARD_PHASE;

	private Board board;


	public Game(Board board, List<Player> players) {
		this.board = board;
		this.players = players;

		this.currentPlayer = players.get(0);

		for (Player p : players) {
			decks.add(p.getPlayerDeck().createGameDeck());
		}
	}

	public GameDeck getCurrentPlayerGameDeck() {
		String currentPlayerDeckName = currentPlayer.getPlayerDeck().getDeckName();
		for (GameDeck deck : decks) {
			if (currentPlayerDeckName.equals(deck.getDeckName())) {
				return deck;
			}
		}
		return null;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
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

	public TurnPhase getTurnPhase() {
		return turnPhase;
	}

	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
	}

	public void setTurnPhase(TurnPhase turnPhase) {
		this.turnPhase = turnPhase;
	}

}
