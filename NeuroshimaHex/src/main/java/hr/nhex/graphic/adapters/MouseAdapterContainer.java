package hr.nhex.graphic.adapters;

import hr.nhex.graphic.NeuroshimaCanvas;

import java.util.ArrayList;
import java.util.List;

public class MouseAdapterContainer {

	private List<AbstractMouseAdapter> mouseAdapters = new ArrayList<>();

	public void registerAll(NeuroshimaCanvas cn) {
		mouseAdapters.add(new TileRotateMouseAdapter(cn));
		mouseAdapters.add(new TilePlacementMouseAdapter(cn));
		mouseAdapters.add(new TileMovementMouseAdapter(cn));
		mouseAdapters.add(new TilePushMouseAdapter(cn));

		for (AbstractMouseAdapter ma : mouseAdapters) {
			cn.addMouseListener(ma);
			cn.addMouseMotionListener(ma);
		}
	}
	/**
	 * Method that receives listener instance and turns that listener on and all the other listeners off.
	 *
	 * @param MouseAdapter instance of the one of MouseAdapter subclasses
	 */
	public void mouseListenerActivate(AdapterType type) {
		for (AbstractMouseAdapter a : mouseAdapters) {
			if (a.getType() == type) {
				a.setListenerOn();
			} else {
				a.setListenerOff();
			}
		}
	}

	public void deactivateAll() {
		for (AbstractMouseAdapter a : mouseAdapters) {
			a.setListenerOff();
		}
	}

	/**
	 * Method that return TilePlacementMouseAdapter for canvas registered adapter if it exists.
	 *
	 * @return TilePlacementMouseAdapter if it exists, <code>null</code> otherwise
	 */
	public TilePlacementMouseAdapter getTpma() {
		for (AbstractMouseAdapter ma : mouseAdapters) {
			if (ma instanceof TilePlacementMouseAdapter) {
				return (TilePlacementMouseAdapter) ma;
			}
		}
		return null;
	}

	public TileRotateMouseAdapter getTrma() {
		for (AbstractMouseAdapter ma : mouseAdapters) {
			if (ma instanceof TileRotateMouseAdapter) {
				return (TileRotateMouseAdapter) ma;
			}
		}
		return null;
	}

	public TileMovementMouseAdapter getTmma() {
		for (AbstractMouseAdapter ma : mouseAdapters) {
			if (ma instanceof TileMovementMouseAdapter) {
				return (TileMovementMouseAdapter) ma;
			}
		}
		return null;
	}

}
