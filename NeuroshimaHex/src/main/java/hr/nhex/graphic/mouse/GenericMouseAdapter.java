package hr.nhex.graphic.mouse;

import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.mouse.adapters.AdapterType;
import hr.nhex.graphic.mouse.resolvers.AbstractMouseResolver;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse adapter that resolves mouse events that happen in Neuroshima Game application by calling the correct method in
 * one of the mouse event resolvers.
 *
 * @author Luka Ruklic
 *
 */

public class GenericMouseAdapter extends MouseAdapter {
	/**
	 * Data structure that holds all the mouse resolvers for Neuroshima game.
	 */
	private MouseResolverTree tree;

	public GenericMouseAdapter(NeuroshimaCanvas cn) {
		this.tree = new MouseResolverTree(cn);
	}

	public AbstractMouseResolver getActiveResolver() {
		return tree.getActiveResolver();
	}

	public void disableAllResolvers() {
		tree.disableAllResolvers();
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
