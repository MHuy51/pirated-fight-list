package main;
import java.util.InputMismatchException;
import java.util.Scanner;

import game.Admin;
import game.Game;
import game.Leaderboard;

public class Main {
	public static void main(String[] args) {
		Leaderboard lb = new Leaderboard();
		lb.readLeaderboard();
		int choice =0;
		try (Scanner input = new Scanner(System.in)) {
			while (choice !=4) {
				Game.pseudoClearScreen();
				Game.displayMainMenu();	
				try {
					choice = input.nextInt();
					input.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("Please enter a valid number");
					System.out.println("Press enter to continue");
					input.nextLine();
				}
				switch (choice) {
					case 1: //New game
						Game game = new Game();
						game.startGame(input);
						lb.addGame(game);
						lb.writeLeaderboard();
						Game.pseudoClearScreen();
						break;
					case 2:
						lb.displayLeaderboard();
						input.nextLine();
						break;
					case 3:
						Game.displayTutorial();
						input.nextLine();
						break;
					case 7: //Not really hidden bruh
						Admin.adminMenu(input);
						break;
					case 8:
						Admin.readAndAddTopic();
				}
			}
			input.close();
		}

	}
}
