package hr.nhex.model.action;

import hr.nhex.model.Player;
import hr.nhex.model.Tile;

/**
 * Razred koji predstavlja polje akcije unutar igre Neuroshima hex.
 *
 * @author Luka Rukli�
 *
 */

public class ActionTile extends Tile {

	/**
	 * Vrsta akcije.
	 */
	private ActionType actionType;

	private Player player;

	/**
	 * Konstruktor.
	 *
	 * @param actionType vrsta akcije
	 */
	public ActionTile(String name, ActionType actionType) {
		super(name);
		this.actionType = actionType;
	}

	/**
	 * Getter za vrsta akcije akcijskog polja.
	 * @return vrsta akcije
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * Setter za vrstu akcije akcijskog polja.
	 * @param actionType vrsta akcije
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public int getAngle() {
		return 0;
	}

}
