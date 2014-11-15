package hr.nhex.graphic.timer;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.graphic.NeuroshimaCanvas;

import javax.swing.Timer;

public class BattleTimer {

	private static final int EVENT_ANIMATION_TIME = 2500;

	private NeuroshimaCanvas cn;
	private BattleSimulator bs;
	private TileAttackTimer tat;

	public BattleTimer(NeuroshimaCanvas cn, BattleSimulator bs, TileAttackTimer tat) {
		this.cn = cn;
		this.bs = bs;
		this.tat = tat;
	}

	public void animateBattle() {

		Timer timer = new Timer(EVENT_ANIMATION_TIME, new BattleAnimation(cn, bs, tat));

		bs.executeBattle();
		timer.start();

	}

}
