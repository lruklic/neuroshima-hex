package hr.nhex.graphic;

import hr.nhex.board.Board;
import hr.nhex.decks.implementation.BorgoDeck;
import hr.nhex.decks.implementation.HegemonyDeck;
import hr.nhex.game.Game;
import hr.nhex.game.GamePhase;
import hr.nhex.game.TurnPhase;
import hr.nhex.graphic.adapters.CanvasResizeComponentAdapter;
import hr.nhex.graphic.adapters.MouseAdapterContainer;
import hr.nhex.graphic.hexagon.BoardDrawer;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.imagecache.ImageCache;
import hr.nhex.model.player.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	private BufferedImage backgroundImage;

	private ImageCache cache = new ImageCache();

	private MouseAdapterContainer mac = new MouseAdapterContainer();

	public NeuroshimaCanvas(JFrame mainWindow, Game gameInstance) {

		addBtn();

		this.label = new JLabel("Neuroshima game has begun.");
		this.add(label, BorderLayout.NORTH);

		// TEST-RUN (this should go in NeuroshimaHex.java)

		Player player1 = new Player("Luka", Color.BLUE, new BorgoDeck());
		Player player2 = new Player("Marin", Color.YELLOW, new HegemonyDeck());
		List<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);

		Board board = new Board();

		this.gameInstance = new Game(board, players);
		addMouseAdapters();

	}

	private void addMouseAdapters() {
		addComponentListener(new CanvasResizeComponentAdapter());
		mac.registerAll(this);
	}

	private void addBtn() {
		JButton newTilesBtn = new JButton("Draw!");
		newTilesBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = getGameInstance();
				if (game.getGamePhase() == GamePhase.GAME_START) {
					label.setText("Please place your HQ");
					game.setGamePhase(GamePhase.HQ_SETUP);
					game.setTurnPhase(TurnPhase.TILES_DRAWN);
				} else if (game.getGamePhase() == GamePhase.HQ_SETUP) {
					game.getCurrentPlayerDeck().drawHQ();
				} else {
					game.getCurrentPlayerDeck().shuffleDeck();
					game.getCurrentPlayerDeck().drawNew();
					gameInstance.setTurnPhase(TurnPhase.DISCARD_PHASE);
				}
				repaint();
			}
		});
		this.add(newTilesBtn, BorderLayout.SOUTH);

		JButton endTurnBtn = new JButton("End Turn");
		endTurnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGameInstance().getBoard().numberOfHQ() == getGameInstance().getNumberOfPlayers()) {
					getGameInstance().setGamePhase(GamePhase.PLAYER_TURN);
				}
				//getGameInstance().getCurrentPlayerGameDeck().discardAllTiles();
				getGameInstance().nextPlayerTurn();
				repaint();
			}
		});
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
				backgroundImage = ImageIO.read(new File("background.jpg"));
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

	}

	public Game getGameInstance() {
		return gameInstance;
	}

	public MouseAdapterContainer getMac() {
		return mac;
	}

}
