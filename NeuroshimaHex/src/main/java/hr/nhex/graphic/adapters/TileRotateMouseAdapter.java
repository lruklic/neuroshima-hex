package hr.nhex.graphic.adapters;

import hr.nhex.board.BoardTile;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileRotateMouseAdapter extends MouseAdapter {

	private NeuroshimaCanvas cn;

	private TilePlacementMouseAdapter tpma;

	private BoardTile rotatedTile;

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

		// ovaj dio treba urediti, dogovor oko toga gdje se klikovi priznaju u odnosu na heksagon

		if (ev.getX() < (cn.getDraggedHexagon().getxC()-cn.getHexSize())) {
			// ako je kliknuto lijevo, rotiraj lijevo
			rotatedTile.setAngle(rotatedTile.getAngle()-1);
			cn.repaint();

		} else if (ev.getX() > (cn.getDraggedHexagon().getxC()+cn.getHexSize())) {
			// ako je kliknuto desno, rotiraj desno
			rotatedTile.setAngle(rotatedTile.getAngle()+1);
			cn.repaint();
		} else {
			// ako je kliknuto na tile, vrati na drugi adapter
			cn.setCursor(Cursor.getDefaultCursor());
			cn.removeMouseListener(this);
			cn.removeMouseMotionListener(this);
			cn.addMouseListener(tpma);
			cn.addMouseMotionListener(tpma);
		}

	}

	@Override
	public void mouseMoved(MouseEvent ev) {
		int tileX = cn.getDraggedHexagon().getTileX();
		int tileY = cn.getDraggedHexagon().getTileY();

		Pair p = tpma.getClickedTile(cn, ev);
		if (p != null && p.getX() == tileX && p.getY() == tileY) {
			cn.setCursor(Cursor.getDefaultCursor());
		} else {
			cn.setCursor(this.c);
		}
	}

	public TilePlacementMouseAdapter getTpma() {
		return tpma;
	}

	public void setTpma(TilePlacementMouseAdapter tpma) {
		this.tpma = tpma;
	}

	public void setRotatedTile(BoardTile rotatedTile) {
		this.rotatedTile = rotatedTile;
	}

}
