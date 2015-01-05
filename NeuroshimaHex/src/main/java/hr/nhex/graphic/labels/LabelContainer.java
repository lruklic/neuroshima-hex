package hr.nhex.graphic.labels;

import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.model.HQ;
import hr.nhex.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

public class LabelContainer {

	Map<String, JLabel> hqHpLabels = new HashMap<String, JLabel>();		// umjesto liste mapa ili nova klasa koja čuva ime labele i samu labelu
	// potrebno kada ima više igrača pa jedan ostane bez hqa

	public void createHqHpLabels(List<Player> players) {
		for (Player player : players) {
			hqHpLabels.put(player.getPlayerDeck().getDeckName(), new JLabel());
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

	public JLabel getHqHpLabel(String deckName) {
		return hqHpLabels.get(deckName);
	}

}
