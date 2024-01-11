package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import game.Admin;
import game.Game;
import game.Leaderboard;

/*
 * Basic game
 * Will be played in the console
 * To be done
 * 		- Learn mysql for java to store score, info, questions
 * 		- Learn javaFX to make this more presentable
 * 		- Make the thign more presentable?
 * 		- Clean up and organize code to make more sense and look better
 *
 * Miscellaneous
 * 		- How to play***
 * 		- Log in/ log out (only after mysql)
 * 		- Probably make round as private class for game, but it's really hard to see
 */
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
