package game;

import java.io.Serializable;

public class Answer implements Serializable{
	private static final long serialVersionUID = -3087021628732264370L;
	private String answer;
	private int score;
	public Answer(String answer, int score) {
		this.setAnswer(answer);
		this.setScore(score);
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
