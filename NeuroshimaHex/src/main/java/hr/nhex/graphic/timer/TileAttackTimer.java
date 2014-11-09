package hr.nhex.graphic.timer;

import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;

import javax.swing.Timer;

/**
 * Class that executes graphics for unit attacks while battle is happening.
 * 
 * @author Luka
 *
 */

public class TileAttackTimer {

	private static final int ANIMATION_ATTACK_SPEED = 15;

	private NeuroshimaCanvas cn;

	public TileAttackTimer (NeuroshimaCanvas cn) {
		this.cn = cn;
	}

	public void animateAttack(Pair tilePosition) {

		for (Hexagon h : cn.getHexagonList()) {
			if (h.getTileX() == tilePosition.getX() && h.getTileY() == tilePosition.getY()) {
				Timer timer = new Timer(ANIMATION_ATTACK_SPEED, new TileAttackAnimation(h, cn));
				timer.start();
				cn.getTpma().setListenerOn(false);

			}
		}
	}
}
