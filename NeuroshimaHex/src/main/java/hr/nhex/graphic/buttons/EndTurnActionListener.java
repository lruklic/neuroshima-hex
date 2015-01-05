package hr.nhex.graphic.buttons;

import hr.nhex.game.Game;
import hr.nhex.game.GamePhase;
import hr.nhex.graphic.NeuroshimaCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class EndTurnActionListener implements ActionListener {

	private Game game;

	public EndTurnActionListener(Game game) {
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getBoard().numberOfHQ() == game.getNumberOfPlayers()) {
			setPlayerGamePhase();
		}
		game.nextPlayerTurn();
		//System.out.println("Current gp: "+getGameInstance().getGamePhase()+ " tp: "+getGameInstance().getTurnPhase());

		Object src = e.getSource();
		if (src instanceof JButton && ((JButton) src).getParent() instanceof NeuroshimaCanvas) {
			((NeuroshimaCanvas)((JButton) src).getParent()).repaint();		// prouci repaint određenog dijela prozora!
		}

	}

	private void setPlayerGamePhase() {

		GamePhase gamePhase = game.getGamePhase();

		if (gamePhase == GamePhase.FIRST_PLAYER_TURN) {
			game.setGamePhase(GamePhase.SECOND_PLAYER_TURN);
		} else if (gamePhase == GamePhase.SECOND_PLAYER_TURN) {
			game.setGamePhase(GamePhase.PLAYER_TURN);
		} else {
			game.setGamePhase(GamePhase.FIRST_PLAYER_TURN);
		}
	}

}
