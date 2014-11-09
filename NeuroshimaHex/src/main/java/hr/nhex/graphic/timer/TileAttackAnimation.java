package hr.nhex.graphic.timer;

import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class TileAttackAnimation implements ActionListener {

	private static final int ATTACK_MOVE_PIXEL = 7;

	private Hexagon h;
	private NeuroshimaCanvas cn;

	private int moved = 0;

	public TileAttackAnimation(Hexagon h, NeuroshimaCanvas cn) {
		this.h = h;
		this.cn = cn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (moved < ATTACK_MOVE_PIXEL) {
			h.setLocation(new Point(h.getxC(), h.getyC()));
			h.setxC(h.getxC()+1);
			moved++;
		} else if (moved >= ATTACK_MOVE_PIXEL && moved < 2*ATTACK_MOVE_PIXEL) {
			h.setLocation(new Point(h.getxC(), h.getyC()));
			h.setxC(h.getxC()-1);
			moved++;
		} else {
			((Timer)e.getSource()).stop();
			cn.getTpma().setListenerOn(true);
		}
		cn.repaint();

	}

}
