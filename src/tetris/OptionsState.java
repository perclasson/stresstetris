package tetris;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class OptionsState extends BasicGameState {
	private int stateID = 2;
	private Image background = null;
	private UnicodeFont font, smallFont;
	private FontButton[] buttons;
	private File[] files;
	private FontButton optionsButton;
	public File optionsFile;

	public OptionsState(int stateID) throws SlickException {
		this.stateID = stateID;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, final StateBasedGame game)
			throws SlickException {
		background = new Image("images/tetris.png");
		font = new UnicodeFont(new java.awt.Font("Verdana", Font.BOLD, 40));
		font.getEffects().add(new ColorEffect(new Color(211, 211, 211)));
		font.addNeheGlyphs();
		font.loadGlyphs();

		smallFont = new UnicodeFont(new java.awt.Font("Verdana", Font.BOLD, 20));
		smallFont.getEffects().add(new ColorEffect(new Color(211, 211, 211)));
		smallFont.addNeheGlyphs();
		smallFont.loadGlyphs();

		File folder = new File("conf/");
		files = folder.listFiles();

		buttons = new FontButton[files.length + 1];
		buttons[0] = new FontButton(container, font, "BACK", 200, 270, game,
				stateID) {
			@Override
			public void perform() {
				game.enterState(Game.MAINMENUSTATE);
			}
		};

		for (int i = 0; i < files.length; i++) {
			final File file = files[i];

			buttons[i + 1] = new FontButton(container, smallFont,
					file.getName(), 200, 345 + i * 30, game, stateID) {
				@Override
				public void perform() {
					setIsEnabled(false);
					optionsButton.setIsEnabled(true);
					optionsFile = file;
					optionsButton = this;
				}
			};

			if (file == optionsFile) {
				buttons[i + 1].setIsEnabled(false);
			}
		}

		// Sets the first file as the chosen button.
		optionsFile = files[0];
		optionsButton = buttons[1];
		optionsButton.setIsEnabled(false);
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
