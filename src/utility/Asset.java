package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Asset {

	private BufferedImage tileset, characters;
	private final int SIZE = 16;

	private Asset() {
		tileset = readImage("asset/NES - Lode Runner - Tileset.png");
		characters = readImage("asset/Characters.png");
	}

	private BufferedImage readImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage getGameover() {
		return readImage("asset/gameover.png");
	}

	public BufferedImage getRock() {
		return tileset.getSubimage(SIZE, 0, SIZE, SIZE);
	}

	public BufferedImage getPole() {
		return tileset.getSubimage(3 * SIZE, 0, SIZE, SIZE);
	}

	public Vector<BufferedImage> getLadder() {
		Vector<BufferedImage> vec = new Vector<>();
		for (int i = 0; i < 3; i++)
			vec.add(tileset.getSubimage(SIZE * 2, i * SIZE, SIZE, SIZE));
		return vec;
	}

	public Vector<BufferedImage> getTreasure() {
		Vector<BufferedImage> vec = new Vector<>();
		for (int i = 0; i < 3; i++)
			vec.add(tileset.getSubimage(SIZE * 6, i * SIZE, SIZE, SIZE));
		return vec;
	}

	public Vector<BufferedImage> getPlayer(int state) {
		Vector<BufferedImage> vec = new Vector<>();
		if (state == Variable.STATE_IDLE) {
			vec.add(characters.getSubimage(2 * SIZE, 2 * SIZE, SIZE, SIZE));
			vec.add(characters.getSubimage(3 * SIZE, 2 * SIZE, SIZE, SIZE));
		} else if (state == Variable.STATE_RUN) {
			for (int i = 0; i < 4; i++)
				vec.add(characters.getSubimage(i * SIZE, 0, SIZE, SIZE));
		} else if (state == Variable.STATE_CLIMB_LADDER) {
			for (int i = 0; i < 4; i++)
				vec.add(characters.getSubimage(i * SIZE, SIZE, SIZE, SIZE));
		} else if (state == Variable.STATE_SKYDIVE) {
			for (int i = 0; i < 4; i++)
				vec.add(characters.getSubimage(i * SIZE + 3 * SIZE, 0, SIZE, SIZE));
		} else if (state == Variable.STATE_DEAD) {
			for (int i = 0; i < 8; i++)
				vec.add(characters.getSubimage(i * SIZE + 4 * SIZE, 2 * SIZE, SIZE, SIZE));
		} else if (state == Variable.STATE_CLIMB_POLE) {
			for (int i = 0; i < 4; i++)
				vec.add(characters.getSubimage(i * SIZE + 4 * SIZE, SIZE, SIZE, SIZE));
		}
		return vec;
	}

	public Vector<BufferedImage> getBrick(int state) {
		Vector<BufferedImage> vec = new Vector<>();
		if (state == Variable.BRICK_IDLE_STATE) {
			vec.add(tileset.getSubimage(0, 0, SIZE, SIZE));
		} else if (state == Variable.BRICK_APPEAR_STATE) {
			for (int i = 5; i >= 1; i--)
				vec.add(tileset.getSubimage(SIZE, i * SIZE, SIZE, SIZE));
			vec.add(tileset.getSubimage(0, 0, SIZE, SIZE));
		} else if (state == Variable.BRICK_DESTROY_STATE) {
			for (int i = 0; i < 6; i++)
				vec.add(tileset.getSubimage(0, i * SIZE, SIZE, SIZE));
			vec.add(null);
		}
		return vec;
	}

	public Vector<BufferedImage> getEnemy(int state) {
		Vector<BufferedImage> vec = new Vector<>();
		if (state == Variable.STATE_RUN || state == Variable.STATE_IDLE) {
			for (int i = 0; i < 4; i++)
				vec.add(characters.getSubimage(i * SIZE, 3 * SIZE, SIZE, SIZE));
		} else if (state == Variable.STATE_SKYDIVE) {
			for (int i = 4; i < 8; i++)
				vec.add(characters.getSubimage(i * SIZE, 3 * SIZE, SIZE, SIZE));
		} else if (state == Variable.STATE_CLIMB_LADDER) {
			for (int i = 0; i < 4; i++)
				vec.add(characters.getSubimage(i * SIZE, 4 * SIZE, SIZE, SIZE));
		} else if (state == Variable.STATE_CLIMB_POLE) {
			for (int i = 4; i < 8; i++)
				vec.add(characters.getSubimage(i * SIZE, 4 * SIZE, SIZE, SIZE));
		} else if (state == Variable.STATE_DEAD) {
			for (int i = 11; i >= 8; i--)
				vec.add(characters.getSubimage(i * SIZE, 4 * SIZE, SIZE, SIZE));
			// vec.add(null);
		}
		return vec;
	}

	private static Asset instance = new Asset();

	public static Asset getInstance() {
		return instance;
	}

}
