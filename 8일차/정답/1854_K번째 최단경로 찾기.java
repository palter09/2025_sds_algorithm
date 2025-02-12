import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	static int V, E, K;
	static ArrayList<ArrayList<int[]>> e;
	static PriorityQueue<int[]> pq;
	static PriorityQueue<Integer>[] distPq;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		e = new ArrayList<ArrayList<int[]>>();
		for (int i=0; i<=V; i++) {
			e.add(new ArrayList<int[]>());
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
		distPq = new PriorityQueue[V+1];
		for (int i=0; i<=V; i++) {
			distPq[i] = new PriorityQueue<Integer>((o1, o2) -> {
				return o2 - o1;
			});
		}
		pq.add(new int[] {1, 0});
		distPq[1].add(0);
		int el, w, next, cost, cur[];
		while (!pq.isEmpty()) {
			cur = pq.poll();
			el = cur[0];
			w = cur[1];
			for (int i=0; i<e.get(el).size(); i++) {
				next = e.get(el).get(i)[0];
				cost = e.get(el).get(i)[1];
				if (distPq[next].size() < K) {
					distPq[next].add(cost + w);
					pq.add(new int[] {next, cost + w});
				} else {
					if (distPq[next].size() == K && distPq[next].peek() > (cost+w)) {
						distPq[next].poll();
						distPq[next].add(cost + w);
						pq.add(new int[] {next, cost + w});
					}
				}
			}
		}
		for (int i=1; i<=V; i++) {
			if (distPq[i].size() != K) {
				sb.append("-1\n");
			} else {
				sb.append(distPq[i].peek()+"\n");
			}
		}
		System.out.println(sb.toString());
	}
}
