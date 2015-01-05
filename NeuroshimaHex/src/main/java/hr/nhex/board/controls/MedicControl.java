package hr.nhex.board.controls;

import hr.nhex.board.BoardTile;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.hexagon.SpecialHex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MedicControl {

	List<MedicPair> medicTargetPairs = new ArrayList<MedicPair>();

	List<MedicPair> possibleMedicPairs = new ArrayList<MedicPair>();

	public void executeMedic() {

		possibleMedicPairs.clear();
		findPossibleMedicTargets();

	}

	private void findPossibleMedicTargets() {

		for (MedicPair mp : getMedicTargetPairs()) {
			BoardTile hitUnit = mp.getMedicApplicant();
			if (hitUnit.getHitPoints() < hitUnit.getMaxHitPoints() && mp.getMedicTile().getHitPoints() > 0) {
				possibleMedicPairs.add(mp);
			}
		}


	}

	public List<SpecialHex> possibleMedicSpecialHex() {//List<BattleEvent> battleEvents) {

		List<SpecialHex> specialHexList = new ArrayList<SpecialHex>();

		for (MedicPair medicPair : medicTargetPairs) {

			//			int numberOfMedicTargets = 0;

			BoardTile medic = medicPair.getMedicTile();
			//			for (MedicPair otherMedicPair : medicTargetPairs) {
			//				if (medic.getX() == otherMedicPair.getMedicTile().getX() && medic.getY() == otherMedicPair.getMedicTile().getY()) {
			//					numberOfMedicTargets++;
			//				}
			//			}

			SpecialHex sh = new SpecialHex(new Pair(medic.getX(), medic.getY()), medic.getPlayer().getPlayerColor());

			if (!specialHexList.contains(sh) ) {//&& numberOfMedicTargets > 1) {
				specialHexList.add(sh);
			}

		}





		return specialHexList;
	}

	public void addMedicTargetPair(BoardTile medicTile, BoardTile medicApplicant) {
		medicTargetPairs.add(new MedicPair(medicTile, medicApplicant));
	}

	public void clear() {
		medicTargetPairs.clear();
	}

	public List<MedicPair> getMedicTargetPairs() {
		return medicTargetPairs;
	}

	public List<MedicPair> getPossibleMedicPairs() {
		return possibleMedicPairs;
	}

	public class MedicPairXComparator implements Comparator<BoardTile>
	{
		@Override
		public int compare(BoardTile bt1, BoardTile bt2)
		{
			return ((Integer)bt1.getX()).compareTo(bt2.getX());
		}
	}

	public class MedicPairYComparator implements Comparator<BoardTile>
	{
		@Override
		public int compare(BoardTile bt1, BoardTile bt2)
		{
			return ((Integer)bt1.getY()).compareTo(bt2.getY());
		}
	}

}
