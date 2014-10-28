package hr.nhex.graphic.adapters;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Component adapter for window resizing.
 * 
 * @author Luka Rukliæ
 *
 */

public class CanvasResizeComponentAdapter extends ComponentAdapter {

	@Override
	public void componentMoved(ComponentEvent e) {
		e.getComponent().repaint();
	}

}
