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

public class StabilizerState extends BasicGameState {
	private int stateID = 5;
	private Image background;
	private UnicodeFont font;
	private int time = 0;
	private int noDots = 0;
	private Game game;
	private EDAReader edaReader;

	public StabilizerState(int stateID, Game game) throws SlickException {
		this.stateID = stateID;
		this.game = game;
		this.edaReader = null;
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
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
		if (game.useGSR) {
			edaReader = new EDAReader();
			game.setEDAReader(edaReader);
			edaReader.start();
		}
		else {
			sb.enterState(Game.COOLDOWN);
		}
    }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		font.drawString(260, 310, "Stabilizing" + "....".substring(noDots));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		time += delta;
		noDots = 4 - (time / 500) % 4;
		if (edaReader.isStabilized()) {
			sb.enterState(Game.COOLDOWN);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
