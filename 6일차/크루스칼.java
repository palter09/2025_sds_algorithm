import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
8 10
1 2 6
1 3 6
2 4 7
2 5 3
3 6 4
3 7 1
4 8 8
5 8 1
6 8 2
7 8 9
 */
public class Main {
	
	static int V, E, p[];
	static ArrayList<int[]> edges;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		p = new int[V+1];
		edges = new ArrayList<>();
		for (int i=1; i<=V; i++) {
			p[i] = i;
		}
		int s, e, c;
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			edges.add(new int[] {s, e, c});
		}
		Collections.sort(edges, (o1, o2) -> {
			return o1[2] - o2[2];
		});
		int cnt = 0, cost = 0;
		for (int i=0; i<E; i++) {
			s = edges.get(i)[0];
			e = edges.get(i)[1];
			c = edges.get(i)[2];
			if (find(s) != find(e)) {
				union(s, e);
				cnt++;
				cost += c;
			}
			if (cnt == V-1) {
				break;
			}
		}
		System.out.println(cost);

	}
	static void union(int a, int b) {
		int X = find(a);
		int Y = find(b);
		if (X!=Y) {
			p[Y] = X;
		}
	}
	static int find(int a) {
		if (a==p[a]) {
			return a;
		}
		p[a] = find(p[a]);
		return p[a];
	}
	
}
