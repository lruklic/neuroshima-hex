package hr.nhex.graphic.window.main;

import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.window.setup.MainMenu;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class NeuroshimaHex extends JFrame {

	private NeuroshimaCanvas canvas;
	private MainMenu mainMenu;

	public NeuroshimaHex() {
		initGui();
	}

	private void initGui() {

		this.setTitle("Neuroshima Hex");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);

		//createActions();
		//createMenu();

		this.getContentPane().setLayout(new BorderLayout());

		this.mainMenu = new MainMenu(this);
		this.getContentPane().add(mainMenu, BorderLayout.CENTER);

	}

	public void startGame() {
		this.getContentPane().removeAll();
		this.canvas = new NeuroshimaCanvas(null);
		this.getContentPane().add(canvas, BorderLayout.CENTER);
		revalidate();
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				NeuroshimaHex nhex = new NeuroshimaHex();
				//nhex.setUndecorated(true);
				nhex.setVisible(true);

			}

		});

	}

}
