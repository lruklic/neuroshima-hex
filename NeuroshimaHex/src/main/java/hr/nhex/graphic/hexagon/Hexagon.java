package hr.nhex.graphic.hexagon;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Class that represents graphical interpretation of hexagon on the NHex board.
 * 
 * @author Luka Rukliæ
 *
 */

public class Hexagon extends JComponent{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * X coordinate on hexagon board.
	 */
	private int x;
	/**
	 * Y coordinate on hexagon board.
	 */
	private int y;
	/**
	 * Distance between center of the hexagon and any of its six tops.
	 */
	private int centerTopDistance;
	
	/**
	 * Constructor for class hexagon.
	 * 
	 * @param x
	 * @param y
	 * @param centerTopDistance
	 */
	public Hexagon(int x, int y, int centerTopDistance) {
		super();
		this.x = x;
		this.y = y;
		this.centerTopDistance = centerTopDistance;
	}

	@Override
	public void paint(Graphics g) {
		g.drawLine(x, y, x+2, y+2);
	}
	
}
