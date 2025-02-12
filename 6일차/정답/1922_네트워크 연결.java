import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	
	static int N, M;
	static int[] parent;
	static ArrayList<int []> e;

	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		parent = new int[N+1];
		for (int i=1; i<=N; i++) {
			parent[i] = i;
		}
		e = new ArrayList<int[]>();
		int a, b, c;
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			e.add(new int[] {a, b, c});
		}
		Collections.sort(e, (o1, o2) -> {
			return o1[2] - o2[2];
		});
		int cnt = 0;
		int cost = 0;
		for (int i=0; i<M; i++) {
			a = e.get(i)[0];
			b = e.get(i)[1];
			c = e.get(i)[2];
			if (find(a) != find(b)) {
				cnt++;
				cost += c;
				union(a, b);
			}
			if (cnt == N-1) {
				break;
			}
		}
		System.out.println(cost);
	}
	static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x!=y) {
			parent[y] = x;
		}
	}
	static int find(int a) {
		if (parent[a] == a) {
			return a;
		}
		parent[a] = find(parent[a]);
		return parent[a];
	}
}
