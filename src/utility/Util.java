package utility;

import java.util.Random;

public class Util {

	private static Random rand;

	static {
		rand = new Random();
	}

	public static void sleep(long x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getKey(int x, int y) {
		return x + "#" + y;
	}

	public static double getTolerance() {
		try {
			double temp = Time.fps > Variable.LIMIT_FPS ? 1 : Variable.LIMIT_FPS / Time.fps;
			double tolerance = temp * 5;
			return tolerance;
		} catch (Exception e) {
			return 0;
		}
	}

	public static int minMax(int key, int min, int max) {
		if (key > max)
			return min;
		if (key < min)
			return max;
		return key;
	}

	public static int randInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}

}
