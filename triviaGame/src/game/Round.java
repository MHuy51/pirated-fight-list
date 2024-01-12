package game;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
/*
 * Changes to be done:
 * 		- probably make it so it ignores cases
 * 
 */
public class Round implements Serializable{
	private static final long serialVersionUID = -7315542412343735589L;
	private Topic topic;
	private ArrayList<String> guesses;
	private ArrayList<Boolean> results;
	private int score;
	private boolean roundOver;
	public Round() {
		this.setTopic(new Topic());
		this.setGuesses(new ArrayList<String>());
		this.setResults(new ArrayList<Boolean>());
		this.setScore(0);
		this.setRoundOver(false);
	}
	public ArrayList<Boolean> getResults() {
		return results;
	}
	public void setResults(ArrayList<Boolean> results) {
		this.results = results;
	}
	public Topic getTopic() {
		return this.topic;
	}
	private void setTopic(Topic topic) {
		this.topic  = topic;
	}
	public ArrayList<String> getGuesses() {
		return guesses;
	}
	public void setGuesses(ArrayList<String> guesses) {
		this.guesses = guesses;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isRoundOver() {
		return roundOver;
	}
	public void setRoundOver(boolean roundOver) {
		this.roundOver = roundOver;
	}
	public boolean guessedAll() {
		int maxPossibleScore = 0;
		for (int i = 0; i<this.topic.getAnswers().size();i++) {
			maxPossibleScore+=this.topic.getAnswers().get(i).getScore();
		}
		if (this.getScore()==maxPossibleScore) {
			return true;
		}
		return false;
	}
	public boolean checkIfGuessed(String answer) {
		return this.guesses.contains(answer);
	}
	public boolean checkAnswer(String answer) {
		answer.toLowerCase();
		answer = answer.trim();
		this.guesses.add(answer);
		boolean found = false;
		int index = 0;
		while(!found&&index<this.topic.getAnswers().size()) {
			Answer temp = this.topic.getAnswers().get(index);
			if (temp.getAnswer().toLowerCase().equals(answer)) {
				found = true;
				this.score+=temp.getScore();
			}
			index++;
		}
		this.results.add(found);
		return found;
	}
	@SuppressWarnings("unchecked")
	public void randomizeQuestion() {
		ArrayList<Topic> topics = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Admin.getProjectName()+"/topics.ser"))){
			topics = (ArrayList<Topic>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Adding the question into it
		Random rand = new Random();
		int number = rand.nextInt(topics.size());
		this.topic = topics.get(number);
	}
	public void displayResult() {
		System.out.println("Round result:");
		System.out.println("Current guesses:");
		for(int i = 0; i<this.getGuesses().size();i++) {
			System.out.println(this.getGuesses().get(i)+"---"+this.getResults().get(i));
		}
		System.out.println("==========================");
		System.out.println("Final score: "+this.getScore());
	}
	public void displayAllAnswers() {
		System.out.println("All possible answers:");
		for(int i = 0; i<this.topic.getAnswers().size();i++) {
			System.out.println(this.topic.getAnswers().get(i).getAnswer());
		}
	}
	public void displayGuesses() {
		for(int i = 0; i<this.guesses.size();i++) {
			System.out.println(this.guesses.get(i)+"---"+this.results.get(i));
		}
	}
	public void displayMessage() {
		System.out.println("Normal round, try to type as much as you can");
	}

}
