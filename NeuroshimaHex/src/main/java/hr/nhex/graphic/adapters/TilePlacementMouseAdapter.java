package hr.nhex.graphic.adapters;

import hr.nhex.game.TurnPhase;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.model.Tile;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * @author Luka Rukliæ
 * @author Marin Bužanèiæ
 *
 */

public class TilePlacementMouseAdapter extends MouseAdapter {

	private Tile tileSelected = null;

	@Override
	public void mouseClicked(MouseEvent ev) {

		NeuroshimaCanvas cn = null;
		if (ev.getComponent() instanceof NeuroshimaCanvas) {
			cn = ((NeuroshimaCanvas)ev.getComponent());
		} else {
			return;
		}

		double n = -10;
		double m = -10;

		if (cn.getGameInstance().getTurnPhase() == TurnPhase.DISCARD_PHASE) {

			//			n = (ev.getY() - (cn.getHeight()/2)) / ((-1.5)*cn.getHexSize());
			//			m = (ev.getX() - (cn.getWidth()/2) - ((Math.sqrt(3)/2)*cn.getHexSize())*n)/(Math.sqrt(3)*cn.getHexSize());
			//
			//			System.out.println("X: "+Math.round(m) + ", Y: "+Math.round(n));

			Integer clickedTile = getClickedDrawnTile(cn, ev);

			if (clickedTile != null) {
				cn.getGameInstance().getCurrentPlayerGameDeck().discardTile(clickedTile-1);
				cn.getGameInstance().setTurnPhase(TurnPhase.TILES_DRAWN);
				cn.repaint();
			}
		}

		//		int realN = (int)Math.round(n);
		//		int realM = (int)Math.round(m);
		//		Hexagon hex = cn.getHexagon(realM, realN);
		//
		//		if (hex != null) {
		//			Graphics2D g2 = (Graphics2D)cn.getGraphics();
		//			hex.drawHex(g2, cn.getGameInstance().getBoard().getTile(realM, realN), 1);
		//		}
	}

	@Override
	public void mousePressed(MouseEvent ev) {

		NeuroshimaCanvas cn = null;
		if (ev.getComponent() instanceof NeuroshimaCanvas) {
			cn = ((NeuroshimaCanvas)ev.getComponent());
		} else {
			return;
		}

		if (cn.getGameInstance().getTurnPhase() == TurnPhase.TILES_DRAWN) {
			Integer clickedTile = getClickedDrawnTile(cn, ev);
			System.out.println("Mouse pressed");
			this.tileSelected = cn.getGameInstance().getCurrentPlayerGameDeck().getDrawnTile(clickedTile-1);
		}

	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		System.out.println("Mouse let go");
		this.tileSelected = null;
	}

	@Override
	public void mouseDragged(MouseEvent ev) {

		System.out.println("Mouse dragged");
		NeuroshimaCanvas cn = null;
		if (ev.getComponent() instanceof NeuroshimaCanvas) {
			cn = ((NeuroshimaCanvas)ev.getComponent());
		} else {
			return;
		}

		if (tileSelected != null) {
			cn.repaint();
			cn.repaintComponentPart(cn.getGraphics(), tileSelected, ev.getX(), ev.getY());
		}

	}

	/**
	 * Method that returns which of three side tiles was clicked. If none was clicked, this methods returns null.
	 * 
	 * @param cn instance of canvas where game is displayed
	 * @param ev click mouse event that occured
	 * @return Integer 1,2 or 3 which identifies a tile that was clicked,
	 * or null if none of the three tiles was clicked
	 */
	public Integer getClickedDrawnTile(NeuroshimaCanvas cn, MouseEvent ev) {

		double t1 = Math.pow(ev.getX() - (cn.getWidth() - Math.sqrt(3)/2*cn.getHexSize()),2) + Math.pow(ev.getY() - (cn.getHeight() - cn.getHexSize()),2);
		double t2 = Math.pow(ev.getX() - (cn.getWidth() - Math.sqrt(3)/2*cn.getHexSize()),2) + Math.pow(ev.getY() - (cn.getHeight() - 3*cn.getHexSize()),2);
		double t3 = Math.pow(ev.getX() - (cn.getWidth() - Math.sqrt(3)/2*cn.getHexSize()),2) + Math.pow(ev.getY() - (cn.getHeight() - 5*cn.getHexSize()),2);

		//System.out.println("t1: "+t1+" t2: "+t2+" t3: "+t3+" width: "+cn.getWidth()+" height: "+cn.getHeight()+" hexsize: "+cn.getHexSize());

		double tMin = Math.min(Math.min(t1, t2), t3);

		int k = 0;
		if (tMin == t1) {
			k = 1;
		} else if (tMin == t2) {
			k = 2;
		} else {
			k = 3;
		}

		double angle = Math.atan2((-1)*(ev.getY() - (cn.getHeight() - (2*k-1)*cn.getHexSize())), ev.getX() - (cn.getWidth() - Math.sqrt(3)/2*cn.getHexSize()));
		angle = Math.abs(Math.abs(Math.abs(Math.abs(Math.PI*8/6 - (angle + Math.PI)) - Math.PI*4/6) - Math.PI*2/6) - Math.PI*1/6);

		if (Math.sqrt(tMin) < cn.getHexSize()*Math.sin(Math.PI*1/3)/Math.sin(Math.PI*2/3-angle)) {
			if (tMin == t1) {
				return 1;
			} else if (tMin == t2) {
				return 2;
			} else {
				return 3;
			}
		}

		return null;

	}

}
