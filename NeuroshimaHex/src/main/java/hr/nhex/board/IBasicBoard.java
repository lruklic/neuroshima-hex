package hr.nhex.board;

import hr.nhex.model.Player;
import hr.nhex.model.Tile;

/**
 * Suèelje koje propisuje osnovne metode koje mora implementirati svaki tip ploèe za igru Neuroshima Hex.
 * 
 * @author Luka Rukliæ
 *
 */

public interface IBasicBoard {

	/**
	 * Metoda koja provjerava da li se na lokaciji s koordinatama x i y veæ nalazi polje.
	 * 
	 * @param x koordinata x
	 * @param y koordinata y
	 * @return <code>true</code> ukoliko je polje zauzeto, <code>false</code> inaèe
	 */
	public boolean isFilled(int x, int y);

	/**
	 * Metoda koja dohvaæa polje s lokacije odreðene koordinatama x i y.
	 * 
	 * @param x koordinata x
	 * @param y koordinata y
	 * @return polje s lokacije x,y ukoliko je lokacija zauzeta, <code>null</code> ukoliko nije
	 */
	public Tile getTile(int x, int y);

	public boolean tileIsNetted(int x, int y, int noOfIteration, Player player);
}
