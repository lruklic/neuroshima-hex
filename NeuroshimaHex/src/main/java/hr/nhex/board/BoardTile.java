package hr.nhex.board;

import hr.nhex.model.Player;
import hr.nhex.model.Tile;

/**
 * Razred koji predstavlja polje na ploèi.
 * 
 * @author Luka Rukliæ
 *
 */

public class BoardTile extends Tile {

	/**
	 * Prazan konstruktor.
	 */
	public BoardTile() {

	}

	public BoardTile(String name, Player player, int x, int y, int hitPoints, int angle, boolean isNetted) {
		super(name);
		this.player = player;
		this.x = x;
		this.y = y;
		this.hitPoints = hitPoints;
		this.angle = angle;
		this.isNetted = isNetted;
	}

	protected Player player;

	protected int hitPoints;

	protected int x;

	protected int y;

	protected int angle;

	protected boolean isNetted;

	/**
	 * Metoda koja kopira polje na ploèi. Implementacija kod razreda BoardTile ne radi ništa, no pregažena je u odgovarajuæim
	 * podrazredima.
	 * 
	 * @return kopija polja nad kojim se metoda izvršava
	 */
	public BoardTile copy() {
		return null;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getHitPoints() {
		return hitPoints;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardTile other = (BoardTile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}




}
