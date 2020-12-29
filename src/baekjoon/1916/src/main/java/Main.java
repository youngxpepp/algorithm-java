import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	private static List<List<Pair>> graph;

	private static int dijkstra(int source, int destination) {
		Queue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(0, source));
		int[] distances = new int[graph.size()];
		Arrays.fill(distances, Integer.MAX_VALUE);
		distances[source] = 0;

		while (!pq.isEmpty()) {
			int distance = pq.peek().x;
			int nodeNumber = pq.peek().y;
			pq.poll();

			for (int i = 0; i < graph.get(nodeNumber).size(); i++) {
				int nextNodeNumber = graph.get(nodeNumber).get(i).getX();
				int nextDistance = graph.get(nodeNumber).get(i).getY();

				int newDistance = distance + nextDistance;

				if (newDistance < distances[nextNodeNumber]) {
					distances[nextNodeNumber] = newDistance;
					pq.add(new Pair(newDistance, nextNodeNumber));
				}
			}
		}

		return distances[destination];
	}

	public static void main(String[] args) {
		// BufferedReader br = new BufferedReader(new InputStreamReader());
		Scanner scanner = new Scanner(System.in);

		int n = 0, m = 0;
		n = scanner.nextInt();
		m = scanner.nextInt();

		graph = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}

		for (int i = 0; i < m; i++) {
			int a = 0, b = 0, c = 0;
			a = scanner.nextInt() - 1;
			b = scanner.nextInt() - 1;
			c = scanner.nextInt();
			graph.get(a).add(new Pair(b, c));
		}

		int source = 0, destination = 0;
		source = scanner.nextInt() - 1;
		destination = scanner.nextInt() - 1;

		int answer = dijkstra(source, destination);

		System.out.println(answer);
	}

	public static class Pair implements Comparable<Pair> {
		private int x;
		private int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		@Override
		public int compareTo(Pair o) {
			return x - o.x;
		}
	}
}
