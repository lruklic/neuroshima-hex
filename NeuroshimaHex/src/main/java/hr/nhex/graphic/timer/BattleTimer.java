package hr.nhex.graphic.timer;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.timer.observer.TimerObserver;

import javax.swing.Timer;

/**
 * Class
 *
 * @author ZEUS
 *
 */

public class BattleTimer {

	private static final int EVENT_ANIMATION_TIME = 2500;

	private BattleAnimation bta;

	private int battleAnimationTime = 0;

	public BattleTimer(NeuroshimaCanvas cn, BattleSimulator bs) {
		TileAttackTimer tat = new TileAttackTimer(cn);
		this.bta = new BattleAnimation(bs, tat);
		this.battleAnimationTime = bs.getBattleEvents().size() * 500 + 500;
	}

	public void animateRound() {

		Timer timer = new Timer(battleAnimationTime, bta);

		timer.start();

	}

	public void registerPresenterObserver(TimerObserver obj) {
		if (!bta.getObservers().contains(obj)) {
			bta.register(obj);
		}
	}

	public void unregisterPresenterObserver(TimerObserver obj) {
		bta.unregister(obj);
	}

}
