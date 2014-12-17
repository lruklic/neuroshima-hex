package hr.nhex.generic;

import hr.nhex.board.BoardTile;

public class BoardTilePair {

	private BoardTile user;
	private BoardTile source;

	public BoardTilePair(BoardTile user, BoardTile source) {
		this.user = user;
		this.source = source;
	}

	public BoardTile getUser() {
		return user;
	}
	public void setUser(BoardTile user) {
		this.user = user;
	}
	public BoardTile getSource() {
		return source;
	}
	public void setSource(BoardTile source) {
		this.source = source;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BoardTilePair other = (BoardTilePair) obj;
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}



}
