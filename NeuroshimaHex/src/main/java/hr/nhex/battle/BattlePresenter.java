package hr.nhex.battle;

import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.timer.BattleTimer;
import hr.nhex.graphic.timer.observer.TimerObserver;
import hr.nhex.graphic.timer.observer.TimerSubject;

public class BattlePresenter implements TimerObserver {

	private NeuroshimaCanvas cn;
	private BattleSimulator bs;
	private BattleTimer battleTimer;

	public BattlePresenter(NeuroshimaCanvas cn, BattleSimulator bs) {
		this.cn = cn;
		this.bs = bs;
	}

	public void executeAndPresent() {

		bs.executeNextRound();

		this.battleTimer = new BattleTimer(cn, bs);
		battleTimer.registerPresenterObserver(this);

		battleTimer.animateRound();

	}

	@Override
	public void update() {
		System.out.println("updated");
		bs.updateAfterEffects();
		cn.repaint();
		bs.executeNextRound();

		battleTimer.animateRound();
	}

	@Override
	public void setSubject(TimerSubject sub) {
		// TODO Auto-generated method stub

	}
}
