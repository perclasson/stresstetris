package tetris;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

public class FontButton extends MouseOverArea {

	private final UnicodeFont font;
	private final String text;
	private boolean lastMouseOver = false;
	private final StateBasedGame sbg;
	private final int stateID;
	private boolean isEnabled = true;

	public FontButton(GUIContext guic, UnicodeFont font, String text, int x,
			int y, StateBasedGame sbg, int stateID) throws SlickException {
		super(guic, new Image(0, 0), x, y, font.getWidth(text), font
				.getHeight(text));
		this.font = font;
		this.text = text;
		this.sbg = sbg;
		this.stateID = stateID;
	}

	public void setIsEnabled(boolean b) {
		isEnabled = b;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void render(GUIContext guic, Graphics g) {
		g.setFont(font);
		if (isEnabled) {
			g.setColor(Color.white);
			if (isMouseOver()) {
				g.setColor(new Color(200, 50, 30));
			}
		} else {
			g.setColor(Color.gray);
		}
		g.drawString(text, getX(), getY());
		super.render(guic, g);
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if (sbg.getCurrentStateID() == stateID && isEnabled) {
			if (isMouseOver() && !lastMouseOver) {
				lastMouseOver = true;
			} else if (!isMouseOver()) {
				lastMouseOver = false;
			}
		}
		super.mouseMoved(oldx, oldy, newx, newy);
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (isMouseOver() && sbg.getCurrentStateID() == stateID && isEnabled) {
			perform();
		}
		super.mouseClicked(button, x, y, clickCount);
	}

	public void perform() {

	}
}
