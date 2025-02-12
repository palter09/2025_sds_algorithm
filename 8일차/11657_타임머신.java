import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static final long INF = 1000000000000000000L;  // 충분히 큰 값
    static long[] dist;
    static ArrayList<int[]> edges;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dist = new long[N + 1];
        Arrays.fill(dist, INF); // 모든 정점을 INF로 초기화
        edges = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            edges.add(new int[] { s, e, c });
        }

        dist[1] = 0;
        boolean updated;

        // Bellman-Ford: N-1번 완화
        for (int i = 1; i <= N - 1; i++) {
            updated = false;
            for (int[] edge : edges) {
                int s = edge[0], e = edge[1], c = edge[2];
                if (dist[s] != INF && dist[e] > dist[s] + c) {
                    dist[e] = dist[s] + c;
                    updated = true;
                }
            }
            if (!updated) break; // 더 이상 업데이트가 없으면 종료
        }

        boolean negativeCycle = false;
        // 음의 사이클이 존재하는지 체크
        for (int[] edge : edges) {
            int s = edge[0], e = edge[1], c = edge[2];
            if (dist[s] != INF && dist[e] > dist[s] + c) {
                negativeCycle = true;
                break;
            }
        }

        if (negativeCycle) {
            sb.append("-1");
        } else {
            for (int i = 2; i <= N; i++) {
                sb.append(dist[i] == INF ? "-1" : dist[i]).append("\n");
            }
        }

        System.out.print(sb.toString());
    }
}