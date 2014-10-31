package hr.nhex.graphic;

import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.decks.implementation.BorgoDeck;
import hr.nhex.decks.implementation.HegemonyDeck;
import hr.nhex.game.Game;
import hr.nhex.graphic.adapters.CanvasResizeComponentAdapter;
import hr.nhex.graphic.adapters.TilePlacementMouseAdapter;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.model.Player;
import hr.nhex.model.Tile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class that represents canvas that contains NHex playing board.
 * 
 * @author Luka Rukliæ
 * @author Marin Bužanèiæ
 *
 */

public class NeuroshimaCanvas extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFrame mainWindow;

	private Game gameInstance;

	private int hexSize;

	private List<Hexagon> hexagonList = new ArrayList<>();

	private List<Hexagon> hexagonSideList = new ArrayList<>();

	public NeuroshimaCanvas(JFrame mainWindow, Game gameInstance) {

		addComponentListener(new CanvasResizeComponentAdapter());

		TilePlacementMouseAdapter tpma = new TilePlacementMouseAdapter();

		addMouseListener(tpma);
		addMouseMotionListener(tpma);

		this.mainWindow = mainWindow;

		JButton newTilesBtn = new JButton("Draw!");
		newTilesBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getGameInstance().getCurrentPlayerGameDeck().drawNew();
				repaint();
			}
		});
		this.add(newTilesBtn, BorderLayout.SOUTH);

		// TEST-RUN (this should go in NeuroshimaHex.java)

		Player player1 = new Player("Luka", Color.YELLOW, new HegemonyDeck());
		Player player2 = new Player("Marin", Color.BLUE, new BorgoDeck());
		List<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);

		HegemonyDeck deck = new HegemonyDeck();
		for (Tile tile : deck.getTiles()) {
			if (tile instanceof BoardTile) {
				((BoardTile)tile).setPlayer(player1);
			}
		}

		Board board = new Board();

		board.addTile((BoardTile)deck.getTileByName("Officer One"), 0, 0, 0);
		board.addTile((BoardTile)deck.getTileByName("Ganger"), 0, 1, 0);
		board.addTile((BoardTile)deck.getTileByName("Runner"), 1, 0, 2);

		this.gameInstance = new Game(board, players);

	}

	public void repaintComponentPart(Graphics g, Tile t, int xClick, int yClick) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int windowHeight = this.getHeight();
		int windowWidth = this.getWidth();

		int hexSizeX = windowHeight / 12;		// numbers have to be adjusted
		int hexSizeY = (int) (windowWidth / (12*(Math.sqrt(3)/2)));

		if (hexSizeX < hexSizeY) {
			hexSize = hexSizeX;
		} else {
			hexSize = hexSizeY;
		}

		Hexagon h = new Hexagon(-5, -5, xClick, yClick, hexSize);
		h.drawDrawnHex(g2, t, gameInstance.getCurrentPlayer());
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//super.paintComponent(g2);

		hexagonList.clear();
		hexagonSideList.clear();

		int windowHeight = this.getHeight();
		int windowWidth = this.getWidth();

		int hexSizeX = windowHeight / 12;		// numbers have to be adjusted
		int hexSizeY = (int) (windowWidth / (12*(Math.sqrt(3)/2)));

		if (hexSizeX < hexSizeY) {
			hexSize = hexSizeX;
		} else {
			hexSize = hexSizeY;
		}

		for (int m = -2; m <= 2; m++) {
			for (int n = -2; n <= 2; n++) {
				if (Math.abs(m + n) <= 2) {

					double x = (windowWidth/2) + (Math.sqrt(3)*hexSize)*m+((Math.sqrt(3)/2)*hexSize)*n;
					double y = (windowHeight/2) + ((-1.5)*hexSize)*n;

					hexagonList.add(new Hexagon(m, n, (int)x,(int)y,hexSize));
				}
			}
		}

		for (Hexagon h : hexagonList) {
			BoardTile t = this.gameInstance.getBoard().getTile(h.getTileX(), h.getTileY());		// just boardTile drawing
			h.drawHex(g2, t);
		}

		hexagonSideList.add(new Hexagon(3, 0, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - hexSize, hexSize));
		hexagonSideList.add(new Hexagon(3, 1, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - 3*hexSize, hexSize));
		hexagonSideList.add(new Hexagon(3, 2, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - 5*hexSize, hexSize));

		List<Tile> currentDrawnTiles = Arrays.asList(getGameInstance().getCurrentPlayerGameDeck().getDrawnTiles());

		int tileNo = 0;
		for (Hexagon h : hexagonSideList) {
			if (currentDrawnTiles.size() > tileNo) {
				h.drawDrawnHex(g2, currentDrawnTiles.get(tileNo), gameInstance.getCurrentPlayer());
			}
			tileNo++;
		}

	}

	public Game getGameInstance() {
		return gameInstance;
	}

	public int getHexSize() {
		return hexSize;
	}

	public Hexagon getHexagon(int x, int y) {
		for (Hexagon hex : hexagonList) {
			if (hex.getTileX() == x && hex.getTileY() == y) {
				return hex;
			}
		}
		return null;
	}

}
