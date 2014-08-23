package hr.tecaj.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo {

	public static void main(String[] args) {
		//int[] players = new int[4];
		
		List<String> races = new ArrayList<>();
		races.add("Hegemony");
		races.add("Outpost");
		races.add("Moloch");
		races.add("Borgo");
		races.add("New York");
		races.add("Steel Police");
		races.add("Neojungle");
		races.add("Smart");
		races.add("Vegas");
		races.add("Sharrash");
		races.add("Dancer");
		races.add("Mephisto");
		
		List<String> playerRaces = new ArrayList<>();
		
		Random rand = new Random();
		
		while (playerRaces.size() < 4) {
			int i = rand.nextInt(races.size());
			if (!playerRaces.contains(races.get(i))) {
				playerRaces.add(races.get(i));
			}
		}

		System.out.println("Lovro je " + playerRaces.get(0));
		System.out.println("Luka je " + playerRaces.get(1));
		System.out.println("Kruno je " + playerRaces.get(2));
		System.out.println("Marin je " + playerRaces.get(3));

	}

}
