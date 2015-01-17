package hr.nhex.graphic.mouse.resolvers;

import hr.nhex.game.Game;
import hr.nhex.generic.Pair;
import hr.nhex.generic.Position;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.mouse.adapters.AdapterType;
import hr.nhex.graphic.timer.BattleAnimation;

import java.awt.event.MouseEvent;

public abstract class AbstractMouseResolver {

	/**
	 * Top level container.
	 */
	protected NeuroshimaCanvas cn;

	protected Game game;

	protected HexagonListContainer hlc;

	protected AdapterType type;

	public void mouseClicked(MouseEvent ev) {
	}
	public void mousePressed(MouseEvent ev) {
	}
	public void mouseMoved(MouseEvent ev) {
	}
	public void mouseReleased(MouseEvent ev) {
	}
	public void mouseDragged(MouseEvent ev) {
	}

	public AdapterType getType() {
		return type;
	}

	public void setCurrentBattleAnimation(BattleAnimation currentBattleAnimation) {
		// No effect on basic resolvers.
	}

	public void disableAllResolvers() {
		this.cn.getGenericMouseAdapter().disableAllResolvers();
	}

	public void setActiveAdapterType(AdapterType type) {
		this.cn.getGenericMouseAdapter().setActiveAdapterType(type);
	}

	public Pair getClickedTile(NeuroshimaCanvas cn, MouseEvent ev) {		//*** smisli bolje ime za getClickedTile (nije tocno)

		int hexSize = hlc.getHexSize();

		// pairO je pair koordinata sredista, pairU i pairV su vektori baze
		Position pairO = new Position(cn.getWidth()/2, cn.getHeight()/2);
		Position pairU = new Position((Math.sqrt(3))*(hexSize+0.5*HexagonListContainer.HEX_GAP), 0);
		Position pairV = new Position((Math.sqrt(3)/2)*(hexSize+0.5*HexagonListContainer.HEX_GAP), (-1.5)*(hexSize+0.5*HexagonListContainer.HEX_GAP));

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
			if (Math.sqrt(Math.pow(pairT.getX() - pairO.getX(),2) + Math.pow(pairT.getY() - pairO.getY(),2)) < hexSize*Math.sin(Math.PI*1/3)/Math.sin(Math.PI*2/3-angle)) {
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

}
