package hr.nhex.graphic.timer;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.timer.observer.TimerObserver;
import hr.nhex.graphic.timer.observer.TimerSubject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class BattleAnimation implements ActionListener, TimerSubject {

	private List<TimerObserver> observers = new ArrayList<TimerObserver>();

	private TileAttackTimer tat;
	private BattleSimulator bs;

	private int currentEvent = 0;

	public BattleAnimation(BattleSimulator bs, TileAttackTimer tat) {
		this.bs = bs;
		this.tat = tat;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (currentEvent == bs.getBattleEvents().size()) {
			notifyObservers();
			((Timer)e.getSource()).stop();
			currentEvent = 0;
			return;
		}

		String[] eventParameters = bs.getBattleEvents().get(currentEvent).split(" ");

		if (eventParameters[0].equals("attack") || eventParameters[0].equals("rattack")) {

			Pair attackerPos = new Pair(Integer.parseInt(eventParameters[1]), Integer.parseInt(eventParameters[2]));
			Pair hitPos = new Pair(Integer.parseInt(eventParameters[3]), Integer.parseInt(eventParameters[4]));

			tat.animateAttack(attackerPos, hitPos);
		}

		currentEvent++;

	}

	public List<TimerObserver> getObservers() {
		return observers;
	}

	@Override
	public void register(TimerObserver obj) {
		if (!observers.contains(obj)) {
			observers.add(obj);
		}
	}

	@Override
	public void unregister(TimerObserver obj) {
		observers.remove(obj);
	}

	@Override
	public void notifyObservers() {
		for (TimerObserver obj : observers) {
			obj.update();
		}
	}

	@Override
	public Object getUpdate(TimerObserver obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
