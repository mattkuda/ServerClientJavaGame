import java.io.Serializable;

public class Player implements Serializable{
	private String name;
	private int score = 0;
	private String word;
	private String currentWord;
	
	
	public Player(String name, String word, String currentWord) {
		super();
		
		this.name = name;
		this.word = word;
		this.currentWord = currentWord;
	}
	
	public void addToScore()
	{
		score++;
	}
	
	public int getScore()
	{
		return score;
	}

	public String getName() {
		return name;
	}

	public String getWord() {
		return word;
	}

	public String getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}

	public boolean isWinner()
	{
		return getCurrentWord().equalsIgnoreCase(getWord());
	}
}
