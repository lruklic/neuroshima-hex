package hr.nhex.game;

import hr.nhex.board.Board;
import hr.nhex.decks.GameDeck;
import hr.nhex.model.Player;
import hr.nhex.model.Tile;

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

	private GamePhase gamePhase = GamePhase.GAME_START;

	private TurnPhase turnPhase = TurnPhase.DISCARD_PHASE;

	private Board board;

	private Tile selectedTile;


	public Game(Board board, List<Player> players) {
		this.board = board;
		this.players = players;

		this.currentPlayer = players.get(0);

		for (Player p : players) {
			decks.add(p.getPlayerDeck().createGameDeck());
		}
	}

	public void nextPlayerTurn() {
		String playerName = currentPlayer.getPlayerName();
		int deckNo = 0;
		for (Player p : players) {
			if (p.getPlayerName().equals(playerName)) {
				break;
			}
			deckNo++;
		}
		if (deckNo == players.size()-1) {
			this.currentPlayer = players.get(0);
		} else {
			this.currentPlayer = players.get(deckNo+1);
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

	public int getNumberOfPlayers() {
		return players.size();
	}

	public Tile getSelectedTile() {
		return selectedTile;
	}

	public void setSelectedTile(Tile selectedTile) {
		this.selectedTile = selectedTile;
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
