package game;

import java.io.Serializable;
import java.util.ArrayList;

public class Topic implements Serializable{
	private static final long serialVersionUID = -8361898130133860372L;
	private String topic;
	private ArrayList<Answer> answers;
	public Topic() {
		this.setTopic(null);
		this.setAnswers(null);
	}
	public Topic(String topic, ArrayList<Answer> answers) {
		this.setTopic(topic);
		this.setAnswers(answers);
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	public void displayInfo() {
		System.out.println("Topic: "+topic);
		for (int i=0; i<answers.size();i++) {
			System.out.println(i+". "+answers.get(i).getAnswer()+"("+answers.get(i).getScore()+")");
		}
	}
	
}
