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
	private static final int ANIMATION_ATTACK_SPEED = 35;

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

	public void animateAttack(Pair attackerPos, Pair hitPos) {

		Hexagon attackingHex = cn.getHexagon(attackerPos.getX(), attackerPos.getY());
		Hexagon hitHex = cn.getHexagon(hitPos.getX(), hitPos.getY());

		// promijeniti
		Timer timer = new Timer(ANIMATION_ATTACK_SPEED, new TileAttackAnimation(attackingHex, hitHex, cn));
		timer.start();
		cn.getTpma().setListenerOff();

	}
}
