package hr.nhex.graphic.canvas.buttons;

import hr.nhex.decks.Deck;
import hr.nhex.game.Game;
import hr.nhex.game.GamePhase;
import hr.nhex.game.turn.TurnPhase;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Action listener that implements the functionality of draw button.
 *
 * @author Luka Ruklic
 *
 */

public class DrawActionListener implements ActionListener {
	/**
	 * Instance of game that is currently played.
	 */
	private Game game;
	/**
	 * Container for all the buttons in desktop Neuroshima game.
	 */
	private ButtonContainer bc;
	/**
	 * Constructor.
	 * @param game instance of currently played game
	 * @param bc button container
	 */
	public DrawActionListener(Game game, ButtonContainer bc) {
		this.game = game;
		this.bc = bc;
	}
	/**
	 * Method that is invoked when draw button is clicked. It draws tiles, sets up turn phase and toggles button states.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		GamePhase gamePhase = game.getGamePhase();
		Deck currentPlayerDeck = game.getCurrentPlayerDeck();

		if (gamePhase == GamePhase.HQ_SETUP) {
			currentPlayerDeck.drawHQ();
		} else if (gamePhase == GamePhase.FIRST_PLAYER_TURN) {
			bc.toggleEndTurnButton(true);							// relativno loše rješenje, tek nakon discarda je moguće završiti potez (slučaj s više od 3 tilea?!)
			currentPlayerDeck.drawNew(1);
		} else if (gamePhase == GamePhase.SECOND_PLAYER_TURN) {
			bc.toggleEndTurnButton(true);
			currentPlayerDeck.drawNew(2);
		} else {

			if (currentPlayerDeck.getTilesLeft() <= 3) {
				currentPlayerDeck.drawNew(currentPlayerDeck.getTilesLeft());
				if (game.getFinalRoundStarter() == null) {
					game.setFinalRoundStarter(game.getCurrentPlayer());
				}
			}

			game.getCurrentPlayerDeck().drawNew();

			// ako su povučeni zadnji tileovi, postavi game fazu u final round te u Game postavi finalRoundInstantiatora (igrača koji je pokrenuo zadnju rundu)
			// kad dodje do njega, igra završava te se saznaje pobjednik

			game.setTurnPhase(TurnPhase.DISCARD_PHASE);
		}

		bc.toggleDrawButton(false);

		Object src = e.getSource();
		if (src instanceof JButton && ((JButton) src).getParent() instanceof NeuroshimaCanvas) {
			((NeuroshimaCanvas)((JButton) src).getParent()).repaint();		// prouci repaint određenog dijela prozora!
		}
	}

}
