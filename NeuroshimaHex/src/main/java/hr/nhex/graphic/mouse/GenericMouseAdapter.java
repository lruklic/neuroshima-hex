package hr.nhex.graphic.mouse;

import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.adapters.AdapterType;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GenericMouseAdapter extends MouseAdapter {

	private MouseResolverTree tree;

	public GenericMouseAdapter(NeuroshimaCanvas cn) {
		this.tree = new MouseResolverTree(cn);
	}

	public void setActiveAdapterType(AdapterType type) {
		this.tree.setActiveResolver(type);
	}

	@Override
	public void mouseClicked(MouseEvent ev) {
		tree.getActiveResolver().mouseClicked(ev);
	}

	@Override
	public void mouseDragged(MouseEvent ev) {
		tree.getActiveResolver().mouseDragged(ev);
	}

	@Override
	public void mouseMoved(MouseEvent ev) {
		tree.getActiveResolver().mouseMoved(ev);
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		tree.getActiveResolver().mousePressed(ev);
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		tree.getActiveResolver().mouseReleased(ev);
	}

}
