package hr.nhex.graphic.hexagon;

import java.util.ArrayList;
import java.util.List;

public class HexagonListContainer {

	private List<Hexagon> hexagonList = new ArrayList<>();
	private List<Hexagon> hexagonSideList = new ArrayList<>();

	private Hexagon draggedHexagon;

	private List<SpecialHex> specialHexList = new ArrayList<>();

	public HexagonListContainer() {

	}

	public HexagonListContainer(List<Hexagon> hexagonList, List<Hexagon> hexagonSideList, Hexagon draggedHexagon,
			List<SpecialHex> specialHexList) {
		this.hexagonList = hexagonList;
		this.hexagonSideList = hexagonSideList;
		this.draggedHexagon = draggedHexagon;
		this.specialHexList = specialHexList;
	}

	/**
	 * Method that clears all hexagon lists.
	 */
	public void clearHexagonLists() {
		hexagonList.clear();
		hexagonSideList.clear();
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



}
