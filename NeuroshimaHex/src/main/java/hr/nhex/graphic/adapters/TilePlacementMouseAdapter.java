package hr.nhex.graphic.adapters;

import hr.nhex.board.ActionTileResolver;
import hr.nhex.board.BoardTile;
import hr.nhex.game.TurnPhase;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.model.action.ActionTile;

import java.awt.Point;
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

public class TilePlacementMouseAdapter extends AbstractMouseAdapter {

	private Integer clickedTileNo;

	private Point anchorPoint;

	public TilePlacementMouseAdapter(NeuroshimaCanvas cn) {
		this.cn = cn;
		this.game = cn.getGameInstance();
		this.hlc = HexagonListContainer.getInstance();
		this.type = AdapterType.PLACEMENT;
		this.setListenerOn();
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (listenerOn) {

			// Aktivno samo ako treba discardati
			if (game.getTurnPhase() == TurnPhase.DISCARD_PHASE) {

				Integer clickedTile = getClickedDrawnTile(ev);

				if (clickedTile != null) {
					game.getCurrentPlayerDeck().discardTile(clickedTile-1);
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
				this.clickedTileNo = getClickedDrawnTile(ev);
				if (clickedTileNo == null) {
					return;
				}

				game.setSelectedTile(game.getCurrentPlayerDeck().getDrawnTile(clickedTileNo-1));

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

					game.getCurrentPlayerDeck().discardTile(this.clickedTileNo-1);

					if (game.getBoard().getTile(tilePos.getX(), tilePos.getY()) == null) {

						// if tile is board tile
						BoardTile bt = ((BoardTile) cn.getGameInstance().getSelectedTile());
						bt.setX(tilePos.getX());
						bt.setY(tilePos.getY());

						game.setSelectedTile(bt);
						hlc.setDraggedHexagon(new Hexagon(
								tilePos.getX(),
								tilePos.getY(),
								hlc.getHexagon(tilePos.getX(), tilePos.getY()).getxC(),
								hlc.getHexagon(tilePos.getX(), tilePos.getY()).getyC(),
								hlc.getHexSize()+HexagonListContainer.HEX_GAP
								)
								);

						cn.repaint();

						cn.getMac().mouseListenerActivate(AdapterType.ROTATE);

					}
				} else {
					if (tilePos == null) {
						hlc.setDraggedHexagon(null);
						game.setSelectedTile(null);
						cn.repaint();
						return;
					}

					ActionTileResolver atr = new ActionTileResolver();
					if (atr.resolve((ActionTile) game.getSelectedTile(), getClickedTile(cn, ev), cn)) {
						game.getCurrentPlayerDeck().discardTile(this.clickedTileNo-1);
					}
					hlc.setDraggedHexagon(null);
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

				hlc.setDraggedHexagon(new Hexagon(-5, -5, ev.getX(), ev.getY(), hlc.getHexSize()));

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

	/**
	 * Method that returns which of three side tiles was clicked. If none was clicked, this methods returns null.
	 *
	 * @param cn instance of canvas where game is displayed
	 * @param ev click mouse event that occured
	 * @return Integer 1,2 or 3 which identifies a tile that was clicked,
	 * or null if none of the three tiles was clicked
	 */
	public Integer getClickedDrawnTile(MouseEvent ev) {

		int hexSize = hlc.getHexSize();

		double t1 = Math.pow(ev.getX() - (cn.getWidth() - Math.sqrt(3)/2*hexSize),2) + Math.pow(ev.getY() - (cn.getHeight() - hexSize),2);
		double t2 = Math.pow(ev.getX() - (cn.getWidth() - Math.sqrt(3)/2*hexSize),2) + Math.pow(ev.getY() - (cn.getHeight() - 3*hexSize),2);
		double t3 = Math.pow(ev.getX() - (cn.getWidth() - Math.sqrt(3)/2*hexSize),2) + Math.pow(ev.getY() - (cn.getHeight() - 5*hexSize),2);

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

		double angle = Math.atan2((-1)*(ev.getY() - (cn.getHeight() - (2*k-1)*hexSize)), ev.getX() - (cn.getWidth() - Math.sqrt(3)/2*hexSize));
		angle = Math.abs(Math.abs(Math.abs(Math.abs(Math.PI*8/6 - (angle + Math.PI)) - Math.PI*4/6) - Math.PI*2/6) - Math.PI*1/6);

		if (Math.sqrt(tMin) < hexSize*Math.sin(Math.PI*1/3)/Math.sin(Math.PI*2/3-angle)) {
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
