import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	static int N, M, c[];
	static ArrayList<ArrayList<int[]>> e;
	static PriorityQueue<long[]> pq;
	static long d[][];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		e = new ArrayList<ArrayList<int[]>>();
		st = new StringTokenizer(br.readLine());
		c = new int[N+1];
		e.add(new ArrayList<int[]>());
		for (int i=1; i<=N; i++) {
			e.add(new ArrayList<int[]>());
			c[i] = Integer.parseInt(st.nextToken());
		}
		int a, b, w;
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			e.get(a).add(new int[] {b, w});
			e.get(b).add(new int[] {a, w});
		}
		
		d = new long[N+1][2501];
		for (int i=1; i<=N; i++) {
			for (int j=0; j<=2500; j++) {
				d[i][j] = Long.MAX_VALUE;
			}
		}
		pq = new PriorityQueue<long[]>((o1, o2) -> {
			return (int)(o1[0]-o2[0]);
		});
		pq.add(new long[] {0, 1, c[1]});
		long ans = 0, total, temp[];
		int node, cost, to, minCost;
		while (!pq.isEmpty()) {
			temp = pq.poll();
			total = temp[0];
			node = (int)temp[1];
			minCost = (int)temp[2];
			if (node == N) {
				ans = total;
				break;
			}
			for (int i=0; i<e.get(node).size(); i++) {
				to = e.get(node).get(i)[0];
				cost = e.get(node).get(i)[1];
				if (d[to][minCost] > total+(cost*minCost)) {
					d[to][minCost] = total+(cost*minCost);
					pq.add(new long[] {d[to][minCost], to, Math.min(minCost, c[to])});
				}
			}
		}
		System.out.println(ans);
	}
}
