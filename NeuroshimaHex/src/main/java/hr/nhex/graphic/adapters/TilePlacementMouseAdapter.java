package hr.nhex.graphic.adapters;

import hr.nhex.board.ActionTileResolver;
import hr.nhex.board.BoardTile;
import hr.nhex.game.Game;
import hr.nhex.game.TurnPhase;
import hr.nhex.generic.Pair;
import hr.nhex.generic.Position;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.model.action.ActionTile;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * Class that represents mouse listener that responds to mouse events in that
 * occur in the game.
 *
 * @author Luka Ruklic
 * @author Marin Buzancic
 *
 */

public class TilePlacementMouseAdapter extends MouseAdapter implements IMouseAdapter {

	/**
	 * Variable that defines whether the listener is listening or not (is it on or off).
	 */
	private boolean listenerOn = true;
	/**
	 * Top level container that uses this listener.
	 */
	private NeuroshimaCanvas cn;

	private Game game;
	private HexagonListContainer hlc;

	private Integer clickedTileNo;

	private Point anchorPoint;

	private TileRotateMouseAdapter trma;
	private TileMovementMouseAdapter tmma;

	public TilePlacementMouseAdapter(NeuroshimaCanvas cn, HexagonListContainer hlc) {
		this.cn = cn;
		this.hlc = hlc;
		this.game = cn.getGameInstance();
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (listenerOn) {

			// Aktivno samo ako treba discardati
			if (game.getTurnPhase() == TurnPhase.DISCARD_PHASE) {

				Integer clickedTile = getClickedDrawnTile(cn, ev);

				if (clickedTile != null) {
					game.getCurrentPlayerGameDeck().discardTile(clickedTile-1);
					game.setTurnPhase(TurnPhase.TILES_DRAWN);
					cn.repaint();
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent ev) {

		if (listenerOn) {

			if (game.getTurnPhase() == TurnPhase.TILES_DRAWN) {
				this.clickedTileNo = getClickedDrawnTile(cn, ev);
				if (clickedTileNo == null) {
					return;
				}

				game.setSelectedTile(game.getCurrentPlayerGameDeck().getDrawnTile(clickedTileNo-1));

				game.setTurnPhase(TurnPhase.TILE_PLACED);

			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent ev) {

		if (listenerOn) {

			if (game.getSelectedTile() != null) {

				Pair tilePos = getClickedTile(cn, ev);

				if (game.getSelectedTile() instanceof BoardTile && tilePos != null && Math.abs(tilePos.getX()) < 3 && Math.abs(tilePos.getY()) < 3) { // urediti mogu�e koordinate

					game.getCurrentPlayerGameDeck().discardTile(this.clickedTileNo-1);

					if (game.getBoard().getTile(tilePos.getX(), tilePos.getY()) == null) {

						// if tile is board tile
						BoardTile bt = ((BoardTile) cn.getGameInstance().getSelectedTile());
						bt.setX(tilePos.getX());
						bt.setY(tilePos.getY());

						game.setSelectedTile(bt);
						hlc.setDraggedHexagon(new Hexagon(
								tilePos.getX(),
								tilePos.getY(),
								cn.getHexagon(tilePos.getX(), tilePos.getY()).getxC(),
								cn.getHexagon(tilePos.getX(), tilePos.getY()).getyC(),
								cn.getHexSize()+cn.HEX_GAP
								));

						cn.repaint();

						cn.mouseListenerActivate(trma);

					}
				} else {
					if (tilePos == null) {
						hlc.setDraggedHexagon(null);
						game.setSelectedTile(null);
					}

					ActionTileResolver atr = new ActionTileResolver();
					if (atr.resolve((ActionTile) game.getSelectedTile(), ev, cn)) {
						game.getCurrentPlayerGameDeck().discardTile(this.clickedTileNo-1);
						hlc.setDraggedHexagon(null);
					}
				}
			} else {
				if (game.getTurnPhase() != TurnPhase.DISCARD_PHASE) {
					game.setTurnPhase(TurnPhase.TILES_DRAWN);
				}
				hlc.setDraggedHexagon(null);
				cn.repaint();
			}

			//this.tileSelected = null;
			cn.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent ev) {
		if (listenerOn) {
			anchorPoint = ev.getPoint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent ev) {
		if (listenerOn) {

			if (game.getSelectedTile() != null) {
				if (hlc.getDraggedHexagon() != null) {
					hlc.setDraggedHexagon(null);
				}

				hlc.setDraggedHexagon(new Hexagon(-5, -5, ev.getX(), ev.getY(), cn.getHexSize()));

				int anchorX = anchorPoint.x;
				int anchorY = anchorPoint.y;

				Point parentOnScreen = ev.getComponent().getLocationOnScreen();
				Point mouseOnScreen = ev.getLocationOnScreen();
				Point position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);

				hlc.getDraggedHexagon().setLocation(position);
				//System.out.println("X,Y: "+cn.draggedHexagon.getX()+", "+cn.draggedHexagon.getY());
				cn.repaint();
				//cn.repaintComponentPart(cn.getGraphics(), tileSelected, position.x, position.y);
				//cn.paintComponent(cn.getGraphics());

			}
		}

	}

	public TileRotateMouseAdapter getTrma() {
		return trma;
	}

	public void setTrma(TileRotateMouseAdapter trma) {
		this.trma = trma;
	}

	public TileMovementMouseAdapter getTmma() {
		return tmma;
	}

	public void setTmma(TileMovementMouseAdapter tmma) {
		this.tmma = tmma;
	}

	@Override
	public void setListenerOn() {
		this.listenerOn = true;
	}

	@Override
	public void setListenerOff() {
		this.listenerOn = false;
	}

	public static Pair getClickedTile(NeuroshimaCanvas cn, MouseEvent ev) {

		// pairO je pair koordinata sredista, pairU i pairV su vektori baze
		Position pairO = new Position(cn.getWidth()/2, cn.getHeight()/2);
		Position pairU = new Position((Math.sqrt(3))*(cn.getHexSize()+0.5*cn.HEX_GAP), 0);
		Position pairV = new Position((Math.sqrt(3)/2)*(cn.getHexSize()+0.5*cn.HEX_GAP), (-1.5)*(cn.getHexSize()+0.5*cn.HEX_GAP));

		// pairT ce biti pair koordinata koje su pritisnute
		Position pairT = new Position(ev.getX(), ev.getY());

		// trazimo m i n koji su rjesenje sustava m * pairU + n * pairV = (pairT - pairO)
		double m = ( (pairT.getX()-pairO.getX())*(pairV.getY()) - (pairT.getY()-pairO.getY())*(pairV.getX()) )
				/( (pairU.getX())*(pairV.getY()) - (pairV.getX())*(pairU.getY()) );

		double n = ( (pairU.getX())*(pairT.getY()-pairO.getY()) - (pairU.getY())*(pairT.getX()-pairO.getX()) )
				/( (pairU.getX())*(pairV.getY()) - (pairV.getX())*(pairU.getY()) );

		// primijetimo da lin. komb. m * pairU + n * pairV mozemo zapisati na sljedeca tri nacina:
		// ( m ) * (pairU - pairV) + ( m+n ) * pairV
		// ( -n ) * (pairU - pairV) + ( -m-n ) * ( -pairU )
		// ( -m ) * ( -pairU ) + ( n ) * pairV
		// stoga, zaokruzivanjem koef. m, n, m+n dobivamo najvise tri kandidata za tile
		// trazimo onaj s najblizim sredistem

		double tm = Math.abs(Math.round(m) - m);
		double tn = Math.abs(Math.round(n) - n);
		double tmn = Math.abs(Math.round(m+n) - (m+n));
		//System.out.println("m: "+m+" tm: "+tm+" round(m): "+Math.round(m));
		//System.out.println("n: "+n+" tn: "+tn+" round(n): "+Math.round(n));
		//System.out.println("m+n: "+(m+n)+" tmn: "+tmn+" round(m+n): "+Math.round(m+n));

		double tMax = Math.max(Math.max(tm, tn), tmn);

		if (tMax == tmn) {
			//System.out.println("tmn");
			m = Math.round(m);
			n = Math.round(n);
		} else if (tMax == tn) {
			//System.out.println("tn");
			n = Math.round(m+n) - Math.round(m);
			m = Math.round(m);
		} else {
			//System.out.println("tm");
			m = Math.round(m+n) - Math.round(n);
			n = Math.round(n);
		}

		//trenutno je jedini kandidat tile na mjestu (m,n)
		//gledamo koordinatni sustav s novim središtem i provjeravamo trigonometrijski

		pairO = new Position(
				pairO.getX() + m*pairU.getX() + n*pairV.getX(),
				pairO.getY() + m*pairU.getY() + n*pairV.getY()
				);

		double angle = Math.atan2((-1)*(pairT.getY() - pairO.getY()), pairT.getX() - pairO.getX());
		angle = Math.abs(Math.abs(Math.abs(Math.abs(Math.PI*8/6 - (angle + Math.PI)) - Math.PI*4/6) - Math.PI*2/6) - Math.PI*1/6);

		if ((Math.abs(m+n) <= 2) && ((Math.abs(m) <= 2) && (Math.abs(n) <= 2))) {
			if (Math.sqrt(Math.pow(pairT.getX() - pairO.getX(),2) + Math.pow(pairT.getY() - pairO.getY(),2)) < cn.getHexSize()*Math.sin(Math.PI*1/3)/Math.sin(Math.PI*2/3-angle)) {
				//System.out.println("Stisnut je: " + Math.round(m) + ", "+ Math.round(n));
				return new Pair((int) m, (int) n);
			}
			else {
				return null;
			}
		} else {
			//System.out.println("Nista nije stisnuto");
			return null;
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

	public void setListenerOn(boolean listenerOn) {
		this.listenerOn = listenerOn;
	}

}
