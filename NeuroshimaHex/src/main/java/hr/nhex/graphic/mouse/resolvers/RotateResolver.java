package hr.nhex.graphic.mouse.resolvers;

import hr.nhex.board.BoardTile;
import hr.nhex.board.resolvers.ActionTileResolver;
import hr.nhex.game.GamePhase;
import hr.nhex.game.turn.TurnPhase;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.mouse.adapters.AdapterType;
import hr.nhex.model.HQ;
import hr.nhex.model.action.ActionTile;
import hr.nhex.model.action.ActionType;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

public class RotateResolver extends AbstractMouseResolver {

	private Cursor c;

	public RotateResolver(NeuroshimaCanvas cn) {
		this.cn = cn;
		this.game = cn.getGameInstance();
		this.hlc = HexagonListContainer.getInstance();
		this.type = AdapterType.ROTATE;

		// store cursor image to cache?
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image rotateImage = toolkit.getImage("icons/rotate.png");
		this.c = toolkit.createCustomCursor(rotateImage , new Point(cn.getX(),
				cn.getY()), "img");
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

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

			if (game.getGamePhase() == GamePhase.HQ_SETUP && game.getSelectedTile() instanceof HQ) {
				cn.getButtonContainer().toggleEndTurnButton(true);
			}

			game.setSelectedTile(null);
			hlc.setDraggedHexagon(null);
			cn.repaint();

			game.setTurnPhase(TurnPhase.TILES_DRAWN);

			if (game.getBoard().numberOfTilesOnBoard() == 19) {		// ovo radje provjeri s metodom da li su sva polja na ploci zauzeta; zbog foundation tileova
				ActionTileResolver atr = new ActionTileResolver();
				atr.resolve(new ActionTile("Battle", ActionType.BATTLE), null, cn);
			}

			setActiveAdapterType(AdapterType.PLACEMENT);
			//cn.getMac().mouseListenerActivate(AdapterType.PLACEMENT);
		}

	}

	@Override
	public void mouseMoved(MouseEvent ev) {

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
