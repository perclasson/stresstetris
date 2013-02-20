package tetris;

import org.newdawn.slick.Image;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	private int stateID = 0;
	Image background = null;
	Image startGameOption = null;
	Image exitOption = null;
	int menuX;
	int menuY;
	Sound theme;

	float startGameScale = 1;
	float exitScale = 1;

	public MainMenuState(int stateID) throws SlickException {
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
		
		float scaleStep = 0.001f;
		Input input = gc.getInput();

		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		boolean insideStartGame = false;
		boolean insideExit = false;
		if ((mouseX >= menuX && mouseX <= menuX + startGameOption.getWidth())
				&& (mouseY >= menuY && mouseY <= menuY
						+ startGameOption.getHeight())) {
			insideStartGame = true;
		} else if ((mouseX >= menuX && mouseX <= menuX + exitOption.getWidth())
				&& (mouseY >= menuY + 80 && mouseY <= menuY + 80
						+ exitOption.getHeight())) {
			insideExit = true;
		}

		if (insideStartGame) {
			if (startGameScale < 1.3f)
				startGameScale += scaleStep * delta;

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				sb.enterState(Game.GAMEPLAYSTATE);
			}
		} else {
			if (startGameScale > 1.0f)
				startGameScale -= scaleStep * delta;

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				gc.exit();
		}

		if (insideExit) {
			if (exitScale < 1.3f)
				exitScale += scaleStep * delta;
		} else {
			if (exitScale > 1.0f)
				exitScale -= scaleStep * delta;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
