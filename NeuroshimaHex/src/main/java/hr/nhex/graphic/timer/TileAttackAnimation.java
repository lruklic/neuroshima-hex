package hr.nhex.graphic.timer;

import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Action listener that implements methods for animating tile attack on the board.
 * 
 * @author Luka Rukliæ
 *
 */

public class TileAttackAnimation implements ActionListener {
	/**
	 * Distance in pixels that tile crosses when it attacks.
	 */
	private static final int ATTACK_MOVE_PIXEL = 7;
	/**
	 * Number that indicates how long will attacked tile display hit animation.
	 */
	private static final int ATTACK_SHATTER = 9;
	/**
	 * Top level container.
	 */
	private NeuroshimaCanvas cn;
	/**
	 * Instance of hexagon that is attacking.
	 */
	private Hexagon attackingHex;
	/**
	 * Instance of hexagon that is being hit.
	 */
	private Hexagon hitHex;

	private int moved = 0;

	/**
	 * Constructor.
	 * 
	 * @param attackingHex instance of hexagon that is attacking
	 * @param hitHex instance of hexagon that is being hit
	 * @param cn top level containter; canvas
	 */
	public TileAttackAnimation(Hexagon attackingHex, Hexagon hitHex, NeuroshimaCanvas cn) {
		this.attackingHex = attackingHex;
		this.hitHex = hitHex;
		this.cn = cn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (moved < ATTACK_MOVE_PIXEL) {
			// attacking movement of attackingHex
			attackingHex.setLocation(new Point(attackingHex.getxC(), attackingHex.getyC()));
			attackingHex.setxC(attackingHex.getxC()+1);
			moved++;
		} else if (moved >= ATTACK_MOVE_PIXEL && moved < 2*ATTACK_MOVE_PIXEL) {
			// retreat movement of attackingHex
			attackingHex.setLocation(new Point(attackingHex.getxC(), attackingHex.getyC()));
			attackingHex.setxC(attackingHex.getxC()-1);

			// shatter movement of hitHex
			hitHex.setLocation(new Point(hitHex.getxC(), hitHex.getyC()));
			if (moved % 2 == 0) {
				hitHex.setyC(hitHex.getyC()+3);
			} else {
				hitHex.setyC(hitHex.getyC()-3);
			}
			moved++;

		} else if (moved >= 2*ATTACK_MOVE_PIXEL && moved < 2*ATTACK_MOVE_PIXEL+ATTACK_SHATTER) {
			if (moved % 2 == 0) {
				hitHex.setyC(hitHex.getyC()+3);
			} else {
				hitHex.setyC(hitHex.getyC()-3);
			}
			moved++;
		} else {
			((Timer)e.getSource()).stop();
			cn.mouseListenerActivate(cn.getTpma());
		}
		cn.repaint();

	}

}
