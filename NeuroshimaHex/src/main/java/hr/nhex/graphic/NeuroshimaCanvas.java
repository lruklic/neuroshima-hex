package hr.nhex.graphic;

import hr.nhex.generic.Pair;
import hr.nhex.graphic.hexagon.Hexagon;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class NeuroshimaCanvas extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFrame mainWindow;
	private double hexSize = 5;
	
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
				if (Math.abs(m + n) > 2) {
					double x = (windowWidth/2) + ((-3/2)*hexSize)*n;
					double y = (windowHeight/2) + ((Math.sqrt(2)/2)*hexSize*n) + (Math.sqrt(3)*hexSize*m);
					hexagonList.add(new Hexagon((int)x,(int)y,3));
				}
			}
		}
		System.out.println("hex board init success");
		for (Hexagon h : hexagonList) {
			h.paint(this.getGraphics());
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
	}
	
}
