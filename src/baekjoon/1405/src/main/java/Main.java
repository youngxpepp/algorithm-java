import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

	private static double totalRatio = 0;
	private static int limitCount = 0;
	private static boolean[][] visitMap = new boolean[29][29];

	private static void dfs(Pair p, double ratio, int count) {
		if (count >= limitCount) {
			return;
		}

		for (int i = 0; i < Direction.values().length; i++) {
			Direction d = Direction.values()[i];
			Pair np = new Pair(p);
			np.go(d);
			double nRatio = ratio * Direction.ratioMap.get(d.name());

			if (visitMap[np.getY() + 14][np.getX() + 14]) {
				totalRatio += nRatio;
				continue;
			}

			visitMap[p.getY() + 14][p.getX() + 14] = true;
			dfs(np, nRatio, count + 1);
			visitMap[p.getY() + 14][p.getX() + 14] = false;
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int m = 0, e = 0, w = 0, s = 0, n = 0;
		m = scanner.nextInt();
		e = scanner.nextInt();
		w = scanner.nextInt();
		s = scanner.nextInt();
		n = scanner.nextInt();
		limitCount = m;
		Direction.ratioMap.put(Direction.EAST.name(), e / 100d);
		Direction.ratioMap.put(Direction.WEST.name(), w / 100d);
		Direction.ratioMap.put(Direction.SOUTH.name(), s / 100d);
		Direction.ratioMap.put(Direction.NORTH.name(), n / 100d);

		for (boolean[] arr : visitMap) {
			Arrays.fill(arr, false);
		}
		visitMap[14][14] = true;
		dfs(new Pair(0, 0), 1, 0);

		System.out.println(1d - totalRatio);
	}

	private enum Direction {

		EAST(1, 0),
		WEST(-1, 0),
		SOUTH(0, -1),
		NORTH(0, 1);

		private int x;
		private int y;

		public static Map<String, Double> ratioMap = new HashMap<>();

		Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}

	private static class Pair {

		private int x;
		private int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Pair(Pair that) {
			this.x = that.x;
			this.y = that.y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void go(Direction d) {
			x += d.getX();
			y += d.getY();
		}
	}
}
