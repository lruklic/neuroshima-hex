package hr.nhex.graphic.hexagon;

import hr.nhex.graphic.hexgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JComponent;

/**
 * Class that represents graphical interpretation of hexagon on the NHex board.
 * 
 * @author Luka Rukliæ
 * @author Marin Bužanèiæ
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
	private int hexSize;
	
	/**
	 * Constructor for class hexagon.
	 * 
	 * @param x
	 * @param y
	 * @param centerTopDistance
	 */
	public Hexagon(int x, int y, int hexSize) {
		super();
		this.x = x;
		this.y = y;
		this.hexSize = hexSize;
	}
	
	public Polygon createHex(int xC, int yC) {
		
		int x0 = xC; 
		int x1 = (int)(xC + (hexSize*Math.sqrt(3)/2));
		int x2 = x1;
		int x3 = x0;
		int x4 = (int)(xC - (hexSize*Math.sqrt(3)/2));;
		int x5 = x4;
		
		int y0 = yC - hexSize;		
		int y1 = (int)(yC - (hexSize*0.5));
		int y2 = (int)(yC + (hexSize*0.5));
		int y3 = yC + hexSize;
		int y4 = y2;
		int y5 = y1;
		
		int[] arrayX = new int[] {x0,x1,x2,x3,x4,x5};  //this is for the top left vertex being at x,y. Which means that some of the hex is cutoff.
		int[] arrayY = new int[] {y0,y1,y2,y3,y4,y5};	

		return new Polygon(arrayX,arrayY,6);
	}
 	
	public void drawHex(Graphics2D g2) {

		Polygon poly = createHex(x,y);
		g2.setColor(Color.RED);
		//g2.fillPolygon(hexmech.hex(x,y));
		g2.fillPolygon(poly);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);
	}
	
}
