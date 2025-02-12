import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N, M, K, f[], parent[][], ans[];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		f = new int[N+1];
		ans = new int[N+1];
		parent = new int[31][M+1];
		st = new StringTokenizer(br.readLine());
		for (int i=1; i<=N; i++) {
			f[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for (int i=1; i<=M; i++) {
			parent[0][i] = Integer.parseInt(st.nextToken()); 
		}
		for (int i=1; i<=30; i++) {
			for (int j=1; j<=M; j++) {
				parent[i][j] = parent[i-1][parent[i-1][j]];
			}
		}
		for (int i=1; i<=N; i++) {
			int num = f[i];
			int k = K-1;
			for (int j=30; j>=0; j--) {
				if (k >= (1 << j)) {
					num = parent[j][num];
					k -= (1 << j);
				}
			}
			sb.append(num+" ");
		}
		System.out.println(sb.toString());
		
	}
}
