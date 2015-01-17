package hr.nhex.board.controls;

import hr.nhex.board.BoardTile;

public class MedicPair {

	private BoardTile medicTile;
	private BoardTile medicApplicant;

	public MedicPair(BoardTile medicTile, BoardTile medicApplicant) {
		super();
		this.medicTile = medicTile;
		this.medicApplicant = medicApplicant;
	}
	public BoardTile getMedicTile() {
		return medicTile;
	}
	public void setMedicTile(BoardTile medicTile) {
		this.medicTile = medicTile;
	}
	public BoardTile getMedicApplicant() {
		return medicApplicant;
	}
	public void setMedicApplicant(BoardTile medicApplicant) {
		this.medicApplicant = medicApplicant;
	}

}
