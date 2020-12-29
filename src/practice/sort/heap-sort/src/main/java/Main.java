import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int n = Integer.parseInt(br.readLine());
		int[] numArr = new int[n];
		for (int i = 0; i < numArr.length; i++) {
			numArr[i] = Integer.parseInt(br.readLine());
		}
		br.close();

		heapSort(numArr);

		for (int i = 0; i < numArr.length; i++) {
			bw.write(numArr[i] + "\n");
		}
		bw.flush();
		bw.close();
	}

	private static void siftDown(int[] numArr, int index, int maxIndex) {
		int tracedIndex = index;

		while (2 * tracedIndex + 1 < maxIndex) {
			int leftChild = 2 * tracedIndex + 1;
			int rightChild = 2 * tracedIndex + 2;
			int target = tracedIndex;

			if (numArr[target] < numArr[leftChild]) {
				target = leftChild;
			}

			if (rightChild < maxIndex && numArr[target] < numArr[rightChild]) {
				target = rightChild;
			}

			if (target != tracedIndex) {
				int temp = numArr[tracedIndex];
				numArr[tracedIndex] = numArr[target];
				numArr[target] = temp;
				tracedIndex = target;
				continue;
			}

			break;
		}
	}

	private static void siftUp(int[] numArr, int index) {
		if (index == 0) {
			return;
		}

		int tracedIndex = index;

		while (tracedIndex > 0) {
			int parent = (tracedIndex - 1) / 2;
			if (numArr[tracedIndex] > numArr[parent]) {
				int temp = numArr[tracedIndex];
				numArr[tracedIndex] = numArr[parent];
				numArr[parent] = temp;
				tracedIndex = parent;
				continue;
			}
			break;
		}
	}

	private static void heapifyTopDown(int[] numArr) {
		for (int i = 1; i < numArr.length; i++) {
			siftUp(numArr, i);
		}
	}

	private static void heapifyBottomUp(int[] numArr) {
		int maxIndex = numArr.length - 1;
		for (int i = numArr.length - 1; i >= 0; i--) {
			int leftChild = 2 * i + 1;
			if (leftChild <= numArr.length - 1) {
				maxIndex = i;
				break;
			}
		}

		for (int i = maxIndex; i >= 0; i--) {
			siftDown(numArr, i, numArr.length);
		}
	}

	private static void heapSort(int[] numArr) {
		// heapifyTopDown(numArr);
		heapifyBottomUp(numArr);

		int maxIndex = numArr.length - 1;
		for (int i = maxIndex; i >= 1; i--) {
			int temp = numArr[0];
			numArr[0] = numArr[i];
			numArr[i] = temp;
			siftDown(numArr, 0, i);
		}
	}
}
