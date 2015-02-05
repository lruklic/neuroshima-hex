package hr.nhex.graphic.canvas.buttons;

import hr.nhex.board.resolvers.ActionTileResolver;
import hr.nhex.game.Game;
import hr.nhex.game.GamePhase;
import hr.nhex.game.finish.GameFinisher;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.singleton.Repainter;
import hr.nhex.model.action.ActionTile;
import hr.nhex.model.action.ActionType;

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
	private NeuroshimaCanvas cn;

	private boolean finalBattleFinished = false;

	public EndTurnActionListener(Game game, ButtonContainer bc, NeuroshimaCanvas cn) {
		this.game = game;
		this.bc = bc;
		this.cn = cn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getBoard().numberOfHQ() == game.getNumberOfPlayers()) {
			setPlayerGamePhase();
		}

		if (game.getFinalRoundStarter() != null && game.getFinalRoundStarter().equals(game.getNextPlayer())) {
			if (!finalBattleFinished) {
				finalBattleFinished = true;
				bc.disableAllButtons();
				ActionTileResolver atr = new ActionTileResolver();
				atr.resolve(new ActionTile("Battle", ActionType.BATTLE), null, cn);
				return;
			} else {
				GameFinisher finisher = new GameFinisher(cn, game);
				finisher.finish();
			}
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
			if (game.getFinalRoundStarter() != null) {
				game.setGamePhase(GamePhase.FINAL_ROUND);
			} else {
				game.setGamePhase(GamePhase.PLAYER_TURN);
			}
		}
	}

}
