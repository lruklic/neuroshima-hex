package hr.nhex.board;

import hr.nhex.model.AbstractTile;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.player.Player;

import java.util.List;

/**
 * Class that represents tile that can be placed on the board.
 *
 * @author Luka Ruklic
 *
 */

public class BoardTile extends AbstractTile {

	/**
	 * Empty constructor. Defined for purpose of creating board tiles outside of board, like in alternate constructors
	 * in module and unit classes.
	 */
	public BoardTile() {

	}

	public BoardTile(String name, Player player, int x, int y, int hitPoints, int angle, boolean isNetted) {
		super(name);
		this.player = player;
		this.x = x;
		this.y = y;
		this.hitPoints = hitPoints;
		this.maxHitPoints = hitPoints;
		this.angle = angle;
		this.isNetted = isNetted;
	}

	protected int x;

	protected int y;

	protected int hitPoints;

	protected int maxHitPoints;

	protected int angle;

	protected boolean isNetted;

	/**
	 * Metoda koja kopira polje na plo�i. Implementacija kod razreda BoardTile ne radi ni�ta, no prega�ena je u odgovaraju�im
	 * podrazredima.
	 *
	 * @return kopija polja nad kojim se metoda izvr�ava
	 */
	public BoardTile copy() {
		return null;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<Ability> getAbilities() {
		return null;
	}

	@Override
	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		if (angle < 0) {
			// angle 5 is equal to angle rotate for one in opposite direction, -1
			angle = 5;
		}
		this.angle = angle % 6;
	}

	public boolean isNetted() {
		return isNetted;
	}

	public void setNetted(boolean isNetted) {
		this.isNetted = isNetted;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BoardTile other = (BoardTile) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

}
