package hr.nhex.graphic.canvas.buttons;

import hr.nhex.game.Game;
import hr.nhex.game.GamePhase;
import hr.nhex.graphic.singleton.Repainter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener that determines the action that occurs when End Turn button is pressed.
 *
 * @author Luka Ruklic
 *
 */

public class EndTurnActionListener implements ActionListener {

	private Game game;
	private ButtonContainer bc;

	public EndTurnActionListener(Game game, ButtonContainer bc) {
		this.game = game;
		this.bc = bc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getBoard().numberOfHQ() == game.getNumberOfPlayers()) {
			setPlayerGamePhase();
		}
		game.nextPlayerTurn();
		// System.out.println("Current gp: "+game.getGamePhase()+ " tp: "+game.getTurnPhase());

		bc.toggleEndTurnButton(false);
		bc.toggleDrawButton(true);

		Repainter.repaint(); // prouci repaint određenog dijela prozora!

	}

	private void setPlayerGamePhase() {

		GamePhase gamePhase = game.getGamePhase();

		if (gamePhase == GamePhase.HQ_SETUP) {
			game.setGamePhase(GamePhase.FIRST_PLAYER_TURN);
		} else if (gamePhase == GamePhase.FIRST_PLAYER_TURN) {
			game.setGamePhase(GamePhase.SECOND_PLAYER_TURN);
		} else if (gamePhase == GamePhase.SECOND_PLAYER_TURN) {
			game.setGamePhase(GamePhase.PLAYER_TURN);
		} else {
			game.setGamePhase(GamePhase.PLAYER_TURN);
		}
	}

}
