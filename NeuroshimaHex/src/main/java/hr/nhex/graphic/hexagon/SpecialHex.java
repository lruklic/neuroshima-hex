package hr.nhex.graphic.hexagon;

import hr.nhex.generic.Pair;

import java.awt.Color;

public class SpecialHex {

	private Pair coordinates;
	private Color color;

	public SpecialHex(Pair coordinates, Color color) {
		this.coordinates = coordinates;
		this.color = color;
	}

	public Pair getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Pair coordinates) {
		this.coordinates = coordinates;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coordinates == null) ? 0 : coordinates.hashCode());
		return result;
	}

	/**
	 * Compares two special hexagons by their coordinates. If coordinates are the same, hexagons are presumed to be the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SpecialHex other = (SpecialHex) obj;
		if (coordinates == null) {
			if (other.coordinates != null) {
				return false;
			}
		} else if (!coordinates.equals(other.coordinates)) {
			return false;
		}
		return true;
	}

}
