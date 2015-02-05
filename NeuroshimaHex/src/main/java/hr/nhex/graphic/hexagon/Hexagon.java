package hr.nhex.graphic.hexagon;

import java.awt.Polygon;

import javax.swing.JComponent;

/**
 * Class that represents graphical interpretation of hexagon on the NHex board.
 *
 * @author Luka Ruklic
 * @author Marin Buzancic
 *
 */

public class Hexagon extends JComponent {

	private static final long serialVersionUID = 1L;

	/**
	 * X coordinate on hexagon board.
	 */
	private Integer tileX;
	/**
	 * Y coordinate on hexagon board.
	 */
	private Integer tileY;
	/**
	 * X pixel coordinate on the window with hexagon board.
	 */
	private int xC;
	/**
	 * Y pixel coordinate on the window with hexagon board.
	 */
	private int yC;
	/**
	 * Pixel distance between center of the hexagon and any of its six tops.
	 */
	private int hexSize;

	/**
	 * Constructor for class hexagon.
	 *
	 * @param tileX
	 * @param tileY
	 * @param xC
	 * @param yC
	 * @param hexSize
	 */
	public Hexagon(Integer tileX, Integer tileY, int xC, int yC, int hexSize) {		// da li moraju postojati
		super();
		this.tileX = tileX;
		this.tileY = tileY;
		this.xC = xC;
		this.yC = yC;
		this.hexSize = hexSize;
	}

	/**
	 * Method that creates instance on hexagon based on x and y coordinates.
	 *
	 * @return newly created polygon
	 */

	public Polygon createHex() {

		int[] arrayX = null;
		int[] arrayY = null;

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

		arrayX = new int[] {x0,x1,x2,x3,x4,x5};
		arrayY = new int[] {y0,y1,y2,y3,y4,y5};



		return new Polygon(arrayX,arrayY,6);
	}

	//					ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
	//					ColorConvertOp op = new ColorConvertOp(cs, null);
	//					img2 = op.filter(image, null);

	/**
	 * Method that crops given picture to adjust its size for board hexagons.
	 *
	 * @param src picture that is being cropped
	 * @return cropped picture
	 */

	public int getxC() {
		return xC;
	}

	public int getyC() {
		return yC;
	}

	public void setxC(int xC) {
		this.xC = xC;
	}

	public void setyC(int yC) {
		this.yC = yC;
	}

	public Integer getTileX() {
		return tileX;
	}

	public Integer getTileY() {
		return tileY;
	}

}
