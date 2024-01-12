package game;

public class FreezeRound extends Round{
	private static final long serialVersionUID = -533460592406770676L;
	public void displayMessage() {
		System.out.println("This round has no timer, Just type as much as you want");
	}

	public boolean isRoundOver() {
		return false;
	}
}
