package Tetris.Components;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Block {
	private Image image;
	private float x, y;
	
	public Block() {
		try {
			image = new Image("images/block.png");
			float x = 100;
			float y = 100;
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
