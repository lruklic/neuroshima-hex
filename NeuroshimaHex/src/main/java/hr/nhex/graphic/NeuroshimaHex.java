package hr.nhex.graphic;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class NeuroshimaHex extends JFrame {
	
	private NeuroshimaCanvas canvas;

	public NeuroshimaHex() {
		initGui();
	}

	private void initGui() {

		this.setTitle("Neuroshima Hex");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);

		createActions();
		createMenu();
		
		this.canvas = new NeuroshimaCanvas(this);
		this.getContentPane().add(canvas);
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("Play");
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newGameAction));

		this.setJMenuBar(menuBar);
	}

	private void createActions() {

		newGameAction.putValue(
				Action.NAME,
				"New Game");
		newGameAction.putValue(
				Action.SHORT_DESCRIPTION,
				"Starts new Neuroshima Hex game.");
	}

	private Action newGameAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			NeuroshimaGameOptions.main(null);
			NeuroshimaHex.this.dispose();
		}
	};

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
