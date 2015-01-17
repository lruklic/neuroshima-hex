package hr.nhex.graphic.canvas.buttons;

import hr.nhex.game.Game;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.HexagonListContainer;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

/**
 * Container for all the buttons in Neuroshima canvas. Contains draw button, end turn button and
 * discard buttons for each drawn tile.
 *
 * @author Luka Ruklic
 * @author Marin Buzancic
 *
 */

public class ButtonContainer {

	/**
	 * Number of drawn tiles per round. Default is 3.
	 */
	private static final int DISCARD_BUTTON_NUMBER = 3;

	private JButton drawButton;
	private JButton endButton;

	private List<DiscardButton> discardButtons = new ArrayList<DiscardButton>();

	public void initialize(Game game) {
		for (int discardButtonNumber = 0; discardButtonNumber < DISCARD_BUTTON_NUMBER; discardButtonNumber++) {
			DiscardButton db = new DiscardButton(this, DISCARD_BUTTON_NUMBER - discardButtonNumber - 1, game);
			//db.setToolTipText("D"+String.valueOf(discardButtonNumber+1));
			discardButtons.add(db);
		}
	}

	public JButton getDrawButton() {
		return drawButton;
	}
	public JButton getEndButton() {
		return endButton;
	}
	public void setDrawButton(JButton drawButton) {
		this.drawButton = drawButton;
	}
	public void setEndButton(JButton endButton) {
		this.endButton = endButton;
	}

	public void toggleDrawButton(boolean state) {
		drawButton.setEnabled(state);
	}

	public void toggleEndTurnButton(boolean state) {
		endButton.setEnabled(state);
	}

	public void disableAllButtons() {
		drawButton.setEnabled(false);
		endButton.setEnabled(false);
	}

	public void setButtonLocations(int windowWidth, int windowHeight, int hexSize, int hexGap) {
		for (int i = 0; i < DISCARD_BUTTON_NUMBER; i++) {
			//System.out.println("Width: "+windowWidth+" height: "+windowHeight+" hexsize: "+hexSize);
			DiscardButton btn = discardButtons.get(i);
			btn.setButtonSize(HexagonListContainer.getInstance().getHexSize()/2);
			btn.setLocation((int)(windowWidth - (1.5*Math.sqrt(3)*hexSize) - btn.getSize().height/2), windowHeight - (hexSize + 2*hexSize*i) - btn.getSize().height/2);
		}
	}

	public void addButtonsToCanvas(NeuroshimaCanvas cn) {
		cn.add(drawButton, BorderLayout.SOUTH);
		cn.add(endButton, BorderLayout.SOUTH);
		endButton.setEnabled(false);
		for (DiscardButton btn : discardButtons) {
			cn.add(btn);
		}
	}

	public void makeButtonsTransparent(boolean transparent) {
		if (transparent) {
			for (DiscardButton btn : discardButtons) {
				drawButton.setVisible(false);
				endButton.setVisible(false);
				btn.setTransparent(true);
			}
		} else {
			for (DiscardButton btn : discardButtons) {
				drawButton.setVisible(true);
				endButton.setVisible(true);
				btn.setTransparent(false);
			}
		}
	}

}
