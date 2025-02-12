import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int V, E;
	static ArrayList<ArrayList<int[]>> edges;
	static ArrayList<int[]> e, remains;
	static int[] depth, union;
	static int[][] parent, max, second;
	static Queue<Integer> q;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		edges = new ArrayList<ArrayList<int[]>>(V+1);
		e = new ArrayList<int[]>(E);
		remains = new ArrayList<int[]>();
		union = new int[V+1];
		depth = new int[V+1];
		parent = new int[17][V+1];
		max = new int[17][V+1];
		second = new int[17][V+1];
		q = new LinkedList<Integer>();
		for (int i=0; i<=V; i++) {
			edges.add(new ArrayList<int[]>());
			union[i] = i;
		}
		int a, b, c;
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			e.add(new int[] {a, b, c});
		}
		Collections.sort(e, (o1, o2) ->  {
			return o1[2] - o2[2];
		});
		
		int vcnt = 0;
		long mstCost = 0;
		for (int i=0; i<E; i++) {
			a = e.get(i)[0];
			b = e.get(i)[1];
			c = e.get(i)[2];
			if (find(a) != find(b)) {
				vcnt++;
				mstCost += (long)c;
				union(a, b);
				edges.get(a).add(new int[] {b, c});
				edges.get(b).add(new int[] {a, c});
			} else {
				remains.add(new int[] {a, b, c});
			}
		}
		if (vcnt != V-1) {
			System.out.println(-1);
			return;
		}
		q.add(1);
		depth[1] = 1;
		int el, next, cost;
		while (!q.isEmpty()) {
			el = q.poll();
			for (int i=0; i<edges.get(el).size(); i++) {
				next = edges.get(el).get(i)[0];
				if (depth[next] == 0) {
					cost = edges.get(el).get(i)[1];
					depth[next] = depth[el]+1;
					parent[0][next] = el;
					max[0][next] = cost;
					second[0][next] = -1;
					q.add(next);
				}
			}
		}
		for (int i=1; i<=16; i++) {
			for (int j=1; j<=V; j++) {
				parent[i][j] = parent[i-1][parent[i-1][j]];
				max[i][j] = Math.max(max[i-1][j], max[i-1][parent[i-1][j]]);
				int l1 = largestNeq(max[i-1][j], max[i-1][parent[i-1][j]], max[i][j]);
				int l2 = largestNeq(second[i-1][j], second[i-1][parent[i-1][j]], max[i][j]);
				second[i][j] = Math.max(l1, l2);
			}
		}
		long ans = Long.MAX_VALUE;
		for (int i=0; i<remains.size(); i++) {
			a = remains.get(i)[0];
			b = remains.get(i)[1];
			c = remains.get(i)[2];
			int maxCycleEdge = lca(a, b, c);
			if (maxCycleEdge < 0) {
				continue;
			}
			long ret = mstCost - maxCycleEdge + c;
			if (ret > mstCost && ret < ans) {
				ans = ret;
			}
		}
		if (ans == Long.MAX_VALUE) {
			System.out.println(-1);
		} else {
			System.out.println(ans);
		}
	}
	static int largestNeq(int a, int b, int maxVal) {
		if (a == maxVal && b == maxVal) {
			return -1;
		}
		if (a == maxVal) {
			return b;
		}
		if (b == maxVal) {
			 return a;
		}
		return Math.max(a, b);
	}
	static int find(int a) {
		if (union[a] == a) {
			return a;
		}
		union[a] = find(union[a]);
		return union[a];
	}
	static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x!=y) {
			union[y] = x;
		}
	}
	static int lca(int x, int y, int c) {
		if (depth[x] > depth[y]) {
			int temp = x;
			x = y;
			y = temp;
		}
		int ret = -1;
		for (int i=16; i>=0; i--) {
			if (depth[y]-depth[x] >= (1 << i)) {
				ret = Math.max(ret, max[i][y] != c ? max[i][y] : second[i][y]);			
				y = parent[i][y];
			}
		}
		if (x == y) {
			return ret;
		}
		for (int i=16; i>=0; i--) {
			if (parent[i][y] != parent[i][x]) {
				ret = Math.max(ret, max[i][y] != c ? max[i][y] : second[i][y]);
				ret = Math.max(ret, max[i][x] != c ? max[i][x] : second[i][x]);
				y = parent[i][y];
				x = parent[i][x];
			}
		}
		ret = Math.max(ret, max[0][y] != c ? max[0][y] : second[0][y]);
		ret = Math.max(ret, max[0][x] != c ? max[0][x] : second[0][x]);
		return ret;
	}
}
