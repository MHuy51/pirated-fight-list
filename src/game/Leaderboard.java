package game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard{
	private ArrayList<Game> games;
	public Leaderboard() {
		this.games = new ArrayList<Game>();
	}
	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	public void addGame(Game game) {
		if (this.games == null) {
			this.games = new ArrayList<Game>();
		}
		this.games.add(game);
		Collections.sort(games, Comparator.comparingInt(Game::getScore).reversed());
		if(this.games.size()>10) {
			this.games = (ArrayList<Game>) this.games.subList(0, 10);
		}
	}
	// Write new leader board into the file as serial
	public void writeLeaderboard() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("leaderboard.ser"))){
			oos.writeObject(games);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked") 
	public void readLeaderboard() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("leaderboard.ser"))){
			games = (ArrayList<Game>) ois.readObject();
			ois.close();
		} catch (EOFException e) {
			System.out.println("First time running");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			}
	}
	public void displayLeaderboard() {
		Game.pseudoClearScreen();
		System.out.println("===========================================");
		System.out.print("||");
		System.out.printf("%-5s", "Rank");
		System.out.print("||");
		System.out.printf("%-20s", "Name");
		System.out.print("||");
		System.out.printf("%-10s", "Score");
		System.out.println("||");
		System.out.println("===========================================");
		if (games != null) {
			int rank = 1;
			for (Game game : games) {
				System.out.print("||");
				System.out.printf("%-5s", rank);
				System.out.print("||");
				System.out.printf("%-20s",game.getName());
				System.out.print("||");
				System.out.printf("%10s", game.getScore());
				System.out.println("||");
				rank++;
			}
		}
		
		System.out.println("===========================================");
		System.out.print("Press enter to continue");
	}
}
