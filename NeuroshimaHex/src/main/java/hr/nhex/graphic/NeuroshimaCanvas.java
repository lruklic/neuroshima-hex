package hr.nhex.graphic;

import hr.nhex.generic.Pair;
import hr.nhex.graphic.hexagon.Hexagon;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class that represents canvas that contains NHex playing board.
 * 
 * @author Luka Rukliæ
 * @author Marin Bužanèiæ
 *
 */

public class NeuroshimaCanvas extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFrame mainWindow;
	
	private int hexSize;
	
	private List<Hexagon> hexagonList = new ArrayList<>();
	
	public NeuroshimaCanvas(JFrame mainWindow) {
		this.mainWindow = mainWindow;
		initCanvas();
	}

	private void initCanvas() {
		int windowHeight = mainWindow.getHeight();
		int windowWidth = mainWindow.getWidth();
				
		for (int m = -2; m <= 2; m++) {
			for (int n = -2; n <= 2; n++) {
				if (Math.abs(m + n) <= 2) {
					double x = (windowWidth/2) + (Math.sqrt(3)*hexSize)*m+((Math.sqrt(3)/2)*hexSize)*n;
					double y = (windowHeight/2) + ((-3/2)*hexSize)*n;
					
					int hexSizeX = windowHeight / 6;
					int hexSizeY = (int) (windowWidth / (7*(Math.sqrt(3)/2)));
					
					if (hexSizeX < hexSizeY) {
						hexSize = hexSizeX;
					} else {
						hexSize = hexSizeY;
					}
					
					hexagonList.add(new Hexagon((int)x,(int)y,hexSize));
				}
			}
		}
		System.out.println("hex board init success");
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g2);
		for (Hexagon h : hexagonList) {
			h.drawHex(g2);
		}
	}
	
}
