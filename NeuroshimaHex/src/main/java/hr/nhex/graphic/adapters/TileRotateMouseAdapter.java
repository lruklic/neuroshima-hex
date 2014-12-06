package hr.nhex.graphic.adapters;

import hr.nhex.board.BoardTile;
import hr.nhex.game.TurnPhase;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.HexagonListContainer;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

/**
 * Class that represents mouse listener that is used when tile need to be
 * rotated in the game.
 *
 * @author Luka Ruklic
 *
 */

public class TileRotateMouseAdapter extends AbstractMouseAdapter {

	private Cursor c;

	public TileRotateMouseAdapter(NeuroshimaCanvas cn) {
		this.cn = cn;
		this.game = cn.getGameInstance();
		this.hlc = HexagonListContainer.getInstance();
		this.type = AdapterType.ROTATE;
		this.setListenerOff();

		// store cursor image to cache?
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image rotateImage = toolkit.getImage("icons/rotate.png");
		this.c = toolkit.createCustomCursor(rotateImage , new Point(cn.getX(),
				cn.getY()), "img");
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (listenerOn) {

			BoardTile bt = ((BoardTile)game.getSelectedTile());

			// ovaj dio treba urediti, dogovor oko toga gdje se klikovi priznaju u odnosu na heksagon

			if (ev.getX() < (hlc.getDraggedHexagon().getxC()-hlc.getHexSize())) {
				// ako je kliknuto lijevo, rotiraj lijevo
				bt.setAngle(game.getSelectedTile().getAngle() + 1);
				//System.out.println("Kut: "+selectedTile.getAngle());
				cn.repaint();

			} else if (ev.getX() > (hlc.getDraggedHexagon().getxC()+hlc.getHexSize())) {
				// ako je kliknuto desno, rotiraj desno
				bt.setAngle(game.getSelectedTile().getAngle() - 1);
				//System.out.println("Kut: "+selectedTile.getAngle());
				cn.repaint();
			} else {
				// ako je kliknuto na tile, vrati na drugi adapter
				cn.setCursor(Cursor.getDefaultCursor());

				// If tile is already filled, then don't add new tile to board
				// Else add it to the board
				if (!game.getBoard().isFilled(bt.getX(), bt.getY())) {
					bt.setPlayer(game.getCurrentPlayer());
					game.getBoard().addTile(bt);
				}

				game.setSelectedTile(null);
				hlc.setDraggedHexagon(null);
				cn.repaint();

				game.setTurnPhase(TurnPhase.TILES_DRAWN);

				cn.getMac().mouseListenerActivate(AdapterType.PLACEMENT);
			}

		}

	}

	@Override
	public void mouseMoved(MouseEvent ev) {

		if (listenerOn) {

			int tileX = hlc.getDraggedHexagon().getTileX();
			int tileY = hlc.getDraggedHexagon().getTileY();

			Pair p = getClickedTile(cn, ev);
			if (p != null && p.getX() == tileX && p.getY() == tileY) {
				cn.setCursor(Cursor.getDefaultCursor());
			} else {
				cn.setCursor(this.c);
			}

		}

	}


}
