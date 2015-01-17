package hr.nhex.graphic.hexagon;

import hr.nhex.board.BoardTile;
import hr.nhex.game.Game;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.canvas.buttons.ButtonContainer;
import hr.nhex.graphic.imagecache.ImageCache;
import hr.nhex.model.AbstractTile;
import hr.nhex.model.HQ;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class BoardDrawer {

	private static final int SPECIAL_STROKE_SIZE = 3;
	private static final int DAMAGE_TOKEN_DIAMETER = 30;
	private static final int SPECIAL_DISTANCE = 3;
	private static final int DAMAGE_TOKEN_SHIFT = 10;

	private Graphics2D g2;
	private ImageCache cache;
	private Game game;
	private HexagonListContainer hlc;
	private ButtonContainer bc;

	/**
	 * Constructor.
	 *
	 * @param g2
	 * @param cache
	 * @param cn
	 * @param specialHex
	 */

	public BoardDrawer(Graphics2D g2, ImageCache cache, Game game, HexagonListContainer hlc, ButtonContainer bc) {
		this.g2 = g2;
		this.cache = cache;
		this.game = game;
		this.hlc = hlc;
		this.bc = bc;
	}

	public void drawAllHex() {
		drawBoardHex();
		drawSideHex();
		drawDamageTokens();
		drawDraggedHex();
	}

	private void drawBoardHex() {
		for (Hexagon h : hlc.getHexagonList()) {
			BoardTile t = game.getBoard().getTile(h.getTileX(), h.getTileY());		// just boardTile drawing
			drawHex(h, t);
		}
	}

	private void drawSideHex() {
		List<AbstractTile> currentDrawnTiles = Arrays.asList(game.getCurrentPlayerDeck().getDrawnTiles());
		int tileNo = 0;

		for (Hexagon h : hlc.getHexagonSideList()) {
			if (currentDrawnTiles.size() > tileNo) {
				drawHex(h, currentDrawnTiles.get(tileNo));
			}
			tileNo++;
		}
	}

	private void drawDraggedHex() {
		if (hlc.getDraggedHexagon() != null) {
			bc.makeButtonsTransparent(true);
			AbstractTile t = game.getSelectedTile();
			drawHex(hlc.getDraggedHexagon(), t);
		} else {
			bc.makeButtonsTransparent(false);
		}
	}

	private void drawDamageTokens() {
		for (BoardTile bt : game.getBoard().getTiles()) {
			if (bt.getHitPoints() < bt.getMaxHitPoints() && !(bt instanceof HQ)) {
				for (int i = 0; i < bt.getMaxHitPoints() - bt.getHitPoints(); i++) {
					int damageTokenShift = DAMAGE_TOKEN_SHIFT*i;
					Hexagon h = hlc.getHexagon(bt.getX(), bt.getY());
					Ellipse2D.Double circle = new Ellipse2D.Double(h.getxC()+damageTokenShift, h.getyC(), DAMAGE_TOKEN_DIAMETER, DAMAGE_TOKEN_DIAMETER);
					BufferedImage image = null;
					if (cache.getImage("damageToken") == null) {
						try {
							image = ImageIO.read(new File("pics/damageToken.jpg"));
						} catch (IOException e) {
							System.out.println("Damage token image not found.");
						}
					} else {
						image = cache.getImage("damageToken");
					}

					TexturePaint tex = new TexturePaint(image, circle.getBounds2D());
					g2.setPaint(tex);
					g2.fillOval(h.getxC()+damageTokenShift, h.getyC(), DAMAGE_TOKEN_DIAMETER, DAMAGE_TOKEN_DIAMETER);
				}
			}
		}
	}

	public void drawHex(Hexagon h, AbstractTile t) {

		Polygon poly = h.createHex();

		String imageName = null;
		BufferedImage image = null;

		String playerDeckName = null;
		if (t != null) {
			if (t.getPlayer() != null) {
				playerDeckName = t.getPlayer().getPlayerDeck().getDeckName();
			} else {
				playerDeckName = game.getCurrentPlayer().getPlayerDeck().getDeckName();
			}

			imageName = playerDeckName + "." + t.getName() + t.getAngle();
			image = cache.getImage(imageName);
		}

		TexturePaint tex = null;
		if (image != null) {
			tex = new TexturePaint(image, poly.getBounds2D());
			g2.setPaint(tex);
			g2.fillPolygon(poly);

			if (h.getTileX() != null && h.getTileY() != null) {
				drawSpecialHexBorder(h, poly);
			}
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

		if (tex != null && (!(t instanceof BoardTile) || t.getAngle() == 0)) {
			g2.setPaint(tex);
			cache.addToCache(imageName, image);
		}
		else if (tex != null && t instanceof BoardTile && t.getAngle() != 0) {

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

		if (h.getTileX() != null && h.getTileY() != null) {
			drawSpecialHexBorder(h, poly);
		}


	}

	private void drawSpecialHexBorder(Hexagon h, Polygon poly) {

		SpecialHex specialHexPair = new SpecialHex(new Pair(h.getTileX(), h.getTileY()), null);
		if (hlc.getSpecialHexList() != null && hlc.getSpecialHexList().contains(specialHexPair)) {
			SpecialHex sh = hlc.getSpecialHexList().get(hlc.getSpecialHexList().indexOf(specialHexPair));
			drawHexBorder(sh);
		} else {
			g2.setColor(Color.BLACK);
			g2.drawPolygon(poly);
		}
	}

	private void drawHexBorder(SpecialHex sh) {
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(SPECIAL_STROKE_SIZE));
		g2.setColor(sh.getColor());

		Hexagon h = hlc.getHexagon(sh.getCoordinates().getX(), sh.getCoordinates().getY());

		Polygon poly = h.createHex();

		g2.drawLine(poly.xpoints[0], poly.ypoints[0]-SPECIAL_DISTANCE, poly.xpoints[1]+SPECIAL_DISTANCE, poly.ypoints[1]);
		g2.drawLine(poly.xpoints[1]+SPECIAL_DISTANCE, poly.ypoints[1], poly.xpoints[2]+SPECIAL_DISTANCE, poly.ypoints[2]);
		g2.drawLine(poly.xpoints[2]+SPECIAL_DISTANCE, poly.ypoints[2], poly.xpoints[3], poly.ypoints[3]+SPECIAL_DISTANCE);
		g2.drawLine(poly.xpoints[3], poly.ypoints[3]+SPECIAL_DISTANCE, poly.xpoints[4]-SPECIAL_DISTANCE, poly.ypoints[4]);
		g2.drawLine(poly.xpoints[4]-SPECIAL_DISTANCE, poly.ypoints[4], poly.xpoints[5]-SPECIAL_DISTANCE, poly.ypoints[5]);
		g2.drawLine(poly.xpoints[5]-SPECIAL_DISTANCE, poly.ypoints[5], poly.xpoints[0], poly.ypoints[0]-SPECIAL_DISTANCE);

		g2.setStroke(oldStroke);
	}

	private BufferedImage cropImage(BufferedImage src) {
		BufferedImage dest = src.getSubimage(0, 0, 230, 270);
		return dest;
	}
}
