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
 * @author Luka Rukli�
 *
 */

public class Game {

	/**
	 * List of players participating in current game.
	 */
	private List<Player> players = new ArrayList<>();

	private GamePhase gamePhase = GamePhase.GAME_START;

	private Turn currentTurn;

	private Board board;

	private AbstractTile selectedTile;

	public Game(Board board, List<Player> players) {
		this.board = board;
		this.players = players;
		this.currentTurn = new Turn(players.get(0));
	}

	public void nextPlayerTurn() {
		int currentPlayerIndex = players.indexOf(currentTurn.getCurrentPlayer());
		if (currentPlayerIndex != players.size()-1) {
			currentTurn.newPlayerTurn(players.get(currentPlayerIndex+1), gamePhase);
		} else {
			currentTurn.newPlayerTurn(players.get(0), gamePhase);
		}
	}

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
