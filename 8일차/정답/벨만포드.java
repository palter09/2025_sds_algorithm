import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
7 10
1 3 5
1 4 3
1 2 7
3 5 3
3 4 3
4 5 9
4 7 7
5 3 2
5 7 1
6 2 3
6 4 1
6 7 7

5 8
1 3 5
1 4 3
1 2 7
3 5 3
3 4 3
4 2 3
4 5 -6
5 3 2
 */
public class Main {

	static int V, E, dist[], INF=98765;
	static ArrayList<int[]> edges;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();

		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		dist = new int[V+1];
		edges = new ArrayList<>();
		for (int i=1; i<=V; i++) {
			dist[i] = INF;
		}
		int s, e, c;
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			edges.add(new int[] {s, e, c});
		}
		dist[1] = 0;
		for (int i=0; i<V-1; i++) {
			for (int j=0; j<E; j++) {
				s = edges.get(j)[0];
				e = edges.get(j)[1];
				c = edges.get(j)[2];
				if (dist[s] == INF) {
					continue;
				}
				if (dist[e] > dist[s] + c) {
					dist[e] = dist[s] + c;
				}
			}
		}
		boolean flag = false;
		for (int j=0; j<E; j++) {
			s = edges.get(j)[0];
			e = edges.get(j)[1];
			c = edges.get(j)[2];
			if (dist[s] == INF) {
				continue;
			}
			if (dist[e] > dist[s] + c) {
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(-1);
		} else {
			for (int i=1; i<=V; i++) {
				System.out.print(dist[i] + " ");
			}
		}	
	}
}
