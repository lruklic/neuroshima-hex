package hr.nhex.battle;

import hr.nhex.board.BoardTile;

import java.util.ArrayList;
import java.util.List;

public class BattleEvent {

	private BattleEventType eventType;
	private BoardTile attacker;
	private List<BoardTile> targets = new ArrayList<BoardTile>();
	private int value;


	public BattleEvent(BattleEventType eventType, BoardTile attacker, int value, BoardTile target) {
		this.eventType = eventType;
		this.attacker = attacker;
		this.value = value;
		this.targets.add(target);
	}

	public BattleEvent(BattleEventType eventType, BoardTile attacker, int value, BoardTile... targets) {
		this.eventType = eventType;
		this.attacker = attacker;
		this.value = value;
		for (BoardTile target : targets) {
			this.targets.add(target);
		}
	}

	public BattleEventType getEventType() {
		return eventType;
	}


	public void setEventType(BattleEventType eventType) {
		this.eventType = eventType;
	}


	public BoardTile getAttacker() {
		return attacker;
	}


	public void setAttacker(BoardTile attacker) {
		this.attacker = attacker;
	}


	public BoardTile getTarget() {
		return targets.get(0);
	}

	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}




}

