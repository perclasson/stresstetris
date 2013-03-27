package tetris;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bluetooth.EDAReader;

public class Game extends StateBasedGame {

	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	public static final int OPTIONS = 3;
	public static final int COOLDOWN = 4;
	public static final int STABILIZER = 5;
	public File optionsFile;
	public static boolean useGSR = false;
	public static boolean useGSRFeedback = false;
	private EDAReader reader;

	public Game() {
		super("Stress Tetris");
		optionsFile = new File("conf/easy.dif");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game());

		app.setDisplayMode(800, 600, false);
		app.start();
	}

	public void setOptionsFile(File file) {
		optionsFile = file;
	}

	public void setEDAReader(EDAReader reader) {
		this.reader = reader;
	}

	public EDAReader getEDAReader() {
		return reader;
	}

	public void useGSR(boolean usage) {
		useGSR = usage;
	}

	public void useGSRFeedback(boolean usage) {
		useGSRFeedback = usage;
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GamePlayState(GAMEPLAYSTATE, this));
		this.addState(new GameOverState(GAMEOVERSTATE));
		this.addState(new OptionsState(OPTIONS, this));
		this.addState(new CooldownState(COOLDOWN, this));
		this.addState(new StabilizerState(STABILIZER, this));
	}
}
