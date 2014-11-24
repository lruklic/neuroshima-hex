package hr.nhex.graphic.hexagon;

import hr.nhex.generic.Pair;

import java.util.List;

public class HexagonListContainer {

	private List<Hexagon> hexagonList;
	private List<Hexagon> hexagonSideList;

	private Hexagon draggedHexagon;

	private List<Pair> specialHexList;

	public HexagonListContainer(List<Hexagon> hexagonList, List<Hexagon> hexagonSideList, Hexagon draggedHexagon,
			List<Pair> specialHexList) {
		this.hexagonList = hexagonList;
		this.hexagonSideList = hexagonSideList;
		this.draggedHexagon = draggedHexagon;
		this.specialHexList = specialHexList;
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

	public List<Pair> getSpecialHexList() {
		return specialHexList;
	}

	public void setSpecialHexList(List<Pair> specialHexList) {
		this.specialHexList = specialHexList;
	}



}
