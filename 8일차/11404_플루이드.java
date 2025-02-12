import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

        static int V, E, adj[][], INF = 100000000;  // INF 값 증가

        public static void main(String[] args) throws IOException {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                StringTokenizer st = new StringTokenizer(br.readLine());

                V = Integer.parseInt(st.nextToken());
                E = Integer.parseInt(st.nextToken());  // 여기 수정!

                adj = new int[V+1][V+1];

                for (int i=1; i<=V; i++) {
                        for (int j=1; j<=V; j++) {
                                if (i == j) adj[i][j] = 0;
                                else adj[i][j] = INF;
                        }
                }

                for (int i=0; i<E; i++) {
                        st = new StringTokenizer(br.readLine());
                        int s = Integer.parseInt(st.nextToken());
                        int e = Integer.parseInt(st.nextToken());
                        int c = Integer.parseInt(st.nextToken());

                        adj[s][e] = Math.min(adj[s][e], c);
                }

                // 플로이드-워셜 알고리즘
                for (int k=1; k<=V; k++) {
                        for (int i=1; i<=V; i++) {
                                for (int j=1; j<=V; j++) {
                                        adj[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
                                }
                        }
                }

                // 결과 출력 최적화
                StringBuilder sb = new StringBuilder();
                for (int i=1; i<=V; i++) {
                        for (int j=1; j<=V; j++) {
                                if (adj[i][j] == INF) sb.append("0 ");
                                else sb.append(adj[i][j]).append(" ");
                        }
                        sb.append("\n");
                }
                System.out.print(sb);
        }
}