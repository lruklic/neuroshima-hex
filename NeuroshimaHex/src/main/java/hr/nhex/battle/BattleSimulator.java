package hr.nhex.battle;

import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.board.IBasicBoard;
import hr.nhex.generic.Pair;
import hr.nhex.model.HQ;
import hr.nhex.model.Module;
import hr.nhex.model.Netter;
import hr.nhex.model.Player;
import hr.nhex.model.Tile;
import hr.nhex.model.Unit;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.ability.AbilityType;
import hr.nhex.model.unit.Attack;
import hr.nhex.model.unit.AttackType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that represents battle simulator in Neuroshima Hex board game.
 * 
 * @author Luka Rukliæ
 * @author Marin Buanèiæ
 *
 */

public class BattleSimulator implements IBasicBoard {

	// hp treba updateat

	private Board board;

	private List<BattleTile> boardBattleTiles = new ArrayList<>();

	private StringBuilder battleEvents = new StringBuilder();

	private Integer currentInitiative = null;

	private static final int MAXSHIFT_SMALL_BOARD = 4;

	int[] angleX = {1, 0, -1, -1, 0, 1};	// objasni komentarom
	int[] angleY = {0, 1, 1, 0, -1, -1};

	/**
	 * Konstruktor koji popunjava listu s poljima koja ï¿½e se koristiti prilikom borbe.
	 * 
	 * @param listTiles lista s poljima trenutno na ploï¿½i
	 */
	public BattleSimulator(Board board) {
		this.board = board;
		for (BoardTile tile : board.getTiles()) {
			boardBattleTiles.add(new BattleTile(tile.copy()));
		}
	}

	/**
	 * Metoda koja obavlja funkciju konstruktora razreda BattleSimulator na naï¿½in da kopira ï¿½itavu ploï¿½u
	 * odnosno sva njezina polja u razred <code>BattleSimulator</code>.
	 * 
	 * @param listTiles lista s poljima
	 */
	public void reloadBoardToBattle(List<BoardTile> listTiles) {
		boardBattleTiles.clear();
		for (BoardTile tile : listTiles) {
			boardBattleTiles.add(new BattleTile(tile.copy()));
		}
	}

	/**
	 * Metoda simulatora borbe koja izvršava borbu.
	 */
	public void executeBattle() {
		applyModuleBonus();

		for (int currentSpeed = defineHighestSpeed(); currentSpeed >= 0; currentSpeed--) {
			//applyInstant();
			executeBattleInitiative(currentSpeed);
			updateAfterEffects();
			if (currentSpeed != 0) {
				applyModuleBonus();
			}
		}

	}

	public boolean executeNextRound() {

		battleEvents.setLength(0);
		applyModuleBonus();

		if (currentInitiative == null) {
			this.currentInitiative = defineHighestSpeed();
		}

		executeBattleInitiative(currentInitiative);
		currentInitiative--;

		if (currentInitiative == 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Metoda koja izvrï¿½ava fazu borbe odreï¿½enu ulaznom varijablom <code>currentSpeed</code> koja
	 * predstavlja trenutnu inicijativu.
	 * 
	 * @param currentSpeed trenutna inicijativa
	 */

	private void executeBattleInitiative(int currentSpeed) {

		for (BattleTile bt : boardBattleTiles) {
			if ((bt.getTile() instanceof Unit)
					&& !tileIsNetted(bt.getX(), bt.getY(), 0, bt.getTile().getPlayer())) {
				Unit unit = ((Unit)bt.getTile());
				for (Integer unitSpeed : unit.getSpeed()) {
					if (unitSpeed == currentSpeed) {
						executeAttacks(unit);
					}
				}
			}
		}
	}

	/**
	 * <p>Metoda koja pretraÅ¾uje napade odreï¿½ene jedinice primljene u metodu preko varijable <code>unit</code> i te iste napade izvrï¿½ava.
	 * 
	 * <p>Ako je rijeÄ o napadu prsa-o-prsa (engl. <i>melee</i>), napad se izvrï¿½ava nad jedinicom na polju na koje je napad usmjeren.
	 * Ukoliko je pak rijeÄ o napadu na daljinu (engl. <i>ranged</i>), napad se izvrï¿½ava nad svim poljima u liniji smjera napada, dok ne
	 * doï¿½e do protivniÄke jedinice. Napad na daljinu je moguï¿½e oslabiti ï¿½titom (engl. <i>block</i>).
	 * 
	 * @param unit jedinica ï¿½iji se napadi izvrï¿½avaju
	 */

	private void executeAttacks(Unit unit) {
		for (Attack attack : unit.getAttacks()) {
			if (attack.getType() == AttackType.MELEE || attack.getType() == AttackType.HQ_MELEE) {
				int pointsToTileX = unit.getX() + angleX[(attack.getPointsTo() + unit.getAngle()) % 6];	// vjerojatno se % 6 moe maknuti
				int pointsToTileY = unit.getY() + angleY[(attack.getPointsTo() + unit.getAngle()) % 6];
				BattleTile attacked = getBattleTile(pointsToTileX, pointsToTileY);
				if (attacked != null && !unit.getPlayer().equals(attacked.getTile().getPlayer()) && !(unit instanceof HQ && attacked.getTile() instanceof HQ)) {
					addBattleEvent("attack", unit, attacked);
					attack.getType().attack(attacked, attack.getValue(), attack.getType());
				}
			}
			else if (attack.getType() == AttackType.RANGED) {
				int shift = 1;
				while (true) {
					int pointsToTileXrange =
							unit.getX() + angleX[(attack.getPointsTo() + unit.getAngle()) % 6]*shift;
					int pointsToTileYrange =
							unit.getY() + angleY[(attack.getPointsTo() + unit.getAngle()) % 6]*shift;
					if (shift > MAXSHIFT_SMALL_BOARD) {
						break;
					}
					BattleTile attacked = getBattleTile(pointsToTileXrange, pointsToTileYrange);
					if (attacked != null && !unit.getPlayer().equals(attacked.getTile().getPlayer())) {

						if (attacked.getTile() instanceof Unit) {
							Unit unitBlock = (Unit)attacked.getTile();
							int pointsToBlock = (attack.getPointsTo() + unit.getAngle() + 3 - unitBlock.getAngle()) % 6;
							if (unitBlock.getAbilities().contains(new Ability(pointsToBlock, AbilityType.BLOCK))) {
								attack.getType().attack(attacked, attack.getValue() - 1, attack.getType());
							} else {
								attack.getType().attack(attacked, attack.getValue(), attack.getType());
							}
							addBattleEvent("rattack", unit, attacked);
							break;
						}
						if (attacked.getTile() instanceof Module) {
							Module moduleBlock = (Module)attacked.getTile();
							int pointsToBlock = (attack.getPointsTo() + unit.getAngle() + 3 - moduleBlock.getAngle()) % 6;
							if (moduleBlock.getAbilities().contains(new Ability(pointsToBlock, AbilityType.BLOCK))) {
								attack.getType().attack(attacked, attack.getValue() - 1, attack.getType());
							} else {
								attack.getType().attack(attacked, attack.getValue(), attack.getType());
							}
							addBattleEvent("rattack", unit, attacked);
							break;
						}

					}
					shift++;
				}

			}
		}
	}

	private void addBattleEvent(String eventType, Unit unit, BattleTile attacked) {
		this.battleEvents.append(eventType+" "+unit.getX()+" "+unit.getY()+" "+attacked.getX()+" "+attacked.getY()+"\n");
	}

	/**
	 * Metoda koja odreÄ‘uje najveÄ‡u brzinu (inicijativu) koja se pojavljuje tijekom borbe.
	 */

	private int defineHighestSpeed() {
		int highestSpeed = 0;
		for (BattleTile bt : boardBattleTiles) {
			if (bt.getTile() instanceof Unit) {
				Unit unit = (Unit)bt.getTile();
				for (int speed : unit.getSpeed()) {
					if (speed > highestSpeed) {
						highestSpeed = speed;
					}
				}
			}
		}
		return highestSpeed;
	}

	private void applyModuleBonus() {

		for (BattleTile battleTile : boardBattleTiles) {
			BoardTile currentTile = battleTile.getTile();
			// provjera da li postoje moguï¿½i potezi unutar borbe
			if (currentTile instanceof Module && !tileIsNetted(currentTile.getX(), currentTile.getY(), 0, battleTile.getTile().getPlayer())) {
				for (Ability ability : ((Module)currentTile).getAbilities()) {
					int pointsToTileX = currentTile.getX() + angleX[(ability.getPointsTo() + currentTile.getAngle()) % 6];
					int pointsToTileY = currentTile.getY() + angleY[(ability.getPointsTo() + currentTile.getAngle()) % 6];
					BattleTile bonusApplicant = getBattleTile(pointsToTileX, pointsToTileY);
					if (bonusApplicant != null && currentTile.getPlayer().equals(bonusApplicant.getTile().getPlayer())) {
						ability.getType().applyBonus(bonusApplicant);
					}
				}
			}
		}
	}

	/**
	 * <p>Metoda koja nakon izvrï¿½ene borbe aï¿½urira stanje ploï¿½e (ï¿½ivotne bodove, mreï¿½e, otrov) te
	 * miï¿½e jedinice ï¿½iji su se ï¿½ivotni bodovi spustili ispod 1.
	 * <p>Takoï¿½er, metoda nakon svake borbe provjerava maknute jedinice sa sposobnosti bacanje mreï¿½e (
	 * engl. <i>Netter</i>) te ukoliko je netko od njih ima
	 */

	public void updateAfterEffects() {

		List<BoardTile> deadTiles = new ArrayList<BoardTile>();

		for (BattleTile bt : boardBattleTiles) {
			if (bt.getTile().getHitPoints() <= 0) {
				deadTiles.add(bt.getTile());
			}
			else if (bt.getTile().getHitPoints() != board.getTile(bt.getX(), bt.getY()).getHitPoints()) {
				board.getTile(bt.getTile().getX(), bt.getTile().getY()).setHitPoints(bt.getTile().getHitPoints());
			}
		}

		board.getTiles().removeAll(deadTiles);
		reloadBoardToBattle(board.getTiles());

	}

	@Override
	public boolean isFilled(int x, int y) {
		for (BattleTile battleTile : boardBattleTiles) {
			if (battleTile.getTile().getX() == x && battleTile.getTile().getY() == y) {
				return true;
			}
		}
		return false;
	}

	public BattleTile getBattleTile(int x, int y) {
		for (BattleTile battleTile : boardBattleTiles) {
			if (battleTile.getTile().getX() == x && battleTile.getTile().getY() == y) {
				return battleTile;
			}
		}
		return null;
	}

	@Override
	public Tile getTile(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getBattleEvents() {
		return Arrays.asList(battleEvents.toString().split("\n"));
	}

	/**
	 * Metoda koja ispisuje trenutna polja na ploï¿½i, zajedno s njihovim imenima, ï¿½ivotnim bodovima te lokacijom
	 */

	public void printBoardState() {
		for (BattleTile battleTile : boardBattleTiles) {
			BoardTile tile = battleTile.getTile();
			System.out.println(tile.getName() + ", HP: " +
					tile.getHitPoints() + ", LOC: " +
					tile.getX() + ", " + tile.getY());
		}
	}

	public void printBoardUnitState() {
		for (BattleTile battleTile : boardBattleTiles) {
			if (battleTile.getTile() instanceof Unit) {
				Unit unit = (Unit)battleTile.getTile();
				System.out.println(unit.getName() + ", HP: " +
						unit.getHitPoints() + ", LOC: " +
						unit.getX() + ", " + unit.getY());
				System.out.print("speed:");
				for (Integer speed : unit.getSpeed()) {
					System.out.println(speed + " ");
				}
				System.out.print("attacks: ");
				for (Attack attack : unit.getAttacks()) {
					System.out.println("Attack Side: " + attack.getPointsTo() + " Attack Value: "+attack.getValue());
				}
				System.out.println();
			}
		}
	}

	@Override
	public boolean tileIsNetted(int x, int y, int noOfIterations, Player player) {
		if (noOfIterations == 10) {
			return false;
		}
		List<Pair> adjacentTiles = board.getAdjecantTiles(x, y);
		for (BattleTile tile : boardBattleTiles) {
			if (adjacentTiles.contains(new Pair(tile.getX(), tile.getY()))) {
				if (tile.getTile() instanceof Netter && !tile.getTile().getPlayer().equals(player)) {
					if (!tileIsNetted(tile.getX(), tile.getY(), noOfIterations+1, player)) {
						for (Pair p : ((Netter) tile.getTile()).getNettedTiles()) {
							if (p.getX() == x && p.getY() == y) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
