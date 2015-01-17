package hr.nhex.battle;

import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.timer.BattleAnimation;
import hr.nhex.graphic.timer.TileAttackTimer;
import hr.nhex.graphic.timer.observer.TimerObserver;
import hr.nhex.graphic.timer.observer.TimerSubject;

public class BattlePresenter implements TimerObserver {

	private NeuroshimaCanvas cn;
	private BattleSimulator bs;

	private BattleAnimation battleAnimation;

	public BattlePresenter(NeuroshimaCanvas cn, BattleSimulator bs) {
		this.cn = cn;
		this.bs = bs;
	}

	public void executeAndPresent() {

		bs.executeNextRound();

		this.battleAnimation = new BattleAnimation(bs, new TileAttackTimer(cn));
		battleAnimation.register(this);
		battleAnimation.animateRound();

	}

	@Override
	public void update() {
		bs.updateAfterEffects();
		cn.repaint();

		if (bs.executeNextRound()) {
			cn.getButtonContainer().getEndButton().doClick();
			//disablePressedButton();
			return;
		}

		battleAnimation.animateRound();
	}

	@Override
	public void setSubject(TimerSubject sub) {
		// TODO Auto-generated method stub

	}
}
