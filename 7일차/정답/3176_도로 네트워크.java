import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int N, K, INF=987654321;
	static ArrayList<ArrayList<int[]>> e;
	static int[] depth;
	static int[][] parent, min, max;
	
	static Queue<Integer> q;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		e = new ArrayList<ArrayList<int[]>>(N+1);
		for (int i=0; i<=N; i++) {
			e.add(new ArrayList<int[]>());
		}
		int a, b, c;
		for (int i=0; i<N-1; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			e.get(a).add(new int[] {b, c});
			e.get(b).add(new int[] {a, c});
		}
		depth = new int[N+1];
		parent = new int[18][N+1];
		min = new int[18][N+1];
		max = new int[18][N+1];
		q = new LinkedList<Integer>();
		q.add(1);
		depth[1] = 1;
		while (!q.isEmpty()) {
			int el = q.poll();
			for (int i=0; i<e.get(el).size(); i++) {
				int[] next = e.get(el).get(i);
				if (depth[next[0]] == 0) {
					depth[next[0]] = depth[el]+1;
					parent[0][next[0]] = el;
					min[0][next[0]] = next[1];
					max[0][next[0]] = next[1];
					q.add(next[0]);
				}
			}
		}
		for (int i=1; i<=17; i++) {
			for (int j=1; j<=N; j++) {
				parent[i][j] = parent[i-1][parent[i-1][j]];
				min[i][j] = Math.min(min[i-1][j], min[i-1][parent[i-1][j]]);
				max[i][j] = Math.max(max[i-1][j], max[i-1][parent[i-1][j]]);
			}
		}
		
		K = Integer.parseInt(br.readLine());
		for (int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			int[] ret = lca(a,b);
			sb.append(ret[0] + " " + ret[1] + "\n");
		}
		System.out.println(sb.toString());
	}
	static int[] lca(int x, int y) {
		if (depth[x] > depth[y]) {
			int temp = x;
			x = y;
			y = temp;
		}
		int ans1 = INF;
		int ans2 = -INF;
		for (int i=17; i>=0; i--) {
			if (depth[y]-depth[x] >= (1<<i)) {
				ans1 = Math.min(ans1, min[i][y]);
				ans2 = Math.max(ans2, max[i][y]);
				y = parent[i][y];
			}
		}
		if (x==y) {
			return new int[] {ans1, ans2};
		}
		for (int i=17; i>=0; i--) {
			if (parent[i][x] != parent[i][y]) {
				ans1 = Math.min(ans1, Math.min(min[i][x], min[i][y]));
				ans2 = Math.max(ans2, Math.max(max[i][x], max[i][y]));
				x = parent[i][x];
				y = parent[i][y];
			}
		}
		ans1 = Math.min(ans1, Math.min(min[0][x], min[0][y]));
		ans2 = Math.max(ans2, Math.max(max[0][x], max[0][y]));
		return new int[] {ans1, ans2};
	}
}
