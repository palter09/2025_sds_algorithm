import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(st.nextToken());
		int pCount = 0;
		int[] prime = new int[N+1];
		for (int i=2; i<=N; i++) {
			if (prime[i]==1) {
				continue;
			}
			pCount++;
			for (int j=i*2; j<=N; j+=i) {
				prime[j] = 1;
			}
		}
		int[] d = new int[pCount+1];
		int p = 1;
		for (int i=2; i<=N; i++) {
			if (prime[i] == 0) {
				d[p] = d[p-1] + i;
				p++;
			}
		}
		int left = 0;
		int right = 1;
		int cnt = 0;
		while (left <= pCount && right <= pCount &&  left < right) {
			int val = d[right] - d[left];
			if (val == N) {
				cnt++;
			}
			if (val < N) {
				right++;
			} else {
				left++;
			}
		}
		System.out.println(cnt);
	}

}
