package tetris;

import org.newdawn.slick.Image;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState {
	private int stateID = 2;
	Image background = null;
	Image startGameOption = null;
	Image exitOption = null;
	int menuX;
	int menuY;
	Sound theme;

	float startGameScale = 1;
	float exitScale = 1;

	public GameOverState(int stateID) throws SlickException {
		menuX = 10;
		menuY = 400;
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Image("images/mainmenu.png");
		// load the menu images
		Image menuOptions = new Image("images/menuoptions.png");
		startGameOption = menuOptions.getSubImage(0, 0, 250, 71);
		exitOption = menuOptions.getSubImage(0, 71, 250, 71);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// render the background
		background.draw(0, 0);
		// Draw menu
		startGameOption.draw(menuX, menuY, startGameScale);

		exitOption.draw(menuX, menuY + 80, exitScale);
		

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
	}

	@Override
	public int getID() {
		return stateID;
	}

}
