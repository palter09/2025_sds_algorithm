import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    static int N, H, sy, sx;
    static char[][] map;
    static int[][] visited;
    static int[][] heights;
    static Integer[] alts;
    static int[] dy = new int[] {1, 1, 1, 0, -1, -1, -1, 0};
    static int[] dx = new int[] {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        H = 0;
        map = new char[N][N];
        heights = new int[N][N];
        visited = new int[N][N];
        for (int i = 0; i < N; i++) {
            char[] c = br.readLine().toCharArray();
            for (int j = 0; j < N; j++) {
                map[i][j] = c[j];
                if (c[j] == 'K') {
                    H++;
                }
                if (c[j] == 'P') {
                    sy = i;
                    sx = j;
                }
            }
        }
        Set<Integer> s = new HashSet<Integer>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                heights[i][j] = Integer.parseInt(st.nextToken());
                s.add(heights[i][j]);
            }
        }
        alts = s.toArray(new Integer[0]);
        Arrays.sort(alts);

        int left = 0;
        int right = 0;
        int ans = Integer.MAX_VALUE;

        while(left <= right && left < alts.length && right < alts.length) {
            int low  = alts[left];
            int high = alts[right];

            if (heights[sy][sx] < low || heights[sy][sx] > high) {
                right++;
                continue;
            }
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    visited[i][j] = 0;
                }
            }
            if (dfs(sy, sx, low, high) == H) {
                ans = Math.min(ans, high - low);
                left++;
            } else {
                right++;
            }
        }
        System.out.println(ans);
    }
    static int dfs(int y, int x, int low, int high) {
        int hCount = 0;
        for (int d = 0; d < 8; d++) {
            int ny = y + dy[d];
            int nx = x + dx[d];
            if (ny >= 0 && nx >=0 && ny < N && nx < N) {
                if (visited[ny][nx] == 0 && heights[ny][nx] >= low && heights[ny][nx] <= high) {
                    if (map[ny][nx] == 'K') {
                        hCount++;
                    }
                    visited[ny][nx] = 1;
                    hCount += dfs(ny, nx, low, high);
                }

            }
        }
        return hCount;
    }
}
