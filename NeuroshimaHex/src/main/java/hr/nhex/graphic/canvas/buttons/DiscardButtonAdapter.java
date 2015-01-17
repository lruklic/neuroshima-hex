package hr.nhex.graphic.canvas.buttons;

import hr.nhex.game.Game;
import hr.nhex.game.GamePhase;
import hr.nhex.game.turn.TurnPhase;
import hr.nhex.graphic.singleton.Repainter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DiscardButtonAdapter extends MouseAdapter {

	private Game game;
	private int discardButtonNumber;
	private ButtonContainer bc;

	public DiscardButtonAdapter(ButtonContainer bc, int discardButtonNumber, Game game) {
		this.bc = bc;
		this.game = game;
		this.discardButtonNumber = discardButtonNumber;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (game.getGamePhase() != GamePhase.HQ_SETUP) {
			game.getCurrentPlayerDeck().discardTile(discardButtonNumber);
			bc.toggleEndTurnButton(true);
		} else {
			return;
		}

		if (game.getTurnPhase() == TurnPhase.DISCARD_PHASE) {
			game.setTurnPhase(TurnPhase.TILES_DRAWN);
		}

		Repainter.repaint();
	}
}
