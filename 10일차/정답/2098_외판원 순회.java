import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, adj[][], d[][], INF=9876543;
    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        adj = new int[N][N];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<N; j++) {
                adj[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // d[current][visited] = current 도시에 visited 상태로 방문했을때의 앞으로의 최소비용
        d = new int[N][1<<N];
        System.out.println(tsp(0, 1));
    }
    static int tsp(int current, int visited) {
        // 종료 조건 (모든 도시를 탐색)
        if (visited == (1<<N)-1) {
            if (adj[current][0] == 0) {
                return INF;
            }
            return adj[current][0];
        }
        if (d[current][visited] != 0) {
            return d[current][visited];
        }
        int ret = INF;
        // 도시들을 방문
        for (int i=0; i<N; i++) {
            // 현재 visited 기준으로 가보지 않은 도시 탐색
            if (adj[current][i] != 0 && (visited & (1<<i)) == 0) {
                int t = tsp(i, visited | (1<<i)) + adj[current][i];
                ret = Math.min(ret, t);
            }
        }
        d[current][visited] = ret;
        return ret;
    }
}
