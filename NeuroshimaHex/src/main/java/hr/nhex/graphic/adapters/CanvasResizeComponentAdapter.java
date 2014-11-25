package hr.nhex.graphic.adapters;

import hr.nhex.graphic.NeuroshimaCanvas;

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
		NeuroshimaCanvas cn = ((NeuroshimaCanvas)e.getComponent());
		cn.getHlc().clearHexagonLists();
		e.getComponent().repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		NeuroshimaCanvas cn = ((NeuroshimaCanvas)e.getComponent());
		cn.getHlc().clearHexagonLists();
		e.getComponent().repaint();
	}

}
