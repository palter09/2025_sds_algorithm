import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, K, a[][], d[][];
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		int w, v;
		a = new int[N+1][2];
		d = new int[N+1][K+1];
		for (int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			a[i+1][0] = Integer.parseInt(st.nextToken());
			a[i+1][1] = Integer.parseInt(st.nextToken());
		}
		// d[i][k] = i번째 가방까지 k 무게만큼 사용가능할때의 최대값
		for (int i=1; i<=N; i++) {
			for (int k=0; k<=K; k++) {
				d[i][k] = d[i-1][k];
				if (a[i][0]+k <= K) {
					d[i][k] = Math.max(d[i][k], d[i-1][k+a[i][0]] + a[i][1]);
				}
			}
		}
		int ans=0;
		for (int i=0; i<=K; i++) {
			ans = Math.max(ans, d[N][i]);
		}
		System.out.println(ans);
//		System.out.println(solve(1, K));
	}
	static int solve(int i, int k) {
		if (i > N) {
			return 0;
		}
		int ret = d[i][k];
		if (ret != 0) {
			return ret;
		}
		ret = Math.max(ret, solve(i+1, k));
		if (a[i][0] <= k) {
			ret = Math.max(ret, solve(i+1, k-a[i][0])+a[i][1]);
		}
		d[i][k] = ret;
		return ret;
	}
}
