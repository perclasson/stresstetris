package Tetris.Components;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Square {
	private Image image;
	private float x, y;

	public Square(int color, float x, float y) {
		try {
			if (color == Codes.RED) {
				image = new Image("images/block.png");
			}
			this.x = x;
			this.y = y;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Square setX(float x) {
		this.x = x;
		return this;
	}

	public Square setY(float y) {
		this.y = y;
		return this;
	}

	public void draw() {
		image.draw(x, y);
	}

	public void moveLeft() {
		x -= 3;
	}

	public void moveRight() {
		x += 3;
	}

	public void moveDown() {
		y += 0.5f;
	}

	public void moveUp() {
		y -= 3;
	}
}
