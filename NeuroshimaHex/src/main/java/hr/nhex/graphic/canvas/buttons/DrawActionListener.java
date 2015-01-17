package hr.nhex.graphic.canvas.buttons;

import hr.nhex.game.Game;
import hr.nhex.game.GamePhase;
import hr.nhex.game.turn.TurnPhase;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DrawActionListener implements ActionListener {

	private Game game;
	private ButtonContainer bc;

	public DrawActionListener(Game game, ButtonContainer bc) {
		this.game = game;
		this.bc = bc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		GamePhase gamePhase = game.getGamePhase();

		if (gamePhase == GamePhase.HQ_SETUP) {
			game.getCurrentPlayerDeck().drawHQ();
		} else if (gamePhase == GamePhase.FIRST_PLAYER_TURN) {
			bc.toggleEndTurnButton(true);							// relativno loše rješenje, tek nakon discarda je moguće završiti potez (slučaj s više od 3 tilea?!)
			game.getCurrentPlayerDeck().drawNew(1);
		} else if (gamePhase == GamePhase.SECOND_PLAYER_TURN) {
			bc.toggleEndTurnButton(true);
			game.getCurrentPlayerDeck().drawNew(2);
		} else {
			game.getCurrentPlayerDeck().drawNew();
			game.setTurnPhase(TurnPhase.DISCARD_PHASE);
		}

		bc.toggleDrawButton(false);

		Object src = e.getSource();
		if (src instanceof JButton && ((JButton) src).getParent() instanceof NeuroshimaCanvas) {
			((NeuroshimaCanvas)((JButton) src).getParent()).repaint();		// prouci repaint određenog dijela prozora!
		}
	}

}
