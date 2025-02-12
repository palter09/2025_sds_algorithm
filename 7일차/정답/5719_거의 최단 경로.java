import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int V, E, S, D, dist[], used[], INF=987654321;
	static ArrayList<ArrayList<int []>> e, re;
	static PriorityQueue<int[]> pq;
	static Queue<Integer> q;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		while (true) {
			st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			if (V == 0 && E == 0) {
				System.out.println(sb.toString());
				return;
			}
			e = new ArrayList<ArrayList<int[]>>(V);
			re = new ArrayList<ArrayList<int[]>>(V);
			dist = new int[V];
			used = new int[E];
			for (int i=0; i<V; i++) {
				e.add(new ArrayList<int[]>());
				re.add(new ArrayList<int[]>());
			}
			st = new StringTokenizer(br.readLine());
			S = Integer.parseInt(st.nextToken());
			D = Integer.parseInt(st.nextToken());
			int a, b, c;
			for (int i=0; i<E; i++) {
				st = new StringTokenizer(br.readLine());
				a = Integer.parseInt(st.nextToken());
				b = Integer.parseInt(st.nextToken());
				c = Integer.parseInt(st.nextToken());
				e.get(a).add(new int[] {b, c, i});
				re.get(b).add(new int[] {a, c, i});
			}
			dijkstra(S);
			bfs(D);
			dijkstra(S);
			if (dist[D] == INF) {
				sb.append("-1\n");
			} else {
				sb.append(dist[D]+ "\n");
			}
		}
	}
	static void bfs(int D) {
		q = new LinkedList<Integer>();
		q.add(D);
		int el, rnext, rw, ri;
		while (!q.isEmpty()) {
			el = q.poll();
			for (int i=0; i<re.get(el).size(); i++) {
				rnext = re.get(el).get(i)[0];
				rw = re.get(el).get(i)[1];
				ri = re.get(el).get(i)[2];
				if (used[ri] == 1) {
					continue;
				}
				if (dist[el] == dist[rnext] + rw) {
					used[ri] = 1;
					q.add(rnext);
				}
			}
		}
	}
	static void dijkstra(int S) {
		for (int i=0; i<V; i++) {
			dist[i] = INF;
		}
		pq = new PriorityQueue<int[]>((o1, o2) -> {
			return o1[1] - o2[1];
		});
		dist[S] = 0;
		pq.add(new int[] {S, 0});
		int el, w, next, cost, use, cur[];
		while (!pq.isEmpty()) {
			cur = pq.poll();
			el = cur[0];
			w = cur[1];
			for (int i=0; i<e.get(el).size(); i++) {
				next = e.get(el).get(i)[0];
				cost = e.get(el).get(i)[1];
				use = used[e.get(el).get(i)[2]];
				if (use == 0 && dist[next] > w + cost) {
					dist[next] = w + cost;
					pq.add(new int[] {next, w + cost});
				}
			}
		}
	}
}
