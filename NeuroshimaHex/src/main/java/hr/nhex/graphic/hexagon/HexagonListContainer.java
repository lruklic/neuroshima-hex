package hr.nhex.graphic.hexagon;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class that contains all hexagon component that are used in one instance of Neuroshima game.
 *
 * @author Luka Ruklic
 * @author Marin Buzancic
 *
 */

public class HexagonListContainer {

	private static HexagonListContainer hlc;

	public static final int HEX_GAP = 10;

	private List<Hexagon> hexagonList = new ArrayList<>();
	private List<Hexagon> hexagonSideList = new ArrayList<>();

	private Hexagon draggedHexagon;

	private List<SpecialHex> specialHexList = new ArrayList<>();

	private int hexSize = 0;

	protected HexagonListContainer() {

	}

	public static HexagonListContainer getInstance() {
		if (hlc == null) {
			hlc = new HexagonListContainer();
		}
		return hlc;

	}

	public HexagonListContainer prepareHexagonContainer(int windowHeight, int windowWidth) {
		clearHexagonLists();
		this.hexSize = calculateHexSize(windowHeight, windowWidth);

		// Fill out hexagon lists if they are empty
		if (hexagonList.isEmpty() || hexagonSideList.isEmpty()) {
			fillEmptyHexagonLists(windowHeight, windowWidth, hexSize);
		}

		return this;
	}

	/**
	 * Method that fills empty hexagon lists that represents board tiles and drawn tiles.
	 *
	 * @param windowHeight window height
	 * @param windowWidth window width
	 * @param hexSize hexagon size
	 */
	private void fillEmptyHexagonLists(int windowHeight, int windowWidth, int hexSize) {

		clearHexagonLists();
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

	public Hexagon getHexagon(int x, int y) {
		for (Hexagon hex : hexagonList) {
			if (hex.getTileX() == x && hex.getTileY() == y) {
				return hex;
			}
		}
		return null;
	}

	/**
	 * Method that clears all hexagon lists.
	 */
	public void clearHexagonLists() {
		hexagonList.clear();
		hexagonSideList.clear();
	}

	public void clearHexSpecialList() {
		specialHexList.clear();
	}

	public List<Hexagon> getHexagonList() {
		return hexagonList;
	}

	public void setHexagonList(List<Hexagon> hexagonList) {
		this.hexagonList = hexagonList;
	}

	public List<Hexagon> getHexagonSideList() {
		return hexagonSideList;
	}

	public void setHexagonSideList(List<Hexagon> hexagonSideList) {
		this.hexagonSideList = hexagonSideList;
	}

	public Hexagon getDraggedHexagon() {
		return draggedHexagon;
	}

	public void setDraggedHexagon(Hexagon draggedHexagon) {
		this.draggedHexagon = draggedHexagon;
	}

	public List<SpecialHex> getSpecialHexList() {
		return specialHexList;
	}

	public void setSpecialHexList(List<SpecialHex> specialHexList) {
		this.specialHexList = specialHexList;
	}

	public int getHexSize() {
		return hexSize;
	}

	public int getHexGap() {
		return HEX_GAP;
	}



}
