package hr.nhex.graphic.canvas.labels;

import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.game.Game;
import hr.nhex.model.HQ;
import hr.nhex.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

public class LabelContainer {

	Map<String, JLabel> tilesLeftLabels = new HashMap<String, JLabel>();
	Map<String, JLabel> hqHpLabels = new HashMap<String, JLabel>();		// umjesto liste mapa ili nova klasa koja čuva ime labele i samu labelu
	// potrebno kada ima više igrača pa jedan ostane bez hqa

	public void createTilesLeftLabels(List<Player> players) {
		for (Player player : players) {
			tilesLeftLabels.put(player.getPlayerDeck().getDeckName(), new JLabel());
		}
	}

	public void createHqHpLabels(List<Player> players) {
		for (Player player : players) {
			hqHpLabels.put(player.getPlayerDeck().getDeckName(), new JLabel());
		}
	}

	public void updateTilesLeft(Game game) {
		for (Player player : game.getPlayers()) {
			JLabel currentLabel = tilesLeftLabels.get(player.getPlayerDeck().getDeckName());
			//			if (currentLabel == null) {
			//				System.out.println("Empty");
			//				return;
			//			}
			currentLabel.setForeground(player.getPlayerColor());
			currentLabel.setText("Tiles left: "+player.getPlayerDeck().getTilesLeft());
		}
	}

	public void updateHp(Board board) {
		for (BoardTile tile : board.getTiles()) {
			if (tile instanceof HQ) {
				String deckName = tile.getPlayer().getPlayerDeck().getDeckName();
				JLabel currentLabel = hqHpLabels.get(deckName);
				currentLabel.setText(deckName+": "+String.valueOf(tile.getHitPoints()));
				currentLabel.setForeground(tile.getPlayer().getPlayerColor());
			}
		}
	}

	public JLabel getTilesLeftLabel(String deckName) {
		return tilesLeftLabels.get(deckName);
	}

	public JLabel getHqHpLabel(String deckName) {
		return hqHpLabels.get(deckName);
	}

}
