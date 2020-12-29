import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

	private static int[] dices = new int[10];
	private static Horse[] horses = new Horse[4];
	private static int maxPoint = 0;

	private static void dfs(int point, int count) {
		if (count > 9) {
			if (point > maxPoint) {
				maxPoint = point;
			}
			return;
		}

		for (int i = 0; i < horses.length; i++) {
			Horse horse = horses[i];
			Horse nHorse = new Horse(horse);

			if (horse.isArrived()) {
				continue;
			}

			horse.index += dices[count];
			if (horse.state == HorseState.NORMAL) {
				if (horse.index == 4) {
					horse.index = 0;
					horse.state = HorseState.TENTH;
				} else if (horse.index == 9) {
					horse.index = 0;
					horse.state = HorseState.TWENTIETH;
				} else if (horse.index == 14) {
					horse.index = 0;
					horse.state = HorseState.THIRTIETH;
				}
			}

			boolean exit = false;
			for (int j = 0; j < horses.length; j++) {
				if (horse.isArrived()) {
					break;
				}

				if (horse == horses[j]) {
					continue;
				}

				if (horses[j].isArrived()) {
					continue;
				}

				if (horses[j].index == -1) {
					continue;
				}

				if (horse.equals(horses[j])) {
					exit = true;
					break;
				}

				int commonPoint = horse.state.scoreList[horse.index];
				if (commonPoint != 25 && commonPoint != 30 && commonPoint != 35 && commonPoint != 40) {
					continue;
				}

				if (commonPoint == 30 && horse.index == 0) {
					continue;
				}

				if (horses[j].state.scoreList[horses[j].index] == 30 && horses[j].index == 0) {
					continue;
				}

				if (horse.state.scoreList[horse.index] == horses[j].state.scoreList[horses[j].index]) {
					exit = true;
					break;
				}
			}
			if (exit) {
				horses[i] = nHorse;
				continue;
			}

			int newPoint = point + horse.getPoint();
			dfs(newPoint, count + 1);
			horses[i] = nHorse;
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		for (int i = 0; i < dices.length; i++) {
			int input = scanner.nextInt();
			dices[i] = input;
		}

		for (int i = 0; i < horses.length; i++) {
			horses[i] = new Horse();
		}

		dfs(0, 0);

		System.out.println(maxPoint);
	}

	private static class Horse {

		public HorseState state = HorseState.NORMAL;
		public int index = -1;

		public Horse() {
		}

		public Horse(Horse that) {
			this.state = that.state;
			this.index = that.index;
		}

		public boolean isArrived() {
			return index >= state.scoreList.length;
		}

		public boolean isEnd() {
			return index == state.scoreList.length - 1;
		}

		public int getPoint() {
			if (isArrived()) {
				return 0;
			}

			return state.scoreList[index];
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Horse horse = (Horse)o;
			return index == horse.index &&
				state == horse.state;
		}

		@Override
		public int hashCode() {
			return Objects.hash(state, index);
		}
	}

	private enum HorseState {

		NORMAL(IntStream.range(1, 21).map((i) -> 2 * i).toArray()),
		TENTH(new int[] {10, 13, 16, 19, 25, 30, 35, 40}),
		TWENTIETH(new int[] {20, 22, 24, 25, 30, 35, 40}),
		THIRTIETH(new int[] {30, 28, 27, 26, 25, 30, 35, 40});

		private int[] scoreList;

		HorseState(int[] scoreList) {
			this.scoreList = scoreList;
		}

		public int[] getScoreList() {
			return scoreList;
		}
	}
}
