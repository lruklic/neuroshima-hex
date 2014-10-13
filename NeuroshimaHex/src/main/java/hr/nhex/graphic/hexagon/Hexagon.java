package hr.nhex.graphic.hexagon;

import hr.nhex.graphic.hexgame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * Class that represents graphical interpretation of hexagon on the NHex board.
 * 
 * @author Luka Rukliæ
 * @author Marin Bužanèiæ
 *
 */

public class Hexagon extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private int tileX;
	private int tileY;
	
	/**
	 * X coordinate on the window with hexagon board.
	 */
	private int x;
	/**
	 * Y coordinate on the window with hexagon board.
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
	public Hexagon(int tileX, int tileY, int x, int y, int hexSize) {
		super();
		this.tileX = tileX;
		this.tileY = tileY;
		this.x = x;
		this.y = y;
		this.hexSize = hexSize;
	}
	
	/**
	 * Method that creates instance on hexagon based on x and y coordinates.
	 * @param xC x coordinate of hexagon center
	 * @param yC y coordinate of hexagon center
	 * @return instance of polygon that is hexagon	
	 */
	
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
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("pics/universal_soldier.jpg"));
		} catch (IOException e) {
			System.out.println("image not found");
		}
		
		//Shape hxgn = getPointedShape(6, 32);
		//final BufferedImage txtr = getTexturedImage(img, hxgn, -200, -120);
		
        TexturePaint tex = new TexturePaint(img, poly.getBounds2D());
		
        //g2.setColor(Color.CYAN);
        if (this.tileX == -1 && this.tileY == 0) {
    		g2.setPaint(tex);
        }
        else if (this.tileX == -1 && this.tileY == -1) {
        	
        	AffineTransform texture = new AffineTransform();
        	texture.rotate(Math.PI/3, img.getWidth()/2, img.getHeight()/2);
        	AffineTransformOp op = new AffineTransformOp(texture, AffineTransformOp.TYPE_BILINEAR);
        	img = op.filter(img, null);
        	
        	File outputfile = new File("image.jpg");
			try {
				ImageIO.write(img, "jpg", outputfile);
			} catch (IOException e) {
			}
			
			img = cropImage(img);

        	tex = new TexturePaint(img, poly.getBounds2D());
        	g2.setPaint(tex);	
        }
        else {
        	g2.setColor(Color.WHITE);
        }

		g2.fillPolygon(poly);
		
		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);
	}
	
	  private BufferedImage cropImage(BufferedImage src) {
	      BufferedImage dest = src.getSubimage(0, 0, 351, 398);
	      return dest; 
	   }
	
}
