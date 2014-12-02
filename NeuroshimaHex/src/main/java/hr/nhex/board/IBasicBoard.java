package hr.nhex.board;

import hr.nhex.model.AbstractTile;
import hr.nhex.model.player.Player;

/**
 * Su�elje koje propisuje osnovne metode koje mora implementirati svaki tip plo�e za igru Neuroshima Hex.
 * 
 * @author Luka Rukli�
 *
 */

public interface IBasicBoard {

	/**
	 * Metoda koja provjerava da li se na lokaciji s koordinatama x i y ve� nalazi polje.
	 * 
	 * @param x koordinata x
	 * @param y koordinata y
	 * @return <code>true</code> ukoliko je polje zauzeto, <code>false</code> ina�e
	 */
	public boolean isFilled(int x, int y);

	/**
	 * Metoda koja dohva�a polje s lokacije odre�ene koordinatama x i y.
	 * 
	 * @param x koordinata x
	 * @param y koordinata y
	 * @return polje s lokacije x,y ukoliko je lokacija zauzeta, <code>null</code> ukoliko nije
	 */
	public AbstractTile getTile(int x, int y);

	public boolean tileIsNetted(int x, int y, int noOfIteration, Player player);
}
