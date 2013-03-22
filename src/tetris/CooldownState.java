package tetris;

import java.awt.Color;
import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bluetooth.EDAReader;

public class CooldownState extends BasicGameState {
	private int stateID = 4;
	private Image gamecontrols, goodluck;
	private UnicodeFont font;
	private int time = 0;
	private Game game;
	private EDAReader edaReader;

	public CooldownState(int stateID, Game game) throws SlickException {
		this.stateID = stateID;
		this.game = game;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, final StateBasedGame game)
			throws SlickException {
		gamecontrols = new Image("images/gamecontrols.png");
		goodluck = new Image("images/goodluck.png");
		font = new UnicodeFont(new java.awt.Font("Verdana", Font.BOLD, 40));
		font.getEffects().add(new ColorEffect(new Color(211, 211, 211)));
		font.addNeheGlyphs();
		font.loadGlyphs();
	}

	@Override
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
		edaReader = game.getEDAReader();
		time = 0;
    }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		if (time > 7000) {
			goodluck.draw(0, 0);
		}
		else {
			gamecontrols.draw(0, 0);	
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		time += delta;
		if (time > 10000) {
			if (edaReader != null) {
				game.setBaseline(edaReader.getMedian());	
			}
			sb.enterState(Game.GAMEPLAYSTATE);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
