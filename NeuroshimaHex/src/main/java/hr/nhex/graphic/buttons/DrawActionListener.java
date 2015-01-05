package hr.nhex.graphic.buttons;

import hr.nhex.game.Game;
import hr.nhex.game.GamePhase;
import hr.nhex.game.turn.TurnPhase;
import hr.nhex.graphic.NeuroshimaCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DrawActionListener implements ActionListener {

	private Game game;

	public DrawActionListener(Game game) {
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		GamePhase gamePhase = game.getGamePhase();

		if (gamePhase == GamePhase.GAME_START) {
			game.setGamePhase(GamePhase.HQ_SETUP);
			game.setTurnPhase(TurnPhase.TILES_DRAWN);
		} else if (gamePhase == GamePhase.HQ_SETUP) {
			game.getCurrentPlayerDeck().drawHQ();
		} else if (gamePhase == GamePhase.FIRST_PLAYER_TURN) {
			game.getCurrentPlayerDeck().drawNew();
		} else {
			game.getCurrentPlayerDeck().shuffleDeck();
			game.getCurrentPlayerDeck().drawNew();
			game.setTurnPhase(TurnPhase.DISCARD_PHASE);
		}

		Object src = e.getSource();
		if (src instanceof JButton && ((JButton) src).getParent() instanceof NeuroshimaCanvas) {
			((NeuroshimaCanvas)((JButton) src).getParent()).repaint();		// prouci repaint određenog dijela prozora!
		}
	}

}
