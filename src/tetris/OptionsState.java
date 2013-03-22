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
	private FontButton fileButtonSelected, gsrButtonSelected;
	private File fileSelected;
	private Game mainGame;
	
	public OptionsState(int stateID, Game game) throws SlickException {
		this.stateID = stateID;
		this.fileSelected = game.optionsFile;
		mainGame = game;
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
		int noButtonsExcludingFiles = 4;
		buttons = new FontButton[files.length + noButtonsExcludingFiles];
		
		buttons[0] = new FontButton(container, font, "BACK", 200, 270, game,
				stateID) {
			@Override
			public void perform() {
				game.enterState(Game.MAINMENUSTATE);
			}
		};

		buttons[1] = new FontButton(container, smallFont, "No GSR", 450, 345, game,
				stateID) {
			@Override
			public void perform() {
				setIsEnabled(false);
				gsrButtonSelected.setIsEnabled(true);
				mainGame.useGSR(false);
				mainGame.useGSRFeedback(false);
				gsrButtonSelected = this;
			}
		};
		buttons[2] = new FontButton(container, smallFont, "Listen to GSR", 450, 375, game,
				stateID) {
			@Override
			public void perform() {
				setIsEnabled(false);
				gsrButtonSelected.setIsEnabled(true);
				mainGame.useGSR(true);
				mainGame.useGSRFeedback(false);
				gsrButtonSelected = this;
			}
		};
		buttons[3] = new FontButton(container, smallFont, "Feedback from GSR", 450, 405, game,
				stateID) {
			@Override
			public void perform() {
				setIsEnabled(false);
				gsrButtonSelected.setIsEnabled(true);
				mainGame.useGSR(true);
				mainGame.useGSRFeedback(true);
				gsrButtonSelected = this;
			}
		};

		for (int i = 0; i < files.length; i++) {
			final File file = files[i];

			buttons[i + noButtonsExcludingFiles] = new FontButton(container, smallFont,
					"Difficulty: " + file.getName(), 200, 345 + i * 30, game, stateID) {
				@Override
				public void perform() {
					setIsEnabled(false);
					fileButtonSelected.setIsEnabled(true);
					mainGame.setOptionsFile(file);
					fileButtonSelected = this;
				}
			};

			if (file == fileSelected) {
				buttons[i + noButtonsExcludingFiles].setIsEnabled(false);
			}
		}

		// Sets the first file as the chosen button.
		fileSelected = files[0];
		fileButtonSelected = buttons[noButtonsExcludingFiles];
		fileButtonSelected.setIsEnabled(false);
		
		//Set first GSR option as the chosen button.
		gsrButtonSelected = buttons[3];
		gsrButtonSelected.setIsEnabled(false);
		mainGame.useGSR(true);
		mainGame.useGSRFeedback(true);
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
	
	public File getOptionsFile() {
		return fileSelected;
	}

}
