package game;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
	public static void adminMenu(Scanner input) {
		int choice = 0;
		while(choice!=3) {
			displayMenu();
			choice = input.nextInt();
			input.nextLine();
			switch(choice) {
				case 1:
					addTopic(input);
					break;
				case 2:
					editTopic(input);
					break;
				case 3:
					break;
				default:
					System.out.println("Pelase enter a valid number");
			}
		}
	}
	public static void displayMenu() {
		System.out.println("ADMIN MENU");
		System.out.println("1. Add a new topic");
		System.out.println("2. Edit a topic");
		System.out.println("3. Exit");
		System.out.print("Enter your choice:");
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<Topic> readTopics() {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("topics.ser"))){
			topics = (ArrayList<Topic>) ois.readObject();
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
		return topics;
	}
	public static void writeTopics(ArrayList<Topic> topics) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("topics.ser"))){
			oos.writeObject(topics);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void addTopic(Scanner input) {
		ArrayList<Answer> answers = new ArrayList<Answer>();
		Topic topic = new Topic();
		String temp="";
		int choice = 0;
		System.out.println("Enter Topic: ");
		temp = input.nextLine();
		topic.setTopic(temp);
		System.out.println("Enter Answers (You can continue forever, when done write 'none': ");
		while(!temp.equals("none")){
			temp = input.nextLine();
			if (!temp.equals("none")) {
				answers.add(new Answer(temp,1));
			}
		}
		Game.pseudoClearScreen();
		System.out.println("Set any special score? (Some answer can have more score than other)");
		System.out.println("Enter 1 to confirm, any other number to skip: ");
		choice = input.nextInt();
		input.nextLine();
		if(choice==1) {
			for(int i =0; i<answers.size();i++) {
				System.out.println(i+". "+answers.get(i).getAnswer());
			}
			System.out.println("Enter all answer you want to have score of 2 (You can continue forever, when done write '-1'): ");
			do {
				choice = input.nextInt();
				input.nextLine();
				if(choice!=-1&&choice<answers.size()) {
					answers.get(choice).setScore(2);
				}
			} while (choice !=-1);
			for(int i =0; i<answers.size();i++) {
				System.out.println(i+". "+answers.get(i).getAnswer());
			}
			System.out.println("Enter all answer you want to have score of 3 (You can continue forever, when done write '-1'): ");
			do {
				choice = input.nextInt();
				input.nextLine();
				if(choice!=-1&&choice<answers.size()) {
					answers.get(choice).setScore(3);
				}
			} while (choice !=-1);
		}
		System.out.println("Topic: "+topic.getTopic());
		topic.setAnswers(answers);
		for(int i =0; i<answers.size();i++) {
			System.out.println(i+". "+topic.getAnswers().get(i).getAnswer()+ "("+topic.getAnswers().get(i).getScore()+")");
		}
		System.out.println("Enter 1 to confirm, any other number to discard");
		choice = input.nextInt();
		input.nextLine();
		if(choice == 1) {
			ArrayList<Topic> topics = readTopics();
			topics.add(topic);
			writeTopics(topics);
			System.out.println("Added new topic to the mix");
		}
	}
	public static void editTopic(Scanner input) {
		int choice = 0, index = 0;
		String temp=null;
		Topic topic;
		Answer answer;
		ArrayList<Topic> topics = readTopics();
		for(int i = 0;i<topics.size();i++) {
			System.out.println(i+". "+topics.get(i).getTopic());
		}
		System.out.println("Enter the number of the topic that you want to edit(-1 to exit)");
		index = input.nextInt();
		input.nextLine();
		if (index !=-1) {
			topic = topics.get(index);
			while(choice != 6) {
				topic.displayInfo();
				displayEditMenu();
				choice = input.nextInt();
				input.nextLine();
				switch(choice) {
					case 1:
						System.out.print("Enter new topic title: ");
						temp = input.nextLine();
						topic.setTopic(temp);
						break;
					case 2:
						System.out.print("Enter the number of the answer you want to edit");
						choice = input.nextInt();
						input.nextLine();
						answer = topic.getAnswers().get(choice);
						System.out.print("Enter new answer");
						temp = input.nextLine();
						answer.setAnswer(temp);
						break;
					case 3:
						System.out.print("Enter new answer: ");
						temp = input.nextLine();
						System.out.print("Enter the score (1,2,3): ");
						choice = input.nextInt();
						input.nextLine();
						topic.addAnswer(new Answer(temp,choice));
						break;
					case 4:
						System.out.println("Enter the number of the answer you want to remove: ");
						int answerIndex = input.nextInt();
						input.nextLine();
						topic.getAnswers().remove(answerIndex);
						break;
					case 5:
						topics.remove(index);
						choice = 6;
						break;
					default:
						System.out.println("Please enter a valid number");
				}
			}
		}
		writeTopics(topics);
	}
	public static void displayEditMenu() {
		System.out.println("Select what you want to do");
		System.out.println("1. Edit topic");
		System.out.println("2. Edit an answer");
		System.out.println("3. Add new answer");
		System.out.println("4. Remove an answer");
		System.out.println("5. Delete");
		System.out.println("6. Exit");
	}
	// This will read the topicToAdd.txt file, and add it to the list
	public static void readAndAddTopic() {
		String filePath = "topicToAdd.txt", topicTitle;
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<Topic> oldTopics = readTopics();
		Topic newTopic = new Topic();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine())!=null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		topicTitle = lines.get(0);
		System.out.println("Topic: "+topicTitle);
		newTopic.setTopic(topicTitle);
		ArrayList<Answer> answers = new ArrayList<Answer>();
		for (int i = 1; i<lines.size();i++) {
			System.out.println(lines.get(i));
			answers.add(new Answer(lines.get(i),1));
		}
		newTopic.setAnswers(answers);
		oldTopics.add(newTopic);
		writeTopics(oldTopics);
		System.out.println("Successfully add the topic and answer");
	}
}
