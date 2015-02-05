package hr.nhex.graphic.window.setup;

import hr.nhex.graphic.window.main.NeuroshimaHex;
import hr.nhex.graphic.window.setup.buttons.NewGameButton;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage backgroundImage;

	private NewGameButton newGameButton;

	public MainMenu(NeuroshimaHex topContainer) {
		this.newGameButton = new NewGameButton(topContainer);
		this.add(newGameButton);

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		super.paintComponent(g2);

		if (this.backgroundImage == null) {
			try {
				backgroundImage = ImageIO.read(new File("pics/background/background2.jpg"));
			} catch (IOException e) {
				System.out.println("Background image not found.");
			}
		}
		g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

		newGameButton.setLocation((getWidth())/10, getHeight()/4);

	}


}
