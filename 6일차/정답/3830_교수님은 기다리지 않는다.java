import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N, M;
	static int[] parent;
	static int[] diff;

	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int a, b, w, A, B;
		while (true) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			if (N == 0 && M == 0) {
				System.out.println(sb.toString());
				return;
			}
			parent = new int[N+1];
			diff = new int[N+1];
			for (int i=1; i<=N; i++) {
				parent[i] = i;
			}		
			for (int i=0; i<M; i++) {
				st = new StringTokenizer(br.readLine());
				if (st.nextToken().charAt(0) == '!') {
					a = Integer.parseInt(st.nextToken());
					b = Integer.parseInt(st.nextToken());
					w = Integer.parseInt(st.nextToken());
					union(a, b, w);
				} else {
					a = Integer.parseInt(st.nextToken());
					b = Integer.parseInt(st.nextToken());
					A = find(a);
					B = find(b);
					if (A != B) {
						sb.append("UNKNOWN\n");
					} else {
						sb.append((diff[b]-diff[a])+"\n");
					}
				}
			}
		}
	}
	static void union(int a, int b, int w) {
		int A = find(a);
		int B = find(b);
		if (A != B) {
			diff[B] = diff[a]-diff[b]+w;
			parent[B] = A;
		}
	}
	static int find(int a) {
		if (parent[a] == a) {
			return a;
		}
		int p = find(parent[a]);
		diff[a] += diff[parent[a]];
		parent[a] = p;
		return parent[a];
	}
}

