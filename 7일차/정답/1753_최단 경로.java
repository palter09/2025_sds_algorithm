import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	static int V, E, S, dist[], INF=987654321;
	static ArrayList<ArrayList<int []>> e;
	static PriorityQueue<int[]> pq;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(br.readLine());
		e = new ArrayList<ArrayList<int[]>>();
		dist = new int[V+1];
		for (int i=0; i<=V; i++) {
			e.add(new ArrayList<int[]>());
			dist[i] = INF;
		}
		int a, b, c;
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			e.get(a).add(new int[] {b, c});
		}
		pq = new PriorityQueue<int[]>((o1, o2) -> {
			return o1[1] - o2[1];
		});
		dist[S] = 0;
		pq.add(new int[] {S, 0});
		int cur[], el, cost, next, w;
		while (!pq.isEmpty()) {
			cur = pq.poll();
			el = cur[0];
			cost = cur[1];
			for (int i=0; i<e.get(el).size(); i++) {
				next = e.get(el).get(i)[0];
				w = e.get(el).get(i)[1];
				if (dist[next] > cost + w) {
					dist[next] = cost + w;
					pq.add(new int[] {next, cost+w});
				}
			}
		}
		for (int i=1; i<=V; i++) {
			if (dist[i] == INF) {
				sb.append("INF\n");
			} else {
				sb.append(dist[i]+"\n");
			}
		}
		System.out.println(sb.toString());
	}
}
