package hr.nhex.graphic.canvas.buttons;

import hr.nhex.game.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class DiscardButton extends JComponent {

	private static final long serialVersionUID = 1L;

	private int discardButtonNumber;

	private int size;
	private boolean transparent = false;

	public DiscardButton(ButtonContainer bc, int discardButtonNumber, Game game) {
		super();

		addMouseListener(new DiscardButtonAdapter(bc, discardButtonNumber, game));
		// enableInputMethods(true);

		this.discardButtonNumber = discardButtonNumber;
		// setFocusable(true);
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (!transparent) {
			g2.setColor(Color.RED);
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
