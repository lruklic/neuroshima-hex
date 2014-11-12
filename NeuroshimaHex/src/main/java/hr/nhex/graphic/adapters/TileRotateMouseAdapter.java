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

/**
 * Class that represents mouse listener that is used when tile need to be
 * rotated in the game.
 * 
 * @author Luka Rukliæ
 *
 */

public class TileRotateMouseAdapter extends MouseAdapter implements IMouseAdapter{

	/**
	 * Variable that defines whether the listener is listening or not (is it on or off).
	 */
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
				cn.repaint();

			} else if (ev.getX() > (cn.getDraggedHexagon().getxC()+cn.getHexSize())) {
				// ako je kliknuto desno, rotiraj desno
				selectedTile.setAngle(selectedTile.getAngle()+1);
				//System.out.println("Kut: "+selectedTile.getAngle());
				cn.repaint();
			} else {
				// ako je kliknuto na tile, vrati na drugi adapter
				cn.setCursor(Cursor.getDefaultCursor());

				// If tile is already filled, then don't add new tile to board
				if (!cn.getGameInstance().getBoard().isFilled(selectedTile.getX(), selectedTile.getY())) {
					selectedTile.setPlayer(cn.getGameInstance().getCurrentPlayer());
					cn.getGameInstance().getBoard().addTile(selectedTile);
				}

				tpma.setTileSelected(null);
				cn.setDraggedHexagon(null);
				cn.repaint();

				cn.getGameInstance().setTurnPhase(TurnPhase.TILES_DRAWN);

				cn.mouseListenerActivate(tpma);
			}

		}

	}

	@Override
	public void mouseMoved(MouseEvent ev) {

		if (listenerOn) {

			int tileX = cn.getDraggedHexagon().getTileX();
			int tileY = cn.getDraggedHexagon().getTileY();

			Pair p = TilePlacementMouseAdapter.getClickedTile(cn, ev);
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

	public BoardTile getSelectedTile() {
		return selectedTile;
	}

	public void setSelectedTile(BoardTile selectedTile) {
		this.selectedTile = selectedTile;
	}

	@Override
	public void setListenerOn() {
		this.listenerOn = true;
	}

	@Override
	public void setListenerOff() {
		this.listenerOn = false;
	}

}
