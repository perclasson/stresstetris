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

public class CooldownState extends BasicGameState {
	private int stateID = 4;
	private Image background;
	private UnicodeFont font;
	private int time = 0;

	public CooldownState(int stateID) throws SlickException {
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

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		font.drawString(200, 400, "Cooldown");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		time += delta;
		if (time > 3000) {
			sb.enterState(Game.GAMEPLAYSTATE);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
