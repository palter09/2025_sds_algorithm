
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
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
 */
public class Main {
	
	static int V, E, dist[], INF=99999999;
	static ArrayList<ArrayList<int[]>> edges;
	static PriorityQueue<int[]> pq;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		edges = new ArrayList<>();
		dist = new int[V+1];
		for (int i=0; i<=V; i++) {
			edges.add(new ArrayList<>());
			dist[i] = INF;
		}
		int s, e, c;
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			edges.get(s).add(new int[] {e, c});
		}
		pq = new PriorityQueue<>((o1, o2) -> {
			return o1[1] - o2[1];
		});
		pq.add(new int[] {1, 0});
		dist[1] = 0;
		int cur, curW, next, nextW;
		while (!pq.isEmpty()) {
			cur = pq.peek()[0];
			curW = pq.peek()[1];
			pq.poll();
			
			for (int i=0; i<edges.get(cur).size(); i++) {
				next = edges.get(cur).get(i)[0];
				nextW = edges.get(cur).get(i)[1];
				if (dist[next] > curW + nextW) {
					dist[next] = curW+nextW;
					pq.add(new int[] {next, curW+nextW});
				}
			}
		}
		for (int i=1; i<=V; i++) {
			System.out.println(dist[i] + " ");
		}	
	}
}
