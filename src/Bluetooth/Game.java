package Bluetooth;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Game extends StateBasedGame{

	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	
	public Game() {
		super("Kjell The Game");
		// TODO Auto-generated constructor stub
	}
	
	 public static void main(String[] args) throws SlickException
	    {
	         AppGameContainer app = new AppGameContainer(new Game());
	 
	         app.setDisplayMode(800, 600, false);
	         app.start();
	    }
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
	}

}
