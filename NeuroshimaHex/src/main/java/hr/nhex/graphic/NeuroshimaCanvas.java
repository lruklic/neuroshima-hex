package hr.nhex.graphic;

import hr.nhex.board.Board;
import hr.nhex.decks.implementation.BorgoDeck;
import hr.nhex.decks.implementation.HegemonyDeck;
import hr.nhex.game.Game;
import hr.nhex.graphic.adapters.CanvasResizeComponentAdapter;
import hr.nhex.graphic.buttons.DrawActionListener;
import hr.nhex.graphic.buttons.EndTurnActionListener;
import hr.nhex.graphic.hexagon.BoardDrawer;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.imagecache.ImageCache;
import hr.nhex.graphic.labels.LabelContainer;
import hr.nhex.graphic.mouse.GenericMouseAdapter;
import hr.nhex.model.player.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that represents top level container for NHex playing board.
 *
 * @author Luka Ruklic
 * @author Marin Buzancic
 *
 */

public class NeuroshimaCanvas extends JPanel {

	private static final long serialVersionUID = 1L;

	private Game gameInstance;

	/**
	 * TEST LABEL
	 */
	private JLabel label;

	LabelContainer lc = new LabelContainer();

	private BufferedImage backgroundImage;

	private ImageCache cache = new ImageCache();

	// private MouseAdapterContainer mac = new MouseAdapterContainer();

	private GenericMouseAdapter genericMouseAdapter;

	public NeuroshimaCanvas(JFrame mainWindow, Game gameInstance) {

		this.label = new JLabel("Neuroshima game has begun.");
		this.add(label, BorderLayout.NORTH);

		// TEST-RUN (this should go in NeuroshimaHex.java)

		Player player1 = new Player("Luka", Color.BLUE, new BorgoDeck());
		Player player2 = new Player("Marin", Color.YELLOW, new HegemonyDeck());

		List<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);

		lc.createHqHpLabels(players);

		for (Player player : players) {
			this.add(lc.getHqHpLabel(player.getPlayerDeck().getDeckName()));
		}

		Board board = new Board();

		this.gameInstance = new Game(board, players);
		addMouseAdapters();

		addBtn();
	}

	private void addMouseAdapters() {
		addComponentListener(new CanvasResizeComponentAdapter());

		this.genericMouseAdapter = new GenericMouseAdapter(this);
		addMouseListener(genericMouseAdapter);
		addMouseMotionListener(genericMouseAdapter);

		// mac.registerAll(this);
	}

	private void addBtn() {
		JButton newTilesBtn = new JButton("Draw!");
		newTilesBtn.addActionListener(new DrawActionListener(gameInstance));
		this.add(newTilesBtn, BorderLayout.SOUTH);

		JButton endTurnBtn = new JButton("End Turn");
		endTurnBtn.addActionListener(new EndTurnActionListener(gameInstance));
		this.add(endTurnBtn, BorderLayout.SOUTH);

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		super.paintComponent(g2);

		if (this.backgroundImage == null) {
			try {
				backgroundImage = ImageIO.read(new File("background2.jpg"));
			} catch (IOException e) {
				System.out.println("Background image not found.");
			}
		}
		g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

		// Calculating hexagon size from window height and width
		int windowHeight = this.getHeight();
		int windowWidth = this.getWidth();

		BoardDrawer bd = new BoardDrawer(g2, cache, gameInstance, HexagonListContainer.getInstance().prepareHexagonContainer(windowHeight, windowWidth));

		bd.drawAllHex();

		lc.updateHp(gameInstance.getBoard());

	}

	public Game getGameInstance() {
		return gameInstance;
	}

	public GenericMouseAdapter getGenericMouseAdapter() {
		return genericMouseAdapter;
	}

}
