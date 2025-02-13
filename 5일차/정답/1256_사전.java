import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, M, K;
    static int INF = 1000000000;
    static int[][] d;
    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        d = new int[N+M+1][N+M+1];
        d[0][0] = 1;
        for (int i=1; i<=N+M; i++) {
            d[i][0] = 1;
            for (int j=1; j<=N; j++) {
                d[i][j] = d[i-1][j-1]+d[i-1][j];
                if (d[i][j] > INF) {
                    d[i][j] = INF+1;
                }
            }
        }
        if (d[N+M][N] < K) {
            System.out.println(-1);
            return;
        }
        int n = N;
        for (int i=0; i<N+M; i++) {
            if (n==0) {
                sb.append("z");
                continue;
            }
            if (d[N+M-i-1][n-1] < K) {
                sb.append("z");
                K -= d[N+M-i-1][n-1];
            } else {
                sb.append("a");
                n--;
            }
        }
        System.out.println(sb.toString());
    }
}
