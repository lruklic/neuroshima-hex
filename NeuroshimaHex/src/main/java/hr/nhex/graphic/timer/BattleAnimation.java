package hr.nhex.graphic.timer;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class BattleAnimation implements ActionListener {

	private NeuroshimaCanvas cn;
	private BattleSimulator bs;
	private TileAttackTimer tat;

	private int currentEvent = 0;

	public BattleAnimation(NeuroshimaCanvas cn, BattleSimulator bs, TileAttackTimer tat) {
		this.cn = cn;
		this.bs = bs;
		this.tat = tat;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (currentEvent == bs.getBattleEvents().size()) {

			System.out.println("izvrsi se");
			((Timer)e.getSource()).stop();
			bs.updateAfterEffects();
			cn.repaint();
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

}
