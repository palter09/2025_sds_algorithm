import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int V, E, adj[][], INF=100000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		V = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		E = Integer.parseInt(st.nextToken());
		adj = new int[V+1][V+1];
		for (int i=1; i<=V; i++) {
			for (int j=1; j<=V; j++) {
				adj[i][j] = INF;
				if (i == j) {
					adj[i][j] = 0;
				}
			}
		}
		int s, e, c;
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			adj[s][e] = Math.min(adj[s][e], c);
		}
		// k 경유지 i 시작점 j 도착점
		for (int k=1; k<=V; k++) {
			for (int i=1; i<=V; i++) {
				for (int j=1; j<=V; j++) {
					if (adj[i][k] != INF && adj[k][j] != INF) {
						adj[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
					}
				}
			}
		}
		
		for (int i=1; i<=V; i++) {
			for (int j=1; j<=V; j++) {
				if (adj[i][j] == INF) {
					System.out.print("0 ");
				}
				else {
					System.out.print(adj[i][j] + " ");
				}
			}
			System.out.println();
		}	
	}
}
