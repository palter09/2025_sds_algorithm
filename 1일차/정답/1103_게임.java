import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, M, ans;
    static int[][] map;
    static int[][] visited;
    static int[][] cache;
    static boolean loop;

    static int[] dy = new int[] {1, -1, 0, 0};
    static int[] dx = new int[] {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        visited = new int[N][M];
        cache = new int[N][M];

        for (int n = 0; n < N; n++) {
            String[] s = br.readLine().split("");
            for (int m = 0; m < M; m++) {
                if (s[m].equals("H")) {
                    map[n][m] = -1;
                } else {
                    map[n][m] = Integer.parseInt(s[m]);
                }
            }
        }
        loop = false;
        ans = 0;
        visited[0][0] = 1;
        dfs(0, 0, 1);
        System.out.println(loop ? -1 : ans);
    }
    static void dfs(int y, int x, int count) {
        if (loop) {
            return;
        }
        ans = Math.max(ans, count);
        cache[y][x] = count;

        for (int d = 0; d < 4; d++) {
            int ny = y + (dy[d] * map[y][x]);
            int nx = x + (dx[d] * map[y][x]);

            if (ny >= 0 && nx >=0 && ny < N && nx < M && map[ny][nx] > 0) {
                if (visited[ny][nx] == 1) {
                    loop = true;
                    return;
                }
                if (cache[ny][nx] >= count + 1) {
                    continue;
                }
                visited[ny][nx] = 1;
                dfs(ny, nx, count + 1);
                visited[ny][nx] = 0;
            }
        }
        return;
    }

}
