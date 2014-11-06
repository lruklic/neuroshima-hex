package hr.nhex.graphic.adapters;

import hr.nhex.board.BoardTile;
import hr.nhex.game.TurnPhase;
import hr.nhex.generic.Pair;
import hr.nhex.generic.Position;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.model.Tile;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * Class that represents mouse listener that responds to mouse events in that
 * occur in the game.
 * 
 * @author Luka Rukliæ
 * @author Marin Bužanèiæ
 *
 */

public class TilePlacementMouseAdapter extends MouseAdapter {

	private NeuroshimaCanvas cn;

	private Tile tileSelected = null;
	private Pair tileLocation = null;
	private int tileAngle = 0;

	private Point anchorPoint;

	private TileRotateMouseAdapter trma;

	public TilePlacementMouseAdapter(NeuroshimaCanvas cn) {
		this.cn = cn;
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (cn.getGameInstance().getTurnPhase() == TurnPhase.DISCARD_PHASE) {

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

		if (cn.getGameInstance().getTurnPhase() == TurnPhase.TILE_PLACED) {
			// ako klikne desno, rotira ga desno
			// ako klikne lijevo, rotira ga lijevo

			// ako klikne na njega, tile se sprema na board tako kako je okrenut
			cn.getGameInstance().getBoard().addTile(
					(BoardTile)tileSelected,
					tileLocation.getX(),
					tileLocation.getY(),
					tileAngle
					);
		}
	}

	@Override
	public void mousePressed(MouseEvent ev) {

		if (cn.getGameInstance().getTurnPhase() == TurnPhase.TILES_DRAWN) {
			Integer clickedTileNo = getClickedDrawnTile(cn, ev);

			this.tileSelected = cn.getGameInstance().getCurrentPlayerGameDeck().getDrawnTile(clickedTileNo-1);
			cn.getGameInstance().setTurnPhase(TurnPhase.TILE_PLACED);

			// ako je BoardTile, promijeni kursor u onaj za rotaciju

		}

	}

	@Override
	public void mouseReleased(MouseEvent ev) {

		if (tileSelected != null) {

			Pair tilePos = getClickedTile(cn, ev);

			if (tilePos != null && Math.abs(tilePos.getX()) < 2 && Math.abs(tilePos.getY()) < 2) { // urediti moguæe koordinate

				if (cn.getGameInstance().getBoard().getTile(tilePos.getX(), tilePos.getY()) == null) {

					if (tileSelected instanceof BoardTile) {
						// if tile is board tile
						trma.setRotatedTile((BoardTile)tileSelected);
						cn.setDraggedHexagon(new Hexagon(
								tilePos.getX(),
								tilePos.getY(),
								cn.getHexagon(tilePos.getX(), tilePos.getY()).getxC(),
								cn.getHexagon(tilePos.getX(), tilePos.getY()).getyC(),
								cn.getHexSize()+cn.HEX_GAP
								));

						cn.repaint();
						cn.removeMouseListener(this);
						cn.removeMouseMotionListener(this);
						cn.addMouseListener(trma);
						cn.addMouseMotionListener(trma);

					} else {
						// if tile is action tile
					}
				}
			}

		}
		//this.tileSelected = null;
		cn.repaint();

	}

	@Override
	public void mouseMoved(MouseEvent ev) {
		anchorPoint = ev.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent ev) {

		if (tileSelected != null) {
			if (cn.getDraggedHexagon() != null) {
				cn.setDraggedHexagon(null);
			}

			cn.setDraggedHexagon(new Hexagon(-5, -5, ev.getX(), ev.getY(), cn.getHexSize()));

			int anchorX = anchorPoint.x;
			int anchorY = anchorPoint.y;

			Point parentOnScreen = ev.getComponent().getLocationOnScreen();
			Point mouseOnScreen = ev.getLocationOnScreen();
			Point position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);

			cn.getDraggedHexagon().setLocation(position);
			//System.out.println("X,Y: "+cn.draggedHexagon.getX()+", "+cn.draggedHexagon.getY());
			cn.repaint();
			//cn.repaintComponentPart(cn.getGraphics(), tileSelected, position.x, position.y);
			//cn.paintComponent(cn.getGraphics());

		}

	}

	public TileRotateMouseAdapter getTrma() {
		return trma;
	}

	public void setTrma(TileRotateMouseAdapter trma) {
		this.trma = trma;
	}

	public Pair getClickedTile(NeuroshimaCanvas cn, MouseEvent ev) {

		// pairO je pair koordinata središta, pairU i pairV su vektori baze
		Position pairO = new Position(cn.getWidth()/2, cn.getHeight()/2);
		Position pairU = new Position((Math.sqrt(3))*(cn.getHexSize()+0.5*cn.HEX_GAP), 0);
		Position pairV = new Position((Math.sqrt(3)/2)*(cn.getHexSize()+0.5*cn.HEX_GAP), (-1.5)*(cn.getHexSize()+0.5*cn.HEX_GAP));

		//pairT æe biti pair koordinata koje su pritisnute
		Position pairT = new Position(ev.getX(), ev.getY());

		//tražimo m i n koji su rješenje sustava m * pairU + n * pairV = (pairT - pairO)
		double m = ( (pairT.getX()-pairO.getX())*(pairV.getY()) - (pairT.getY()-pairO.getY())*(pairV.getX()) )
				/( (pairU.getX())*(pairV.getY()) - (pairV.getX())*(pairU.getY()) );

		double n = ( (pairU.getX())*(pairT.getY()-pairO.getY()) - (pairU.getY())*(pairT.getX()-pairO.getX()) )
				/( (pairU.getX())*(pairV.getY()) - (pairV.getX())*(pairU.getY()) );

		//trenutno je kandidat tile na mjestu (round(m), round(n))
		//gledamo koordinatni sustav s novim središtem  i novim vektorima baze

		pairO = new Position(
				pairO.getX() + Math.round(m)*pairU.getX() + Math.round(n)*pairV.getX(),
				pairO.getY() + Math.round(m)*pairU.getY() + Math.round(n)*pairV.getY()
				);

		pairU = new Position(
				(Math.sqrt(3))*cn.getHexSize(),
				0
				);

		pairV = new Position(
				(Math.sqrt(3)/2)*cn.getHexSize(),
				(-1.5)*cn.getHexSize()
				);

		//tražimo p i q koji su rješenje sustava p * pairU + q * pairV = (pairT - pairO)

		double p = ( (pairT.getX()-pairO.getX())*(pairV.getY()) - (pairT.getY()-pairO.getY())*(pairV.getX()) )
				/( (pairU.getX())*(pairV.getY()) - (pairV.getX())*(pairU.getY()) );

		double q = ( (pairU.getX())*(pairT.getY()-pairO.getY()) - (pairU.getY())*(pairT.getX()-pairO.getX()) )
				/( (pairU.getX())*(pairV.getY()) - (pairV.getX())*(pairU.getY()) );

		//ako su p i q dovoljno blizu nuli, onda je stvarno stisnut tile
		if ((Math.round(p) == 0) && (Math.round(q) == 0)) {
			//System.out.println("Stisnut je: " + Math.round(m) + ", "+ Math.round(n));
			return new Pair((int)Math.round(m), (int)Math.round(n));
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

	public Tile getTileSelected() {
		return tileSelected;
	}

}
