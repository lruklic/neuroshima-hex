package hr.nhex.battle;


public class BattleSimulatorTest {
	//
	//	@Test
	//	public void battleTest1() {
	//
	//		Player firstPlayer = new Player("Stijena", Color.YELLOW, null);
	//		Player secondPlayer = new Player("Marin", Color.BLUE, null);
	//
	//		HegemonyDeck deck = new HegemonyDeck();
	//		for (AbstractTile tile : deck.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(firstPlayer);
	//			}
	//		}
	//
	//		BorgoDeck deck2 = new BorgoDeck();
	//		for (AbstractTile tile : deck2.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(secondPlayer);
	//			}
	//		}
	//
	//		deck.getTiles();
	//
	//		Board board = new Board();
	//
	//		board.addTile((BoardTile)deck.getTileByName("Officer One"), 0, 0, 0);
	//		board.addTile((BoardTile)deck.getTileByName("Ganger"), 0, 1, 0);
	//		board.addTile((BoardTile)deck.getTileByName("Runner"), 1, 0, 1);
	//		board.addTile((BoardTile)deck.getTileByName("Universal Soldier"), 2, 0, 4);
	//		board.addTile((BoardTile)deck.getTileByName("The Boss"), 2, -1, 1);
	//		board.addTile((BoardTile)deck.getTileByName("Scout"), -1, 2, 1);
	//
	//		board.addTile((BoardTile)deck2.getTileByName("Mutant"), 0, 2, 3);
	//		board.addTile((BoardTile)deck2.getTileByName("Super Mutant"), 1, 1, 3);
	//		board.addTile((BoardTile)deck2.getTileByName("Officer"), 2, -2, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Butcher"), 1, -1, 1);
	//
	//		BattleSimulator bs = new BattleSimulator(board);
	//		bs.executeBattle();
	//		bs.printBoardUnitState();
	//
	//	}
	//
	//	@Test
	//	public void battleTest2() {
	//
	//		Player firstPlayer = new Player("Stijena", Color.YELLOW, null);
	//		Player secondPlayer = new Player("Marin", Color.BLUE, null);
	//
	//		HegemonyDeck deck = new HegemonyDeck();
	//		for (AbstractTile tile : deck.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(firstPlayer);
	//			}
	//		}
	//
	//		BorgoDeck deck2 = new BorgoDeck();
	//		for (AbstractTile tile : deck2.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(secondPlayer);
	//			}
	//		}
	//
	//		deck.getTiles();
	//
	//		Board board = new Board();
	//
	//		board.addTile((BoardTile)deck.getTileByName("Runner"), -1, 0, 0);
	//		board.addTile((BoardTile)deck.getTileByName("Universal Soldier"), -1, 2, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Thug"), 1, -2, 1);
	//		board.addTile((BoardTile)deck.getTileByName("Gladiator"), 1, 0, 0);
	//
	//		board.addTile((BoardTile)deck2.getTileByName("Mutant"), 0, 0, 0);
	//		board.addTile((BoardTile)deck2.getTileByName("Super Mutant"), -2, 2, 0);
	//		board.addTile((BoardTile)deck2.getTileByName("Mutant"), -1, -1, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Butcher"), 2, -2, 2);
	//		board.addTile((BoardTile)deck2.getTileByName("Butcher"), 0, 2, 3);
	//		board.addTile((BoardTile)deck2.getTileByName("Assassin"), 2, 0, 3);
	//
	//		board.addTile((BoardTile)deck2.getTileByName("Scout"), 1, 1, 3);
	//
	//
	//		BattleSimulator bs = new BattleSimulator(board);
	//
	//		System.out.println("Before battle: ");
	//		bs.printBoardState();
	//		bs.executeBattle();
	//		System.out.println("After battle: ");
	//		bs.printBoardState();
	//
	//	}
	//
	//	@Test
	//	public void battleTest3() {
	//
	//		Player firstPlayer = new Player("Lenard", Color.YELLOW, null);
	//		Player secondPlayer = new Player("Luka", Color.BLUE, null);
	//
	//		HegemonyDeck deck = new HegemonyDeck();
	//		for (AbstractTile tile : deck.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(firstPlayer);
	//			}
	//		}
	//
	//		BorgoDeck deck2 = new BorgoDeck();
	//		for (AbstractTile tile : deck2.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(secondPlayer);
	//			}
	//		}
	//
	//		deck.getTiles();
	//
	//		Board board = new Board();
	//
	//		board.addTile((BoardTile)deck2.getTileByName("Brawler"), 0, -1, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Scout"), 0, -2, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Super Mutant"), -1, -1, 2);
	//		board.addTile((BoardTile)deck2.getTileByName("Butcher"), 1, -2, 0);
	//		board.addTile((BoardTile)deck2.getTileByName("Mutant"), 0, 0, 0);
	//		board.addTile((BoardTile)deck2.getTileByName("Net Fighter"), -2, 1, 5);
	//		board.addTile((BoardTile)deck2.getTileByName("Mutant"), 1, 1, 4);
	//		board.addTile((BoardTile)deck2.getTileByName("Super Officer"), 0, 2, 4);
	//		board.addTile((BoardTile)deck2.getTileByName("Net Fighter"), 0, 1, 5);
	//		board.addTile((BoardTile)deck2.getTileByName("Assassin"), -1, 2, 4);
	//
	//		board.addTile((BoardTile)deck.getTileByName("Thug"), 1, -1, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Gladiator"), -1, 0, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Universal Soldier"), -1, 1, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Guard"), 1, 0, 2);
	//		board.addTile((BoardTile)deck.getTileByName("Ganger"), -2, 2, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Runner"), 2, 0, 2);
	//		board.addTile((BoardTile)deck.getTileByName("Scout"), 2, -1, 2);
	//		board.addTile((BoardTile)deck.getTileByName("Universal Soldier"), 2, -2, 3);
	//
	//		BattleSimulator bs = new BattleSimulator(board);
	//
	//		System.out.println("Before battle: ");
	//		bs.printBoardState();
	//		bs.executeBattle();
	//		System.out.println();
	//		System.out.println("After battle: ");
	//		bs.printBoardState();
	//
	//	}
	//
	//	@Test
	//	public void battleTest4() {
	//
	//		Player firstPlayer = new Player("Lenard", Color.YELLOW, null);
	//		Player secondPlayer = new Player("Luka", Color.BLUE, null);
	//
	//		HegemonyDeck deck = new HegemonyDeck();
	//		for (AbstractTile tile : deck.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(firstPlayer);
	//			}
	//		}
	//
	//		BorgoDeck deck2 = new BorgoDeck();
	//		for (AbstractTile tile : deck2.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(secondPlayer);
	//			}
	//		}
	//
	//		deck.getTiles();
	//
	//		Board board = new Board();
	//
	//		board.addTile((BoardTile)deck2.getTileByName("Brawler"), -1, 0, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Scout"), 0, -2, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Scout"), -1, -1, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Super Officer"), -2, 0, 0);
	//		board.addTile((BoardTile)deck2.getTileByName("Officer"), -2, 1, 5);
	//		board.addTile((BoardTile)deck2.getTileByName("Net Fighter"), 0, -1, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Net Fighter"), -1, 2, 4);
	//		board.addTile((BoardTile)deck2.getTileByName("Mutant"), 1, -2, 1);
	//		board.addTile((BoardTile)deck2.getTileByName("Super Mutant"), 2, -1, 3);
	//		board.addTile((BoardTile)deck2.getTileByName("Butcher"), 1, 0, 1);
	//
	//		board.addTile((BoardTile)deck.getTileByName("Officer One"), 0, 1, 3);
	//		board.addTile((BoardTile)deck.getTileByName("Officer Two"), -2, 2, 5);
	//		board.addTile((BoardTile)deck.getTileByName("Gladiator"), -1, 1, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Scout"), 0, 2, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Guard"), 0, 0, 5);
	//		board.addTile((BoardTile)deck.getTileByName("Ganger"), 1, -1, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Net Master"), 2, -2, 3);
	//		board.addTile((BoardTile)deck.getTileByName("Net Fighter"), 2, 0, 3);
	//		board.addTile((BoardTile)deck.getTileByName("Universal Soldier"), 1, 1, 4);
	//
	//		BattleSimulator bs = new BattleSimulator(board);
	//
	//		System.out.println("Before battle: ");
	//		bs.printBoardState();
	//		bs.executeBattle();
	//		System.out.println();
	//		System.out.println("After battle: ");
	//		bs.printBoardState();
	//
	//	}
	//
	//	@Test
	//	public void battleTest5() {
	//
	//		Player firstPlayer = new Player("Rade", Color.YELLOW, null);
	//		Player secondPlayer = new Player("Stijena", Color.BLUE, null);
	//
	//		HegemonyDeck deck = new HegemonyDeck();
	//		for (AbstractTile tile : deck.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(firstPlayer);
	//			}
	//		}
	//
	//		BorgoDeck deck2 = new BorgoDeck();
	//		for (AbstractTile tile : deck2.getTiles()) {
	//			if (tile instanceof BoardTile) {
	//				((BoardTile)tile).setPlayer(secondPlayer);
	//			}
	//		}
	//
	//		deck.getTiles();
	//
	//		Board board = new Board();
	//
	//		board.addTile((BoardTile)deck2.getTileByName("Scout"), 0, -2, 0);
	//		board.addTile((BoardTile)deck2.getTileByName("Net Fighter"), 1, -1, 3);
	//		board.addTile((BoardTile)deck2.getTileByName("Butcher"), 1, -2, 0);
	//		board.addTile((BoardTile)deck2.getTileByName("Butcher"), -2, 0, 5);
	//		board.addTile((BoardTile)deck2.getTileByName("Mutant"), 0, 2, 5);
	//		board.addTile((BoardTile)deck2.getTileByName("Assassin"), -1, 2, 4);
	//
	//		board.addTile((BoardTile)deck.getTileByName("The Boss"), 0, -1, 3);
	//		board.addTile((BoardTile)deck.getTileByName("Gladiator"), -1, -1, 1);
	//		board.addTile((BoardTile)deck.getTileByName("Thug"), 1, 0, 4);
	//		board.addTile((BoardTile)deck.getTileByName("Guard"), 1, 1, 1);
	//		board.addTile((BoardTile)deck.getTileByName("Net Fighter"), 2, -2, 2);
	//
	//		BattleSimulator bs = new BattleSimulator(board);
	//
	//		System.out.println("Before battle: ");
	//		bs.printBoardState();
	//		bs.executeBattle();
	//		System.out.println();
	//		System.out.println("After battle: ");
	//		bs.printBoardState();
	//
	//	}

}
