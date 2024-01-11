package game;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class Game implements Serializable{
	private static final long serialVersionUID = 2972434127030124554L;
	private String name;
	private ArrayList<Round> rounds;
	private Round currentRound;
	private int streak;
	private int score;

	public static void displayMainMenu() {
		System.out.println("Pirated Fight List");
		System.out.println("==================");
		System.out.println("1. Start a new game");
		System.out.println("2. Check leaderboard");
		System.out.println("3. Tutorial");
		System.out.println("4. Exit");
		System.out.print("Select an option (Enter a number): ");
	}
	public static void displayTutorial() {
		System.out.println("Tutorial");
		System.out.println("========");
		System.out.println("Each round a topic will be displayed");
		System.out.println("You have 40 seconds to type in any word associated to that topic");
		System.out.println("Don't worry about cases");
		System.out.println("NOTE THAT MULTIPLE WORDS ANSWER, DON'T TYPE THE SPACE, PUT THEM ALL AS ONE WORD");
		System.out.println("After 3 rounds your game will be concluded");
		System.out.println("Occasionally there will be a special round");
		System.out.println("Press enter to continue");
	}
	public Game() {
		this.setName(null);
		this.setStreak(0);
		this.setScore(0);
		this.setRounds(new ArrayList<Round>());
	}
	public Game(String name) {
		this.setName(name);
		this.setStreak(0);
		this.setScore(0);
		this.setRounds(new ArrayList<Round>());
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStreak() {
		return streak;
	}
	public void setStreak(int streak) {
		this.streak = streak;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public ArrayList<Round> getRounds() {
		return rounds;
	}
	public void setRounds(ArrayList<Round> rounds) {
		this.rounds = rounds;
	}
	public Round getCurrentRound() {
		return currentRound;
	}
	public void setCurrentRound(Round currentRound) {
		this.currentRound = currentRound;
	}
	public static void pseudoClearScreen() {
		for(int i = 0; i<50;i++) {
			System.out.println();
		}
	}
	public void newRound() {
		Round round = null;
		Random rand = new Random();
		int num = rand.nextInt(100); 
		if (num<10) {
			round = new BonusRound();
		} else if (num>=10&&num<20) {
			round = new FreezeRound();
		}else {
			round = new Round();
		}
		round.randomizeQuestion();
		this.currentRound = round;
		this.rounds.add(round);
	}
	public void startGame(Scanner input) {
		int numOfRounds = 3;
		System.out.print("Enter you name: ");
		String name = input.nextLine();
		this.name = name;
		for (int i =0;i<numOfRounds;i++) {
			System.out.print("Press Enter when you are ready for the next round");
			input.nextLine();
			newRound();
			playRound(input);
			endRoundOptions(input);
		}
		endGameOptions(input);
	}
	private void playRound(Scanner input) {
		pseudoClearScreen();
		Round currentRound = getCurrentRound();
		long startTime = System.currentTimeMillis();
		Timer timer = new Timer();
		long gameTime = 40000;
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("Game Over, Press Enter to Continue!");
			}
		},gameTime);
		while(((System.currentTimeMillis()-startTime)<gameTime)&&!getCurrentRound().guessedAll()) {
			pseudoClearScreen();
			System.out.println("Topic: "+currentRound.getTopic().getTopic());
			System.out.println("Current Guesses:");
			currentRound.displayGuesses();
			System.out.println("Current Score: "+currentRound.getScore());
			currentRound.displayMessage();
			System.out.print("Enter your answer: ");
			String answer = input.nextLine();
			if (!currentRound.checkIfGuessed(answer)) {
				currentRound.checkAnswer(answer);
			}
		}
		timer.cancel();
		timer.purge();
		if (getCurrentRound().guessedAll()) {
			System.out.println("YOU GOT ALL THE ANSWER!!!");
			System.out.println("Game Over, Press Enter to Continue!");
			input.nextLine();
		}
		if (currentRound instanceof BonusRound) {
			this.score += currentRound.getScore()*2;
		} else {
			this.score += currentRound.getScore();
		}
	}	
	private void endRoundOptions(Scanner input) {
		System.out.println("Type 1 to display all answer");
		System.out.println("Type any other number to skip");
		int choice = 0;
		choice = input.nextInt();
		input.nextLine();
		if (choice ==1) {
			this.currentRound.displayAllAnswers();
		}
	}
	private void endGameOptions(Scanner input) {
		int choice = 0;
		while(choice!=3) {
			pseudoClearScreen();
			displayEndGameOptions();
			choice = input.nextInt();
			input.nextLine();
			switch (choice){
				case 1:
					reviewRounds(input);
					break;
				case 2: //Implement score board later
					break;
			}
		}
		
	}	private void displayRoundResult(int i) {
		if (i >= getRounds().size()) {
			System.out.println("Please enter a valid round number");
			return;
		}
		pseudoClearScreen();
		System.out.println("Displaying result for round "+i);
		System.out.println("Topics: "+getRounds().get(i-1).getTopic());
		System.out.println("Guesses:");
		getRounds().get(i-1).displayGuesses();
	}
	private void displayRoundsPlayed() {
		for (int i = 0; i<getRounds().size();i++) {
			System.out.println((i+1)+". "+getRounds().get(i).getTopic());
		}
	}
	private void displayEndGameOptions() {
		System.out.println("1. Review rounds");
		System.out.println("2. Check the leaderboard");
		System.out.println("3. End Game");
		System.out.print("Select an option (Enter a number): ");
	}
	private void reviewRounds(Scanner input) {
		System.out.println("REVIEW ROUNDS");
		System.out.println("Rounds:");
		displayRoundsPlayed();
		System.out.print("Enter a round number: ");
		int choice = input.nextInt();
		input.nextLine();
		displayRoundResult(choice);
		System.out.println("Enter 1 to show all possible answers, any other number to skip");
		int show = input.nextInt();
		input.nextLine();
		if(show ==1) {
			getRounds().get(choice).displayAllAnswers();
		}
		System.out.println("Press enter to continue");
		input.nextLine();
	}

}
