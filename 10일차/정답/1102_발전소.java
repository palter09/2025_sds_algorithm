import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N, P, INF;
	static int W[][];
	static int D[];
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		W = new int[N][N];
		D = new int[1<<N];
		for (int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j=0; j<N; j++) {
				W[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		for (int i=0; i<1<<N; i++) {
			D[i] = -1;
		}
		int start = 0;
		String yn = br.readLine();
		for (int i=0; i<yn.length(); i++) {
			if (yn.charAt(i) == 'Y') {
				start |= (1 << i);
			}
		}
		P = Integer.parseInt(br.readLine());
		if (P==0) {
			System.out.println(0);
			return;
		}
		INF = 987654321;
		int ans = solve(start);
		System.out.println(ans == INF ? "-1" : ans);
	}
	static int solve(int visited) {
		if (Integer.bitCount(visited) >= P) {
			return 0;
		}
		if (D[visited] != -1) {
			return D[visited];
		}
		D[visited] = INF;
		// 현재상태에서 켜져있는 도시들에 대해서 모두 탐색
		for (int i=0; i<N; i++) {
			if ((visited & (1<<i)) == 0) {
				continue;
			}
			for (int j=0; j<N; j++) {
				if ((visited & (1<<j)) != 0) {
					continue;
				}
				D[visited] = Math.min(D[visited], solve(visited|(1<<j)) + W[i][j]);
			}
		}
		return D[visited];
	}

}
