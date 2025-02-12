import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

	static int N, M, INF=98765;
	static long dist[];
	static ArrayList<int[]> edges;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		dist = new long[N+1];
		edges = new ArrayList<>();
		for (int i=1; i<=N; i++) {
			dist[i] = INF;
		}
		int s, e, c;
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			edges.add(new int[] {s, e, c});
		}
		dist[1] = 0;
		for (int i=0; i<N-1; i++) {
			for (int j=0; j<M; j++) {
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
		for (int j=0; j<M; j++) {
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
			sb.append("-1" + "\n");
		} else {
			for (int i=2; i<=N; i++) {
				if (dist[i] != INF) {
					sb.append(dist[i] + "\n");
				}
				else {
					sb.append("-1" + "\n");
				}	
			}
		}
		System.out.println(sb.toString());
	}
}
