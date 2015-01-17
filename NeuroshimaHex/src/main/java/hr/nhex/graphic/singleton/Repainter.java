package hr.nhex.graphic.singleton;

import hr.nhex.graphic.canvas.NeuroshimaCanvas;

public class Repainter {

	private static NeuroshimaCanvas cn;

	public static void setCanvas(NeuroshimaCanvas canvas) {
		cn = canvas;
	}

	public static void repaint() {
		cn.repaint();
	}

}
