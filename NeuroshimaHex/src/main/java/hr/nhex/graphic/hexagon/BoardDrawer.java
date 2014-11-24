package hr.nhex.graphic.hexagon;

import hr.nhex.board.BoardTile;
import hr.nhex.game.Game;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.imagecache.ImageCache;
import hr.nhex.model.Player;
import hr.nhex.model.Tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class BoardDrawer {

	private Graphics2D g2;
	private ImageCache cache;
	private Game game;
	private HexagonListContainer hlc;

	/**
	 * Constructor.
	 * 
	 * @param g2
	 * @param cache
	 * @param cn
	 * @param specialHex
	 */

	public BoardDrawer(Graphics2D g2, ImageCache cache, Game game, HexagonListContainer hlc) {
		this.g2 = g2;
		this.cache = cache;
		this.game = game;
	}

	public void drawAllHex() {

	}

	private void drawBoardHex() {
		for (Hexagon h : hlc.getHexagonList()) {
			BoardTile t = game.getBoard().getTile(h.getTileX(), h.getTileY());		// just boardTile drawing
			drawHex(h, t, null);
		}
	}

	private void drawSideHex() {
		List<Tile> currentDrawnTiles = Arrays.asList(game.getCurrentPlayerGameDeck().getDrawnTiles());
		int tileNo = 0;

		for (Hexagon h : hlc.getHexagonSideList()) {
			if (currentDrawnTiles.size() > tileNo) {
				drawHex(h, currentDrawnTiles.get(tileNo), game.getCurrentPlayer());
			}
			tileNo++;
		}
	}

	private void drawDraggedHex() {
		if (hlc.getDraggedHexagon() != null) {
			Tile t;
			if (this.getTpma().getTileSelected() != null) {
				t = this.getTpma().getTileSelected();
			} else {
				t = this.getTrma().getSelectedTile();
			}
			hlc.getDraggedHexagon().drawHex(hlc.getDraggedHexagon(), game.getCurrentPlayer(), hlc.getSpecialHexList());
		}
	}

	public void drawHex(Hexagon h, Tile t, Player currentPlayer) {

		// odluèiti kako se šalje podatak u drawHex koji da heksagoni budu iscrtani drugom bojom
		// kod movementa ili pusha
		// opcije: slati Canvas pa u njemu neku listu/mapu s koordinatama i bojom u koju se iscrtava
		// ili slati samo tu listu/mapu

		Polygon poly = h.createHex(h.getxC(),h.getyC());

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

			double theta = (-Math.PI/3)*((BoardTile)t).getAngle();

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

		if (specialHex != null && specialHex.contains(new Pair(h.getTileX(), h.getTileY()))) {
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.RED);
			g2.drawPolygon(poly);
			g2.setStroke(oldStroke);
		} else {
			g2.setColor(Color.BLACK);
			g2.drawPolygon(poly);
		}

	}

	private BufferedImage cropImage(BufferedImage src) {
		//System.out.println("dimension: "+src.getWidth() + ", "+src.getHeight());
		BufferedImage dest = src.getSubimage(0, 0, 230, 270);
		return dest;
	}
}
