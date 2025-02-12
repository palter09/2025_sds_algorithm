import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int W, H, G, E;
	static final long INF = 1000000000000000000L;
	static ArrayList<int[]> rip;
	static ArrayList<int[]> edges;
	static int[][] dist;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();

		while (true) {
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			
			if (W == 0 && H == 0) {
				break;
			}
			
			G = Integer.parseInt(st.nextToken());
			rip = new ArrayList<>();
			
			for (int i=0; i<G; i++) {
				int X, Y;
				st = new StringTokenizer(br.readLine());
				X = Integer.parseInt(st.nextToken());
				Y = Integer.parseInt(st.nextToken());
				rip.add(new int[] {X, Y});
			}
			
			edges = new ArrayList<>();
			
			int dx[] = {1, -1, 0, 0};
			int dy[] = {0, 0, 1, -1};
			
			for (int i=0; i<H; i++) {
				for (int j=0; j<W; j++) {
					for (int d=0; d<4; d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						boolean flag = false;
						
						for (int k=0; k<G; k++) {
							if (nx == rip.get(k)[0] || ny == rip.get(k)[1]) {
								flag = true;
							}
						}
						
						if (nx < 0 || ny < 0 || nx >= H || ny >= W || flag) continue;
						
						edges.add(new int[] {i, j, nx, ny, 1});
					}
				}
			}
			
			E = Integer.parseInt(st.nextToken());
			
			for (int i=0; i<E; i++) {
				int X1, X2, Y1, Y2, T;
				st = new StringTokenizer(br.readLine());
				X1 = Integer.parseInt(st.nextToken());
				Y1 = Integer.parseInt(st.nextToken());
				X2 = Integer.parseInt(st.nextToken());
				Y2 = Integer.parseInt(st.nextToken());
				T = Integer.parseInt(st.nextToken());
				
				edges.add(new int[] {X1, Y1, X2, Y2, T});
			}
			
			dist = new int [H][W];
			Arrays.fill(dist, INF);
			dist[0][0] = 0;
			for (int i=0; i<H; i++) {
				for (int j=0; j<W; j++) {
					int s_x = edges.get(j)[0];
					int s_y = edges.get(j)[1];
					int e_x = edges.get(j)[2];
					int e_y = edges.get(j)[3];
					int t = edges.get(j)[4];
					
					if (dist[s_x][s_y] == INF) {
						continue;
					}
					
					if (dist[e_x][e_y] > dist[s_x][s_y] + t) {
						dist[e_x][e_y] = dist[s_x][s_y] + t;
					}
				}
			}
			
			boolean negativeCycle = false;
		}
		
		System.out.println(sb.toString());
	}
}
