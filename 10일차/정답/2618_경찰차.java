import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N, W;
	static int[][] wPos, d;
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		W = Integer.parseInt(br.readLine());
		wPos = new int[W+1][2];
		// d[i][j] = i가 해결한 사건번호, j가 해결한사건번호에서 최소 비용
		d = new int[W+1][W+1];
		
		for (int i=0; i<=W; i++) {
			for (int j=0; j<=W; j++) {
				d[i][j] = -1;
			}
		}
		for (int i=1; i<=W; i++) {
			st = new StringTokenizer(br.readLine());
			wPos[i][0] = Integer.parseInt(st.nextToken());
			wPos[i][1] = Integer.parseInt(st.nextToken());
		}
	
		int ans = solve(0, 0);
		sb.append(ans+"\n");
		int w = 0, i = 0, j = 0;
		int x1 = 1, y1 = 1, x2 = N, y2 = N;
		while (w < W) {
			w++;
			int dist1 = dist(x1, y1, wPos[w][0], wPos[w][1]) + d[w][j];
			int dist2 = dist(x2, y2, wPos[w][0], wPos[w][1]) + d[i][w];
			if (dist1 < dist2) {
				i = w;
				x1 = wPos[w][0];
				y1 = wPos[w][1];
				sb.append("1\n");
			} else {
				j = w;
				x2 = wPos[w][0];
				y2 = wPos[w][1];
				sb.append("2\n");
			}
		}
		System.out.println(sb.toString());

	}
	static int solve(int i, int j) {
		if (i == W || j == W) {
			return 0;
		}
		if (d[i][j] != -1) {
			return d[i][j];
		}
		int nextW = Math.max(i, j)+1;
		// 1번차가 다음사건을 해결
		int x1, y1, x2, y2, cost1, cost2;
		x1 = wPos[i][0];
		y1 = wPos[i][1];
		x2 = wPos[nextW][0];
		y2 = wPos[nextW][1];
		if (i==0) {
			x1 = 1;
			y1 = 1;
		}
		cost1 = solve(nextW, j)+dist(x1, y1, x2, y2);
		// 2번차가 다음사건을 해결
		x1 = wPos[j][0];
		y1 = wPos[j][1];
		x2 = wPos[nextW][0];
		y2 = wPos[nextW][1];
		if (j==0) {
			x1 = N;
			y1 = N;
		}
		cost2 = solve(i, nextW)+dist(x1, y1, x2, y2);
		return d[i][j] = Math.min(cost1, cost2);
	}
	static int dist(int x1, int y1, int x2, int y2) {
		return Math.abs(y1-y2)+Math.abs(x1-x2);
	}
}
