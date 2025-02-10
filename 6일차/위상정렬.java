import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	/**
입력
5 7
1 2
1 3
1 4
2 5
3 4
4 2
4 5
	 */
	static int V, E, ind[];
	static ArrayList<ArrayList<Integer>> edges;
	static Queue<Integer> q;
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		edges = new ArrayList<>();
		ind = new int[V+1];
		for (int i=0; i<=V; i++) {
			edges.add(new ArrayList<>());
		}
		int s, e;
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			edges.get(s).add(e);
			ind[e]++;
		}
		q = new LinkedList<Integer>();
		for (int i=1; i<=V; i++) {
			if (ind[i] == 0) {
				q.add(i);
			}
		}
		int cur, next;
		while (!q.isEmpty()) {
			cur = q.poll();
			System.out.println(cur);
			for (int i=0; i<edges.get(cur).size(); i++) {
				next = edges.get(cur).get(i);
				if (--ind[next] == 0) {
					q.add(next);
				}
			}
		}
		
		
	}
}

