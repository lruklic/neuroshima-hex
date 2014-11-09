package hr.nhex.graphic.hexagon;

import hr.nhex.board.BoardTile;
import hr.nhex.graphic.imagecache.ImageCache;
import hr.nhex.model.Player;
import hr.nhex.model.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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
	public Hexagon(int tileX, int tileY, int xC, int yC, int hexSize) {
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
	 * @param xC x coordinate of hexagon center
	 * @param yC y coordinate of hexagon center
	 * @param flatOrientation is hexagon rotated to flat side up
	 * @return instance of polygon that is hexagon
	 */

	public Polygon createHex(int xC, int yC, boolean flatOrientation) {

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


	public void drawHex(Graphics2D g2, ImageCache cache, Tile t, Player currentPlayer) {

		Polygon poly = createHex(xC,yC,false);

		String imageName = null;
		BufferedImage image = null;

		String playerDeckName = null;
		if (t != null) {
			if (t instanceof BoardTile && ((BoardTile)t).getPlayer() != null) {
				playerDeckName = ((BoardTile)t).getPlayer().getPlayerDeck().getDeckName();
				imageName = playerDeckName + "." + t.getName() + ((BoardTile)t).getAngle();
			} else {
				playerDeckName = currentPlayer.getPlayerDeck().getDeckName();
				int tileAngle = 0;
				if (t instanceof BoardTile) {
					tileAngle = ((BoardTile) t).getAngle();
				} else {
					tileAngle = 0;
				}

				imageName = playerDeckName + "." + t.getName() + tileAngle;
			}

			image = cache.getImage(imageName);
		}

		TexturePaint tex = null;
		if (image != null) {
			tex = new TexturePaint(image, poly.getBounds2D());
			g2.setPaint(tex);
			g2.fillPolygon(poly);

			g2.setColor(Color.BLACK);
			g2.drawPolygon(poly);
			return;

		}

		if (t != null) {
			try {
				if (image == null) {
					StringBuilder imagePath = new StringBuilder();

					imagePath.append("pics/"+playerDeckName.toLowerCase()+"/");
					imagePath.append(t.getName().toLowerCase().replaceAll(" ", "_")+".jpg");
					image = ImageIO.read(new File(imagePath.toString()));
				}

				tex = new TexturePaint(image, poly.getBounds2D());
			} catch (IOException e) {
				System.out.println("Image not found.");
			}
		}

		if (tex != null && (!(t instanceof BoardTile) || ((BoardTile)t).getAngle() == 0)) {
			g2.setPaint(tex);
			cache.addToCache(imageName, image);
		}
		else if (tex != null && t instanceof BoardTile && ((BoardTile)t).getAngle() != 0) {

			double theta = (Math.PI/3)*((BoardTile)t).getAngle();

			AffineTransform texture = new AffineTransform();
			texture.rotate(theta, image.getWidth()/2, image.getHeight()/2);
			AffineTransformOp op = new AffineTransformOp(texture, AffineTransformOp.TYPE_BILINEAR);
			image = op.filter(image, null);

			image = cropImage(image);
			cache.addToCache(imageName, image);

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
		//System.out.println("dimension: "+src.getWidth() + ", "+src.getHeight());
		BufferedImage dest = src.getSubimage(0, 0, 230, 270);
		return dest;
	}

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

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

}
