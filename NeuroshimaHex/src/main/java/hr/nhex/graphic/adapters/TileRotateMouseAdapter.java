package hr.nhex.graphic.adapters;

import hr.nhex.board.BoardTile;
import hr.nhex.game.TurnPhase;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileRotateMouseAdapter extends MouseAdapter {

	private boolean listenerOn = false;

	private NeuroshimaCanvas cn;

	private TilePlacementMouseAdapter tpma;

	private BoardTile selectedTile;

	private Cursor c;

	public TileRotateMouseAdapter(NeuroshimaCanvas cn) {
		this.cn = cn;

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image rotateImage = toolkit.getImage("icons/rotate.png");
		this.c = toolkit.createCustomCursor(rotateImage , new Point(cn.getX(),
				cn.getY()), "img");
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (listenerOn) {

			// ovaj dio treba urediti, dogovor oko toga gdje se klikovi priznaju u odnosu na heksagon

			if (ev.getX() < (cn.getDraggedHexagon().getxC()-cn.getHexSize())) {
				// ako je kliknuto lijevo, rotiraj lijevo
				selectedTile.setAngle(selectedTile.getAngle()-1);
				System.out.println("Kut: "+selectedTile.getAngle());
				cn.repaint();

			} else if (ev.getX() > (cn.getDraggedHexagon().getxC()+cn.getHexSize())) {
				// ako je kliknuto desno, rotiraj desno
				selectedTile.setAngle(selectedTile.getAngle()+1);
				//System.out.println("Kut: "+selectedTile.getAngle());
				cn.repaint();
			} else {
				// ako je kliknuto na tile, vrati na drugi adapter
				cn.setCursor(Cursor.getDefaultCursor());

				selectedTile.setPlayer(cn.getGameInstance().getCurrentPlayer());
				cn.getGameInstance().getBoard().addTile(selectedTile);
				tpma.setTileSelected(null);
				cn.setDraggedHexagon(null);
				cn.repaint();

				cn.getGameInstance().setTurnPhase(TurnPhase.TILES_DRAWN);
				this.listenerOn = false;
				tpma.setListenerOn(true);
			}

		}

	}

	@Override
	public void mouseMoved(MouseEvent ev) {

		if (listenerOn) {

			int tileX = cn.getDraggedHexagon().getTileX();
			int tileY = cn.getDraggedHexagon().getTileY();

			Pair p = tpma.getClickedTile(cn, ev);
			if (p != null && p.getX() == tileX && p.getY() == tileY) {
				cn.setCursor(Cursor.getDefaultCursor());
			} else {
				cn.setCursor(this.c);
			}

		}

	}

	public TilePlacementMouseAdapter getTpma() {
		return tpma;
	}

	public void setTpma(TilePlacementMouseAdapter tpma) {
		this.tpma = tpma;
	}

	public void setSelectedTile(BoardTile selectedTile) {
		this.selectedTile = selectedTile;
	}

	public void setListenerOn(boolean listenerOn) {
		this.listenerOn = listenerOn;
	}

}
