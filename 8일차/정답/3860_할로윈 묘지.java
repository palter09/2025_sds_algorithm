import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	
	static int W, H, G, E, map[][], dist[][], INF=Integer.MAX_VALUE;
	static ArrayList<int[]> e;
	static int[] dy = new int[] {1, -1, 0, 0};
	static int[] dx = new int[] {0, 0, 1, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		while (true) {
			st = new StringTokenizer(br.readLine());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			if (W==0 && H==0) {
				return;
			}
			G = Integer.parseInt(br.readLine());
			map = new int[H][W];
			dist = new int[H][W];
			e = new ArrayList<int[]>();
			int x1, y1, x2, y2, t;
			for (int i=0; i<G; i++) {
				st = new StringTokenizer(br.readLine());
				x1 = Integer.parseInt(st.nextToken());
				y1 = Integer.parseInt(st.nextToken());
				map[y1][x1] = -1;
			}
			E = Integer.parseInt(br.readLine());
			for (int i=0; i<E; i++) {
				st = new StringTokenizer(br.readLine());
				x1 = Integer.parseInt(st.nextToken());
				y1 = Integer.parseInt(st.nextToken());
				x2 = Integer.parseInt(st.nextToken());
				y2 = Integer.parseInt(st.nextToken());
				t = Integer.parseInt(st.nextToken());
				map[y1][x1] = 1;
				e.add(new int[] {y1, x1, y2, x2, t});
			}
			int ni, nj;
			for (int i=0; i<H; i++) {
				for (int j=0; j<W; j++) {
					dist[i][j] = INF;
					if (i == (H-1) && j == (W-1)) {
						continue;
					}
					if (map[i][j] != 0) {
						continue;
					}
					for (int d=0; d<4; d++) {
						ni = i + dy[d];
						nj = j + dx[d];
						if (ni >= 0 && nj >= 0 && ni < H && nj < W && map[ni][nj] != -1) {
							e.add(new int[] {i, j, ni, nj, 1});
						}
					}
				}
			}
			dist[0][0] = 0;
			for (int i=0; i<(H*W)-G; i++) {
				for (int j=0; j<e.size(); j++) {
					y1 = e.get(j)[0];
					x1 = e.get(j)[1];
					y2 = e.get(j)[2];
					x2 = e.get(j)[3];
					t = e.get(j)[4];
					if (dist[y1][x1] == INF) {
						continue;
					}
					dist[y2][x2] = Math.min(dist[y2][x2], dist[y1][x1]+t);
				}
			}
			boolean flag = false;
			for (int j=0; j<e.size(); j++) {
				y1 = e.get(j)[0];
				x1 = e.get(j)[1];
				y2 = e.get(j)[2];
				x2 = e.get(j)[3];
				t = e.get(j)[4];
				if (dist[y1][x1] == INF) {
					continue;
				}
				if (dist[y2][x2] > dist[y1][x1]+t) {
					flag = true;
				}
			}
			if (flag) {
				System.out.println("Never");
				continue;
			}
			if (dist[H-1][W-1] == INF) {
				System.out.println("Impossible");
				continue;
			}
			System.out.println(dist[H-1][W-1]);
		}
	}
}
