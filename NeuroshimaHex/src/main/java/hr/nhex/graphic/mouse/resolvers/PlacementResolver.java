package hr.nhex.graphic.mouse.resolvers;

import hr.nhex.board.BoardTile;
import hr.nhex.board.resolvers.ActionTileResolver;
import hr.nhex.game.turn.TurnPhase;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.adapters.AdapterType;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.hexagon.SpecialHex;
import hr.nhex.model.action.ActionTile;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class PlacementResolver extends AbstractMouseResolver {

	private Integer clickedTileNo;

	private Point anchorPoint;

	public PlacementResolver(NeuroshimaCanvas cn) {
		this.cn = cn;
		this.game = cn.getGameInstance();
		this.hlc = HexagonListContainer.getInstance();
		this.type = AdapterType.PLACEMENT;
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		game.getTurn().getMovementControl().locateAllMovementSource(game.getBoard()); 		// kršenje demetra, popravi

		if (game.getTurnPhase() == TurnPhase.DISCARD_PHASE) {

			Integer drawnTileForDiscardIndex = getClickedDrawnTile(ev);

			if (drawnTileForDiscardIndex != null) {
				game.getCurrentPlayerDeck().discardTile(drawnTileForDiscardIndex-1);
				game.setTurnPhase(TurnPhase.TILES_DRAWN);
				cn.repaint();
			}
		} else if (game.getTurnPhase() == TurnPhase.TILES_DRAWN) {

			Pair tileOnBoard = getClickedTile(cn, ev);

			if (tileOnBoard != null) {
				BoardTile bt = game.getBoard().getTile(tileOnBoard.getX(), tileOnBoard.getY());		// REFAKTORIRATI! moduli i uniti bi trebali u isti razred - donekle riješeno s overridanom metodom getAbilities
				if (bt != null && bt.getPlayer() == game.getCurrentPlayer() && !game.getBoard().tileIsNetted(bt.getX(), bt.getY(), 0, bt.getPlayer())) { // usporedba abilityja usporedjuje po tipu i smjeru(?)

					if (game.getTurn().getMovementControl().tileCanMove(bt)) {
						unitMovementOperation(bt);
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent ev) {

		if (game.getTurnPhase() == TurnPhase.TILES_DRAWN) {
			this.clickedTileNo = getClickedDrawnTile(ev);
			if (clickedTileNo == null) {
				return;
			}

			game.setSelectedTile(game.getCurrentPlayerDeck().getDrawnTile(clickedTileNo-1));

			game.setTurnPhase(TurnPhase.TILE_PLACED);

		}

	}

	@Override
	public void mouseReleased(MouseEvent ev) {

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

					setActiveAdapterType(AdapterType.ROTATE);

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
			cn.repaint();
		} else {
			if (game.getTurnPhase() != TurnPhase.DISCARD_PHASE) {
				game.setTurnPhase(TurnPhase.TILES_DRAWN);
			}
			hlc.setDraggedHexagon(null);
			cn.repaint();
		}

		//this.tileSelected = null;
	}

	@Override
	public void mouseMoved(MouseEvent ev) {
		anchorPoint = ev.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent ev) {

		if (game.getSelectedTile() != null) {
			if (hlc.getDraggedHexagon() != null) {
				hlc.setDraggedHexagon(null);
			}

			hlc.setDraggedHexagon(new Hexagon(null, null, ev.getX(), ev.getY(), hlc.getHexSize()));

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

	private void unitMovementOperation(BoardTile bt) {

		for (Pair p : game.getBoard().getAdjecantTiles(bt.getX(), bt.getY())) {
			if (!game.getBoard().isFilled(p.getX(), p.getY())) {
				hlc.getSpecialHexList().add(new SpecialHex(new Pair(p.getX(), p.getY()), game.getCurrentPlayer().getPlayerColor()));
			}
		}

		if (!hlc.getSpecialHexList().isEmpty()) {
			game.setSelectedTile(bt);
			setActiveAdapterType(AdapterType.MOVEMENT);
		} else {
			hlc.getSpecialHexList().clear();
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
				return 3;		// promijenjeno da je najviši 1, a najniži 3
			} else if (tMin == t2) {
				return 2;
			} else {
				return 1;
			}
		}

		return null;

	}

}
