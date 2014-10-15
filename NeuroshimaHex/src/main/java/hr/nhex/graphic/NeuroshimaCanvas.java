package hr.nhex.graphic;

import hr.nhex.decks.implementation.HegemonyDeck;
import hr.nhex.game.Game;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.model.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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

public class NeuroshimaCanvas extends JPanel implements ComponentListener {

	private static final long serialVersionUID = 1L;

	private JFrame mainWindow;
	
	private Game gameInstance;
	
	private int hexSize;
	
	private List<Hexagon> hexagonList = new ArrayList<>();
	
	public NeuroshimaCanvas(JFrame mainWindow, Game gameInstance) {
		addComponentListener(this);
		this.mainWindow = mainWindow;
		
		// test-run
		
		Player player1 = new Player("Luka", Color.YELLOW, new HegemonyDeck());
		List<Player> players = new ArrayList<>();
		players.add(player1);
		
		this.gameInstance = new Game(players);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g2);
		
		hexagonList.clear();
		
		int windowHeight = this.getHeight();
		int windowWidth = this.getWidth();
				
		int hexSizeX = windowHeight / 12;
		int hexSizeY = (int) (windowWidth / (12*(Math.sqrt(3)/2)));
		
		if (hexSizeX < hexSizeY) {
			hexSize = hexSizeX;
		} else {
			hexSize = hexSizeY;
		}
		
		for (int m = -2; m <= 2; m++) {
			for (int n = -2; n <= 2; n++) {
				if (Math.abs(m + n) <= 2) {
					
					double x = (windowWidth/2) + (Math.sqrt(3)*hexSize)*m+((Math.sqrt(3)/2)*hexSize)*n;
					double y = (windowHeight/2) + ((-1.5)*hexSize)*n;
					
					hexagonList.add(new Hexagon(m, n, (int)x,(int)y,hexSize));
				}
			}
		}
		
		hexagonList.add(new Hexagon(-3, -3, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - hexSize, hexSize));
		hexagonList.add(new Hexagon(-3, -3, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - 3*hexSize, hexSize));
		hexagonList.add(new Hexagon(-3, -3, (int)(windowWidth - Math.sqrt(3)/2*hexSize), windowHeight - 5*hexSize, hexSize));
		
		for (Hexagon h : hexagonList) {
			h.drawHex(g2);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
