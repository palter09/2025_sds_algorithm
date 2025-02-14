import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N, matrix[][], d[][], INF=Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		matrix = new int[N+1][2];
		d = new int[N+1][N+1];
		for (int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			matrix[i][0] = Integer.parseInt(st.nextToken());
			matrix[i][1] = Integer.parseInt(st.nextToken());
		}
		for (int i=1; i<N; i++) {
			d[i][i+1] = matrix[i][0] * matrix[i][1] * matrix[i+1][1];
		}

		for (int gap=2; gap<N; gap++) {
			for (int start=1; start+gap<=N; start++) {
				int end = start + gap;
				d[start][end] = INF;
				for (int k=start; k<end; k++) {
					d[start][end] = Math.min(d[start][end],
							d[start][k]+d[k+1][end]+matrix[start][0]*matrix[k][1]*matrix[end][1]);
				}
			}
		}

		System.out.println(d[1][N]);
	}
}
