package hr.nhex.graphic.adapters;

import hr.nhex.game.TurnPhase;
import hr.nhex.graphic.NeuroshimaCanvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TilePlacementMouseAdapter extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		if (((NeuroshimaCanvas)e.getComponent()).getGameInstance().getTurnPhase() == TurnPhase.DISCARD_PHASE) {
			System.out.println("Discard");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

}
