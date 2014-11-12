package hr.nhex.graphic.adapters;

/**
 * Interface that defines basic custom adapter options, like turning the adapter on and off.
 * 
 * @author Luka Rukliæ
 *
 */

public interface IMouseAdapter {

	/**
	 * Method that turns the adapter on.
	 */
	public void setListenerOn();

	/**
	 * Method that turns the adapter off.
	 */
	public void setListenerOff();

}
