package tetris;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	private int stateID = 0;
	private FontButton[] buttons;
	private UnicodeFont font;
	private Image background;
	Sound theme;

	float startGameScale = 1;
	float exitScale = 1;

	public MainMenuState(int stateID) throws SlickException {
		this.stateID = stateID;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, final StateBasedGame game)
			throws SlickException {
		background = new Image("images/mainmenu.png");
		font = new UnicodeFont(new java.awt.Font("Verdana", Font.BOLD, 40));
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addNeheGlyphs();
		font.loadGlyphs();

		buttons = new FontButton[2];
		buttons[0] = new FontButton(container, font, "Play", 200, 250, game,
				stateID) {
			@Override
			public void perform() {
				game.enterState(Game.GAMEPLAYSTATE);
			}
		};

		buttons[1] = new FontButton(container, font, "Exit", 200, 310, game,
				stateID) {
			@Override
			public void perform() {
				((GameContainer) container).exit();
			}
		};

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		for (FontButton button : buttons) {
			button.render(container, g);
		}
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
