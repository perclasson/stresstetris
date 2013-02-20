package tetris.components;

import java.util.Random;

import resources.BlockInfo;
import resources.Measurements;
import tetris.GamePlayState;

public class BlockBuilder {
	private float blockSpeed;
	
	public BlockBuilder(GamePlayState gps) {
		blockSpeed = gps.getBlockSpeed();
	}
	
	public Block generateBlock() {
		Random random = new Random();
		int block = random.nextInt(7);
		System.out.println(block);             
		switch(block) {
			case 0:
				return new IBlock(276 + 4 * BlockInfo.SIZE, Measurements.GRID_YSTART
				- BlockInfo.SIZE, blockSpeed);
			case 1:
				return new JBlock(276 + 4 * BlockInfo.SIZE, Measurements.GRID_YSTART
						- BlockInfo.SIZE, blockSpeed);
			case 2:
				return new LBlock(276 + 4 * BlockInfo.SIZE, Measurements.GRID_YSTART
						- BlockInfo.SIZE, blockSpeed);
			case 3:
				return new OBlock(276 + 4 * BlockInfo.SIZE, Measurements.GRID_YSTART
						- BlockInfo.SIZE, blockSpeed);
			case 4:
				return new SBlock(276 + 4 * BlockInfo.SIZE, Measurements.GRID_YSTART
						- BlockInfo.SIZE, blockSpeed);
			case 5:
				return new TBlock(276 + 4 * BlockInfo.SIZE, Measurements.GRID_YSTART
						- BlockInfo.SIZE, blockSpeed);
			case 6:
				return new ZBlock(276 + 4 * BlockInfo.SIZE, Measurements.GRID_YSTART
						- BlockInfo.SIZE, blockSpeed);
		}
		return null;
	}
}
