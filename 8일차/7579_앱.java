import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static int N, M, a[][], d[], INF=10000000;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		a = new int[N+1][2];
		d = new int[102];
		
		Arrays.fill(d,  0);
		
		st = new StringTokenizer(br.readLine());
		for (int i=1; i<=N; i++) {
			a[i][0] = Integer.parseInt(st.nextToken());
		}
		
		st = new StringTokenizer(br.readLine());
		for (int i=1; i<=N; i++) {
			a[i][1] = Integer.parseInt(st.nextToken());
		}
		int ans = 0;
		boolean flag = false;
		for (int i=0; i<=100; i++) {
			for (int j=1; j<=N; j++) {
				if (i >= a[j][1]) {
					d[i] = Math.max(d[i], d[i-a[j][1]] + a[j][0]);
					//System.out.println("i : " + i);
					//System.out.println("d[i] : " + d[i] + "\n");
				}
				if (d[i] >= M) {
					flag = true;
					ans = i;
					break;
					
				}
			}
			if (flag) {
				break;
			}
		}
		
		System.out.print(ans);
	}
}
