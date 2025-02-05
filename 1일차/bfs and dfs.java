import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int V, E, visited[];
	static ArrayList<ArrayList<Integer>> edge;
	static Queue<Integer> q;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		V = 7;
		visited = new int[8]; // 기본값 0, 방문한적X -> 0 방문한적 -> 1
		E = 8;
		edge = new ArrayList<>();
		for (int i=0; i<=V; i++) {
			edge.add(new ArrayList<>());
		}
		int a, b;
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			edge.get(a).add(b);
			edge.get(b).add(a);
		}
//		visited[1] = 1;
//		dfs(1);
		q = new LinkedList<>();
		visited[1] = 1;
		q.add(1);
		int cur, next;
		while (!q.isEmpty()) {
			cur = q.poll();
			System.out.println(cur);
			for (int i=0; i<edge.get(cur).size(); i++) {
				next = edge.get(cur).get(i);
				if (visited[next] == 0) {
					visited[next] = 1;
					q.add(next);
				}
			}
		}
	}
	static void dfs(int node) {
		System.out.println(node);
		int next;
		for (int i=0; i<edge.get(node).size(); i++) {
			next = edge.get(node).get(i);
			if (visited[next] == 0) {
				visited[next] = 1;
				dfs(next);
			}
		}
	}
}
