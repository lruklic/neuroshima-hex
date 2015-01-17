package hr.nhex.graphic.canvas;

import hr.nhex.graphic.hexagon.HexagonListContainer;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Component adapter for window resizing.
 *
 * @author Luka Ruklic
 *
 */

public class CanvasResizeComponentAdapter extends ComponentAdapter {

	@Override
	public void componentMoved(ComponentEvent e) {
		HexagonListContainer.getInstance().clearHexagonLists();
		e.getComponent().repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		HexagonListContainer.getInstance().clearHexagonLists();
		e.getComponent().repaint();
	}

}
