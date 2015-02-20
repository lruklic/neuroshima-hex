package hr.nhex.graphic.canvas.buttons;

import hr.nhex.game.Game;
import hr.nhex.graphic.imagecache.ImageCache;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class DiscardButton extends JComponent {

	private static final long serialVersionUID = 1L;

	private int discardButtonNumber;

	private ImageCache cache;

	private int size;
	private boolean transparent = false;

	public DiscardButton(ButtonContainer bc, int discardButtonNumber, ImageCache cache, Game game) {
		super();

		addMouseListener(new DiscardButtonAdapter(bc, discardButtonNumber, game));
		// enableInputMethods(true);

		this.cache = cache;
		this.discardButtonNumber = discardButtonNumber;
		// setFocusable(true);
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (!transparent) {

			Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
			BufferedImage image = null;
			if (cache.getImage("discard") == null) {
				try {
					image = ImageIO.read(new File("pics/discard.jpg"));
				} catch (IOException e) {
					System.out.println("Discard not found.");
				}
			} else {
				image = cache.getImage("discard");
			}

			TexturePaint tex = new TexturePaint(image, circle.getBounds2D());
			g2.setPaint(tex);

			g2.fillOval(0, 0, size, size);
		}

	}

	public void setButtonSize(int size) {
		setSize(size, size);
		this.size = size;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	public int getDiscardButtonNumber() {
		return discardButtonNumber;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
