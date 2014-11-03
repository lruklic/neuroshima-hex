package hr.nhex.graphic.hexagon;

import hr.nhex.board.BoardTile;
import hr.nhex.model.Player;
import hr.nhex.model.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.TexturePaint;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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

	/**
	 * X coordinate on hexagon board.
	 */
	private int tileX;
	/**
	 * Y coordinate on hexagon board.
	 */
	private int tileY;
	/**
	 * X pixel coordinate on the window with hexagon board.
	 */
	private int x;
	/**
	 * Y pixel coordinate on the window with hexagon board.
	 */
	private int y;
	/**
	 * Pixel distance between center of the hexagon and any of its six tops.
	 */
	private int hexSize;

	private BufferedImage image;
	/**
	 * Constructor for class hexagon.
	 * 
	 * @param tileX
	 * @param tileY
	 * @param x
	 * @param y
	 * @param hexSize
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
	 * 
	 * @param xC x coordinate of hexagon center
	 * @param yC y coordinate of hexagon center
	 * @param flatOrientation is hexagon rotated to flat side up
	 * @return instance of polygon that is hexagon
	 */

	public Polygon createHex(int xC, int yC, boolean flatOrientation) {

		int[] arrayX = null;
		int[] arrayY = null;

		if (flatOrientation) {
			int x0;
			int x1;
			int x2;
			int x3;
			int x4;
			int x5;

			int y0;
			int y1;
			int y2;
			int y3;
			int y4;
			int y5;
		} else {
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
		}


		return new Polygon(arrayX,arrayY,6);
	}

	public void drawDrawnHex(Graphics2D g2, Tile t, Player currentPlayer) {

		Polygon poly = createHex(x,y,false);

		TexturePaint tex = null;
		if (t != null) {
			try {
				if (image == null) {
					StringBuilder imagePath = new StringBuilder();
					imagePath.append("pics/"+currentPlayer.getPlayerDeck().getDeckName().toLowerCase()+"/");
					imagePath.append(t.getName().toLowerCase().replaceAll(" ", "_")+".jpg");
					this.image = ImageIO.read(new File(imagePath.toString()));
				}

				tex = new TexturePaint(image, poly.getBounds2D());

			} catch (IOException e) {
				System.out.println("Image not found.");
			}
		}

		if (tex != null) {
			g2.setPaint(tex);
		} else {
			g2.setColor(Color.WHITE);
		}

		g2.fillPolygon(poly);

		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);

	}

	public void drawHex(Graphics2D g2, BoardTile bt, int se) {

		Polygon poly = createHex(x,y,false);

		BufferedImage img2 = null;
		TexturePaint tex = null;
		if (bt != null) {
			try {
				if (image == null) {
					StringBuilder imagePath = new StringBuilder();
					imagePath.append("pics/"+bt.getPlayer().getPlayerDeck().getDeckName().toLowerCase()+"/");
					imagePath.append(bt.getName().toLowerCase().replaceAll(" ", "_")+".jpg");
					this.image = ImageIO.read(new File(imagePath.toString()));
				}

				if (se == 1) {
					ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
					ColorConvertOp op = new ColorConvertOp(cs, null);
					img2 = op.filter(image, null);

					//					File outputfile = new File("prvi.png");
					//					ImageIO.write(img, "png", outputfile);
					//
					//					File outputfile2 = new File("drugi.png");
					//					ImageIO.write(img2, "png", outputfile2);
				}
				tex = new TexturePaint(img2, poly.getBounds2D());
			} catch (IOException e) {
				System.out.println("Unit image not found.");
			}
		}

		if (tex != null && bt.getAngle() == 0) {
			g2.setPaint(tex);
		}
		else if (tex != null && bt.getAngle() != 0) {

			double theta = (Math.PI/3)*bt.getAngle();

			AffineTransform texture = new AffineTransform();
			texture.rotate(theta, image.getWidth()/2, image.getHeight()/2);
			AffineTransformOp op = new AffineTransformOp(texture, AffineTransformOp.TYPE_BILINEAR);
			image = op.filter(img2, null);

			image = cropImage(image);

			tex = new TexturePaint(image, poly.getBounds2D());
			g2.setPaint(tex);
		}
		else {
			g2.setColor(Color.WHITE);
		}

		g2.fillPolygon(poly);

		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);

	}

	public void drawHex(Graphics2D g2, BoardTile bt) {

		Polygon poly = createHex(x,y,false);

		TexturePaint tex = null;

		if (image != null) {
			tex = new TexturePaint(image, poly.getBounds2D());
			g2.setPaint(tex);
			g2.fillPolygon(poly);

			g2.setColor(Color.BLACK);
			g2.drawPolygon(poly);
			return;

		}

		if (bt != null) {
			try {
				if (image == null) {
					StringBuilder imagePath = new StringBuilder();
					imagePath.append("pics/"+bt.getPlayer().getPlayerDeck().getDeckName().toLowerCase()+"/");
					imagePath.append(bt.getName().toLowerCase().replaceAll(" ", "_")+".jpg");
					image = ImageIO.read(new File(imagePath.toString()));
				}

				tex = new TexturePaint(image, poly.getBounds2D());
			} catch (IOException e) {
				System.out.println("Unit image not found.");
			}
		}

		if (tex != null && bt.getAngle() == 0) {
			g2.setPaint(tex);
		}
		else if (tex != null && bt.getAngle() != 0) {
			System.out.println("something");

			double theta = (Math.PI/3)*bt.getAngle();

			AffineTransform texture = new AffineTransform();
			texture.rotate(theta, image.getWidth()/2, image.getHeight()/2);
			AffineTransformOp op = new AffineTransformOp(texture, AffineTransformOp.TYPE_BILINEAR);
			image = op.filter(image, null);

			image = cropImage(image);

			tex = new TexturePaint(image, poly.getBounds2D());
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
		BufferedImage dest = src.getSubimage(0, 0, 234, 270);
		return dest;
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

}
