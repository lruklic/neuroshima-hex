package hr.nhex.graphic.window.setup.buttons;

import hr.nhex.graphic.window.main.NeuroshimaHex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class NewGameButton extends JComponent {

	private static final long serialVersionUID = 1L;

	private NeuroshimaHex topContainer;

	private boolean isSelected = false;

	public NewGameButton(final NeuroshimaHex topContainer) {
		super();
		this.topContainer = topContainer;
		setSize(200, 100);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				isSelected = false;
				repaint();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				isSelected = true;
				repaint();

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				topContainer.startGame();

			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));

		drawLetterN(g2, 100, 50, 2, 2);
		drawLetterE(g2, 100, 50, 2, 2);
		drawLetterW(g2, 100, 50, 2, 2);

		if (isSelected) {
			g2.setColor(Color.RED);
			drawLetterN(g2, 100, 50, 1, 1);
			drawLetterE(g2, 100, 50, 1, 1);
			drawLetterW(g2, 100, 50, 1, 1);
		}

	}

	private void drawLetterN(Graphics2D g2, int w, int h, int hInset, int wInset) {

		g2.drawLine(wInset, h-hInset, wInset, hInset);
		g2.drawLine(wInset, hInset, wInset+3*h/5, hInset+3*h/5);
		g2.drawLine(wInset+3*h/5, hInset+3*h/5, wInset+3*h/5, hInset);

		g2.drawLine(wInset, h-hInset, wInset+h/5, h-hInset);
		g2.drawLine(wInset+h/5, h-hInset, wInset+h/5, hInset+h/2);
		g2.drawLine(wInset+h/5, hInset+h/2, wInset+3*h/5, h-hInset);

		g2.drawLine(wInset+3*h/5, h-hInset, wInset+4*h/5, h-hInset);
		g2.drawLine(wInset+4*h/5, h-hInset, wInset+4*h/5, hInset);
		g2.drawLine(wInset+4*h/5, hInset, wInset+3*h/5, hInset);

	}

	private void drawLetterE(Graphics2D g2, int w, int h, int hInset, int wInset) {

		int cs = wInset+h; // current size; N

		g2.drawLine(cs, h-hInset, cs+2*h/5, h-hInset);
		g2.drawLine(cs, h-hInset, cs,  hInset+2*h/5);
		g2.drawLine(cs, hInset+2*h/5, cs+2*h/5,  hInset+2*h/5);

		g2.drawLine(cs+2*h/5, hInset+2*h/5, cs+2*h/5,  hInset+2*h/5+h/10);
		g2.drawLine(cs+2*h/5, hInset+2*h/5+h/10, cs+h/10, hInset+2*h/5+h/10);
		g2.drawLine(cs+h/10,  hInset+2*h/5+h/10, cs+h/10, hInset+2*h/5+2*h/10);

		g2.drawLine(cs+h/10,  hInset+2*h/5+2*h/10, cs+3*h/10, hInset+2*h/5+2*h/10);
		g2.drawLine(cs+3*h/10, hInset+2*h/5+2*h/10, cs+3*h/10, hInset+2*h/5+3*h/10);
		g2.drawLine(cs+3*h/10, hInset+2*h/5+3*h/10, cs+h/10, hInset+2*h/5+3*h/10);

		g2.drawLine(cs+h/10, hInset+2*h/5+3*h/10, cs+h/10, hInset+2*h/5+2*h/5);
		g2.drawLine(cs+h/10, hInset+2*h/5+2*h/5, cs+2*h/5, hInset+2*h/5+2*h/5);
		g2.drawLine(cs+2*h/5, hInset+2*h/5+2*h/5, cs+2*h/5, h-hInset);


	}

	private void drawLetterW(Graphics2D g2, int w, int h, int hInset, int wInset) {

		int cs = wInset+15*h/10; // current size; Ne

		g2.drawLine(cs, hInset+2*h/5, cs+h/10, hInset+2*h/5);
		g2.drawLine(cs+h/10, hInset+2*h/5, cs+3*h/10, hInset+4*h/5);
		g2.drawLine(cs+3*h/10, hInset+4*h/5, cs+2*h/5, hInset+3*h/5);

		g2.drawLine(cs+2*h/5, hInset+3*h/5, cs+h/2, hInset+4*h/5);
		g2.drawLine(cs+h/2, hInset+4*h/5, cs+7*h/10, hInset+2*h/5);
		g2.drawLine(cs+7*h/10, hInset+2*h/5, cs+4*h/5, hInset+2*h/5);

		g2.drawLine(cs+4*h/5, hInset+2*h/5, cs+6*h/10, h-hInset);
		g2.drawLine(cs+6*h/10, h-hInset, cs+5*h/10, h-hInset);
		g2.drawLine(cs+5*h/10, h-hInset, cs+4*h/10, hInset+4*h/5);

		g2.drawLine(cs+4*h/10, hInset+4*h/5, cs+3*h/10, h-hInset);
		g2.drawLine(cs+3*h/10, h-hInset, cs+2*h/10, h-hInset);
		g2.drawLine(cs+2*h/10, h-hInset, cs, hInset+2*h/5);

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
