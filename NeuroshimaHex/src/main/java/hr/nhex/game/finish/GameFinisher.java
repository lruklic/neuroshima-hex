package hr.nhex.game.finish;

import hr.nhex.board.BoardTile;
import hr.nhex.game.Game;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.model.HQ;
import hr.nhex.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameFinisher {

	private NeuroshimaCanvas cn;

	private Game game;

	public GameFinisher(NeuroshimaCanvas cn, Game game) {
		this.cn = cn;
		this.game = game;
	}

	public void finish() {

		Player winner = determineWinner();
		System.out.println("Winner is " + winner.getPlayerName());
		cn.removeAll();
		cn.validate();
	}

	/**
	 * Method that finds out who is the winner based on current game status. If there is a tie, method
	 * return null value.
	 *
	 * @return instance of winning player
	 */

	private Player determineWinner() {

		if (game.getPlayers().size() == 1) {
			return game.getPlayers().get(0);
		} else {

			Map<Player, Integer> players = new HashMap<Player, Integer>();

			for (Player player : game.getPlayers()) {
				for (BoardTile tile : game.getBoard().getTiles()) {
					if (tile instanceof HQ && player == tile.getPlayer()) {
						players.put(tile.getPlayer(), tile.getHitPoints());
					}
				}
			}

			int winnerHP = -1;
			List<Player> winners = new ArrayList<Player>();
			for (Player player : game.getPlayers()) {
				if (players.containsKey(player)) {
					if (players.get(player) > winnerHP) {
						winnerHP = players.get(player);
						winners.clear();
						winners.add(player);
					} else if (players.get(player) == winnerHP) {
						winners.add(player);
					}
				}
			}

			if (winners.size() == 1) {
				return winners.get(0);
			} else {
				return null;		// vraća null ukoliko ima više pobjednika, u tom slucaju se odigrava nova borba ili se jednostavno proglašava TIE
			}
		}


	}

}
