package hr.nhex.graphic;

import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.decks.implementation.BorgoDeck;
import hr.nhex.decks.implementation.HegemonyDeck;
import hr.nhex.game.Game;
import hr.nhex.graphic.adapters.CanvasResizeComponentAdapter;
import hr.nhex.graphic.adapters.TilePlacementMouseAdapter;
import hr.nhex.graphic.adapters.TileRotateMouseAdapter;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.graphic.imagecache.ImageCache;
import hr.nhex.model.Player;
import hr.nhex.model.Tile;

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
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
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
	public final int HEX_GAP = 10;

	private Game gameInstance;

	private int hexSize;

	private Hexagon draggedHexagon;
	private Tile draggedTile;
	private int draggedNo;

	private List<Hexagon> hexagonList = new ArrayList<>();

	private List<Hexagon> hexagonSideList = new ArrayList<>();

	private BufferedImage img;

	private ImageCache cache = new ImageCache();

	private TilePlacementMouseAdapter tpma = new TilePlacementMouseAdapter(this);
	private TileRotateMouseAdapter trma = new TileRotateMouseAdapter(this);

	public NeuroshimaCanvas(JFrame mainWindow, Game gameInstance) {

		addComponentListener(new CanvasResizeComponentAdapter());

		tpma.setTrma(trma);
		trma.setTpma(tpma);

		addMouseListener(tpma);
		addMouseMotionListener(tpma);

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

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g2);

		if (this.img == null) {
			try {
				img = ImageIO.read(new File("background.jpg"));
			} catch (IOException e) {
				System.out.println("Background image not found.");
			}
		}
		g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);

		// Calculating hexagon size from window height and width
		int windowHeight = this.getHeight();
		int windowWidth = this.getWidth();

		this.hexSize = calculateHexSize(windowHeight, windowWidth);

		// Fill out hexagon lists if they are empty
		if (hexagonList.isEmpty() || hexagonSideList.isEmpty()) {
			fillEmptyHexagonLists(windowHeight, windowWidth, hexSize);
		}

		// Draw hexagons that are in hexagon lists
		for (Hexagon h : hexagonList) {
			BoardTile t = this.gameInstance.getBoard().getTile(h.getTileX(), h.getTileY());		// just boardTile drawing
			h.drawHex(g2, cache, t, null);
		}
		List<Tile> currentDrawnTiles = Arrays.asList(getGameInstance().getCurrentPlayerGameDeck().getDrawnTiles());
		int tileNo = 0;

		for (Hexagon h : hexagonSideList) {
			if (currentDrawnTiles.size() > tileNo) {
				h.drawHex(g2, cache, currentDrawnTiles.get(tileNo), gameInstance.getCurrentPlayer());
			}
			tileNo++;
		}

		if (draggedHexagon != null) {
			draggedHexagon.drawHex(g2, cache, tpma.getTileSelected(), gameInstance.getCurrentPlayer());
		}

	}

	/**
	 * Method that calculates hexagon size on tile board from window height and width.
	 * 
	 * @param windowHeight window height
	 * @param windowWidth window width
	 * @return calculated hexagon size in pixels
	 */
	private int calculateHexSize(int windowHeight, int windowWidth) {

		int hexSizeX = windowHeight / 12;		// numbers have to be adjusted
		int hexSizeY = (int) (windowWidth / (12*(Math.sqrt(3)/2)));

		if (hexSizeX < hexSizeY) {
			return hexSizeX;
		} else {
			return hexSizeY;
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

	public List<Hexagon> getHexagonList() {
		return hexagonList;
	}

	/**
	 * Method that fills empty hexagon lists that represents board tiles and drawn tiles.
	 * 
	 * @param windowHeight window height
	 * @param windowWidth window width
	 * @param hexSize hexagon size
	 */
	private void fillEmptyHexagonLists(int windowHeight, int windowWidth, int hexSize) {

		for (int m = -2; m <= 2; m++) {
			for (int n = -2; n <= 2; n++) {
				if (Math.abs(m + n) <= 2) {

					double x = (windowWidth/2) + (Math.sqrt(3)*(hexSize+(HEX_GAP/2))*m+((Math.sqrt(3)/2)*(hexSize+(HEX_GAP/2)))*n);
					double y = (windowHeight/2) + ((-1.5)*(hexSize+(HEX_GAP/2)))*n;

					hexagonList.add(new Hexagon(m, n, (int)x,(int)y,hexSize));
				}
			}
		}

		hexagonSideList.add(new Hexagon(3, 0, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - hexSize, hexSize));
		hexagonSideList.add(new Hexagon(3, 1, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - 3*hexSize, hexSize));
		hexagonSideList.add(new Hexagon(3, 2, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - 5*hexSize, hexSize));
	}

	/**
	 * Method that clears all hexagon lists.
	 */
	public void clearHexagonLists() {
		hexagonList.clear();
		hexagonSideList.clear();
	}

	public Hexagon getDraggedHexagon() {
		return draggedHexagon;
	}

	public int getDraggedNo() {
		return draggedNo;
	}

	public void setDraggedHexagon(Hexagon draggedHexagon) {
		this.draggedHexagon = draggedHexagon;
	}

	public void setDraggedNo(int draggedNo) {
		this.draggedNo = draggedNo;
	}

	public Tile getDraggedTile() {
		return draggedTile;
	}

	public void setDraggedTile(Tile draggedTile) {
		this.draggedTile = draggedTile;
	}





}
