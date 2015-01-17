package hr.nhex.graphic.timer;

import hr.nhex.generic.Pair;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.graphic.mouse.adapters.AdapterType;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Action listener that implements methods for animating tile attack on the board.
 *
 * @author Luka Ruklic
 * @author Marin Buzancic
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

		AttackDirection ad = getAttackDirection(
				new Pair(attackingHex.getTileX(), attackingHex.getTileY()),
				new Pair(hitHex.getTileX(), hitHex.getTileY())
				);

		if (moved < ATTACK_MOVE_PIXEL) {
			// attacking movement of attackingHex
			attackingHex.setLocation(new Point(attackingHex.getxC(), attackingHex.getyC()));
			setHexLocation(attackingHex, ad);
			moved++;
		} else if (moved >= ATTACK_MOVE_PIXEL && moved < 2*ATTACK_MOVE_PIXEL) {
			// retreat movement of attackingHex
			attackingHex.setLocation(new Point(attackingHex.getxC(), attackingHex.getyC()));
			setHexLocation(attackingHex, ad.oppositeDirection());

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
			cn.getGenericMouseAdapter().setActiveAdapterType(AdapterType.PLACEMENT);
		}
		//System.out.println("Att hex coord: "+attackingHex.getxC()+", "+attackingHex.getyC());
		cn.repaint();

	}

	private void setHexLocation(Hexagon attackingHex, AttackDirection ad) {

		if (ad == AttackDirection.E) {
			attackingHex.setxC(attackingHex.getxC()+1);
		} else if (ad == AttackDirection.W) {
			attackingHex.setxC(attackingHex.getxC()-1);
		} else if (ad == AttackDirection.NE) {
			attackingHex.setxC(attackingHex.getxC()+1);
			attackingHex.setyC(attackingHex.getyC()-1);
		} else if (ad == AttackDirection.SW) {
			attackingHex.setxC(attackingHex.getxC()-1);
			attackingHex.setyC(attackingHex.getyC()+1);
		} else if (ad == AttackDirection.NW) {
			attackingHex.setxC(attackingHex.getxC()-1);
			attackingHex.setyC(attackingHex.getyC()-1);
		} else if (ad == AttackDirection.SE) {
			attackingHex.setxC(attackingHex.getxC()+1);
			attackingHex.setyC(attackingHex.getyC()+1);
		}
	}

	/**
	 * Method that detects in which geographic direction is the attacker tile attacking.
	 *
	 * @param attackerPos pair that contains attacker coordinates
	 * @param hitPos pair that contains coordinates of hit tile
	 * @return direction in which attacker is attacking
	 */

	private AttackDirection getAttackDirection(Pair attackerPos, Pair hitPos) {
		int x = hitPos.getX() - attackerPos.getX();
		int y = hitPos.getY() - attackerPos.getY();

		if (y == 0) {
			if (x > 0) {
				return AttackDirection.E;
			} else {
				return AttackDirection.W;
			}
		} else if (x == 0) {
			if (y > 0) {
				return AttackDirection.NE;
			} else {
				return AttackDirection.SW;
			}
		} else {
			if (y > 0) {
				return AttackDirection.NW;
			} else {
				return AttackDirection.SE;
			}
		}
	}

}
