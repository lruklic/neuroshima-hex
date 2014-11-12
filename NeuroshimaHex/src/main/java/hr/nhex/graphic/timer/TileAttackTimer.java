package hr.nhex.graphic.timer;

import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;

import javax.swing.Timer;

/**
 * Class that executes graphics for unit attacks while battle is happening.
 * 
 * @author Luka Rukliæ
 *
 */

public class TileAttackTimer {

	/**
	 * Constant that defines speed of attack animation in miliseconds for each pixel movement.
	 */
	private static final int ANIMATION_ATTACK_SPEED = 15;

	/**
	 * Top level container.
	 */
	private NeuroshimaCanvas cn;

	/**
	 * Constructor.
	 * 
	 * @param cn top level container
	 */
	public TileAttackTimer (NeuroshimaCanvas cn) {
		this.cn = cn;
	}

	/**
	 * Method that is invoked when unit attacks. Uses timer and TileAttackAnimation class to animate
	 * melee attacks.
	 * 
	 * @param tilePosition tile that is attacking
	 * @param ad directions in which he attacks, can be multiple
	 */
	// potrebno je urediti ulogu directiona kod napada (Marin!), kao i omoguæiti napade u više smjerova
	// razmisliti o animaciji ranged napada (nova metoda ili nešto drugo)

	public void animateAttack(Pair tilePosition, AttackDirection... ad) {

		for (Hexagon h : cn.getHexagonList()) {
			if (h.getTileX() == tilePosition.getX() && h.getTileY() == tilePosition.getY()) {
				// promijeniti
				Hexagon hitHex = cn.getHexagon(h.getTileX()+1, h.getTileY());
				Timer timer = new Timer(ANIMATION_ATTACK_SPEED, new TileAttackAnimation(h, hitHex, cn));
				timer.start();
				cn.getTpma().setListenerOff();

			}
		}
	}
}
