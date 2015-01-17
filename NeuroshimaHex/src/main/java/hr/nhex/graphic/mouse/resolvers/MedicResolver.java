package hr.nhex.graphic.mouse.resolvers;

import hr.nhex.battle.BattleEvent;
import hr.nhex.battle.BattleSimulator;
import hr.nhex.battle.BattleTile;
import hr.nhex.board.controls.MedicPair;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.hexagon.SpecialHex;
import hr.nhex.graphic.mouse.adapters.AdapterType;
import hr.nhex.graphic.timer.BattleAnimation;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MedicResolver extends AbstractMouseResolver {

	private MedicPhase medicSelectionPhase = MedicPhase.CHOOSE_MEDIC;

	private BattleAnimation currentBattleAnimation;
	private BattleSimulator currentBattleSimulator;

	private BattleTile tileMedic = null;
	private BattleTile tileTarget = null;
	private BattleTile tileAttacker = null;

	public MedicResolver(NeuroshimaCanvas cn) {
		this.cn = cn;
		this.game = cn.getGameInstance();
		this.hlc = HexagonListContainer.getInstance();
		this.type = AdapterType.MEDIC;
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		cn.repaint();
		Pair clickedTile = getClickedTile(cn, ev);
		int specialTilePos = hlc.getSpecialHexList().lastIndexOf(new SpecialHex(clickedTile, null));

		if (specialTilePos != -1) {
			if (medicSelectionPhase == MedicPhase.CHOOSE_MEDIC) {

				tileMedic = currentBattleSimulator.getBattleTile(clickedTile.getX(), clickedTile.getY());

				hlc.clearHexSpecialList();
				List<SpecialHex> specialHexList = new ArrayList<SpecialHex>();
				for (MedicPair medicPair : currentBattleSimulator.getMedicControl().getPossibleMedicPairs()) {
					if (medicPair.getMedicTile().getX() == tileMedic.getX() && medicPair.getMedicTile().getY() == tileMedic.getY()) {
						specialHexList.add(new SpecialHex(
								new Pair(medicPair.getMedicApplicant().getX(), medicPair.getMedicApplicant().getY()),
								medicPair.getMedicApplicant().getPlayer().getPlayerColor()));
					}
				}

				medicSelectionPhase = MedicPhase.CHOOSE_TARGET;
				hlc.setSpecialHexList(specialHexList);
				cn.repaint();
				return;
			} else if (medicSelectionPhase == MedicPhase.CHOOSE_TARGET) {

				tileTarget = currentBattleSimulator.getBattleTile(clickedTile.getX(), clickedTile.getY());

				hlc.clearHexSpecialList();
				List<SpecialHex> specialHexList = new ArrayList<SpecialHex>();
				for (BattleEvent battleEvent : currentBattleSimulator.getBattleEvents()) {
					if (battleEvent.getTarget().getX() == tileTarget.getX() && battleEvent.getTarget().getY() == tileTarget.getY()) {
						specialHexList.add(new SpecialHex(
								new Pair(battleEvent.getAttacker().getX(), battleEvent.getAttacker().getY()),
								tileMedic.getTile().getPlayer().getPlayerColor()));
					}
				}

				medicSelectionPhase = MedicPhase.CHOOSE_ATTACKER;
				hlc.setSpecialHexList(specialHexList);
				cn.repaint();
				return;
			} else if (medicSelectionPhase == MedicPhase.CHOOSE_ATTACKER) {

				tileAttacker = currentBattleSimulator.getBattleTile(clickedTile.getX(), clickedTile.getY());

				int damageFromSource = 0;

				for (BattleEvent battleEvent : currentBattleSimulator.getBattleEvents()) {
					if (battleEvent.getAttacker().getX() == tileAttacker.getX() && battleEvent.getAttacker().getY() == tileAttacker.getY() &&
							battleEvent.getTarget().getX() == tileTarget.getX() && battleEvent.getTarget().getY() == tileTarget.getY()) {
						damageFromSource += battleEvent.getValue();
					}
				}

				tileTarget.getTile().setHitPoints(tileTarget.getTile().getHitPoints()+damageFromSource);
				tileMedic.getTile().setHitPoints(0);

				medicSelectionPhase = MedicPhase.CHOOSE_MEDIC;
				disableAllResolvers();

				// stavi u animaciju medica
				currentBattleSimulator.getMedicControl().executeMedic();
				currentBattleAnimation.animateRound();

			}

		} else {
			return;
		}

	}

	public void setCurrentBattleSimulator(BattleSimulator bs) {
		this.currentBattleSimulator = bs;
	}

	@Override
	public void setCurrentBattleAnimation(BattleAnimation currentBattleAnimation) {
		this.currentBattleAnimation = currentBattleAnimation;
	}



}
