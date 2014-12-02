package hr.nhex.game;

import hr.nhex.board.Board;
import hr.nhex.decks.Deck;
import hr.nhex.model.AbstractTile;
import hr.nhex.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents instance of Neuroshima Hex game.
 *
 * @author Luka Rukli�
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
	private int currentPlayerIndex;

	private GamePhase gamePhase = GamePhase.GAME_START;

	private TurnPhase turnPhase = TurnPhase.DISCARD_PHASE;

	private Board board;

	private AbstractTile selectedTile;

	public Game(Board board, List<Player> players) {
		this.board = board;
		this.players = players;
	}

	public void nextPlayerTurn() {
		if (currentPlayerIndex != players.size()-1) {
			currentPlayerIndex++;
		} else {
			currentPlayerIndex = 0;
		}
	}

	public Deck getCurrentPlayerDeck() {
		return getCurrentPlayer().getPlayerDeck();
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
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
