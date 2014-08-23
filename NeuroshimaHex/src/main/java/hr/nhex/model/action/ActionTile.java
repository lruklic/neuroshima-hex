package hr.nhex.model.action;

import hr.nhex.model.Tile;

/**
 * Razred koji predstavlja polje akcije unutar igre Neuroshima hex.
 * 
 * @author Luka Rukliæ
 *
 */

public class ActionTile extends Tile {

	/**
	 * Vrsta akcije.
	 */
	private ActionType actionType;

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


}
